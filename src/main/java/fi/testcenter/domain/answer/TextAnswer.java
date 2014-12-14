package fi.testcenter.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class TextAnswer extends Answer {

	@Column(length = 1500)
	String answer = "";

	public TextAnswer() {
	}

	public TextAnswer(Question question) {
		super(question);
	}

	public TextAnswer(ReportQuestionGroup reportQuestionGroup, Question question) {
		super(reportQuestionGroup, question);
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
