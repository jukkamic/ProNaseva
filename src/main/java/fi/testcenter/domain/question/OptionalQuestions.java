package fi.testcenter.domain.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class OptionalQuestions extends Question {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "QUESTION_ID")
	@OrderColumn(name = "OPTIONAL_Q_ORDER")
	List<Question> questions = new ArrayList<Question>();

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

}
