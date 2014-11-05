package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class MultipleChoiceQuestion extends Question {

	private String question;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "MULTIPLECHOICEQUESTION_MULTIPLECHOICEOPTION", joinColumns = @JoinColumn(name = "MULTIPLECHOICEQUESTION_ID"), inverseJoinColumns = @JoinColumn(name = "MULTIPLECHOICEOPTION_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private List<MultipleChoiceOption> options = new ArrayList<MultipleChoiceOption>();

	private int chosenOptionIndex;

	private boolean multipleSelectionsAllowed = false;

	public MultipleChoiceQuestion() {
		this.chosenOptionIndex = -1;
	}

	public int getChosenOptionIndex() {
		return chosenOptionIndex;
	}

	public void setChosenOptionIndex(int chosenOptionIndex) {
		this.chosenOptionIndex = chosenOptionIndex;
	}

	public void setOptions(List<MultipleChoiceOption> options) {
		this.options = options;
	}

	public List<MultipleChoiceOption> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<MultipleChoiceOption> options) {
		this.options = options;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isMultipleSelectionsAllowed() {
		return multipleSelectionsAllowed;
	}

	public void setMultipleSelectionsAllowed(boolean multipleSelectionAllowed) {
		this.multipleSelectionsAllowed = multipleSelectionAllowed;
	}

}
