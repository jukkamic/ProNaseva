package fi.testcenter.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;

@Entity
public class TextAnswer extends Answer {

	@Column(length = 1500)
	String answer = "";

	public TextAnswer() {
	}

	public TextAnswer(Question question) {
		super(question);
	}

	public TextAnswer(Report report, Question question) {
		super(report, question);
	}

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
