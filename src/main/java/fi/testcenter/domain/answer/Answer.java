package fi.testcenter.domain.answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	@JoinColumn
	private Question question;

	@ManyToOne
	@JoinColumn
	ReportQuestionGroup reportQuestionGroup;

	@ManyToOne
	@JoinTable(name = "OPTIONALANSWER_ANSWER")
	OptionalQuestionsAnswer optionalQuestionsAnswer;

	private int score = -1;
	private int maxScore;
	private boolean highlightAnswer;
	private boolean removeAnswerFromReport = false;

	private String approvalRemarks;

	int answerOrderNumber;
	int subquestionAnswerOrderNumber;

	public Answer() {

	}

	public Answer(Question question) {
		this.question = question;
	}

	public Answer(Question question, int answerOrderNumber) {
		this.question = question;
		this.answerOrderNumber = answerOrderNumber;

	}

	public Answer(OptionalQuestionsAnswer optionalQuestionsAnswer,
			Question question, int answerOrderNumber) {
		this.optionalQuestionsAnswer = optionalQuestionsAnswer;
		this.question = question;
		this.answerOrderNumber = answerOrderNumber;

	}

	public Answer(ReportQuestionGroup reportQuestionGroup, Question question) {
		this.question = question;
		this.reportQuestionGroup = reportQuestionGroup;

	}

	public Answer(ReportQuestionGroup reportQuestionGroup, Question question,
			int answerOrderNumber) {
		this.question = question;
		this.reportQuestionGroup = reportQuestionGroup;
		this.answerOrderNumber = answerOrderNumber;
	}

	public Answer(ReportQuestionGroup reportQuestionGroup, Question question,
			int answerOrderNumber, int subquestionAnswerOrderNumber) {
		this.question = question;
		this.reportQuestionGroup = reportQuestionGroup;
		this.answerOrderNumber = answerOrderNumber;
		this.subquestionAnswerOrderNumber = subquestionAnswerOrderNumber;
	}

	public ReportQuestionGroup getReportQuestionGroup() {
		return reportQuestionGroup;
	}

	public void setReportQuestionGroup(ReportQuestionGroup reportQuestionGroup) {
		this.reportQuestionGroup = reportQuestionGroup;
	}

	public boolean isHighlightAnswer() {
		return highlightAnswer;
	}

	public void setHighlightAnswer(boolean highlightAnswer) {
		this.highlightAnswer = highlightAnswer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public boolean isRemoveAnswerFromReport() {
		return removeAnswerFromReport;
	}

	public void setRemoveAnswerFromReport(boolean removeAnswerFromReport) {
		this.removeAnswerFromReport = removeAnswerFromReport;
	}

	public int getAnswerOrderNumber() {
		return answerOrderNumber;
	}

	public void setAnswerOrderNumber(int answerOrderNumber) {
		this.answerOrderNumber = answerOrderNumber;
	}

	public int getSubquestionAnswerOrderNumber() {
		return subquestionAnswerOrderNumber;
	}

	public void setSubquestionAnswerOrderNumber(int subquestionAnswerOrderNumber) {
		this.subquestionAnswerOrderNumber = subquestionAnswerOrderNumber;
	}

	public OptionalQuestionsAnswer getOptionalQuestionsAnswer() {
		return optionalQuestionsAnswer;
	}

	public void setOptionalQuestionsAnswer(
			OptionalQuestionsAnswer optionalQuestionsAnswer) {
		this.optionalQuestionsAnswer = optionalQuestionsAnswer;
	}

	public String getApprovalRemarks() {
		return approvalRemarks;
	}

	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}

}
