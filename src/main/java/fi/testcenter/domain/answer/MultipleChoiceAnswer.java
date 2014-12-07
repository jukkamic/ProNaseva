package fi.testcenter.domain.answer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;

@Entity
public class MultipleChoiceAnswer extends Answer {

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private MultipleChoiceOption chosenOption;

	private String[] chosenSelections;

	private int chosenOptionIndex = -1;

	@Column(length = 1500)
	private String remarks = "";

	public MultipleChoiceAnswer() {
	}

	public MultipleChoiceAnswer(Question question) {
		super(question);
	}

	public MultipleChoiceAnswer(Report report, Question question) {
		super(report, question);
	}

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

	public String[] getChosenSelections() {
		return chosenSelections;
	}

	public void setChosenSelections(String[] chosenSelections) {
		this.chosenSelections = chosenSelections;
	}

}
