package fi.testcenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportQueryObject;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@SessionAttributes({ "report", "reportSearchList", "readyReport",
		"searchReportCriteria", "importers", "workshops", "users" })
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
}