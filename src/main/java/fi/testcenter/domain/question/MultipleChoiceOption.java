package fi.testcenter.domain.question;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MultipleChoiceOption {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;

	private String multipleChoiceOption;

	private String radiobuttonText;

	private int points;

	public MultipleChoiceOption() {
	}

	public MultipleChoiceOption(String option, String radiobuttonText,
			int points) {
		this.multipleChoiceOption = option;
		this.radiobuttonText = radiobuttonText;
		this.points = points;
	}

	public MultipleChoiceOption(String option, int points) {
		this.multipleChoiceOption = option;
		this.points = points;
	}

	public String getMultipleChoiceOption() {
		return multipleChoiceOption;
	}

	public void setMultipleChoiceOption(String option) {
		this.multipleChoiceOption = option;
	}

	public long getChoiceId() {
		return id;
	}

	public void setChoiceID(long id) {
		this.id = id;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String toString() {
		return this.multipleChoiceOption;
	}

}
