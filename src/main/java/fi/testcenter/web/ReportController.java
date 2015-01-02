package fi.testcenter.web;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.ReportTemplate;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.ReportTemplateService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "reportTemplate", "report", "formAnswers",
		"workshops", "readyReport", "addOptionalToQuestionGroup", "importers",
		"addQuestionToReportPart", "optionalQuestionsAnswerIndex",
		"editReportPartNumber", "addQuestionsToOptionalAnswer",
		"optionalQuestionsAnswer" })
public class ReportController {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@Autowired
	private ReportTemplateService rts;

	@Autowired
	private ReportService rs;

	@Autowired
	private UserAccountService us;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm() {

		return "start";
	}

	@RequestMapping(value = "/admin/reportsSummary", method = RequestMethod.GET)
	public String summary(Model model) {

		List<Report> reports = rs.findAllReports();
		ReportTemplate template = rts
				.findReportTemplateByName("Autoasi raporttipohja");

		model.addAttribute("reportsList", reports);
		model.addAttribute("template", template);

		return "report/reportsSummary";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.GET)
	public String prepareNewReportBasicInfoForm(Model model) {

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		return "report/newReportSelectImporter";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.POST)
	public String submitNewReportBasicInfo(Model model,
			@RequestParam("importerID") Integer importerID) {

		Report report = new Report();
		Importer importer = is.finImporterById(importerID.longValue());
		if (importer.getReportTemplateName() == null
				|| importer.getReportTemplateName().isEmpty()) {
			model.addAttribute("alertMessage",
					"Maahantuojalle ei ole valittu raporttipohjaa");
			return "report/newReportSelectImporter";
		}
		try {
			report = new Report(rts.findReportTemplateByName(importer
					.getReportTemplateName()), rs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		report.setUser(us.findLoginUser());
		report.setImporter(importer);
		report.setImporterId(importerID.longValue());

		model.addAttribute("report", report);

		return "redirect:/prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(Model model,
			@ModelAttribute("report") Report report) {
		model.addAttribute("report", report);

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);
		model.addAttribute("initialAnswerIndexCounter", 0);

		model.addAttribute("editReportPartNumber", 0);
		return "report/editReport";
	}

	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public String submitReport(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("report") Report report,
			@RequestParam("navigateToReportPart") Integer navigateToReportPart,
			@RequestParam("optionalQuestionsAnswerIndex") Integer optionalQuestionsAnswerIndex,
			@ModelAttribute("editReportPartNumber") Integer addOptionalToReportPart,
			@RequestParam("addOptionalToQuestionGroup") Integer addOptionalToQuestionGroup,
			BindingResult result) {

		String auto = ((TextAnswer) report.getReportParts().get(0)
				.getReportQuestionGroups().get(0).getAnswers().get(0))
				.getAnswer();
		model.addAttribute("auto", auto);
		report.setWorkshop(ws.findWorkshop(report.getWorkshopId()));
		report.checkReportHighlights();
		report.setRemovedQuestions(); // Nollataan vastaukset
										// MultipleChoiceQuestion- ja
										// PointsQuestion-luokan kysymyksiin
										// jotta ei huomioida pisteytyksessä.
		report = report.calculateReportScore(rs);

		for (ReportPart part : report.getReportParts()) {
			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				for (Answer answer : group.getAnswers()) {
					if (answer instanceof CostListingAnswer)
						((CostListingAnswer) answer).formatCurrencies();

				}
			}
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		try {
			report.setDate(simpleDateFormat.parse(report.getReportDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			report = rs.saveReport(report);

		} catch (Exception e) {
			e.printStackTrace();

		}

		// Jos käyttäjä on clickannut navigointia toisen raportinosan
		// muokkaukseen, asetetaan tallennuksen jälkeen tarvittavat muuttujat ja
		// palataan
		// editReport.jsp

		if (navigateToReportPart != null) {

			model.addAttribute("report", report);
			model.addAttribute("editReportPartNumber", navigateToReportPart);

			return "report/editReport";

		} else if (optionalQuestionsAnswerIndex != null) {

			// Asetetaan JSP:tä varten chosenQuestions-muuttuujaan
			// aikaisemmin
			// valitut valinnaiset kysymykset
			OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) report
					.getReportParts().get(addOptionalToReportPart)
					.getReportQuestionGroups().get(addOptionalToQuestionGroup)
					.getAnswers().get(optionalQuestionsAnswerIndex);
			List<Question> optionalQuestions = ((OptionalQuestions) oqa
					.getQuestion()).getQuestions();

			int[] oldQuestions = new int[0];
			if (oqa.getQuestions() != null) {

				for (Question question : oqa.getQuestions()) {

					int index = optionalQuestions.indexOf(question);
					oldQuestions = Arrays.copyOf(oldQuestions,
							oldQuestions.length + 1);
					oldQuestions[oldQuestions.length - 1] = index;
				}
			}

			ChosenQuestions chosenQ = new ChosenQuestions();
			chosenQ.setChosenQuestions(oldQuestions);
			model.addAttribute("addQuestionsToOptionalAnswer", oqa);
			model.addAttribute("chosenQuestions", chosenQ);
			model.addAttribute("readyReport", report);
			model.addAttribute("report", report);
			model.addAttribute("editReportPartNumber", addOptionalToReportPart);
			model.addAttribute("addOptionalToQuestionGroup",
					addOptionalToQuestionGroup);
			model.addAttribute("optionalQuestionsAnswerIndex",
					optionalQuestionsAnswerIndex);

			model.addAttribute("optionalQuestions", optionalQuestions);

			return "report/addOptionalQuestions";
		}

		else {

			model.addAttribute("readyReport", report);
			model.addAttribute("report", report);

			if (report.isSmileysSet() == false) {

				model.addAttribute("editSmileys", false);
				if (report.getReportStatus() == "DRAFT"
						|| report.getReportStatus() == "AWAIT_APPROVAL")
					model.addAttribute("editSmileys", true);
				if (report.getReportStatus() == "APPROVED"
						&& request.isUserInRole("ROLE_ADMIN"))
					model.addAttribute("editSmileys", true);

			} else
				model.addAttribute("editSmileys", false);

			return "report/showReport";
		}
	}

	@RequestMapping(value = "/addChosenQuestions", method = RequestMethod.POST)
	public String addChosenQuestions(
			Model model,
			@ModelAttribute("chosenQuestions") ChosenQuestions chosenQuestions,
			@ModelAttribute("report") Report report,
			@ModelAttribute("addQuestionsToOptionalAnswer") OptionalQuestionsAnswer optionalQuestionsAnswer) {

		optionalQuestionsAnswer.addOptionalQuestions(chosenQuestions, rs);

		try {
			report = rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("report", report);
		model.addAttribute("initialAnswerIndexCounter", 0);
		model.addAttribute("editReportPartNumber", 0);
		return "report/editReport";
	}

	@RequestMapping(value = "/submitReportForApproval", method = RequestMethod.GET)
	public String submitReportForApproval(Model model,
			@ModelAttribute("report") Report report) {

		report.setReportStatus("AWAIT_APPROVAL");
		model.addAttribute("report", report);

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "report/showReport";
	}

	@RequestMapping(value = "/saveSmileysAndHighlights", method = RequestMethod.POST)
	public String saveSmileyAndHighlights(Model model,
			@ModelAttribute("readyReport") Report formReport,
			@ModelAttribute("report") Report report) {

		report.checkReportHighlights();
		if (report.getOverallResultSmiley() != null
				&& report.getOverallResultSmiley() != "") {
			report.setSmileysSet(true);
			model.addAttribute("editSmileys", false);
		} else
			model.addAttribute("editSmileys", true);
		try {
			report = rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();

		}

		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);
		return "report/showReport";
	}

	@RequestMapping(value = "/printReport")
	public String printReport(Model model,
			@ModelAttribute("report") Report report) {

		model.addAttribute("report", report);

		return "printReport/printReport";
	}

	@RequestMapping(value = "/printDone")
	public String printDone(Model model,
			@ModelAttribute("report") ReportTemplate report) {

		model.addAttribute("report", report);
		return "report/showReport";
	}

	@RequestMapping(value = "editReport")
	public String editReport(Model model,
			@ModelAttribute("report") Report report) {

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);
		model.addAttribute("initialAnswerIndexCounter", 0);
		model.addAttribute("editReportPartNumber", 0);
		model.addAttribute("report", report);

		model.addAttribute("edit", "TRUE");

		report.setSmileysSet(false);
		return "report/editReport";
	}

	@RequestMapping(value = "deleteReport")
	public String editReport(@ModelAttribute("report") Report report) {

		rs.deleteReport(report);

		return "start";

	}

	@RequestMapping(value = "approveReport")
	public String approveReport(@ModelAttribute("report") Report report) {

		report.setReportStatus("APPROVED");
		rs.saveReport(report);

		return "report/showReport";

	}

	@RequestMapping(value = "editSmileys")
	public String approveReport(Model model,
			@ModelAttribute("report") Report report) {

		model.addAttribute("editSmileys", true);
		model.addAttribute("openSummaryPanel", true);
		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);

		return "report/showReport";

	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		Report selectedReport = rs.getReportById(id.longValue());

		model.addAttribute("report", selectedReport);
		model.addAttribute("edit", "TRUE");
		model.addAttribute("readyReport", selectedReport);

		if (selectedReport.isSmileysSet() == false) {

			model.addAttribute("editSmileys", false);
			if (selectedReport.getReportStatus() == "DRAFT"
					|| selectedReport.getReportStatus() == "AWAIT_APPROVAL")
				model.addAttribute("editSmileys", true);
			if (selectedReport.getReportStatus() == "APPROVED"
					&& request.isUserInRole("ROLE_ADMIN"))
				model.addAttribute("editSmileys", true);

		} else
			model.addAttribute("editSmileys", false);

		return "report/showReport";

	}

}