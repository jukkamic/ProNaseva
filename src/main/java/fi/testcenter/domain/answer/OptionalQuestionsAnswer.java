package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;

@Entity
public class OptionalQuestionsAnswer extends Answer {

	List<Question> questions;
	List<Answer> answers;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
