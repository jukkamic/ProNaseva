package fi.testcenter.domain.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.answer.Answer;

@Entity
public class OptionalQuestions extends Question {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderColumn(name = "ORDERINDEX")
	List<Question> questions = new ArrayList<Question>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderColumn(name = "ORDERINDEX")
	List<Answer> answers = new ArrayList<Answer>();

	public OptionalQuestions() {
	}

	public OptionalQuestions(List<Question> questions) {
		this.questions = questions;

	}

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
