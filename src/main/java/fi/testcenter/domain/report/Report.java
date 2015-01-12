package fi.testcenter.domain.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.domain.Workshop;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.DateAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.ImportantPointsItem;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.DateQuestion;
import fi.testcenter.domain.question.ImportantPointsQuestion;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.question.TextQuestion;
import fi.testcenter.domain.reportTemplate.ReportTemplate;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column
	@Temporal(TemporalType.DATE)
	private Date testDate = new Date();

	ReportTemplate reportTemplate;

	private int totalScorePercentage = -1;

	private int totalScore = -1;
	private int totalMaxScore;

	private String testDateString;

	private String reportStatus; // 'DRAFT', 'AWAIT_APPROVAL', or 'APPROVED'

	@ManyToOne
	@JoinColumn
	private Importer importer;

	@Transient
	private Long importerId;

	@ManyToOne
	@JoinColumn
	private Workshop workshop;

	@Transient
	private Long workshopId;

	@ManyToOne
	@JoinColumn
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		this.testDateString = DATE_FORMAT.format(new Date());
	}

	public String getTestDateString() {
		return testDateString;
	}

	public void setTestDateString(String testDateString) {
		this.testDateString = testDateString;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		try {
			testDate = DATE_FORMAT.parse(testDateString);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Importer getImporter() {
		return importer;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public Long getImporterId() {
		return importerId;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public void setImporterId(Long importerId) {
		this.importerId = importerId;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTotalScorePercentage() {
		return totalScorePercentage;
	}

	public void setTotalScorePercentage(int totalScorePercentage) {
		this.totalScorePercentage = totalScorePercentage;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalMaxScore() {
		return totalMaxScore;
	}

	public void setTotalMaxScore(int totalMaxScore) {
		this.totalMaxScore = totalMaxScore;
	}

	public Answer getAnswerInstance(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		if (question instanceof TextQuestion) {

			return new TextAnswer(reportQuestionGroup, question,
					answerOrderNumber);

		}

		if (question instanceof DateQuestion) {
			return new DateAnswer(reportQuestionGroup, question,
					answerOrderNumber);
		}
		if (question instanceof MultipleChoiceQuestion) {

			return new MultipleChoiceAnswer(reportQuestionGroup, question,
					answerOrderNumber);

		}
		if (question instanceof CostListingQuestion) {

			CostListingAnswer answer = new CostListingAnswer(
					reportQuestionGroup, question, answerOrderNumber);
			CostListingQuestion clq = (CostListingQuestion) question;
			List<String> answerList = new ArrayList<String>();
			for (int i = 0; i < clq.getQuestionItems().size(); i++)
				answerList.add(new String(""));
			answer.setAnswersIn(answerList);

			return answer;

		}
		if (question instanceof ImportantPointsQuestion) {
			ImportantPointsAnswer answer = new ImportantPointsAnswer(
					reportQuestionGroup, question, answerOrderNumber);
			ImportantPointsQuestion listQuestion = (ImportantPointsQuestion) question;
			List<ImportantPointsItem> answerItems = new ArrayList<ImportantPointsItem>();
			for (String item : listQuestion.getQuestionItems())
				answerItems.add(new ImportantPointsItem(item));
			answer.setAnswerItems(answerItems);
			return answer;
		}
		if (question instanceof PointsQuestion) {
			return new PointsAnswer(reportQuestionGroup, question,
					answerOrderNumber);

		}

		if (question instanceof OptionalQuestions) {

			return new OptionalQuestionsAnswer(reportQuestionGroup, question);

		}
		return null;

	}

	public Answer getSubQuestionAnswer(ReportQuestionGroup reportQuestionGroup,
			Question subQuestion, int answerOrderNumber,
			int subquestionOrderNumber) {
		Answer answer = getAnswerInstance(reportQuestionGroup, subQuestion,
				answerOrderNumber);
		answer.setSubquestionAnswerOrderNumber(subquestionOrderNumber);
		return answer;
	}

}
