package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.dao.QuestionDAO;
import fi.testcenter.domain.Question;

@Service
public class ReportService {
	
	@Autowired
	private QuestionDAO qd;
	
	public List<Question> getQuestions() {
		return qd.getQuestions();
	}

}
