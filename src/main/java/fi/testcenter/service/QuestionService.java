package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.dao.QuestionDAOMock;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;

@Service
public class QuestionService {

	@Autowired
	private QuestionDAOMock qd;

	public List<Question> getQuestions() {
		return qd.getQuestions();
	}

	public List<QuestionGroup> getQuestionGroups() {
		return qd.getQuestionGroups();
	}

}
