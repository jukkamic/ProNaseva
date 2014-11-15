package fi.testcenter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

public class AdminController {

	@Controller
	@RequestMapping("/")
	@SessionAttributes(value = { "report", "dbReports" })
	public class ReportController {

		@RequestMapping(value = "/admin", method = RequestMethod.GET)
		public String submitNewReportBasicInfo() {
			return "admin";
		}
	}
}
