package fi.testcenter.domain.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate;
import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;
import fi.testcenter.service.ReportService;

@Entity
public class PhoneCallTestReport extends Report {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "PHONECALLTESTREPORT_GROUPS_JOIN")
	@OrderColumn(name = "PHONECALLTESTREPORT_GROUPS_ORDER")
	List<ReportQuestionGroup> reportQuestionGroups = new ArrayList<ReportQuestionGroup>();

	public PhoneCallTestReport() {
		super.setTestDate(new Date());
		super.setReportStatus("DRAFT");
	}

	public PhoneCallTestReport(PhoneCallTestReportTemplate reportTemplate,
			ReportService rs) {

		this.reportTemplate = reportTemplate;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		super.setTestDate(new Date());
		super.setReportStatus("DRAFT");
		int reportTemplatePartOrderNumber = 1;

		List<ReportQuestionGroup> reportQuestionGroupList = new ArrayList<ReportQuestionGroup>();
		int questionGroupOrderNumber = 1;
		for (ReportTemplateQuestionGroup reportTemplateQuestionGroup : reportTemplate
				.getQuestionGroups()) {
			ReportQuestionGroup reportQuestionGroup = new ReportQuestionGroup();
			reportQuestionGroup
					.setQuestionGroupOrderNumber(questionGroupOrderNumber++);
			reportQuestionGroup
					.setReportTemplateQuestionGroup(reportTemplateQuestionGroup);

			List<Answer> reportQuestionGroupAnswerList = new ArrayList<Answer>();
			int answerOrderNumber = 1;
			for (Question question : reportTemplateQuestionGroup.getQuestions()) {
				reportQuestionGroupAnswerList.add(super.getAnswerInstance(
						reportQuestionGroup, question, answerOrderNumber));

				if (!question.getSubQuestions().isEmpty()) {
					int subquestionOrderNumber = 1;

					for (Question subQuestion : question.getSubQuestions()) {
						reportQuestionGroupAnswerList.add(super
								.getSubQuestionAnswer(reportQuestionGroup,
										subQuestion, answerOrderNumber,
										subquestionOrderNumber++));

					}
				}
				answerOrderNumber++;

			}
			reportQuestionGroup.setAnswers(reportQuestionGroupAnswerList);
			reportQuestionGroupList.add(reportQuestionGroup);
		}
		this.reportQuestionGroups = reportQuestionGroupList;

	}

	public List<ReportQuestionGroup> getReportQuestionGroups() {
		return reportQuestionGroups;
	}

	public void setReportQuestionGroups(
			List<ReportQuestionGroup> reportQuestionGroups) {
		this.reportQuestionGroups = reportQuestionGroups;
	}
}
