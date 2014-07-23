package fi.testcenter.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MultipleChoiceOption implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer choiceID;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MULTIQ_ID")
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

	public Integer getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(Integer choiceID) {
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

}
