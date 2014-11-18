package fi.testcenter.domain.answer;

import javax.persistence.Entity;

@Entity
public class PointsAnswer extends Answer {

	private int givenPoints;
	private String remarks;

	public PointsAnswer() {
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
