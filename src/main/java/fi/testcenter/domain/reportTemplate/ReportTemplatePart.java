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

@Entity
public class ReportTemplatePart {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String title;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "TEMPLATEPART_JOIN")
	@OrderColumn(name = "TEMPLATEPART_ORDER")
	private List<ReportTemplateQuestionGroup> questionGroups = new ArrayList<ReportTemplateQuestionGroup>();

	boolean showScorePercentage;
	private int scorePercentage;

	private boolean showScoreInReportHighlights = true;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ReportTemplateQuestionGroup> getQuestionGroups() {
		return questionGroups;
	}

	public void setQuestionGroups(
			List<ReportTemplateQuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isShowScorePercentage() {
		return showScorePercentage;
	}

	public void setShowScorePercentage(boolean showScorePercentage) {
		this.showScorePercentage = showScorePercentage;
	}

	public int getScorePercentage() {
		return scorePercentage;
	}

	public void setScorePercentage(int scorePercentage) {
		this.scorePercentage = scorePercentage;
	}

	public boolean isShowScoreInReportHighlights() {
		return showScoreInReportHighlights;
	}

	public void setShowScoreInReportHighlights(
			boolean showScoreInReportHighlights) {
		this.showScoreInReportHighlights = showScoreInReportHighlights;
	}

}
