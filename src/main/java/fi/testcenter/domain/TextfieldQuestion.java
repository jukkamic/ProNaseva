package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TextfieldQuestion extends Question {

	private String question;

	@OneToOne
	private TextAnswer answer = new TextAnswer();

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public TextAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(TextAnswer answer) {
		this.answer = answer;
	}

}
