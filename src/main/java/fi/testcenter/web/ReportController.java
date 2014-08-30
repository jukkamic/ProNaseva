package fi.testcenter.web;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.WorkshopService;
import fi.testcenter.web.form.ReportBasicInfo;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "report", "dbReports" })
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
		log.debug("report controller prepare new report basic info form, first workshop id: "
				+ workshops.get(0).getId());
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
		report.setWorkshopId(reportInfo.getWorkshopID());
		report.setImporterId(reportInfo.getImporterID());

		model.addAttribute("report", report);

		return "redirect:/prepareReport";
	}

	@RequestMapping(value = "/prepareReport")
	public String prepareForm(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		model.addAttribute("report", report);
		return "newReport";
	}

	@RequestMapping(value = "/submitReport", method = RequestMethod.POST)
	public String submitReport(HttpServletRequest request, Model model,
			@ModelAttribute("report") Report report, BindingResult result) {

		log.debug("\n\n\nJSP:N SYÖTETYT TIEDOT SIDOTTUINA REPORT-OLIOON: \n\n");
		for (QuestionGroup group : report.getQuestionGroups()) {
			for (Question question : group.getQuestions()) {

				if (question instanceof MultipleChoiceQuestion) {
					MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;

					if (mcq.getChosenOptionIndex() != -1) {
						mcq.setChosenOption(mcq.getOptions().get(
								mcq.getChosenOptionIndex()));
						log.debug("Monivalintakysymys: " + mcq.getQuestion()
								+ " - valinta: "
								+ mcq.getChosenOption().getOption()
								+ " - pisteet: "
								+ mcq.getChosenOption().getPoints());
					}

					else
						log.debug("Monivalintakysymys: " + mcq.getQuestion()
								+ " - ei valintaa");
				} else
					log.debug(question);
			}
		}

		try {
			rs.saveReport(report);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Report> dbReports = new ArrayList<Report>();

		dbReports = rs.findAllReports();
		Report showReport = new Report();

		log.debug("\n \nTIETOKANNASTA HAETUT RAPORTIT: \n");

		int i = 1;
		for (Report loopReport : dbReports) {
			showReport = loopReport;
			log.debug("\n\n\n" + i++ + ".RAPORTTI\n\n");
			log.debug("Maahantuoja: " + loopReport.getImporter().getName());
			log.debug("Korjaamo: " + loopReport.getWorkshop().getName());

			for (QuestionGroup loopQuestionGroup : loopReport
					.getQuestionGroups()) {
				for (Question loopQuestion : loopQuestionGroup.getQuestions()) {
					if (loopQuestion instanceof MultipleChoiceQuestion) {

						MultipleChoiceQuestion loopMcq = (MultipleChoiceQuestion) loopQuestion;
						if (loopMcq.getChosenOption() != null) {
							log.debug("Monivalintakysymys: "
									+ loopMcq.getQuestion() + " - valinta: "
									+ loopMcq.getChosenOption().getOption()
									+ " - pisteet: "
									+ loopMcq.getChosenOption().getPoints());
						}

						else {
							log.debug("Monivalintakysymys: "
									+ loopMcq.getQuestion() + " - ei valintaa");
						}
					}

					else
						log.debug(loopQuestion);

				}
			}

		}

		model.addAttribute("report", showReport);
		return "showReport";
	}
}
