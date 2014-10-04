package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.User;
import fi.testcenter.repository.UserRepository;

@Service
public class UserAccountService {

	@Autowired
	UserRepository ur;

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

		ArrayList<User> deleteUser = new ArrayList<User>();
		deleteUser.add(user);
		ur.deleteInBatch(deleteUser);
	}

}
