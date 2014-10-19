package fi.testcenter.web;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.MultipleChoiceOption;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.ReportPart;
import fi.testcenter.domain.SubQuestion;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.WorkshopService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "report" })
public class ReportController {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@Autowired
	private ReportService rs;

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {

		return "start";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.GET)
	public String prepareNewReportBasicInfoForm(HttpServletRequest request,
			Model model) {

		List<Workshop> workshops = ws.getWorkshops();

		model.addAttribute("workshops", workshops);

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		model.addAttribute("reportBasicInfo", new ReportBasicInfo());

		return "newReportBasicInfo";
	}

	@RequestMapping(value = "/addNewReport", method = RequestMethod.POST)
	public String submitNewReportBasicInfo(HttpServletRequest request,
			Model model,
			@ModelAttribute("reportBasicInfo") ReportBasicInfo reportInfo,
			BindingResult result) {

		Report report = rs.getReportTemplate();

		Workshop workshop = ws.findWorkshop(reportInfo.getWorkshopID());
		Importer importer = is.getImporterById(reportInfo.getImporterID());
		report.setWorkshop(workshop);
		report.setImporter(importer);
		report.setWorkshopId(reportInfo.getWorkshopID());
		report.setImporterId(reportInfo.getImporterID());

		model.addAttribute("report", report);

		return "redirect:/prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		model.addAttribute("report", report);
		return "editReport";
	}

	@RequestMapping(value = "/submitReport", method = RequestMethod.POST)
	public String submitReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		int reportTotalScore = 0;

		int reportMaxScore = 0;
		for (ReportPart reportPart : report.getReportParts()) {
			int reportPartScore = 0;
			int reportPartMaxScore = 0;
			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {
				int questionGroupMaxScore = 0;
				int questionGroupScore = 0;
				for (Question question : questionGroup.getQuestions()) {
					for (SubQuestion loopSubQuestion : question
							.getSubQuestions()) {
						Question subQuestion = loopSubQuestion.getQuestion();

						// SubQuestion score

						if (subQuestion instanceof MultipleChoiceQuestion) {
							MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) subQuestion;

							int maxScore = 0;
							for (MultipleChoiceOption option : mcq.getOptions()) {
								if (option.getPoints() != -1
										&& option.getPoints() > maxScore) {
									maxScore = option.getPoints();

								}
							}

							mcq.getAnswer().setMaxScore(maxScore);

							if (mcq.getAnswer().getChosenOptionIndex() != -1
									&& mcq.getOptions()
											.get(mcq.getAnswer()
													.getChosenOptionIndex())
											.getPoints() != -1) {
								questionGroupMaxScore = questionGroupMaxScore
										+ maxScore;
								mcq.getAnswer().setShowScore(true);
								questionGroup.setShowScore(true);
								mcq.getAnswer()
										.setScore(
												mcq.getOptions()
														.get(mcq.getAnswer()
																.getChosenOptionIndex())
														.getPoints());
								questionGroupScore = questionGroupScore
										+ mcq.getOptions()
												.get(mcq.getAnswer()
														.getChosenOptionIndex())
												.getPoints();
							}
							log.debug("Multiple choice subquestion score : "
									+ mcq.getAnswer().getScore() + " / "
									+ mcq.getAnswer().getMaxScore());
						}

					}

					// Question score

					if (question instanceof MultipleChoiceQuestion) {
						MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;

						int maxScore = 0;
						for (MultipleChoiceOption option : mcq.getOptions()) {
							if (option.getPoints() != -1
									&& option.getPoints() > maxScore) {
								maxScore = option.getPoints();

							}
						}

						mcq.getAnswer().setMaxScore(maxScore);

						if (mcq.getAnswer().getChosenOptionIndex() != -1
								&& mcq.getOptions()
										.get(mcq.getAnswer()
												.getChosenOptionIndex())
										.getPoints() != -1) {
							questionGroupMaxScore = questionGroupMaxScore
									+ maxScore;
							mcq.getAnswer().setShowScore(true);
							questionGroup.setShowScore(true);

							mcq.getAnswer().setScore(
									mcq.getOptions()
											.get(mcq.getAnswer()
													.getChosenOptionIndex())
											.getPoints());
							questionGroupScore = questionGroupScore
									+ mcq.getOptions()
											.get(mcq.getAnswer()
													.getChosenOptionIndex())
											.getPoints();
						}
						log.debug("Multiple choice question score : "
								+ mcq.getAnswer().getScore() + " / "
								+ mcq.getAnswer().getMaxScore());
					}
				}
				reportPartMaxScore = reportPartMaxScore + questionGroupMaxScore;
				reportPartScore = reportPartScore + questionGroupScore;

				questionGroup.setMaxScore(questionGroupMaxScore);
				questionGroup.setScore(questionGroupScore);

				log.debug("Question group score : " + questionGroup.getScore()
						+ " / " + questionGroup.getMaxScore());
			}

			if (reportPartMaxScore > 0) {

				reportTotalScore = reportTotalScore + reportPartScore;

				reportPart.setShowScorePercentage(true);

				reportPart.setScorePercentage((int) Math
						.round((double) reportPartScore
								/ (double) reportPartMaxScore * 100));
				log.debug("Report part score : "
						+ reportPart.getScorePercentage() + " % ");
			}
			reportMaxScore = reportMaxScore + reportPartMaxScore;
		}

		log.debug("report max score : " + reportMaxScore);
		log.debug("report total score : " + reportTotalScore);

		report.setTotalScorePercentage((int) Math
				.round((double) reportTotalScore / (double) reportMaxScore
						* 100));

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("report", report);
		return "showReport";
	}

	@RequestMapping(value = "/printReport")
	public String printReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report) {
		model.addAttribute("report", report);
		return "printReport";
	}

	@RequestMapping(value = "/printDone")
	public String printDone(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report) {

		model.addAttribute("report", report);
		return "showReport";
	}

	@RequestMapping(value = "editReport")
	public String editReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report) {

		model.addAttribute("edit", "TRUE");

		return "editReport";
	}

	@RequestMapping(value = "deleteReport")
	public String editReport(HttpServletRequest request,
			@ModelAttribute("report") Report report) {

		rs.deleteReport(report);

		return "start";

	}

}