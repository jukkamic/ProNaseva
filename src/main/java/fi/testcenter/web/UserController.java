package fi.testcenter.web;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@SessionAttributes({ "user", "roles", "oldPassword" })
@Controller
public class UserController {

	@Autowired
	UserAccountService us;

	Logger log = Logger.getLogger("fi.testcenter.web.UserController");

	@RequestMapping(value = "/admin/showUserList", method = RequestMethod.GET)
	public String showUserAdminPage(Model model,
			@RequestParam("page") Integer page) {

		List<User> userList = us.findActiveUsers();

		int currentPage = page;

		int userListStart;
		int userListEnd;

		int pageCount = (int) Math.ceil(userList.size() / 10.0);

		if (userList.size() == 0)
			pageCount = 0;

		if (currentPage == 1)
			userListStart = 0;
		else
			userListStart = (currentPage - 1) * 10;

		if ((currentPage * 10) >= userList.size())
			userListEnd = userList.size() - 1;
		else
			userListEnd = userListStart + 9;

		model.addAttribute("pageCount", pageCount);

		model.addAttribute("currentPage", currentPage);

		model.addAttribute("users", userList);
		model.addAttribute("userListStart", userListStart);
		model.addAttribute("userListEnd", userListEnd);

		return "userWorkshopImporter/showUserList";
	}

	@RequestMapping(value = "/admin/showUser", method = RequestMethod.GET)
	public String processEditUserForm(Model model,
			@RequestParam("id") Integer id) {

		model.addAttribute("user", us.findUserById(id));

		return "userWorkshopImporter/showUser";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.GET)
	public String prepareNewUserForm(Model model) {

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Testaaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");

		model.addAttribute("editPassword", true);

		model.addAttribute("roles", roles);

		model.addAttribute("user", new User());

		return "userWorkshopImporter/editUser";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.POST)
	public String processNewUserForm(@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			BindingResult result, Model model) {

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		us.saveUser(user);

		return "redirect:/admin/showUserList?page=1";
	}

	@RequestMapping(value = "/admin/editUser", method = RequestMethod.GET)
	public String prepareEditUserForm(Model model,
			@RequestParam("id") Integer id, @ModelAttribute("user") User user) {

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Testaaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");

		model.addAttribute("roles", roles);
		model.addAttribute("oldPassword", user.getPassword());
		user.setPassword("");
		model.addAttribute("user", user);

		model.addAttribute("edit", "TRUE");

		return "userWorkshopImporter/editUser";
	}

	@RequestMapping(value = "/admin/editUser", method = RequestMethod.POST)
	public String deleteUser(Model model, @ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("editPassword") boolean editPassword,
			@ModelAttribute("oldPassword") String password) {

		if (editPassword == true) {

			model.addAttribute("editPassword", true);

			return "userWorkshopImporter/editUser";
		} else {
			if (user.getPassword() == "") {
				user.setPassword(password);

			}

			else {
				user.setPassword(new BCryptPasswordEncoder().encode(user
						.getPassword()));

			}
		}
		us.saveUser(user);

		return "redirect:/admin/showUser?id=" + user.getId();
	}

	@RequestMapping(value = "/admin/deleteUser", method = RequestMethod.GET)
	public String processEditUserForm(@ModelAttribute("user") User user) {

		us.deleteUser(user);

		return "redirect:/admin/showUserList?page=1";
	}

}
