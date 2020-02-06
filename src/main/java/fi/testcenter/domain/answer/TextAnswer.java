package fi.testcenter.domain.answer;

import javax.persistence.Column;
import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class TextAnswer extends Answer {

	@Column(length = 1500)
	String answer = "";

	public TextAnswer() {
	}

	public TextAnswer(Question question) {
		super(question);
	}

	public TextAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
	}

	public TextAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
	}

	public TextAnswer(OptionalQuestionsAnswer optionalQuestionsAnswer,
			Question question, int answerOrderNumber) {
		super(optionalQuestionsAnswer, question, answerOrderNumber);

	}

	public TextAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber,
			int subquestionAnswerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber,
				subquestionAnswerOrderNumber);
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String toString() {
		return answer;
	}

}
