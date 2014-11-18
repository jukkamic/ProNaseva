package fi.testcenter.domain.question;

import javax.persistence.Entity;

@Entity
public class PointsQuestion extends Question {

	private String question;
	private int maxPoints;

	public PointsQuestion() {

	}

	public PointsQuestion(String question, int maxPoints) {
		this.question = question;
		this.maxPoints = maxPoints;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

}
