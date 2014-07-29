package fi.testcenter.web;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.WorkshopService;

@Controller
public class ImporterWorkshopController {

	Logger log = Logger
			.getLogger("fi.testcenter.web.ImporterWorkshopController");

	@Autowired
	private ImporterService is;

	@Autowired
	private WorkshopService ws;

	@RequestMapping(value = "/importers", method = RequestMethod.GET)
	public String setupImporterPage(HttpServletRequest request, Model model) {

		List<Importer> importers = is.getImporters();
		model.addAttribute("importers", importers);

		return "importerAdmin";
	}

	@RequestMapping(value = "/workshops", method = RequestMethod.GET)
	public String setupWorkshopPage(HttpServletRequest request, Model model) {

		List<Workshop> workshops = ws.getWorkshops();
		model.addAttribute("workshops", workshops);

		return "workshopAdmin";
	}
}
