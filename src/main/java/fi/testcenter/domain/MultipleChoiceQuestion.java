package fi.testcenter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

@Entity
public class MultipleChoiceQuestion extends Question implements Serializable {

	@Column(name = "MULTIQ_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer multiChoiceId;
	private String question;

	@OneToMany(mappedBy = "multiQuestion", cascade = CascadeType.PERSIST)
	private List<MultipleChoiceOption> options;

	private MultipleChoiceOption chosenOption;
	private String remarks;

	private int chosenOptionIndex;

	public MultipleChoiceQuestion() {
		this.chosenOptionIndex = -1;
	}

	public Integer getMultiChoiceId() {
		return multiChoiceId;
	}

	public void setMultiChoiceId(Integer multiChoiceId) {
		this.multiChoiceId = multiChoiceId;
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

	public MultipleChoiceOption getChosenOption() {
		return chosenOption;
	}

	public void setChosenOption(MultipleChoiceOption chosenOption) {
		this.chosenOption = chosenOption;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
