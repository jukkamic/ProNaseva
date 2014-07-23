package fi.testcenter.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MULTIQ_ID")
	private MultipleChoiceQuestion question;

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

	public Integer getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(Integer choiceID) {
		this.choiceID = choiceID;
	}

	public MultipleChoiceQuestion getQuestion() {
		return question;
	}

	public void setQuestion(MultipleChoiceQuestion question) {
		this.question = question;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
