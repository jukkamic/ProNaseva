package fi.testcenter.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class MultipleChoiceQuestion extends Question implements Serializable {

	private String question;
	private List<MultipleChoiceOption> options;
	private MultipleChoiceOption chosenOption;
	private String remarks;

	public MultipleChoiceOption getChosenOption() {
		return chosenOption;
	}

	public void setChosenOption(MultipleChoiceOption chosenOption) {
		this.chosenOption = chosenOption;
	}

	public List<MultipleChoiceOption> getOptions() {
		return options;
	}

	public void setOptions(List<MultipleChoiceOption> options) {
		this.options = options;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
