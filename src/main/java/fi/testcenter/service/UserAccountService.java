package fi.testcenter.service;

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

	public List<User> getUserList() {
		return ur.findAll();
	}

}
