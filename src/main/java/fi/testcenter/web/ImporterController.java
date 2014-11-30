package fi.testcenter.web;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Importer;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.ReportTemplateService;

@SessionAttributes(value = "importer")
@Controller
public class ImporterController {

	Logger log = Logger.getLogger("fi.testcenter.web.ImporterController");

	@Autowired
	private ImporterService is;

	@Autowired
	private ReportTemplateService rts;

	@RequestMapping(value = "/showImporterList", method = RequestMethod.GET)
	public String prepareImporterPage(Model model,
			@RequestParam("page") Integer page) {

		List<Importer> importers = is.getImporters();

		int currentPage = page;

		int importerListStart;
		int importerListEnd;

		int pageCount = (int) Math.ceil(importers.size() / 10.0);

		if (importers.size() == 0)
			pageCount = 0;

		if (currentPage == 1)
			importerListStart = 0;
		else
			importerListStart = (currentPage - 1) * 10;

		if ((currentPage * 10) >= importers.size())
			importerListEnd = importers.size() - 1;
		else
			importerListEnd = importerListStart + 9;

		model.addAttribute("pageCount", pageCount);

		model.addAttribute("currentPage", currentPage);

		model.addAttribute("importers", importers);
		model.addAttribute("importerListStart", importerListStart);
		model.addAttribute("importerListEnd", importerListEnd);

		return "userWorkshopImporter/showImporterList";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.GET)
	public String prepareNewImporterForm(Model model) {

		model.addAttribute("importer", new Importer());
		model.addAttribute("reportTemplateList",
				rts.findCurrentReportTemplates());

		return "userWorkshopImporter/editImporter";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.POST)
	public String processNewImporterForm(
			@ModelAttribute("importer") Importer importer) {

		is.saveImporter(importer);

		return "redirect:/showImporterList?page=1";
	}

	@RequestMapping(value = "/showImporter", method = RequestMethod.GET)
	public String showImporter(Model model, @RequestParam("id") Integer id) {

		model.addAttribute("importer", is.finImporterById(id.longValue()));

		model.addAttribute("reportTemplateList",
				rts.findCurrentReportTemplates());

		return "userWorkshopImporter/showImporter";
	}

	@RequestMapping(value = "/admin/editImporter", method = RequestMethod.GET)
	public String prepareEditImporterForm(Model model,
			@RequestParam("id") Integer id) {

		model.addAttribute("importer", is.finImporterById(id.longValue()));
		model.addAttribute("edit", "TRUE");
		model.addAttribute("reportTemplateList",
				rts.findCurrentReportTemplates());

		return "userWorkshopImporter/editImporter";
	}

	@RequestMapping(value = "/admin/editImporter", method = RequestMethod.POST)
	public String processEditImporterForm(
			@ModelAttribute("importer") Importer importer) {

		is.saveImporter(importer);

		return "redirect:/showImporter?id=" + importer.getId();
	}

	@RequestMapping(value = "/admin/deleteImporter")
	public String deleteImporter(@ModelAttribute("importer") Importer importer) {

		is.deleteImporter(importer);
		return "redirect:/showImporterList?page=1";
	}

}
