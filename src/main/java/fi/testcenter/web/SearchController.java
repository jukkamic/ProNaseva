package fi.testcenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.reportSummary.ReportSummary;
import fi.testcenter.domain.reportTemplate.ReportTemplate;
import fi.testcenter.pdf.WorkshopVisitTestReportSummary;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportQueryObject;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@SessionAttributes({ "report", "reportSearchList", "readyReport",
		"searchReportCriteria", "importers", "workshops", "users",
		"summarySearchCriteria" })
@Controller
public class SearchController {

	Logger log = Logger.getLogger("fi.testcenter.web.SearchController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@Autowired
	private ReportService rs;

	@Autowired
	private UserAccountService us;

	@Autowired
	private WorkshopVisitTestReportSummary reportSummaryGenerator;

	@RequestMapping(value = "/userOwnReports", method = RequestMethod.GET)
	public String setupSearchHomePage(HttpServletRequest request, Model model) {

		List<ReportQueryObject> ownReports = rs.findUserOwnReportList(us
				.findLoginUser().getId());

		model.addAttribute("reportSearchList", ownReports);

		// if (request.isUserInRole("ROLE_ADMIN")) {
		// model.addAttribute("awaitApproval",
		// rs.findReportsAwaitingApproval());
		// }

		return "search/userOwnReports";
	}

	@RequestMapping(value = "/showAllUserOwnReports", method = RequestMethod.GET)
	public String showAllUserOwnReports(
			Model model,
			@ModelAttribute("reportSearchList") List<ReportQueryObject> reportSearchList) {

		// Nollataan searchReportCriteria jotta se ei näytä vanhoja hakuehtoja
		// jos käyttäjä menee raporttilistan kautta raporttien hakuun
		model.addAttribute("searchReportCriteria", new SearchReportCriteria());
		return "redirect:showSearchResult?page=1";
	}

	@RequestMapping(value = "/searchReport", method = RequestMethod.GET)
	public String setupSearchForm(Model model) {

		model.addAttribute("searchReportCriteria", new SearchReportCriteria());

		model.addAttribute("importers", is.findImportersInAplhaOrder());
		model.addAttribute("workshops", ws.findWorkshopsInAplhaOrder());
		model.addAttribute("users", us.findUsersInAlphaOrder());
		return "search/searchReport";
	}

	@RequestMapping(value = "/searchReport", method = RequestMethod.POST)
	public String processSearchForm(
			Model model,
			@ModelAttribute("searchReportCriteria") SearchReportCriteria searchReportCriteria) {

		searchReportCriteria.setCriteriaSet(true);
		model.addAttribute("reportSearchList",
				rs.findReportsBySearchCriteria(searchReportCriteria));
		model.addAttribute("paginateFrom", 1);

		return "redirect:showSearchResult?page=1";
	}

	@RequestMapping(value = "/showSearchResult", method = RequestMethod.GET)
	public String paginateSearchResult(
			Model model,
			@ModelAttribute("reportSearchList") List<ReportQueryObject> reportSearchList,
			@RequestParam("page") Integer page) {

		int currentPage = page;

		int reportListStart;
		int reportListEnd;

		int pageCount = (int) Math.ceil(reportSearchList.size() / 10.0);

		if (reportSearchList.size() == 0)
			pageCount = 0;

		if (currentPage == 1)
			reportListStart = 0;
		else
			reportListStart = (currentPage - 1) * 10;

		if ((currentPage * 10) >= reportSearchList.size())
			reportListEnd = reportSearchList.size() - 1;
		else
			reportListEnd = reportListStart + 9;

		model.addAttribute("pageCount", pageCount);

		model.addAttribute("currentPage", currentPage);

		model.addAttribute("reportSearchList", reportSearchList);
		model.addAttribute("reportListStart", reportListStart);
		model.addAttribute("reportListEnd", reportListEnd);

		return "search/searchResult";
	}

	@RequestMapping(value = "/modifySearch", method = RequestMethod.GET)
	public String showAllUserOwnReports(
			Model model,
			@ModelAttribute("searchReportCriteria") SearchReportCriteria searchReportCriteria) {

		model.addAttribute("searchReportCriteria", searchReportCriteria);
		return "search/searchReport";

	}

	@RequestMapping(value = "/reportSummaries", method = RequestMethod.GET)
	public String prepareSelectImporterForm(Model model) {

		model.addAttribute("importers", is.getImporters());
		return "search/selectImporterForSummary";

	}

	@RequestMapping(value = "/reportSummaries", method = RequestMethod.POST)
	public String processSelectImporterForm(Model model,
			@RequestParam("importer") Long importerId) {

		ReportSummarySearchCriteria criteria = new ReportSummarySearchCriteria();
		criteria.setImporter(is.findImporterById(importerId));
		model.addAttribute("summarySearchCriteria", criteria);

		return "search/selectReportTemplateForSummary";

	}

	@RequestMapping(value = "/templateForReportSummary", method = RequestMethod.POST)
	public String prepareSelectSummaryTemplateForm(
			Model model,
			@RequestParam("templateId") Long id,
			@ModelAttribute("summarySearchCriteria") ReportSummarySearchCriteria criteria) {

		for (ReportTemplate template : criteria.getImporter()
				.getReportTemplates()) {

			if (template.getId().equals(id)) {

				criteria.setTemplate(template);

			}

		}

		model.addAttribute("summarySearchCriteria", criteria);
		return "search/createReportSummary";

	}

	@RequestMapping(value = "/createReportSummary", method = RequestMethod.POST)
	public ResponseEntity<byte[]> createReportSummary(
			Model model,
			@ModelAttribute("summarySearchCriteria") ReportSummarySearchCriteria criteria) {

		ReportSummary reportSummary = rs.generateReportSummary(criteria);
		// for (ReportPartSummary partSummary : reportSummary
		// .getReportPartSummaries()) {
		// // log.debug("\nRAPORTINOSA: "
		// // + partSummary.getReportTemplatePart().getTitle());
		// // log.debug("Tulos: " + partSummary.getAverageScorePercengage()
		// // + " %");
		// for (QuestionGroupSummary groupSummary : partSummary
		// .getQuestionGroupSummaries()) {
		// // log.debug("\nKYSYMYSRYHMÄ: "
		// // + groupSummary.getReportTemplateQuestionGroup()
		// // .getTitle());
		// // log.debug("Tulos: " +
		// // groupSummary.getAverageScorePercengage()
		// // + " %\n\n");
		// for (AnswerSummary answerSummary : groupSummary
		// .getAnswerSummaries()) {
		// if (answerSummary instanceof MultipleChoiceAnswerSummary) {
		// MultipleChoiceAnswerSummary mcaSummary =
		// (MultipleChoiceAnswerSummary) answerSummary;
		// // log.debug(mcaSummary.getQuestion().getQuestion());
		// // for (Map.Entry<String, Integer> entry : mcaSummary
		// // .getChosenOptionsCount().entrySet())
		// // log.debug(entry.getKey() + " - " + entry.getValue()
		// // + " kpl");
		// log.debug("MULTIPLECHOICEQUESTION "
		// + mcaSummary.getQuestion().getQuestion()
		// + " keskiarvopisteet : "
		// + mcaSummary.getAverageScore());
		// }
		//
		// if (answerSummary instanceof PointsAnswerSummary) {
		// PointsAnswerSummary ptSummary = (PointsAnswerSummary) answerSummary;
		// log.debug("POINTS ANSWER SUMMARY: "
		// + ptSummary.getQuestion().getQuestion()
		// + " keskiarvopisteet : "
		// + ptSummary.getAverageScore()
		// + "\n Vastaukset : ");
		// int points = 0;
		// for (int count : ptSummary.getAnswerCountForPoints()) {
		// log.debug("\n" + points++ + " pistettä : " + count
		// + " kertaa.");
		// }
		//
		// }
		//
		// }
		// }
		// }

		try {

			byte[] contents = reportSummaryGenerator
					.generateWorkshopVisitTestReportSummary(reportSummary)
					.toByteArray();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = reportSummary.getImporter().getName()
					+ "-raporttiyhteenveto" + ".pdf";
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
}