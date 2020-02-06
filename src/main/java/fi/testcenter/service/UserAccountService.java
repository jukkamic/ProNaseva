package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.User;
import fi.testcenter.domain.report.Report;
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
	public User saveUser(User user) {
		return ur.save(user);
	}

	@Transactional
	public List<User> findAllUsers() {
		return ur.findAll();
	}

	@Transactional
	public List<User> findActiveUsers() {
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
	public List<User> findUsersInAlphaOrder() {
		List<Object[]> userList = em.createQuery(
				"SELECT u.lastName, u FROM User u ORDER BY u.lastName ASC")
				.getResultList();
		List<User> userReturnList = new ArrayList<User>();
		for (Object[] listItem : userList) {
			userReturnList.add((User) listItem[1]);
		}
		return userReturnList;
	}

	@Transactional
	public User findUserById(Integer id) {
		return ur.findOne(id.longValue());
	}

	@Transactional
	public void deleteUser(User user) {
		List<Report> userReports = new ArrayList<Report>();
		try {
			Query query = em
					.createQuery("SELECT COUNT(r) FROM Report r WHERE r.user = :user");
			query.setParameter("user", user);
			Long reportCount = (Long) query.getSingleResult();

			if (reportCount.equals(0))
				ur.delete(user.getId());
			else {
				user.setEnabled(false);
				user = ur.save(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public User findLoginUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return em.createNamedQuery("getLoginUser", User.class)
				.setParameter("userName", auth.getName()).getSingleResult();

	}

}
