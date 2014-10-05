package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MultipleChoiceOption {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long choiceID;

	private MultipleChoiceQuestion multiQuestion;

	private String option;
	private String radiobuttonText;

	private int points;

	public MultipleChoiceOption() {
	}

	public MultipleChoiceOption(String option, String radiobuttonText,
			int points) {
		this.option = option;
		this.radiobuttonText = radiobuttonText;
		this.points = points;
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

	public long getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(long choiceID) {
		this.choiceID = choiceID;
	}

	public MultipleChoiceQuestion getMultiQuestion() {
		return multiQuestion;
	}

	public void setMultiQuestion(MultipleChoiceQuestion multiQuestion) {
		this.multiQuestion = multiQuestion;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getRadiobuttonText() {
		return radiobuttonText;
	}

	public void setRadiobuttonText(String radiobuttonText) {
		this.radiobuttonText = radiobuttonText;
	}

	public String toString() {
		return this.option;
	}

}
