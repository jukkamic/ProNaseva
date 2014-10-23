package fi.testcenter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.User;
import fi.testcenter.repository.UserRepository;

@Service
public class UserAccountService {

	@Autowired
	UserRepository ur;

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

	@Transactional
	public User getUserById(Integer id) {
		return ur.findOne(id.longValue());
	}

	@Transactional
	public void deleteUser(User user) {
		user.setEnabled(false);
		ur.save(user);
	}

	@Transactional
	public User getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return em.createNamedQuery("getLoginUser", User.class)
				.setParameter("userName", auth.getName()).getSingleResult();

	}

}
