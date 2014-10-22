package fi.testcenter.domain;

import javax.persistence.Entity;

@Entity
public class TextQuestion extends Question {

	private String question;
	private boolean textAreaInput;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isTextAreaInput() {
		return textAreaInput;
	}

	public void setTextAreaInput(boolean textAreaInput) {
		this.textAreaInput = textAreaInput;
	}

}
