package fi.testcenter.domain.question;

import javax.persistence.Entity;

@Entity
public class DateQuestion extends Question {

	public DateQuestion() {
	}

	public DateQuestion(String question) {
		super.setQuestion(question);
	}
}
