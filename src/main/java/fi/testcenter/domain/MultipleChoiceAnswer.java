package fi.testcenter.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class MultipleChoiceAnswer extends Answer {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private MultipleChoiceOption chosenOption;

	private int chosenOptionIndex;

	private String remarks = "";

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public MultipleChoiceOption getChosenOption() {
		return chosenOption;
	}

	public void setChosenOption(MultipleChoiceOption chosenOption) {
		this.chosenOption = chosenOption;
	}

	public int getChosenOptionIndex() {
		return chosenOptionIndex;
	}

	public void setChosenOptionIndex(int chosenOptionIndex) {
		this.chosenOptionIndex = chosenOptionIndex;
	}

}