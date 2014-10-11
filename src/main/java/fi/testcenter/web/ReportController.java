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
import fi.testcenter.domain.Report;
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
		return "printTest";
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