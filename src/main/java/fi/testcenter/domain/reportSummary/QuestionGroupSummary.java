package fi.testcenter.domain.reportSummary;

import java.util.ArrayList;
import java.util.List;

import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;

public class QuestionGroupSummary {

	List<AnswerSummary> answerSummaries = new ArrayList<AnswerSummary>();
	ReportTemplateQuestionGroup reportTemplateQuestionGroup;
	int averageScorePercengage = -1;
	boolean showInSummary = false;

	public List<AnswerSummary> getAnswerSummaries() {
		return answerSummaries;
	}

	public void setAnswerSummaries(List<AnswerSummary> answerSummaries) {
		this.answerSummaries = answerSummaries;
	}

	public ReportTemplateQuestionGroup getReportTemplateQuestionGroup() {
		return reportTemplateQuestionGroup;
	}

	public void setReportTemplateQuestionGroup(
			ReportTemplateQuestionGroup reportTemplateQuestionGroup) {
		this.reportTemplateQuestionGroup = reportTemplateQuestionGroup;
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
