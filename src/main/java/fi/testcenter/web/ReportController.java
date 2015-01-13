package fi.testcenter.web;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.context.support.ServletContextResource;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;
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
	private WorkshopVisitTestReportPdf workshopVisitReportPdfCreator;

	@Autowired
	private PhoneCallTestReportPdf phoneCallTestReportPdfCreator;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request) {

		// try {
		// Resource file = resourceLoader.getResource("file:ARIAL.TTF");
		// File arialFile = file.getFile();

		// BaseFont font = BaseFont.createFont(arialFile.getPath(),
		// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		// Font newFont = new Font(font, 12);
		// log.debug("basefont:" + newFont.getBaseFont());
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// return null;
		// }

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
		if (report instanceof PhoneCallTestReport)
			return "report/editPhoneCallTestReport";
		return "start";

	}

	@RequestMapping(value = "/savePhoneCallTestReport", method = RequestMethod.POST)
	public String submitReport(Model model,
			@ModelAttribute("report") PhoneCallTestReport report,
			BindingResult result) {

		report.calculateReportScore();

		report.setWorkshop(ws.findWorkshopById(report.getWorkshopId()));
		model.addAttribute("report", rs.saveReport(report));

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

		report.setWorkshop(ws.findWorkshopById(report.getWorkshopId()));

		report.checkReportHighlights();
		report.setRemovedQuestions(); // Nollataan vastaukset
										// MultipleChoiceQuestion- ja
										// PointsQuestion-luokan
										// kysymyksiin
										// jotta ei huomioida
										// pisteytyksessä.
		report.calculateReportScore();

		for (ReportPart part : report.getReportParts()) {
			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				for (Answer answer : group.getAnswers()) {
					if (answer instanceof CostListingAnswer)
						((CostListingAnswer) answer).formatCurrencies();

				}
			}
		}

		try {
			report = (WorkshopVisitReport) rs.saveReport(report);

		} catch (Exception e) {
			e.printStackTrace();

		}

		// Jos käyttäjä on clickannut navigointia toisen raportinosan
		// muokkaukseen, asetetaan tallennuksen jälkeen tarvittavat
		// muuttujat ja
		// palataan
		// editReport.jsp

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
		return "report/editWorkshopVisitTestReport";
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

		return "report/showWorkshopVisitTestReport";
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
		return "report/showWorkshopVisitTestReport";
	}

	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPdf(
			@ModelAttribute("report") Report report, HttpServletRequest request) {
		try {

			Resource resourceFile = resourceLoader
					.getResource("file:ARIAL.TTF");
			File arialFile = resourceFile.getFile();

			BaseFont BASE_FONT = BaseFont.createFont(arialFile.getPath(),
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			resourceFile = resourceLoader.getResource("file:WINGDING.TTF");
			File wingdingFontFile = resourceFile.getFile();

			BaseFont WINGDINGS_BASE_FONT = BaseFont.createFont(
					wingdingFontFile.getPath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			File file;
			ServletContextResource imageResource = new ServletContextResource(
					request.getSession().getServletContext(),
					"resources/printReportBackground.jpg");

			file = imageResource.getFile();

			Image image = Image.getInstance(file.getAbsolutePath());
			image.scaleAbsolute(595, 842);
			image.setAbsolutePosition(0, 0);

			byte[] contents;
			if (report instanceof WorkshopVisitReport)
				contents = workshopVisitReportPdfCreator.generateReportPdf(
						(WorkshopVisitReport) report, image, BASE_FONT,
						WINGDINGS_BASE_FONT).toByteArray();

			else if (report instanceof PhoneCallTestReport)
				contents = phoneCallTestReportPdfCreator.generateReportPdf(
						(PhoneCallTestReport) report, image, BASE_FONT,
						WINGDINGS_BASE_FONT).toByteArray();
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
			return null;
		}

	}

	@RequestMapping(value = "editReport")
	public String editReport(Model model,
			@ModelAttribute("report") Report report) {

		model.addAttribute("workshops", ws.getWorkshops());

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
	public String editReport(@ModelAttribute("report") Report report) {

		rs.deleteReport(report);

		return "start";

	}

	@RequestMapping(value = "approveReport")
	public String approveReport(
			@ModelAttribute("report") WorkshopVisitReport report) {

		report.setReportStatus("APPROVED");
		rs.saveReport(report);

		return "report/showWorkshopVisitTestReport";

	}

	@RequestMapping(value = "editSmileys")
	public String approveReport(Model model,
			@ModelAttribute("report") WorkshopVisitReport report) {

		model.addAttribute("editSmileys", true);
		model.addAttribute("openSummaryPanel", true);
		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);

		return "report/showWorkshopVisitTestReport";

	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

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
			return "report/showWorkshopVisitTestReport";
		} else
			return "report/showPhoneCallTestReport";
	}

}