package fi.testcenter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.testcenter.service.ReportTemplateService;

@Controller
public class ReportTemplateController {

	@Autowired
	private ReportTemplateService rts;

	@RequestMapping(value = "/admin/reportTemplates", method = RequestMethod.GET)
	public String reportTemplates(Model model) {
		model.addAttribute("unusedTemplates", rts.findUnusedReportTemplates());
		return "report/reportTemplates";
	}

	@RequestMapping(value = "/admin/saveReportTemplate", method = RequestMethod.GET)
	public String saveReportTemplate(
			@RequestParam("name") String reportTemplateName) {

		rts.saveNewReportTemplate(reportTemplateName);
		return "redirect:/admin/reportTemplates";
	}

	@RequestMapping(value = "/admin/deleteReportTemplate", method = RequestMethod.GET)
	public String deleteReportTemplate(@RequestParam("id") Integer id) {
		rts.deleteReportTemplateById(id.longValue());
		return "redirect:/admin/reportTemplates";
	}

}
