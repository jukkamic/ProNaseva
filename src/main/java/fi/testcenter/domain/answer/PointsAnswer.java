package fi.testcenter.domain.answer;

import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class PointsAnswer extends Answer {

	private int givenPoints;
	private String remarks;

	public PointsAnswer() {
		this.givenPoints = -1;
	}

	public PointsAnswer(Question question) {
		super(question);
		this.givenPoints = -1;
	}

	public PointsAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
		this.givenPoints = -1;
	}

	public PointsAnswer(OptionalQuestionsAnswer optionalQuestionsAnswer,
			Question question, int answerOrderNumber) {
		super(optionalQuestionsAnswer, question, answerOrderNumber);
		this.givenPoints = -1;
	}

	public PointsAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
		this.givenPoints = -1;
	}

	public PointsAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber,
			int subquestionAnswerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber,
				subquestionAnswerOrderNumber);
		this.givenPoints = -1;
	}

	public int getGivenPoints() {
		return givenPoints;
	}

	public void setGivenPoints(int givenPoints) {
		this.givenPoints = givenPoints;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
