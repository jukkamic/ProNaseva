package fi.testcenter.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.PhoneCallTestReport;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;
import fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate;
import fi.testcenter.domain.reportTemplate.ReportTemplate;
import fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate;
import fi.testcenter.pdf.PhoneCallTestReportPdf;
import fi.testcenter.pdf.WorkshopVisitTestReportPdf;
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
	ResourceLoader resourceLoader;

	@Autowired
	private ReportTemplateService rts;

	@Autowired
	private ReportService rs;

	@Autowired
	private UserAccountService us;

	@Autowired
	private WorkshopVisitTestReportPdf workshopVisitReportPdfGenerator;

	@Autowired
	private PhoneCallTestReportPdf phoneCallTestReportPdfGenerator;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request) {

		if (request.isUserInRole("ROLE_CLIENT"))
			return "redirect:userOwnReports";
		else
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

		List<Importer> importers = is.findActiveImportersInAplhaOrder();
		if (importers == null || importers.isEmpty()) {
			model.addAttribute("alertMessage", "Ei maahantuojia");
			return "start";
		}
		model.addAttribute("importers", importers);

		return "report/newReportSelectImporter";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.POST)
	public String submitNewReportBasicInfo(Model model,
			@RequestParam("importerID") Integer importerID) {

		ArrayList<String> alertMessages = new ArrayList<String>();

		Importer importer = is.findImporterById(importerID.longValue());
		model.addAttribute("importer", importer);
		if (importer != null
				&& (importer.getWorkshops() == null || importer.getWorkshops()
						.isEmpty())) {
			alertMessages.add("Maahantuojalle ei ole valittu korjaamoja");

			model.addAttribute("alertMessages", alertMessages);
			return "report/newReportSelectImporter";
		}

		if (importer.getReportTemplates() == null
				|| importer.getReportTemplates().isEmpty()) {
			alertMessages.add("Maahantuojalle ei ole valittu raporttipohjaa");
			model.addAttribute("alertMessages", alertMessages);
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
			@RequestParam("selectedReportTemplate") Long selectedTemplateId,
			@ModelAttribute("importer") Importer importer) {

		ReportTemplate chosenTemplate = new ReportTemplate();
		Report report = new Report();

		for (ReportTemplate template : importer.getReportTemplates()) {

			if (selectedTemplateId.equals(template.getId())) {

				if (template instanceof PhoneCallTestReportTemplate)
					chosenTemplate = (PhoneCallTestReportTemplate) template;
				if (template instanceof WorkshopVisitReportTemplate)
					chosenTemplate = (WorkshopVisitReportTemplate) template;

			}
		}

		if (chosenTemplate instanceof PhoneCallTestReportTemplate)
			report = new PhoneCallTestReport(
					(PhoneCallTestReportTemplate) chosenTemplate, rs);

		if (chosenTemplate instanceof WorkshopVisitReportTemplate)
			report = new WorkshopVisitReport(
					(WorkshopVisitReportTemplate) chosenTemplate, rs);
		report.setUser(us.findLoginUser());
		report.setImporter(importer);

		model.addAttribute("report", report);

		return "redirect:prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(Model model,
			@ModelAttribute("report") Report report) {
		model.addAttribute("report", report);

		model.addAttribute("initialAnswerIndexCounter", 0);

		model.addAttribute("editReportPartNumber", 0);

		if (report instanceof WorkshopVisitReport)
			return "report/editWorkshopVisitReport";
		if (report instanceof PhoneCallTestReport)
			return "report/editPhoneCallTestReport";
		return "start";

	}

	@RequestMapping(value = "/savePhoneCallTestReport", method = RequestMethod.POST)
	public String submitReport(Model model,
			@ModelAttribute("report") PhoneCallTestReport report,
			BindingResult result) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		try {
			report.checkApprovalRemarks();
			report.setWorkshop(ws.findWorkshopById(report.getWorkshopId()));
			report = (PhoneCallTestReport) rs.saveReport(report);
			report.setMultipleChoiceAnswers(rs);
			report.calculateReportScore();

			model.addAttribute("report", rs.saveReport(report));
			successMessages.add("Tallennettu!");
		} catch (Exception e) {
			errorMessages.add("Tapahtui virhe!");

		}

		model.addAttribute("successMessages", successMessages);
		model.addAttribute("errorMessages", errorMessages);
		return "report/showPhoneCallTestReport";

	}

	@RequestMapping(value = "/saveWorkshopVisitReport", method = RequestMethod.POST)
	public String submitReport(
			Model model,
			@ModelAttribute("report") WorkshopVisitReport report,
			BindingResult result,
			@RequestParam("navigateToReportPart") Integer navigateToReportPart,
			@RequestParam("optionalQuestionsAnswerIndex") Integer optionalQuestionsAnswerIndex,
			@ModelAttribute("editReportPartNumber") Integer addOptionalToReportPart,
			@RequestParam("addOptionalToQuestionGroup") Integer addOptionalToQuestionGroup,
			HttpServletRequest request) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		try {
			report.setWorkshop(ws.findWorkshopById(report.getWorkshopId()));
			report.checkApprovalRemarks();

			report = (WorkshopVisitReport) rs.saveReport(report);
			report.setRemovedQuestions(); // Nollataan vastaukset
											// MultipleChoiceQuestion- ja
											// PointsQuestion-luokan
											// kysymyksiin
											// jotta ei huomioida
											// pisteytyksessä.
			report.checkIfReportHighlightsSelected();
			report.setMultipleChoiceAnswers(rs);

			report.calculateReportScore();

			for (ReportPart part : report.getReportParts()) {
				for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
					for (Answer answer : group.getAnswers()) {
						if (answer instanceof CostListingAnswer)
							((CostListingAnswer) answer).formatCurrencies();

					}
				}
			}

			report = (WorkshopVisitReport) rs.saveReport(report);

			// Jos käyttäjä on clickannut navigointia toisen raportinosan
			// muokkaukseen, asetetaan tallennuksen jälkeen tarvittavat
			// muuttujat ja
			// palataan
			// editReport.jsp

		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);

		}

		report = (WorkshopVisitReport) report;
		if (navigateToReportPart != null) {

			model.addAttribute("report", report);
			model.addAttribute("editReportPartNumber", navigateToReportPart);

			return "report/editWorkshopVisitReport";

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

			if (errorMessages.isEmpty()) {
				successMessages.add("Tallennettu!");

			}

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

			if (!report.isHighlightsSet() && request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Valitse raportin huomionarvoiset vastaukset ja tallenna!");

			}
			if (!report.isSmileysSet() && request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Arvostele tulokset raportin yhteenvedosta ja tallenna!");

			}
			model.addAttribute("successMessages", successMessages);
			model.addAttribute("alertMessages", alertMessages);
			model.addAttribute("errorMessages", errorMessages);
			return "report/showWorkshopVisitTestReport";
		}

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
		return "report/editWorkshopVisitReport";
	}

	@RequestMapping(value = "/submitReportForApproval", method = RequestMethod.GET)
	public String submitReportForApproval(Model model,
			@ModelAttribute("report") Report report) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		report.setReportStatus("AWAIT_APPROVAL");
		model.addAttribute("report", report);

		try {
			rs.saveReport(report);

			successMessages.add("Lähetetty vahvistettavaksi!");
		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");

		}

		model.addAttribute("successMessages", successMessages);
		model.addAttribute("errorMessages", errorMessages);

		if (report instanceof PhoneCallTestReport)
			return "report/showPhoneCallTestReport";
		if (report instanceof WorkshopVisitReport)
			return "report/showWorkshopVisitTestReport";
		return null;
	}

	@RequestMapping(value = "/saveReadyReport", method = RequestMethod.POST)
	public String saveSmileyAndHighlights(Model model,

	@ModelAttribute("report") Report report,
			@RequestParam("rejectReport") boolean rejectReport,
			HttpServletRequest request) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		WorkshopVisitReport wsReport;
		PhoneCallTestReport phReport;
		if (report instanceof WorkshopVisitReport) {
			wsReport = (WorkshopVisitReport) report;
			wsReport.checkIfReportHighlightsSelected();
			wsReport.checkIfReportHighlightsSelected();
			if (wsReport.getOverallResultSmiley() != null
					&& wsReport.getOverallResultSmiley() != "") {
				wsReport.setSmileysSet(true);
				model.addAttribute("editSmileys", false);
			} else
				model.addAttribute("editSmileys", true);

			if (!wsReport.isHighlightsSet()
					&& request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Valitse raportin huomionarvoiset vastaukset ja tallenna!");

			}
			if (!wsReport.isSmileysSet() && request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Arvostele tulokset raportin yhteenvedosta ja tallenna!");

			}
		}

		if (report instanceof PhoneCallTestReport)
			phReport = (PhoneCallTestReport) report;

		report.checkApprovalRemarks();

		if (rejectReport == true) {
			report.setReportStatus("REJECTED");
		}

		try {
			report = rs.saveReport(report);
			if (rejectReport == true) {
				alertMessages.add("Palautettu korjattavaksi.");
			} else {
				successMessages.add("Tallennettu!");
			}

			model.addAttribute("successMessages", successMessages);
			model.addAttribute("alertMessages", alertMessages);
		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);
		}

		model.addAttribute("alertMessages", alertMessages);
		model.addAttribute("errorMessages", errorMessages);
		model.addAttribute("successMessages", successMessages);
		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);

		if (report instanceof WorkshopVisitReport)
			return "report/showWorkshopVisitTestReport";
		if (report instanceof PhoneCallTestReport)
			return "report/showPhoneCallTestReport";
		return null;
	}

	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPdf(
			@ModelAttribute("report") Report report, Model model) {
		ArrayList<String> errorMessages = new ArrayList<String>();
		try {

			byte[] contents;
			if (report instanceof WorkshopVisitReport)
				contents = workshopVisitReportPdfGenerator.generateReportPdf(
						(WorkshopVisitReport) report).toByteArray();

			else if (report instanceof PhoneCallTestReport)
				contents = phoneCallTestReportPdfGenerator.generateReportPdf(
						(PhoneCallTestReport) report).toByteArray();
			else
				return null;

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = report.getWorkshop().getName() + "-"
					+ report.getTestDateString().replace('.', '-') + ".pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(
					contents, headers, HttpStatus.OK);
			return response;

		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);

			return null;
		}

	}

	@RequestMapping(value = "editReport")
	public String editReport(Model model,
			@ModelAttribute("report") Report report) {

		report.checkApprovalRemarks();

		if (report instanceof WorkshopVisitReport) {
			WorkshopVisitReport visitReport = (WorkshopVisitReport) report;
			visitReport.setSmileysSet(false);
			model.addAttribute("initialAnswerIndexCounter", 0);
			model.addAttribute("editReportPartNumber", 0);
			model.addAttribute("report", visitReport);
			model.addAttribute("edit", "TRUE");
			return "report/editWorkshopVisitReport";
		}

		if (report instanceof PhoneCallTestReport) {
			model.addAttribute("report", report);
			return "report/editPhoneCallTestReport";
		}

		return null;
	}

	@RequestMapping(value = "deleteReport")
	public String editReport(@ModelAttribute("report") Report report,
			Model model) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		try {
			rs.deleteReport(report);
			successMessages.add("Raportti poistettu!");
			model.addAttribute("successMessages", successMessages);
		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);
		}

		return "start";

	}

	@RequestMapping(value = "approveReport")
	public String approveReport(@ModelAttribute("report") Report report,
			Model model, HttpServletRequest request) {
		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		try {
			if (report.isApprovalRemarksGiven())
				report.removeApprovalRemarks();
			report.setReportStatus("APPROVED");

			report = rs.saveReport(report);
			model.addAttribute("report", report);
			successMessages.add("Raportti vahvistettu!");

		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");

		}
		model.addAttribute("successMessages", successMessages);
		model.addAttribute("errorMessages", errorMessages);

		if (report instanceof PhoneCallTestReport)
			return "report/showPhoneCallTestReport";
		if (report instanceof WorkshopVisitReport) {
			WorkshopVisitReport wsReport = (WorkshopVisitReport) report;
			ArrayList<String> alertMessages = new ArrayList<String>();
			if (!wsReport.isHighlightsSet()
					&& request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Valitse raportin huomionarvoiset vastaukset ja tallenna!");

			}
			if (!wsReport.isSmileysSet() && request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Arvostele tulokset raportin yhteenvedosta ja tallenna!");

			}
			model.addAttribute("alertMessages", alertMessages);

			return "report/showWorkshopVisitTestReport";

		}
		return null;

	}

	@RequestMapping(value = "editSmileys")
	public String approveReport(Model model,
			@ModelAttribute("report") WorkshopVisitReport report,
			HttpServletRequest request) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		report.checkApprovalRemarks();
		model.addAttribute("editSmileys", true);
		model.addAttribute("openSummaryPanel", true);
		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);

		if (!report.isHighlightsSet() && request.isUserInRole("ROLE_ADMIN")) {
			alertMessages
					.add("Valitse raportin huomionarvoiset vastaukset ja tallenna!");

		}
		if (!report.isSmileysSet() && request.isUserInRole("ROLE_ADMIN")) {
			alertMessages
					.add("Arvostele tulokset raportin yhteenvedosta ja tallenna!");

		}
		return "report/showWorkshopVisitTestReport";

	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		ArrayList<String> errorMessages = new ArrayList<String>();
		ArrayList<String> successMessages = new ArrayList<String>();
		ArrayList<String> alertMessages = new ArrayList<String>();

		Report selectedReport = rs.getReportById(id.longValue());

		model.addAttribute("report", selectedReport);
		model.addAttribute("edit", "TRUE");
		model.addAttribute("readyReport", selectedReport);

		if (selectedReport instanceof WorkshopVisitReport) {
			WorkshopVisitReport visitReport = (WorkshopVisitReport) selectedReport;
			if (visitReport.isSmileysSet() == false) {

				model.addAttribute("editSmileys", false);
				if (visitReport.getReportStatus() == "DRAFT"
						|| visitReport.getReportStatus() == "AWAIT_APPROVAL")
					model.addAttribute("editSmileys", true);
				if (visitReport.getReportStatus() == "APPROVED"
						&& request.isUserInRole("ROLE_ADMIN"))
					model.addAttribute("editSmileys", true);

			} else
				model.addAttribute("editSmileys", false);
			if (!visitReport.isHighlightsSet()
					&& request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Valitse raportin huomionarvoiset vastaukset ja tallenna!");

			}
			if (!visitReport.isSmileysSet()
					&& request.isUserInRole("ROLE_ADMIN")) {
				alertMessages
						.add("Arvostele tulokset raportin yhteenvedosta ja tallenna!");

			}

			model.addAttribute("alertMessages", alertMessages);
			return "report/showWorkshopVisitTestReport";
		} else
			return "report/showPhoneCallTestReport";
	}

}