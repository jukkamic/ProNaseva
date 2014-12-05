package fi.testcenter.domain.answer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportHighlight;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private Question question;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "answer")
	private ReportHighlight reportHighlight;

	private boolean showScore;
	private int score;
	private int maxScore;
	private boolean highlightAnswer;
	private boolean removeAnswerFromReport = false;

	public Answer() {

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

}
