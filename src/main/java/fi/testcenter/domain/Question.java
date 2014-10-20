package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Transient
	List<Question> subQuestions = new ArrayList<Question>();

	// Main question for subquestions
	Question mainQuestion;

	public Question() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Question> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<Question> subQuestions) {
		this.subQuestions = subQuestions;
	}

	public Question getMainQuestion() {
		return mainQuestion;
	}

	public void setMainQuestion(Question mainQuestion) {
		this.mainQuestion = mainQuestion;
	}

}
