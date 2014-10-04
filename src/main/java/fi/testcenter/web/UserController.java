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
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.domain.User;
import fi.testcenter.service.UserAccountService;

@SessionAttributes("user")
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
	public String prepareNewUserForm(HttpServletRequest request, Model model) {

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Testaaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");

		model.addAttribute("roles", roles);

		model.addAttribute("user", new User());

		return "editUser";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.POST)
	public String processNewUserForm(HttpServletRequest request, Model model,
			@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			BindingResult result) {

		log.debug(confirmPassword);

		us.saveUser(user);

		return "redirect:/admin/user";
	}

	@RequestMapping(value = "/admin/editUser", method = RequestMethod.GET)
	public String prepareEditUserForm(HttpServletRequest request, Model model,
			@ModelAttribute("id") Integer id, BindingResult result) {

		model.addAttribute("user", us.getUserById(id));

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Testaaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");

		model.addAttribute("roles", roles);

		model.addAttribute("edit", "TRUE");

		return "editUser";
	}

	@RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
	public String deleteUser(HttpServletRequest request, Model model,
			@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			BindingResult result) {

		us.saveUser(user);

		return "redirect:/admin/user";

	}

	@RequestMapping(value = "/admin/deleteUser", method = RequestMethod.GET)
	public String processEditUserForm(HttpServletRequest request, Model model,
			@ModelAttribute("user") User user) {

		us.deleteUser(user);

		return "redirect:/admin/user";
	}

}
