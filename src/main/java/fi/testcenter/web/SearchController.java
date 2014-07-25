package fi.testcenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.testcenter.dao.ReportDAO;
import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.BasicInfoService;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportService;

@Controller
public class SearchController {

	Logger log = Logger.getLogger("fi.testcenter.web.SearchController");

	@Autowired
	private ImporterService is;

	@Autowired
	private BasicInfoService ws;

	@Autowired
	private ReportService rs;

	@Autowired
	ReportDAO rdao;

	@RequestMapping(value = "/searchReport", method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {

		List<Workshop> workshops = ws.getWorkshops();
		model.addAttribute("workshops", workshops);

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		model.addAttribute("searchReportCriteria", new SearchReportCriteria());

		return "searchReport";
	}

	@RequestMapping(value = "/searchReport", method = RequestMethod.POST)
	public String prepareNewReportBasicInfoForm(HttpServletRequest request,
			Model model) {

		return "redirect:/";
	}

}