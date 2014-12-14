package fi.testcenter.domain.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class MultipleChoiceQuestion extends Question {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "MCQ_ID")
	private List<MultipleChoiceOption> options = new ArrayList<MultipleChoiceOption>();

	private boolean multipleSelectionsAllowed = false;

	public MultipleChoiceQuestion() {

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

	public boolean isMultipleSelectionsAllowed() {
		return multipleSelectionsAllowed;
	}

	public void setMultipleSelectionsAllowed(boolean multipleSelectionAllowed) {
		this.multipleSelectionsAllowed = multipleSelectionAllowed;
	}

}
