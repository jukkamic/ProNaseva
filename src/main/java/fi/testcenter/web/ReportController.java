package fi.testcenter.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import fi.testcenter.domain.Answer;
import fi.testcenter.domain.Importer;
import fi.testcenter.domain.MultipleChoiceAnswer;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.QuestionGroupScore;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.ReportPart;
import fi.testcenter.domain.ReportPartScore;
import fi.testcenter.domain.ReportTemplate;
import fi.testcenter.domain.TextAnswer;
import fi.testcenter.domain.TextQuestion;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "reportTemplate", "report", "formAnswers",
		"workshops", "readyReport" })
public class ReportController {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@Autowired
	private ReportService rs;

	@Autowired
	private UserAccountService us;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {

		return "start";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.GET)
	public String prepareNewReportBasicInfoForm(HttpServletRequest request,
			Model model) {

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		return "newReportSelectImporter";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.POST)
	public String submitNewReportBasicInfo(HttpServletRequest request,
			Model model, @RequestParam("importerID") Integer importerID) {

		Report report = new Report();
		report.setReportTemplate(rs.getReportTemplate());
		report.setUser(us.getLoginUser());

		Importer importer = is.getImporterById(importerID.longValue());

		report.setImporter(importer);
		report.setImporterId(importerID.longValue());

		model.addAttribute("report", report);

		return "redirect:/prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		model.addAttribute("report", report);

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);
		model.addAttribute("initialAnswerIndexCounter", 0);

		List<Answer> answers = new ArrayList<Answer>();

		for (ReportPart reportPart : report.getReportTemplate()
				.getReportParts()) {
			report.getReportPartSmileys().add("");
			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {
				report.getQuestionGroupSmileys().add("");
				for (Question question : questionGroup.getQuestions()) {

					if (question instanceof TextQuestion) {

						Answer answer = new TextAnswer();
						answer.setQuestion(question);
						answers.add(answer);

					}

					if (question instanceof MultipleChoiceQuestion) {
						Answer answer = new MultipleChoiceAnswer();
						answer.setQuestion(question);
						answers.add(answer);

					}
					if (!question.getSubQuestions().isEmpty()) {
						for (Question subQuestion : question.getSubQuestions()) {
							if (subQuestion instanceof TextQuestion) {
								Answer answer = new TextAnswer();
								answer.setQuestion(subQuestion);
								answers.add(answer);

							}

							if (subQuestion instanceof MultipleChoiceQuestion) {
								Answer answer = new MultipleChoiceAnswer();
								answer.setQuestion(subQuestion);
								answers.add(answer);

							}
						}
					}

				}
			}
		}
		report.setAnswers(answers);

		model.addAttribute("editReportPartNumber", 0);
		return "editReport";
	}

	@RequestMapping(value = "/saveReport", method = RequestMethod.POST)
	public String submitReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result,
			@RequestParam("navigateToReportPart") Integer navigateToReportPart) {

		report.setWorkshop(ws.findWorkshop(report.getWorkshopId()));

		report.calculateReportScore();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		report.setHighlightAnswers();
		try {
			report.setDate(simpleDateFormat.parse(report.getReportDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Jos käyttäjä on clickannut navigointia toisen raportinosan
		// muokkaukseen, asetetaan tallennuksen jälkeen tarvittavat muuttujat ja
		// palataan
		// editReport.jsp

		if (navigateToReportPart != null) {

			int answerIndex = 0;
			for (int i = 0; i < navigateToReportPart; i++) {
				for (QuestionGroup questionGroup : report.getReportTemplate()
						.getReportParts().get(i).getQuestionGroups()) {
					answerIndex += questionGroup.getQuestions().size();
					for (Question question : questionGroup.getQuestions()) {
						answerIndex += question.getSubQuestions().size();
					}
				}
			}
			model.addAttribute("initialAnswerIndexCounter", answerIndex);
			log.debug("Vastausindeksi : " + answerIndex);
			log.debug("Edit report part : " + navigateToReportPart);
			model.addAttribute("editReportPartNumber", navigateToReportPart);

			return "editReport";

		} else
			model.addAttribute("readyReport", report);
		model.addAttribute("report", report);
		return "showReport";
	}

	@RequestMapping(value = "/submitReportForApproval", method = RequestMethod.GET)
	public String submitReportForApproval(HttpServletRequest request,
			Model model, @ModelAttribute("report") Report report) {

		report.setReportStatus("AWAIT_APPROVAL");
		model.addAttribute("report", report);

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();

		}

		return "/showReport";
	}

	@RequestMapping(value = "/saveSmileyAndHighlights", method = RequestMethod.POST)
	public String saveSmileyAndHighlights(HttpServletRequest request,
			Model model, @ModelAttribute("readyReport") Report formReport,
			BindingResult result, @ModelAttribute("report") Report report) {

		log.debug("report eka vastaus : " + report.getAnswers().get(0));
		log.debug("formReport eka vastaus : " + formReport.getAnswers().get(0));
		log.debug("toka reportpart max pisteet: "
				+ report.getReportPartScore().get(1));
		int reportPartScoreIndex = 0;

		for (ReportPartScore reportPartScore : formReport.getReportPartScore()) {

			report.getReportPartScore().get(reportPartScoreIndex++)
					.setScoreSmiley(reportPartScore.getScoreSmiley());

		}

		int questionGroupScoreIndex = 0;
		for (QuestionGroupScore questionGroupScore : formReport
				.getQuestionGroupScore()) {
			report.getQuestionGroupScore().get(questionGroupScoreIndex++)
					.setScoreSmiley(questionGroupScore.getScoreSmiley());
			log.debug("Questiongroup smiley : "
					+ questionGroupScore.getScoreSmiley());
		}

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();

		}

		model.addAttribute("readyReport", report);
		model.addAttribute("report", report);
		return "/showReport";
	}

	@RequestMapping(value = "/printReport")
	public String printReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report) {
		model.addAttribute("report", report);

		return "printReport";
	}

	@RequestMapping(value = "/printDone")
	public String printDone(HttpServletRequest request, Model model,
			@ModelAttribute("report") ReportTemplate report) {

		model.addAttribute("report", report);
		return "showReport";
	}

	@RequestMapping(value = "editReport")
	public String editReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report) {

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);
		model.addAttribute("initialAnswerIndexCounter", 0);
		model.addAttribute("editReportPartNumber", 0);
		model.addAttribute("report", report);

		model.addAttribute("edit", "TRUE");

		return "editReport";
	}

	@RequestMapping(value = "deleteReport")
	public String editReport(HttpServletRequest request,
			@ModelAttribute("report") Report report) {

		rs.deleteReport(report);

		return "start";

	}

	@RequestMapping(value = "approveReport")
	public String approveReport(HttpServletRequest request,
			@ModelAttribute("report") Report report) {

		report.setReportStatus("APPROVED");
		rs.saveReport(report);

		return "showReport";

	}

}