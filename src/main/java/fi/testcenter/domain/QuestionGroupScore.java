package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionGroupScore {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	QuestionGroup questionGroup;

	private int score;
	private int maxScore;
	private int scorePercentage;

	private String scoreSmiley;
	private boolean showInReportSummary = false;
	private boolean showScore = false;

	public QuestionGroupScore() {
	}

	public QuestionGroupScore(int score, int maxScore) {
		this.score = score;
		this.maxScore = maxScore;
		this.scorePercentage = (int) Math
				.round(((double) score / (double) maxScore) * 100);

	}

	public QuestionGroup getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroup questionGroup) {
		this.questionGroup = questionGroup;
	}

	public void calculateScorePercentage() {
		this.scorePercentage = (int) Math
				.round(((double) this.score / (double) this.maxScore) * 100);
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

	public boolean isShowInReportSummary() {
		return showInReportSummary;
	}

	public void setShowInReportSummary(boolean showInReportSummary) {
		this.showInReportSummary = showInReportSummary;
	}

}
