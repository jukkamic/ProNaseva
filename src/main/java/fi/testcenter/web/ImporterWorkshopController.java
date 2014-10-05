package fi.testcenter.web;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String prepareImporterPage(HttpServletRequest request, Model model) {

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		return "importerAdmin";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.GET)
	public String prepareNewImporterForm(HttpServletRequest request, Model model) {

		model.addAttribute("importer", new Importer());

		return "editImporter";
	}

	@RequestMapping(value = "/newImporter", method = RequestMethod.POST)
	public String processNewImporterForm(HttpServletRequest request,
			Model model, @ModelAttribute("importer") Importer importer,
			BindingResult result) {

		is.saveImporter(importer);

		return "redirect:/importers";
	}

	@RequestMapping(value = "/workshops", method = RequestMethod.GET)
	public String prepareWorkshopPage(HttpServletRequest request, Model model) {

		List<Workshop> workshops = ws.getWorkshops();
		model.addAttribute("workshops", workshops);

		return "workshopAdmin";
	}

	@RequestMapping(value = "/newWorkshop", method = RequestMethod.GET)
	public String prepareNewWorkshopForm(HttpServletRequest request, Model model) {

		model.addAttribute("workshop", new Workshop());

		return "editWorkshop";
	}

	@RequestMapping(value = "/newWorkshop", method = RequestMethod.POST)
	public String processNewWorkshopForm(HttpServletRequest request,
			Model model, @ModelAttribute("workshop") Workshop workshop,
			BindingResult result) {

		ws.saveWorkshop(workshop);

		return "redirect:/workshops";
	}

	@RequestMapping(value = "/editWorkshop", method = RequestMethod.GET)
	public String prepareEditWorkshopForm(HttpServletRequest request,
			Model model, @RequestParam("id") Integer id) {

		model.addAttribute("workshop", ws.getWorkshopById(id.longValue()));
		model.addAttribute("edit", "TRUE");

		return "editWorkshop";
	}

	@RequestMapping(value = "/editWorkshop", method = RequestMethod.POST)
	public String processEditWorkshopForm(HttpServletRequest request,
			Model model, @ModelAttribute("workshop") Workshop workshop,
			BindingResult result) {

		ws.saveWorkshop(workshop);

		return "redirect:/workshops";
	}

	@RequestMapping(value = "/deleteWorkshop")
	public String deleteWorkshop(HttpServletRequest request,
			@ModelAttribute("workshop") Workshop workshop) {

		ws.deleteWorkshop(workshop);
		return "redirect:/workshops";
	}

	@RequestMapping(value = "/editImporter", method = RequestMethod.GET)
	public String prepareEditImporterForm(HttpServletRequest request,
			Model model, @RequestParam("id") Integer id) {

		model.addAttribute("importer", is.getImporterById(id.longValue()));
		model.addAttribute("edit", "TRUE");

		return "editImporter";
	}

	@RequestMapping(value = "/editImporter", method = RequestMethod.POST)
	public String processEditImporterForm(HttpServletRequest request,
			Model model, @ModelAttribute("importer") Importer importer,
			BindingResult result) {

		is.saveImporter(importer);

		return "redirect:/importers";
	}

	@RequestMapping(value = "/deleteImporter")
	public String deleteImporter(HttpServletRequest request,
			@ModelAttribute("importer") Importer importer) {

		is.deleteImporter(importer);
		return "redirect:/importers";
	}

}
