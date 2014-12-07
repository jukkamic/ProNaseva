package fi.testcenter.domain.answer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportHighlight;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	private Question question;

	@OneToOne(mappedBy = "answer")
	private ReportHighlight reportHighlight;

	@ManyToOne
	Report report;

	private boolean showScore;
	private int score;
	private int maxScore;
	private boolean highlightAnswer;
	private boolean removeAnswerFromReport = false;

	public Answer() {

	}

	public Answer(Question question) {
		this.question = question;
	}

	public Answer(Report report, Question question) {
		this.report = report;
		this.question = question;
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

	public ReportHighlight getReportHighlight() {
		return reportHighlight;
	}

	public void setReportHighlight(ReportHighlight reportHighlight) {
		this.reportHighlight = reportHighlight;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
