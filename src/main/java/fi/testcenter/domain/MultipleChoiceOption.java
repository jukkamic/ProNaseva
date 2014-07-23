package fi.testcenter.domain;

import java.io.Serializable;

public class MultipleChoiceOption implements Serializable {

	Integer id;

	private String option;
	private int points;

	public MultipleChoiceOption() {
	}

	public MultipleChoiceOption(String option, int points) {
		this.option = option;
		this.points = points;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
