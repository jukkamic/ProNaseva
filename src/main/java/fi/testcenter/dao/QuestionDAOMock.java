package fi.testcenter.dao;

import java.util.ArrayList;
import java.util.List;

import fi.testcenter.domain.Question;

public class QuestionDAOMock implements QuestionDAO {

	@Override
	public List<Question> getQuestions() {
		
		List<Question> questions = new ArrayList<Question>();
		questions.add(new Question("eka kysymys"));		
		questions.add(new Question("toka kysymys"));		

		return questions;
	}

}
