package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class ReportPartScore {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Transient
	ReportPart reportPart;

	int score;
	int maxScore;
	int scorePercentage;
	boolean showScore = false;

	String scoreSmiley;

	public ReportPartScore() {
	}

	public ReportPartScore(int score, int maxScore) {
		this.score = score;
		this.maxScore = maxScore;
		this.scorePercentage = (int) Math
				.round(((double) score / (double) maxScore) * 100);

	}

	public void calculateScorePercentage() {
		this.scorePercentage = (int) Math
				.round(((double) this.score / (double) this.maxScore) * 100);
	}

	public ReportPart getReportPart() {
		return reportPart;
	}

	public void setReportPart(ReportPart reportPart) {
		this.reportPart = reportPart;
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

	public String getScoreSmiley() {
		return scoreSmiley;
	}

	public void setScoreSmiley(String scoreSmiley) {
		this.scoreSmiley = scoreSmiley;
	}

}
