package fi.testcenter.domain.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.answer.Answer;

@Entity
public class ReportQuestionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private int score;

	private int maxScore;
	private int scorePercentage;

	private String scoreSmiley;
	private boolean showInReportSummary = false;
	private boolean showScore = false;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "reportQuestionGroup")
	@OrderColumn
	List<Answer> answers = new ArrayList<Answer>();

	@ManyToOne
	@JoinColumn
	ReportPart reportPart;

	@ManyToOne
	@JoinColumn
	ReportTemplateQuestionGroup reportTemplateQuestionGroup;

	public ReportQuestionGroup() {
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

	public int getScorePercentage() {
		return scorePercentage;
	}

	public void setScorePercentage(int scorePercentage) {
		this.scorePercentage = scorePercentage;
	}

	public String getScoreSmiley() {
		return scoreSmiley;
	}

	public void setScoreSmiley(String scoreSmiley) {
		this.scoreSmiley = scoreSmiley;
	}

	public boolean isShowInReportSummary() {
		return showInReportSummary;
	}

	public void setShowInReportSummary(boolean showInReportSummary) {
		this.showInReportSummary = showInReportSummary;
	}

	public boolean isShowScore() {
		return showScore;
	}

	public void setShowScore(boolean showScore) {
		this.showScore = showScore;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public ReportPart getReportPart() {
		return reportPart;
	}

	public void setReportPart(ReportPart reportPart) {
		this.reportPart = reportPart;
	}

	public ReportTemplateQuestionGroup getReportTemplateQuestionGroup() {
		return reportTemplateQuestionGroup;
	}

	public void setReportTemplateQuestionGroup(
			ReportTemplateQuestionGroup reportTemplateQuestionGroup) {
		this.reportTemplateQuestionGroup = reportTemplateQuestionGroup;
	}

}
