package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Report;
import fi.testcenter.domain.User;
import fi.testcenter.repository.UserRepository;

@Service
public class UserAccountService {

	@Autowired
	UserRepository ur;

	@Autowired
	ReportService rs;

	@PersistenceContext()
	EntityManager em;

	private Logger log = Logger
			.getLogger("fi.testcenter.service.ReportService");

	@Transactional
	public void saveUser(User user) {
		ur.save(user);
	}

	@Transactional
	public List<User> getUserList() {
		return ur.findAll();
	}

	public List<User> getActiveUserList() {
		List<Object[]> userList = em
				.createQuery(
						"SELECT u.lastName, u FROM User u WHERE u.enabled = TRUE order by u.lastName ASC")
				.getResultList();
		List<User> userReturnList = new ArrayList<User>();
		for (Object[] listItem : userList) {
			userReturnList.add((User) listItem[1]);
		}
		return userReturnList;
	}

	@Transactional
	public User getUserById(Integer id) {
		return ur.findOne(id.longValue());
	}

	@Transactional
	public void deleteUser(User user) {
		List<Report> userReports = new ArrayList<Report>();
		try {
			userReports = rs.getReportsByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (userReports.size() > 0) {
			user.setEnabled(false);
			ur.save(user);
		} else {
			try {
				ur.delete(user.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Transactional
	public User getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return em.createNamedQuery("getLoginUser", User.class)
				.setParameter("userName", auth.getName()).getSingleResult();

	}

}
