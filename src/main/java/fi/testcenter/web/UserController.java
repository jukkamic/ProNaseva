package fi.testcenter.web;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.testcenter.domain.User;
import fi.testcenter.service.UserAccountService;

@Controller
public class UserController {

	@Autowired
	UserAccountService us;

	Logger log = Logger.getLogger("fi.testcenter.web.UserController");

	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String showUserAdminPage(HttpServletRequest request, Model model) {

		model.addAttribute("users", us.getUserList());
		return "userAdmin";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.GET)
	public String setupForm(HttpServletRequest request, Model model) {

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Tarkastaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");

		model.addAttribute("roles", roles);

		model.addAttribute("user", new User());

		return "newUser";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.POST)
	public String processForm(HttpServletRequest request, Model model,
			@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			BindingResult result) {

		log.debug(confirmPassword);

		us.saveUser(user);

		return "redirect:/admin/user";
	}

}
