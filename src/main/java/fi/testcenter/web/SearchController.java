package fi.testcenter.web;

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

@SessionAttributes("report")
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

	@RequestMapping(value = "/searchReportHome", method = RequestMethod.GET)
	public String setupSearchHomePage(HttpServletRequest request, Model model) {

		model.addAttribute("reportSearchList",
				rs.getReportsByUserId(us.getLoginUser().getId()));

		if (request.isUserInRole("ROLE_ADMIN")) {
			model.addAttribute("awaitApproval", rs.getReportsAwaitingApproval());
		}

		return "searchReportHome";
	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		Report selectedReport = rs.getReportById(id.longValue());

		log.debug("Tietokannasta haettu vastaus vastaus: "
				+ selectedReport.getAnswers().get(0));
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

		return "searchResult";
	}
}