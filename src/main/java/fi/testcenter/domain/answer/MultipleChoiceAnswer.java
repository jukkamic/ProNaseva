package fi.testcenter.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class MultipleChoiceAnswer extends Answer {

	private String[] chosenSelections;

	private int chosenOptionIndex = -1;

	@Column(length = 1500)
	private String remarks = "";

	public MultipleChoiceAnswer() {
	}

	public MultipleChoiceAnswer(Question question) {
		super(question);
	}

	public MultipleChoiceAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
	}

	public MultipleChoiceAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question) {
		super(reportQuestionGroup, question);
	}

	public MultipleChoiceAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
	}

	public MultipleChoiceAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber,
			int subquestionAnswerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber,
				subquestionAnswerOrderNumber);
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
