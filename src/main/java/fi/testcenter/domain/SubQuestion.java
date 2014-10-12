package fi.testcenter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	Question question;

	private TextAnswer answer = new TextAnswer();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TextAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(TextAnswer answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
