package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class TextareaQuestion extends Question {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long Id;
	private String question;
	private String answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String toString() {
		return new String("Kysymys: " + this.question + " - vastaus: "
				+ this.answer);
	}

}