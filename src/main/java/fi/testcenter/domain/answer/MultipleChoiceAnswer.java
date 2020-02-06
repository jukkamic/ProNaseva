package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class MultipleChoiceAnswer extends Answer {

	private int[] chosenOptionsIndex;

	@ManyToMany(fetch = FetchType.EAGER)
	@OrderColumn
	private List<MultipleChoiceOption> chosenOptions;

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

	public MultipleChoiceAnswer(
			OptionalQuestionsAnswer optionalQuestionsAnswer, Question question,
			int answerOrderNumber) {
		super(optionalQuestionsAnswer, question, answerOrderNumber);

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

	public int[] getChosenOptionsIndex() {
		return chosenOptionsIndex;
	}

	public void setChosenOptionsIndex(int[] chosenOptionsIndex) {
		this.chosenOptionsIndex = chosenOptionsIndex;
	}

	public List<MultipleChoiceOption> getChosenOptions() {
		return chosenOptions;
	}

	public void setChosenOptions(List<MultipleChoiceOption> chosenOptions) {
		this.chosenOptions = chosenOptions;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setScoreForChosenOptions() {

		MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) super
				.getQuestion();

		super.setScore(-1);
		super.setMaxScore(0);

		int questionMaxScore = 0;
		for (MultipleChoiceOption option : mcq.getOptionsList()) {

			if (option.getPoints() != -1) {
				if (questionMaxScore < option.getPoints()
						&& !mcq.isMultipleSelectionsAllowed())
					questionMaxScore += option.getPoints();
				if (mcq.isMultipleSelectionsAllowed())
					questionMaxScore += option.getPoints();
			}

			if (chosenOptions != null && chosenOptions.contains(option)) {
				if (super.getScore() == -1)
					super.setScore(0);

				if (mcq.isMultipleSelectionsAllowed() == true)
					super.setScore(super.getScore() + option.getPoints());
				else
					super.setScore(option.getPoints());
			}
			super.setMaxScore(questionMaxScore);
		}
	}
}
