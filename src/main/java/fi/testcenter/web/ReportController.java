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

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.ImportantPointsQuestion;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.question.TextQuestion;
import fi.testcenter.domain.report.QuestionGroup;
import fi.testcenter.domain.report.QuestionGroupScore;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPartScore;
import fi.testcenter.domain.report.ReportTemplate;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.ReportTemplateService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "reportTemplate", "report", "formAnswers",
		"workshops", "readyReport", "addQuestionToGroup",
		"addQuestionToReportPart" })
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

		try {
			report.setReportTemplate(rts.findReportTemplateByName(importer
					.getReportTemplateName()));
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

		report.prepareAnswerList();
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
			@RequestParam("addQuestionToGroup") Integer addQuestionToGroup,
			@RequestParam("addQuestionToReportPart") Integer addQuestionToReportPart) {

		report.setWorkshop(ws.findWorkshop(report.getWorkshopId()));

		report.calculateReportScore();

		report.setHighlightAnswers(rs);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

		try {
			report.setDate(simpleDateFormat.parse(report.getReportDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Report savedReport = new Report();
		try {
			savedReport = rs.saveReport(report);

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
				for (QuestionGroup questionGroup : savedReport
						.getReportTemplate().getReportParts().get(i)
						.getQuestionGroups()) {
					answerIndex += questionGroup.getQuestions().size();
					for (Question question : questionGroup.getQuestions()) {
						answerIndex += question.getSubQuestions().size();
					}
				}
			}
			model.addAttribute("initialAnswerIndexCounter", answerIndex);
			model.addAttribute("report", savedReport);
			model.addAttribute("editReportPartNumber", navigateToReportPart);

			return "report/editReport";

		} else if (addQuestionToGroup != null) {

			model.addAttribute("optionalQuestions", report.getReportTemplate()
					.getReportParts().get(addQuestionToReportPart)
					.getQuestionGroups().get(addQuestionToGroup)
					.getOptionalQuestions());

			model.addAttribute("addQuestionToReportPart",
					addQuestionToReportPart);
			model.addAttribute("addQuestionToGroup", addQuestionToGroup);
			model.addAttribute("chosenQuestions", new ChosenQuestions());
			model.addAttribute("report", report);

			return "report/addOptionalQuestions";
		}

		else {

			model.addAttribute("readyReport", report);
			model.addAttribute("report", savedReport);

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
	public String addChosenQuestions(Model model,
			@ModelAttribute("chosenQuestions") ChosenQuestions chosenQuestions,
			BindingResult result, @ModelAttribute("report") Report report,
			@ModelAttribute("addQuestionToReportPart") Integer reportPart,
			@ModelAttribute("addQuestionToGroup") Integer questionGroup) {

		List<Question> reportTemplateQuestionsList = report.getReportTemplate()
				.getReportParts().get(reportPart).getQuestionGroups()
				.get(questionGroup).getOptionalQuestions();

		// Get Report answers List start index for optional questions
		int answerIndex = -1;

		for (int i = 0; i < reportPart; i++) {
			for (QuestionGroup group : report.getReportTemplate()
					.getReportParts().get(i).getQuestionGroups()) {
				answerIndex = answerIndex + group.getQuestions().size();

			}
		}

		for (int i = 0; i <= questionGroup; i++) {
			answerIndex = answerIndex
					+ report.getReportTemplate().getReportParts()
							.get(reportPart).getQuestionGroups().get(i)
							.getQuestions().size();

		}

		log.debug("answer index: " + answerIndex);
		log.debug("answer class: "
				+ report.getAnswers().get(answerIndex + 1).getClass());
		for (int i : chosenQuestions.chosenQuestions) {
			log.debug("chosen question " + i);
		}
		OptionalQuestionsAnswer optionalAnswer = (OptionalQuestionsAnswer) report
				.getAnswers().get(answerIndex + 1);

		// Tehdään lista käyttäjän valitsemista kysymyksistä

		ArrayList<Answer> newAnswerList = new ArrayList<Answer>();
		ArrayList<Question> newQuestionList = new ArrayList<Question>();

		for (int questionIndex : chosenQuestions.chosenQuestions) {

			Question question = reportTemplateQuestionsList.get(questionIndex);

			newQuestionList.add(question);
			if (!optionalAnswer.getQuestions().contains(question)) {
				if (question instanceof MultipleChoiceQuestion) {
					Answer mca = new MultipleChoiceAnswer();
					mca.setQuestion(question);
					newAnswerList.add(mca);
				}
				if (question instanceof PointsQuestion) {
					Answer pa = new PointsAnswer();
					pa.setQuestion(question);
					newAnswerList.add(pa);
				}
				if (question instanceof TextQuestion) {
					Answer ta = new TextAnswer();
					ta.setQuestion(question);
					newAnswerList.add(ta);
				}
				if (question instanceof ImportantPointsQuestion) {
					Answer imp = new ImportantPointsAnswer();
					imp.setQuestion(question);
					newAnswerList.add(imp);
				}
				if (question instanceof CostListingQuestion) {
					Answer cla = new CostListingAnswer();
					cla.setQuestion(question);
					newAnswerList.add(cla);
				}
			}

			else {
				Answer oldAnswer = new Answer();
				for (Answer answer : optionalAnswer.getAnswers()) {
					if (answer.getQuestion() == question)
						oldAnswer = answer;
				}
				newAnswerList.add(oldAnswer);
			}
		}

		// Lisätään OptionalQuestionsAnswerin uusiin listoihin vanhat kysymys-
		// ja vastaus-oliot

		OptionalQuestionsAnswer newAnswer = new OptionalQuestionsAnswer();
		newAnswer.setQuestions(newQuestionList);
		newAnswer.setAnswers(newAnswerList);

		List<Answer> reportAnswerList = report.getAnswers();
		reportAnswerList.set(answerIndex + 1, newAnswer);
		report.setAnswers(reportAnswerList);

		// TALLENNA ANSWERLIST

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

		if (report.getReportHighlights().size() > 0) {

			try {
				rs.deleteReportHighlights(report.getReportHighlights());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		report.setHighlightAnswers(rs);

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

		}

		report.setSmileysSet(true);
		Report savedReport = new Report();
		try {
			savedReport = rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();

		}

		model.addAttribute("editSmileys", false);
		model.addAttribute("readyReport", savedReport);
		model.addAttribute("report", savedReport);
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