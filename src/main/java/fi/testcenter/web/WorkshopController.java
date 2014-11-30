package fi.testcenter.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.Workshop;
import fi.testcenter.service.WorkshopService;

@SessionAttributes(value = "workshop")
@Controller
public class WorkshopController {

	@Autowired
	private WorkshopService ws;

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

		return "userWorkshopImporter/showWorkshopList";
	}

	@RequestMapping(value = "/newWorkshop", method = RequestMethod.GET)
	public String prepareNewWorkshopForm(Model model) {

		model.addAttribute("workshop", new Workshop());

		return "userWorkshopImporter/editWorkshop";
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

		return "userWorkshopImporter/showWorkshop";
	}

	@RequestMapping(value = "/admin/editWorkshop", method = RequestMethod.GET)
	public String prepareEditWorkshopForm(
			@ModelAttribute("workshop") Workshop workshop, Model model,
			@RequestParam("id") Integer id) {

		model.addAttribute("workshop", workshop);
		model.addAttribute("edit", "TRUE");

		return "userWorkshopImporter/editWorkshop";
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
}
