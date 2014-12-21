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

@Entity
public class ReportPart {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	int score;
	int maxScore;
	int scorePercentage;
	boolean showScore = false;

	String scoreSmiley = "";

	@ManyToOne
	@JoinColumn
	Report report;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn
	ReportTemplatePart reportTemplatePart;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "reportPart", cascade = CascadeType.ALL)
	@OrderColumn
	List<ReportQuestionGroup> reportQuestionGroups = new ArrayList<ReportQuestionGroup>();

	int reportPartOrderNumber;

	public ReportPart() {
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

	public boolean isShowScore() {
		return showScore;
	}

	public void setShowScore(boolean showScore) {
		this.showScore = showScore;
	}

	public String getScoreSmiley() {
		return scoreSmiley;
	}

	public void setScoreSmiley(String scoreSmiley) {
		this.scoreSmiley = scoreSmiley;
	}

	public List<ReportQuestionGroup> getReportQuestionGroups() {
		return reportQuestionGroups;
	}

	public void setReportQuestionGroups(
			List<ReportQuestionGroup> reportQuestionGroups) {
		this.reportQuestionGroups = reportQuestionGroups;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public ReportTemplatePart getReportTemplatePart() {
		return reportTemplatePart;
	}

	public void setReportTemplatePart(ReportTemplatePart reportTemplatePart) {
		this.reportTemplatePart = reportTemplatePart;
	}

	public int getReportPartOrderNumber() {
		return reportPartOrderNumber;
	}

	public void setReportPartOrderNumber(int reportPartOrderNumber) {
		this.reportPartOrderNumber = reportPartOrderNumber;
	}

}
