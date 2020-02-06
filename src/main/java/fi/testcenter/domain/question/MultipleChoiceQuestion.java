package fi.testcenter.domain.question;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class MultipleChoiceQuestion extends Question {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	@OrderColumn
	private List<MultipleChoiceOption> optionsList = new ArrayList<MultipleChoiceOption>();

	private boolean multipleSelectionsAllowed = false;

	public MultipleChoiceQuestion() {

	}

	public MultipleChoiceQuestion(String question) {
		super.setQuestion(question);
	}

	public void setOptionsList(List<MultipleChoiceOption> options) {
		this.optionsList = options;
	}

	public List<MultipleChoiceOption> getOptionsList() {
		return optionsList;
	}

	public void setOptionsList(ArrayList<MultipleChoiceOption> options) {
		this.optionsList = options;
	}

	public boolean isMultipleSelectionsAllowed() {
		return multipleSelectionsAllowed;
	}

	public void setMultipleSelectionsAllowed(boolean multipleSelectionAllowed) {
		this.multipleSelectionsAllowed = multipleSelectionAllowed;
	}

}
