package fi.testcenter.domain.question;

import javax.persistence.Entity;

@Entity
public class TextQuestion extends Question {

	private boolean textAreaInput;

	public TextQuestion() {
	}

	public TextQuestion(String question) {
		super.setQuestion(question);
	}

	public boolean isTextAreaInput() {
		return textAreaInput;
	}

	public void setTextAreaInput(boolean textAreaInput) {
		this.textAreaInput = textAreaInput;
	}

}
