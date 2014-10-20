package fi.testcenter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class TextQuestion extends Question {

	private String question;
	private boolean textAreaInput;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	public boolean isTextAreaInput() {
		return textAreaInput;
	}

	public void setTextAreaInput(boolean textAreaInput) {
		this.textAreaInput = textAreaInput;
	}

}
