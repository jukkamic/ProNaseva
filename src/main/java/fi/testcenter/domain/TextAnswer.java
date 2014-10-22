package fi.testcenter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class TextAnswer extends Answer {

	@Column(length = 1500)
	String answer = "";

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String toString() {
		return answer;
	}

}
