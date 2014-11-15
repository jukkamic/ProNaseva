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
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.WorkshopService;

@SessionAttributes(value = { "workshop", "importer" })
@Controller
public class ImporterWorkshopController {

	Logger log = Logger
			.getLogger("fi.testcenter.web.ImporterWorkshopController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@RequestMapping(value = "/importers", method = RequestMethod.GET)
	public String prepareImporterPage(Model model) {

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		return "importerAdmin";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.GET)
	public String prepareNewImporterForm(Model model) {

		model.addAttribute("importer", new Importer());

		return "editImporter";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.POST)
	public String processNewImporterForm(
			@ModelAttribute("importer") Importer importer) {

		is.saveImporter(importer);

		return "redirect:/importers";
	}

	@RequestMapping(value = "/showWorkshopList", method = RequestMethod.GET)
	public String prepareWorkshopPage(Model model,
			@RequestParam("page") Integer page) {

		List<Workshop> workshops = ws.findActiveWorkshops();

		int currentPage = page;

		int workshopListStart;
		int workshopListEnd;

		int pageCount = (int) Math.ceil(workshops.size() / 10.0);

		if (workshops.size() == 0)
			pageCount = 0;

		if (currentPage == 1)
			workshopListStart = 0;
		else
			workshopListStart = (currentPage - 1) * 10;

		if ((currentPage * 10) >= workshops.size())
			workshopListEnd = workshops.size() - 1;
		else
			workshopListEnd = workshopListStart + 9;

		model.addAttribute("pageCount", pageCount);

		model.addAttribute("currentPage", currentPage);

		model.addAttribute("workshops", workshops);
		model.addAttribute("workshopListStart", workshopListStart);
		model.addAttribute("workshopListEnd", workshopListEnd);

		return "showWorkshopList";
	}

	@RequestMapping(value = "/newWorkshop", method = RequestMethod.GET)
	public String prepareNewWorkshopForm(Model model) {

		model.addAttribute("workshop", new Workshop());

		return "editWorkshop";
	}

	@RequestMapping(value = "/newWorkshop", method = RequestMethod.POST)
	public String processNewWorkshopForm(
			@ModelAttribute("workshop") Workshop workshop) {

		ws.saveWorkshop(workshop);

		return "redirect:/showWorkshopList?page=1";
	}

	@RequestMapping(value = "/showWorkshop", method = RequestMethod.GET)
	public String showWorkshop(Model model, @RequestParam("id") Integer id) {

		model.addAttribute("workshop", ws.findWorkshopById(id.longValue()));

		return "showWorkshop";
	}

	@RequestMapping(value = "/admin/editWorkshop", method = RequestMethod.GET)
	public String prepareEditWorkshopForm(
			@ModelAttribute("workshop") Workshop workshop, Model model,
			@RequestParam("id") Integer id) {

		model.addAttribute("workshop", workshop);
		model.addAttribute("edit", "TRUE");

		return "editWorkshop";
	}

	@RequestMapping(value = "/admin/editWorkshop", method = RequestMethod.POST)
	public String processEditWorkshopForm(
			@ModelAttribute("workshop") Workshop workshop) {

		ws.saveWorkshop(workshop);

		return "redirect:/showWorkshop?id=" + workshop.getId();
	}

	@RequestMapping(value = "/admin/deleteWorkshop")
	public String deleteWorkshop(@ModelAttribute("workshop") Workshop workshop) {

		ws.deleteWorkshop(workshop);
		return "redirect:/showWorkshopList?page=1";
	}

	@RequestMapping(value = "/editImporter", method = RequestMethod.GET)
	public String prepareEditImporterForm(Model model,
			@RequestParam("id") Integer id) {

		model.addAttribute("importer", is.finImporterById(id.longValue()));
		model.addAttribute("edit", "TRUE");

		return "editImporter";
	}

	@RequestMapping(value = "/editImporter", method = RequestMethod.POST)
	public String processEditImporterForm(
			@ModelAttribute("importer") Importer importer) {

		is.saveImporter(importer);

		return "redirect:/importers";
	}

	@RequestMapping(value = "/deleteImporter")
	public String deleteImporter(@ModelAttribute("importer") Importer importer) {

		is.deleteImporter(importer);
		return "redirect:/importers";
	}

}
