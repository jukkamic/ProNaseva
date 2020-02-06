package fi.testcenter.domain.reportTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.question.Question;

@Entity
public class ReportTemplateQuestionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String title;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	@OrderColumn
	private List<Question> questionsList = new ArrayList<Question>();

	private boolean showScore;
	private int score;
	private int maxScore;
	private boolean showStarCount;
	private int starCount;
	private String scoreSmiley;
	private boolean showInReportSummary = false;

	public ReportTemplateQuestionGroup() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Question> getQuestions() {
		return questionsList;
	}

	public void setQuestions(List<Question> questions) {
		this.questionsList = questions;
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

	public boolean isShowStarCount() {
		return showStarCount;
	}

	public void setShowStarCount(boolean showStarCount) {
		this.showStarCount = showStarCount;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public boolean isShowInReportSummary() {
		return showInReportSummary;
	}

	public void setShowInReportSummary(boolean showInReportSummary) {
		this.showInReportSummary = showInReportSummary;
	}

	public List<Question> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<Question> questionsList) {
		this.questionsList = questionsList;
	}

	public String getScoreSmiley() {
		return scoreSmiley;
	}

	public void setScoreSmiley(String scoreSmiley) {
		this.scoreSmiley = scoreSmiley;
	}

}
