package fi.testcenter.domain.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	String question;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	@OrderColumn
	List<Question> subQuestions = new ArrayList<Question>();

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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
}
