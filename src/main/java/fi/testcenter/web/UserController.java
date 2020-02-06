package fi.testcenter.web;

import java.util.ArrayList;
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
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.UserAccountService;
import fi.testcenter.service.WorkshopService;

@SessionAttributes({ "user", "roles", "oldPassword", "importerList" })
@Controller
public class UserController {

	@Autowired
	UserAccountService us;

	@Autowired
	WorkshopService ws;

	@Autowired
	ImporterService is;

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
		roles.put("IT-tuki", "ROLE_SUPPORT");

		model.addAttribute("editPassword", true);
		model.addAttribute("importerList", is.findImportersInAplhaOrder());
		model.addAttribute("roles", roles);

		model.addAttribute("user", new User());

		return "userWorkshopImporter/editUser";
	}

	@RequestMapping(value = "/admin/newUser", method = RequestMethod.POST)
	public String processNewUserForm(@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword,
			@RequestParam("editWorkshopList") boolean editWorkshopList,
			BindingResult result, Model model) {

		List<String> errorMessages = new ArrayList<String>();
		List<String> successMessages = new ArrayList<String>();
		List<String> alertMessages = new ArrayList<String>();

		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user
					.getPassword()));
			if (user.getChosenImporterId() != null)
				user.setImporter(is.findImporterById(user.getChosenImporterId()));
			model.addAttribute("user", us.saveUser(user));
			if (editWorkshopList != true) {
				successMessages.add("Käyttäjätili luotu!");

				model.addAttribute("successMessages", successMessages);
			}

		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);
		}

		if (editWorkshopList == true) {

			if (user.getImporter() == null) {
				alertMessages
						.add("Valitse ensin käyttäjän edustama maahantuoja.");
				model.addAttribute("alertMessages", alertMessages);
				return "userWorkshopImporter/editUser";

			}
			if (user.getImporter().getWorkshops() == null
					|| user.getImporter().getWorkshops().isEmpty()) {
				alertMessages
						.add("Valitse ensin korjaamot käyttäjän edustamalle maahantuojalle.");
				model.addAttribute("alertMessages", alertMessages);
				return "userWorkshopImporter/editUser";
			}

			return "userWorkshopImporter/editWorkshopsForUser";

		}

		return "userWorkshopImporter/showUser";
	}

	@RequestMapping(value = "/admin/editUser", method = RequestMethod.GET)
	public String prepareEditUserForm(Model model,
			@RequestParam("id") Integer id, @ModelAttribute("user") User user) {

		LinkedHashMap<String, String> roles = new LinkedHashMap<String, String>();
		roles.put("Testaaja", "ROLE_TESTER");
		roles.put("Asiakas", "ROLE_CLIENT");
		roles.put("Admin", "ROLE_ADMIN");
		roles.put("IT-tuki", "ROLE_SUPPORT");

		model.addAttribute("roles", roles);

		model.addAttribute("importerList", is.findImportersInAplhaOrder());
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
			@ModelAttribute("oldPassword") String password,
			@RequestParam("editWorkshopList") boolean editWorkshopList) {

		ArrayList<String> alertMessages = new ArrayList<String>();
		List<String> errorMessages = new ArrayList<String>();
		List<String> successMessages = new ArrayList<String>();

		try {
			if (user.getChosenImporterId() != null)
				user.setImporter(is.findImporterById(user.getChosenImporterId()));

		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);
		}
		model.addAttribute("user", user);

		if (editPassword == true) {

			model.addAttribute("editPassword", true);

			return "userWorkshopImporter/editUser";
		} else {
			if (user.getPassword() == "" || user.getPassword() == null) {
				user.setPassword(password);

			}

			else {
				user.setPassword(new BCryptPasswordEncoder().encode(user
						.getPassword()));
			}
		}
		try {
			user = us.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			errorMessages.add("Tapahtui virhe!");
			model.addAttribute("errorMessages", errorMessages);
		}

		if (editWorkshopList == true) {

			if (user.getImporter() == null) {
				alertMessages
						.add("Valitse ensin käyttäjän edustama maahantuoja.");
				model.addAttribute("alertMessages", alertMessages);
				return "userWorkshopImporter/editUser";

			}
			if (user.getImporter().getWorkshops() == null
					|| user.getImporter().getWorkshops().isEmpty()) {
				alertMessages
						.add("Valitse ensin korjaamot käyttäjän edustamalle maahantuojalle.");
				model.addAttribute("alertMessages", alertMessages);
				return "userWorkshopImporter/editUser";
			}

			return "userWorkshopImporter/editWorkshopsForUser";

		} else

			return "redirect:/admin/showUser?id=" + user.getId();

	}

	@RequestMapping(value = "/admin/saveUserWorkshopList")
	public String processEditWorkshopsForUserForm(
			@ModelAttribute("user") User user, Model model) {

		try {
			List<Workshop> newWsList = new ArrayList<Workshop>();
			if (user.getChosenWorkshopIds() != null
					&& !user.getChosenWorkshopIds().isEmpty()) {

				for (Long id : user.getChosenWorkshopIds()) {
					newWsList.add(ws.findWorkshop(id));
				}

			}
			user.setWorkshops(newWsList);
			user = us.saveUser(user);

		} catch (Exception e) {
			e.printStackTrace();

		}
		model.addAttribute("user", user);

		return "redirect:/admin/editUser?id=" + user.getId();
	}

	@RequestMapping(value = "/admin/deleteUser", method = RequestMethod.GET)
	public String processEditUserForm(@ModelAttribute("user") User user,
			Model model) {

		try {
			us.deleteUser(user);

		} catch (Exception e) {
			e.printStackTrace();

		}

		return "redirect:/admin/showUserList?page=1";
	}

	@RequestMapping(value = "/showWorkshopListForUser", method = RequestMethod.GET)
	public String showWorkshopListForImporter(
			@ModelAttribute("user") User user, Model model) {

		if (user.getWorkshops().isEmpty()) {
			ArrayList<String> alertMessages = new ArrayList<String>();
			alertMessages.add("Käyttäjälle ei ole valittu korjaamoja");
			model.addAttribute("alertMessages", alertMessages);
			return "userWorkshopImporter/showUser";
		}
		return "userWorkshopImporter/showWorkshopListForUser";
	}
}
