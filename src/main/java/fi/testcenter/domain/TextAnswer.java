package fi.testcenter.domain;

import javax.persistence.Entity;

@Entity
public class TextAnswer extends Answer {

	String answer;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
