package fi.testcenter.web;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import fi.testcenter.domain.report.PhoneCallTestReport;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;
import fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate;
import fi.testcenter.domain.reportTemplate.ReportTemplate;
import fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate;
import fi.testcenter.pdf.ReportPdfCreator;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.ReportTemplateService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "reportTemplate", "report", "formAnswers",
		"workshops", "readyReport", "addOptionalToQuestionGroup", "importers",
		"importer", "addQuestionToReportPart", "optionalQuestionsAnswerIndex",
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

	@Autowired
	private ReportPdfCreator pdfCreator;

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

		Importer importer = is.findImporterById(importerID.longValue());
		model.addAttribute("importer", importer);
		if (importer.getReportTemplates() == null
				|| importer.getReportTemplates().isEmpty()) {
			model.addAttribute("alertMessage",
					"Maahantuojalle ei ole valittu raporttipohjaa");
			return "report/newReportSelectImporter";
		}

		if (importer.getReportTemplates().size() > 1)
			return "report/chooseTemplateForReport";
		else {
			Report report;
			ReportTemplate template = importer.getReportTemplates().get(0);
			if (template instanceof PhoneCallTestReportTemplate)
				report = new PhoneCallTestReport(
						(PhoneCallTestReportTemplate) template, rs);

			else
				report = new WorkshopVisitReport(
						(WorkshopVisitReportTemplate) template, rs);
			report.setUser(us.findLoginUser());
			report.setImporter(importer);

			model.addAttribute("report", report);

			return "redirect:prepareReport";
		}

	}

	@RequestMapping(value = "/selectReportTemplate", method = RequestMethod.POST)
	public String selectReportTemplate(Model model,
			@RequestParam("selectedReportTemplate") Long templateId,
			@ModelAttribute("importer") Importer importer) {

		ReportTemplate chosenTemplate = new ReportTemplate();
		Report report;

		for (ReportTemplate template : importer.getReportTemplates()) {

			if (template.getId() == templateId)
				chosenTemplate = template;

		}

		if (chosenTemplate instanceof PhoneCallTestReportTemplate)
			report = new PhoneCallTestReport(
					(PhoneCallTestReportTemplate) chosenTemplate, rs);

		else
			report = new WorkshopVisitReport(
					(WorkshopVisitReportTemplate) chosenTemplate, rs);
		report.setUser(us.findLoginUser());
		report.setImporter(importer);

		model.addAttribute("workshops", ws.getWorkshops());
		model.addAttribute("report", report);

		return "redirect:prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(Model model,
			@ModelAttribute("report") Report report) {
		model.addAttribute("report", report);

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);
		model.addAttribute("initialAnswerIndexCounter", 0);

		model.addAttribute("editReportPartNumber", 0);

		if (report instanceof WorkshopVisitReport)
			return "report/editWorkshopVisitReport";
		else {

			return "report/editPhoneCallTestReport";
		}

	}

	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public String submitReport(Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		report.setWorkshop(ws.findWorkshop(report.getWorkshopId()));

		if (report instanceof PhoneCallTestReport) {
			model.addAttribute("report", rs.saveReport(report));
			return "report/showPhoneCallTestReport";

		}

		if (report instanceof WorkshopVisitReport) {
			WorkshopVisitReport visitReport = (WorkshopVisitReport) report;
			visitReport.checkReportHighlights();
			visitReport.setRemovedQuestions(); // Nollataan vastaukset
												// MultipleChoiceQuestion- ja
												// PointsQuestion-luokan
												// kysymyksiin
												// jotta ei huomioida
												// pisteytyksessä.
			visitReport = visitReport.calculateReportScore(rs);

			for (ReportPart part : visitReport.getReportParts()) {
				for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
					for (Answer answer : group.getAnswers()) {
						if (answer instanceof CostListingAnswer)
							((CostListingAnswer) answer).formatCurrencies();

					}
				}
			}
			report = visitReport;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		try {
			report.setTestDate(simpleDateFormat.parse(report
					.getTestDateString()));
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

		// if (navigateToReportPart != null) {
		//
		// model.addAttribute("report", report);
		// model.addAttribute("editReportPartNumber", navigateToReportPart);
		//
		// return "report/editReport";
		//
		// } else if (optionalQuestionsAnswerIndex != null) {

		// Asetetaan JSP:tä varten chosenQuestions-muuttuujaan
		// aikaisemmin
		// valitut valinnaiset kysymykset

		// OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) report
		// .getReportParts().get(addOptionalToReportPart)
		// .getReportQuestionGroups().get(addOptionalToQuestionGroup)
		// .getAnswers().get(optionalQuestionsAnswerIndex);
		// List<Question> optionalQuestions = ((OptionalQuestions) oqa
		// .getQuestion()).getQuestions();
		//
		// int[] oldQuestions = new int[0];
		// if (oqa.getQuestions() != null) {
		//
		// for (Question question : oqa.getQuestions()) {
		//
		// int index = optionalQuestions.indexOf(question);
		// oldQuestions = Arrays.copyOf(oldQuestions,
		// oldQuestions.length + 1);
		// oldQuestions[oldQuestions.length - 1] = index;
		// }
		// }
		//
		// ChosenQuestions chosenQ = new ChosenQuestions();
		// chosenQ.setChosenQuestions(oldQuestions);
		// model.addAttribute("addQuestionsToOptionalAnswer", oqa);
		// model.addAttribute("chosenQuestions", chosenQ);
		// model.addAttribute("readyReport", report);
		// model.addAttribute("report", report);
		// model.addAttribute("editReportPartNumber",
		// addOptionalToReportPart);
		// model.addAttribute("addOptionalToQuestionGroup",
		// addOptionalToQuestionGroup);
		// model.addAttribute("optionalQuestionsAnswerIndex",
		// optionalQuestionsAnswerIndex);
		//
		// model.addAttribute("optionalQuestions", optionalQuestions);
		//
		// return "report/addOptionalQuestions";
		// }
		//
		// else {
		//
		// model.addAttribute("readyReport", report);
		// model.addAttribute("report", report);
		//
		// // if (report.isSmileysSet() == false) {
		// //
		// // model.addAttribute("editSmileys", false);
		// // if (report.getReportStatus() == "DRAFT"
		// // || report.getReportStatus() == "AWAIT_APPROVAL")
		// // model.addAttribute("editSmileys", true);
		// // if (report.getReportStatus() == "APPROVED"
		// // && request.isUserInRole("ROLE_ADMIN"))
		// // model.addAttribute("editSmileys", true);
		//
		// } else
		// model.addAttribute("editSmileys", false);

		return "report/showReport";
	}

	// }

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
			@ModelAttribute("report") WorkshopVisitReport report) {

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

		// report.checkReportHighlights();
		// if (report.getOverallResultSmiley() != null
		// && report.getOverallResultSmiley() != "") {
		// report.setSmileysSet(true);
		// model.addAttribute("editSmileys", false);
		// } else
		// model.addAttribute("editSmileys", true);
		// try {
		// report = rs.saveReport(report);
		// } catch (Exception e) {
		// e.printStackTrace();
		//
		// }

		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);
		return "report/showReport";
	}

	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPdf(@ModelAttribute("report") Report report) {
		// try {
		//
		// // report = rs.getReportById(report.getId());
		// byte[] contents;
		// contents = pdfCreator.generateReportPdf(report).toByteArray();
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.parseMediaType("application/pdf"));
		// String filename = report.getWorkshop().getName() + "-"
		// + report.getTestDateString().replace('.', '-') + ".pdf";
		// headers.setContentDispositionFormData(filename, filename);
		// headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		// ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
		// contents, headers, HttpStatus.OK);
		// return response;
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// return null;
		// }
		return null;
	}

	@RequestMapping(value = "/printDone")
	public String printDone(Model model,
			@ModelAttribute("report") WorkshopVisitReportTemplate report) {

		model.addAttribute("report", report);
		return "report/showReport";
	}

	@RequestMapping(value = "editReport")
	public String editReport(Model model,
			@ModelAttribute("report") WorkshopVisitReport report) {

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
	public String editReport(
			@ModelAttribute("report") WorkshopVisitReport report) {

		rs.deleteReport(report);

		return "start";

	}

	@RequestMapping(value = "approveReport")
	public String approveReport(
			@ModelAttribute("report") WorkshopVisitReport report) {

		report.setReportStatus("APPROVED");
		rs.saveReport(report);

		return "report/showReport";

	}

	@RequestMapping(value = "editSmileys")
	public String approveReport(Model model,
			@ModelAttribute("report") WorkshopVisitReport report) {

		model.addAttribute("editSmileys", true);
		model.addAttribute("openSummaryPanel", true);
		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);

		return "report/showReport";

	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		// WorkshopVisitReport selectedReport =
		// rs.getReportById(id.longValue());
		//
		// model.addAttribute("report", selectedReport);
		// model.addAttribute("edit", "TRUE");
		// model.addAttribute("readyReport", selectedReport);
		//
		// if (selectedReport.isSmileysSet() == false) {
		//
		// model.addAttribute("editSmileys", false);
		// if (selectedReport.getReportStatus() == "DRAFT"
		// || selectedReport.getReportStatus() == "AWAIT_APPROVAL")
		// model.addAttribute("editSmileys", true);
		// if (selectedReport.getReportStatus() == "APPROVED"
		// && request.isUserInRole("ROLE_ADMIN"))
		// model.addAttribute("editSmileys", true);
		//
		// } else
		// model.addAttribute("editSmileys", false);

		return "report/showReport";

	}

}