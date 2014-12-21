package fi.testcenter.domain.answer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(nullable = true)
	ReportQuestionGroup reportQuestionGroup;

	private boolean showScore;
	private int score;
	private int maxScore;
	private boolean highlightAnswer;
	private boolean removeAnswerFromReport = false;

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

	public boolean isShowScore() {
		return showScore;
	}

	public void setShowScore(boolean showScore) {
		this.showScore = showScore;
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

}
