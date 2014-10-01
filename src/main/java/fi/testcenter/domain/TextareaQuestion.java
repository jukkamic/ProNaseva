package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TextareaQuestion extends Question {

	private String question;

	@OneToOne
	private TextAnswer answer = new TextAnswer();

	public TextAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(TextAnswer answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}
