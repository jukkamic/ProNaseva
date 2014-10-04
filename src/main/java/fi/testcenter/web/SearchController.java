package fi.testcenter.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Report;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;
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

	@RequestMapping(value = "/searchReport", method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {

		// List<Workshop> workshops = ws.getWorkshops();
		// model.addAttribute("workshops", workshops);
		//
		// List<Importer> importers = is.getImporters();
		// model.addAttribute("importers", importers);
		//
		// model.addAttribute("searchReportCriteria", new
		// SearchReportCriteria());
		//
		// return "searchReport";
		// }
		//
		// @RequestMapping(value = "/searchReport", method = RequestMethod.POST)
		// public String prepareNewReportBasicInfoForm(HttpServletRequest
		// request,
		// Model model) {

		model.addAttribute("reportSearchList", rs.findAllReports());

		return "searchResults";
	}

	@RequestMapping(value = "/searchReportSelect", method = RequestMethod.GET)
	public String showSelectedReport(HttpServletRequest request, Model model,
			@RequestParam("id") Integer id) {

		Report selectedReport = rs.getReportById(id.longValue());

		model.addAttribute("report", selectedReport);
		model.addAttribute("edit", "TRUE");

		return "showReport";

	}

}