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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Report;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@SessionAttributes({ "report", "reportSearchList" })
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

		model.addAttribute("reportSearchList",
				rs.getReportsByUserId(us.getLoginUser().getId()));

		if (request.isUserInRole("ROLE_ADMIN")) {
			model.addAttribute("awaitApproval", rs.getReportsAwaitingApproval());
		}

		return "userOwnReports";
	}

	@RequestMapping(value = "/showAllUserOwnReports", method = RequestMethod.GET)
	public String showAllUserOwnReports(HttpServletRequest request,
			Model model,
			@ModelAttribute("reportSearchList") List<Report> reportSearchList) {

		return "redirect:showSearchResult?page=1";
	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		Report selectedReport = rs.getReportById(id.longValue());

		model.addAttribute("report", selectedReport);
		model.addAttribute("edit", "TRUE");
		model.addAttribute("readyReport", selectedReport);

		return "showReport";

	}

	@RequestMapping(value = "/searchReport", method = RequestMethod.GET)
	public String setupSearchForm(HttpServletRequest request, Model model) {

		model.addAttribute("searchReportCriteria", new SearchReportCriteria());
		model.addAttribute("importers", is.getImporters());
		model.addAttribute("workshops", ws.getWorkshops());
		model.addAttribute("users", us.getUserList());

		return "searchReport";
	}

	@RequestMapping(value = "/searchReport", method = RequestMethod.POST)
	public String processSearchForm(
			HttpServletRequest request,
			Model model,
			@ModelAttribute("searchReportCriteria") SearchReportCriteria searchReportCriteria,
			BindingResult result) {

		model.addAttribute("reportSearchList",
				rs.searchReports(searchReportCriteria));
		model.addAttribute("paginateFrom", 1);

		return "redirect:showSearchResult?page=1";
	}

	@RequestMapping(value = "/showSearchResult", method = RequestMethod.GET)
	public String paginateSearchResult(HttpServletRequest request, Model model,
			@ModelAttribute("reportSearchList") List<Report> reportSearchList,
			@RequestParam("page") Integer page) {

		int currentPage = page;

		int reportListStart;
		int reportListEnd;

		int pageCount = (reportSearchList.size() + 7 - 1) / 7;

		if (reportSearchList.size() == 0)
			pageCount = 0;

		if (currentPage == 1)
			reportListStart = 0;
		else
			reportListStart = (currentPage - 1) * 7 + 1;
		if (((reportListStart + 1) * 7) >= reportSearchList.size())
			reportListEnd = reportSearchList.size();
		else
			reportListEnd = reportListStart + 7;

		model.addAttribute("pageCount", pageCount);

		model.addAttribute("currentPage", currentPage);

		model.addAttribute("reportSearchList", reportSearchList);
		model.addAttribute("reportListStart", reportListStart);
		model.addAttribute("reportListEnd", reportListEnd);

		return "searchResult";
	}

	@RequestMapping(value = "/showPage", method = RequestMethod.GET)
	public String showSearchPage(HttpServletRequest request, Model model,
			@ModelAttribute("reportSearchList") List<Report> reportSearchList,
			@RequestParam("page") Integer page) {

		int currentPage = page;

		return "searchResult";
	}

}