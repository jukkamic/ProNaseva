package fi.testcenter.domain.answer;

import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;

@Entity
public class PointsAnswer extends Answer {

	private int givenPoints;
	private String remarks;

	public PointsAnswer() {
		this.givenPoints = -1;
	}

	public PointsAnswer(Question question) {
		super(question);
	}

	public PointsAnswer(Report report, Question question) {
		super(report, question);
		this.givenPoints = -1;
	}

	public int getGivenPoints() {
		return givenPoints;
	}

	public void setGivenPoints(int givenPoints) {
		this.givenPoints = givenPoints;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
