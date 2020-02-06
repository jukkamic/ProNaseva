package fi.testcenter.domain.reportSummary;

import java.util.ArrayList;
import java.util.List;

import fi.testcenter.domain.reportTemplate.ReportTemplatePart;

public class ReportPartSummary {

	List<QuestionGroupSummary> questionGroupSummaries = new ArrayList<QuestionGroupSummary>();
	ReportTemplatePart reportTemplatePart;
	int averageScorePercengage = -1;
	boolean showInSummary = false;

	public List<QuestionGroupSummary> getQuestionGroupSummaries() {
		return questionGroupSummaries;
	}

	public void setQuestionGroupSummaries(
			List<QuestionGroupSummary> questionGroupSummaries) {
		this.questionGroupSummaries = questionGroupSummaries;
	}

	public ReportTemplatePart getReportTemplatePart() {
		return reportTemplatePart;
	}

	public void setReportTemplatePart(ReportTemplatePart reportTemplatePart) {
		this.reportTemplatePart = reportTemplatePart;
	}

	public int getAverageScorePercengage() {
		return averageScorePercengage;
	}

	public void setAverageScorePercengage(int averageScorePercengage) {
		this.averageScorePercengage = averageScorePercengage;
	}

	public boolean isShowInSummary() {
		return showInSummary;
	}

	public void setShowInSummary(boolean showInSummary) {
		this.showInSummary = showInSummary;
	}

}
