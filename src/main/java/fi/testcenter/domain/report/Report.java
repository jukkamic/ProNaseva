package fi.testcenter.domain.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.domain.Workshop;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.ImportantPointsItem;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.ImportantPointsQuestion;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.question.TextQuestion;
import fi.testcenter.service.ReportService;

@Entity
@NamedQueries({
		@NamedQuery(name = "workshopReportCount", query = "SELECT COUNT(r) FROM Report r WHERE r.workshop.id = :workshopId"),
		@NamedQuery(name = "getReportsByImporterId", query = "SELECT r FROM Report r WHERE r.importer.id = :importerId"),
		@NamedQuery(name = "getReportsByUserId", query = "SELECT r FROM Report r WHERE r.user.id = :userId") })
public class Report {

	@Transient
	Logger log = Logger.getLogger("fi.testcenter.domain.report");

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private int totalScorePercentage;
	private String reportStatus; // 'DRAFT', 'AWAIT_APPROVAL', or 'APPROVED'
	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;
	private String overallResultSmiley;
	private boolean smileysSet = false;
	private boolean highlightsSet = false;

	@Column
	@Temporal(TemporalType.DATE)
	private Date date = new Date();

	private String reportDate;

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

	@Transient
	private Long optionalQId;

	@ManyToOne
	@JoinColumn
	private User user;

	@ManyToOne
	@JoinColumn
	private ReportTemplate reportTemplate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "report")
	@OrderColumn
	private List<ReportPart> reportParts = new ArrayList<ReportPart>();

	public Report() {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		this.reportDate = DATE_FORMAT.format(new Date());
		this.reportStatus = "DRAFT";

	}

	public Report(ReportTemplate reportTemplate, ReportService rs) {

		this.reportTemplate = reportTemplate;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		this.reportDate = DATE_FORMAT.format(new Date());
		this.reportStatus = "DRAFT";
		int reportTemplatePartOrderNumber = 1;

		for (ReportTemplatePart reportTemplatePart : reportTemplate
				.getReportParts()) {
			ReportPart reportPart = new ReportPart();
			reportPart
					.setReportPartOrderNumber(reportTemplatePartOrderNumber++);
			reportPart.setReportTemplatePart(reportTemplatePart);
			reportPart.setReport(this);
			List<ReportQuestionGroup> reportQuestionGroupList = new ArrayList<ReportQuestionGroup>();
			int questionGroupOrderNumber = 1;
			for (ReportTemplateQuestionGroup reportTemplateQuestionGroup : reportTemplatePart
					.getQuestionGroups()) {
				ReportQuestionGroup reportQuestionGroup = new ReportQuestionGroup();
				reportQuestionGroup
						.setQuestionGroupOrderNumber(questionGroupOrderNumber++);
				reportQuestionGroup
						.setReportTemplateQuestionGroup(reportTemplateQuestionGroup);
				reportQuestionGroup.setReportPart(reportPart);
				List<Answer> reportQuestionGroupAnswerList = new ArrayList<Answer>();
				int answerOrderNumber = 1;
				for (Question question : reportTemplateQuestionGroup
						.getQuestions()) {

					if (question instanceof TextQuestion) {

						reportQuestionGroupAnswerList.add(new TextAnswer(
								reportQuestionGroup, question,
								answerOrderNumber++));

					}

					if (question instanceof MultipleChoiceQuestion) {

						reportQuestionGroupAnswerList
								.add(new MultipleChoiceAnswer(
										reportQuestionGroup, question,
										answerOrderNumber++));

					}
					if (question instanceof CostListingQuestion) {

						CostListingAnswer answer = new CostListingAnswer(
								reportQuestionGroup, question,
								answerOrderNumber++);
						CostListingQuestion clq = (CostListingQuestion) question;
						List<Float> answerList = new ArrayList<Float>();
						for (int i = 0; i < clq.getQuestionItems().size(); i++)
							answerList.add(new Float(0));
						answer.setAnswers(answerList);

						reportQuestionGroupAnswerList.add(answer);

					}
					if (question instanceof ImportantPointsQuestion) {
						ImportantPointsAnswer answer = new ImportantPointsAnswer(
								reportQuestionGroup, question,
								answerOrderNumber++);
						ImportantPointsQuestion listQuestion = (ImportantPointsQuestion) question;
						List<ImportantPointsItem> answerItems = new ArrayList<ImportantPointsItem>();
						for (int i = 0; i < listQuestion.getQuestionItems()
								.size(); i++)
							answerItems.add(new ImportantPointsItem());
						answer.setAnswerItems(answerItems);
						reportQuestionGroupAnswerList.add(answer);
					}
					if (question instanceof PointsQuestion) {
						reportQuestionGroupAnswerList.add(new PointsAnswer(
								reportQuestionGroup, question,
								answerOrderNumber++));

					}

					if (question instanceof OptionalQuestions) {

						reportQuestionGroupAnswerList
								.add(new OptionalQuestionsAnswer(
										reportQuestionGroup, question));

					}

					if (!question.getSubQuestions().isEmpty()) {
						int subquestionOrderNumber = 1;
						for (Question subQuestion : question.getSubQuestions()) {
							if (subQuestion instanceof TextQuestion) {
								reportQuestionGroupAnswerList
										.add(new TextAnswer(
												reportQuestionGroup,
												subQuestion, answerOrderNumber,
												subquestionOrderNumber++));

							}

							if (subQuestion instanceof MultipleChoiceQuestion) {

								reportQuestionGroupAnswerList
										.add(new MultipleChoiceAnswer(
												reportQuestionGroup,
												subQuestion, answerOrderNumber,
												subquestionOrderNumber++));

							}

							if (subQuestion instanceof PointsQuestion) {

								reportQuestionGroupAnswerList
										.add(new PointsAnswer(
												reportQuestionGroup,
												subQuestion, answerOrderNumber,
												subquestionOrderNumber++));

							}
						}
					}

				}
				reportQuestionGroup.setAnswers(reportQuestionGroupAnswerList);
				reportQuestionGroupList.add(reportQuestionGroup);
			}
			reportPart.setReportQuestionGroups(reportQuestionGroupList);
			reportParts.add(reportPart);
		}
		rs.saveReport(this);

	}

	public Report(Long id, String reportDate, Importer importer,
			Workshop workshop, User user, String reportStatus) {
		this.id = id;
		this.reportDate = reportDate;
		this.importer = importer;
		this.workshop = workshop;
		this.user = user;
		this.reportStatus = reportStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public int getTotalScorePercentage() {
		return totalScorePercentage;
	}

	public void setTotalScorePercentage(int totalScorePercentage) {
		this.totalScorePercentage = totalScorePercentage;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	public String getVehicleRegistrationDate() {
		return vehicleRegistrationDate;
	}

	public void setVehicleRegistrationDate(String vehicleRegistrationDate) {
		this.vehicleRegistrationDate = vehicleRegistrationDate;
	}

	public String getVehicleMileage() {
		return vehicleMileage;
	}

	public void setVehicleMileage(String vehicleMileage) {
		this.vehicleMileage = vehicleMileage;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isHighlightsSet() {
		return highlightsSet;
	}

	public void setHighlightsSet(boolean highlightsSet) {
		this.highlightsSet = highlightsSet;
	}

	public Importer getImporter() {
		return importer;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public Long getImporterId() {
		return importerId;
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

	public User getUser() {
		return user;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public Long getOptionalQId() {
		return optionalQId;
	}

	public void setOptionalQId(Long optionalQId) {
		this.optionalQId = optionalQId;
	}

	public List<ReportPart> getReportParts() {
		return reportParts;
	}

	public void setReportParts(List<ReportPart> reportParts) {
		this.reportParts = reportParts;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}

	public String getOverallResultSmiley() {
		return overallResultSmiley;
	}

	public void setOverallResultSmiley(String overallResultSmiley) {
		this.overallResultSmiley = overallResultSmiley;
	}

	public boolean isSmileysSet() {
		return smileysSet;
	}

	public void setSmileysSet(boolean smileysSet) {
		this.smileysSet = smileysSet;
	}

	// LASKETAAN RAPORTIN PISTEET MONIVALINTOJEN POHJALTA:

	public Report calculateReportScore(ReportService rs) {
		// int reportTotalScore = 0;
		// int reportMaxScore = 0;
		// int answerIndexCounter = 0;
		// int reportPartIndex = 0;
		// int questionGroupIndex = 0;
		// List<QuestionGroupScore> questionGroupScoreList = new
		// ArrayList<QuestionGroupScore>();
		// List<ReportPartScore> reportPartScoreList = new
		// ArrayList<ReportPartScore>();
		//
		// for (ReportTemplatePart reportPart : this.reportTemplate
		// .getReportParts()) {
		// ReportPartScore reportPartScoreObject;
		// if (reportPartScore.size() > 0) {
		// reportPartScoreObject = reportPartScore.get(reportPartIndex);
		// reportPartScoreObject.setScore(0);
		// reportPartScoreObject.setMaxScore(0);
		// reportPartScoreObject.setScorePercentage(0);
		//
		// } else {
		// reportPartScoreObject = new ReportPartScore(this);
		// }
		//
		// reportPartScoreObject.setReportPart(reportPart);
		//
		// for (ReportTemplateQuestionGroup questionGroup : reportPart
		// .getQuestionGroups()) {
		//
		// QuestionGroupScore questionGroupScoreObject;
		//
		// if (questionGroupScore.size() > 0) {
		// questionGroupScoreObject = questionGroupScore
		// .get(questionGroupIndex);
		// questionGroupScoreObject.setScore(0);
		// questionGroupScoreObject.setMaxScore(0);
		// questionGroupScoreObject.setScorePercentage(0);
		// } else {
		// questionGroupScoreObject = new QuestionGroupScore(this,
		// questionGroup);
		// }
		//
		// for (Question question : questionGroup.getQuestions()) {
		// if (answers.get(answerIndexCounter)
		// .isRemoveAnswerFromReport() != true) {
		// if (question instanceof MultipleChoiceQuestion) {
		// MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
		// MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answers
		// .get(answerIndexCounter);
		// mca.setShowScore(false);
		//
		// int maxScore = 0;
		// for (MultipleChoiceOption option : mcq.getOptions()) {
		// if (option.getPoints() != -1
		// && option.getPoints() > maxScore) {
		// maxScore = option.getPoints();
		//
		// }
		// }
		//
		// mca.setMaxScore(maxScore); // Asetetaan
		// // monivalintakysymyksen
		// // maksimipistemäärä
		//
		// // Lasketaan pisteet jos käyttäjä on tehnyt valinnan
		// // ja
		// // monivalinta ei ole sellainen, jota ei pisteytetä
		// // (pistemäärä -1)
		//
		// if (mca.getChosenOptionIndex() != -1
		// && mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints() != -1) {
		//
		// mca.setShowScore(true);
		// mca.setScore(mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// // Lisätään kysymysryhmän pisteisiin ja
		// // asetetaan
		// // kysymysryhmän pisteet näkyviksi raportissa
		//
		// questionGroupScoreObject.setShowScore(true);
		// questionGroupScoreObject
		// .setMaxScore(questionGroupScoreObject
		// .getMaxScore() + maxScore);
		//
		// questionGroupScoreObject
		// .setScore(questionGroupScoreObject
		// .getScore()
		// + mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// reportPartScoreObject.setShowScore(true);
		// }
		//
		// }
		//
		// if (question instanceof PointsQuestion) {
		//
		// PointsQuestion pointsQuestion = (PointsQuestion) question;
		// PointsAnswer pointsAnswer = (PointsAnswer) answers
		// .get(answerIndexCounter);
		// if (pointsAnswer.getGivenPoints() != -1) {
		// questionGroupScoreObject.setShowScore(true);
		// questionGroupScoreObject
		// .setMaxScore(questionGroupScoreObject
		// .getMaxScore()
		// + pointsQuestion.getMaxPoints());
		// questionGroupScoreObject
		// .setScore(questionGroupScoreObject
		// .getScore()
		// + pointsAnswer.getGivenPoints());
		// reportPartScoreObject.setShowScore(true);
		//
		// }
		// }
		//
		// if (question instanceof OptionalQuestions) {
		// OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) this.answers
		// .get(answerIndexCounter);
		// int optionalAnswersCounter = 0;
		// if (oqa.getQuestions() != null) {
		// for (Question optionalQuestion : oqa
		// .getQuestions()) {
		// if (optionalQuestion instanceof MultipleChoiceQuestion) {
		// MultipleChoiceQuestion mcq = (MultipleChoiceQuestion)
		// optionalQuestion;
		// MultipleChoiceAnswer mca = (MultipleChoiceAnswer) oqa
		// .getAnswers()
		// .get(optionalAnswersCounter++);
		// mca.setShowScore(false);
		//
		// int maxScore = 0;
		// for (MultipleChoiceOption option : mcq
		// .getOptions()) {
		// if (option.getPoints() != -1
		// && option.getPoints() > maxScore) {
		// maxScore = option.getPoints();
		//
		// }
		// }
		//
		// mca.setMaxScore(maxScore); // Asetetaan
		// // monivalintakysymyksen
		// // maksimipistemäärä
		//
		// // Lasketaan pisteet jos käyttäjä on
		// // tehnyt
		// // valinnan
		// // ja
		// // monivalinta ei ole sellainen, jota ei
		// // pisteytetä
		// // (pistemäärä -1)
		//
		// if (mca.getChosenOptionIndex() != -1
		// && mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints() != -1) {
		//
		// mca.setShowScore(true);
		// mca.setScore(mcq
		// .getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// // Lisätään kysymysryhmän pisteisiin
		// // ja
		// // asetetaan
		// // kysymysryhmän pisteet näkyviksi
		// // raportissa
		//
		// questionGroupScoreObject
		// .setShowScore(true);
		// questionGroupScoreObject
		// .setMaxScore(questionGroupScoreObject
		// .getMaxScore()
		// + maxScore);
		//
		// questionGroupScoreObject
		// .setScore(questionGroupScoreObject
		// .getScore()
		// + mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// reportPartScoreObject
		// .setShowScore(true);
		// }
		//
		// }
		//
		// if (optionalQuestion instanceof PointsQuestion) {
		//
		// PointsQuestion pointsQuestion = (PointsQuestion) optionalQuestion;
		// PointsAnswer pointsAnswer = (PointsAnswer) oqa
		// .getAnswers()
		// .get(optionalAnswersCounter++);
		// if (pointsAnswer.getGivenPoints() != -1) {
		// questionGroupScoreObject
		// .setShowScore(true);
		// questionGroupScoreObject
		// .setMaxScore(questionGroupScoreObject
		// .getMaxScore()
		// + pointsQuestion
		// .getMaxPoints());
		// questionGroupScoreObject
		// .setScore(questionGroupScoreObject
		// .getScore()
		// + pointsAnswer
		// .getGivenPoints());
		// reportPartScoreObject
		// .setShowScore(true);
		//
		// }
		// }
		//
		// }
		// }
		// }
		//
		// }
		//
		// answerIndexCounter++;
		//
		// // subQuestions loop
		//
		// for (Question subQuestion : question.getSubQuestions()) {
		// if (answers.get(answerIndexCounter)
		// .isRemoveAnswerFromReport() != true) {
		//
		// if (subQuestion instanceof MultipleChoiceQuestion) {
		// MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) subQuestion;
		// MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answers
		// .get(answerIndexCounter);
		//
		// // Lasketan maksimipisteet
		//
		// int maxScore = 0;
		// for (MultipleChoiceOption option : mcq
		// .getOptions()) {
		// if (option.getPoints() != -1
		// && option.getPoints() > maxScore) {
		// maxScore = option.getPoints();
		// }
		// }
		//
		// mca.setMaxScore(maxScore);
		//
		// // Lasketaan pisteet jos käyttäjä on tehnyt
		// // valinnan
		// // ja
		// // monivalinta ei ole sellainen, jota ei
		// // pisteytetä
		// // (pistemäärä -1)
		//
		// if (mca.getChosenOptionIndex() != -1
		// && mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints() != -1) {
		//
		// mca.setShowScore(true);
		// mca.setScore(mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// // Lisätään kysymysryhmän pisteisiin ja
		// // asetetaan
		// // kysymysryhmän ja raportin osan pisteet
		// // näkyviksi raportissa
		//
		// questionGroupScoreObject.setShowScore(true);
		// questionGroupScoreObject
		// .setMaxScore(questionGroupScoreObject
		// .getMaxScore() + maxScore);
		//
		// questionGroupScoreObject
		// .setScore(questionGroupScoreObject
		// .getScore()
		// + mcq.getOptions()
		// .get(mca.getChosenOptionIndex())
		// .getPoints());
		//
		// reportPartScoreObject.setShowScore(true);
		//
		// }
		//
		// }
		// }
		// answerIndexCounter++;
		// }
		//
		// }
		//
		// // Lisätään kysymysryhmän pisteet Report-luokan olioon.
		//
		// questionGroupScoreObject.calculateScorePercentage();
		// questionGroupScoreList.add(questionGroupScoreObject);
		//
		// // Lisätään kysymysryhmän pisteet ja maksimipisteet raportin
		// // osan pisteisiin
		//
		// reportPartScoreObject.setScore(reportPartScoreObject.getScore()
		// + questionGroupScoreObject.getScore());
		// reportPartScoreObject
		// .setMaxScore(reportPartScoreObject.getMaxScore()
		// + questionGroupScoreObject.getMaxScore());
		//
		// questionGroupIndex++;
		//
		// }
		//
		// // Lisätään raportin osan pisteet Report-luokan olioon.
		//
		// reportPartScoreObject.calculateScorePercentage();
		//
		// reportPartScoreList.add(reportPartScoreObject);
		//
		// reportTotalScore = reportTotalScore
		// + reportPartScoreObject.getScore();
		// reportMaxScore = reportMaxScore
		// + reportPartScoreObject.getMaxScore();
		//
		// reportPartIndex++;
		// }
		//
		// questionGroupScore = questionGroupScoreList;
		// reportPartScore = reportPartScoreList;
		//
		// totalScorePercentage = (int) Math.round((double) reportTotalScore
		// / (double) reportMaxScore * 100);
		// return rs.saveReport(this);
		return this;
	}

	public void setRemovedQuestions() {
		// for (Answer answer : this.answers) {
		// if (answer instanceof MultipleChoiceAnswer
		// && answer.isRemoveAnswerFromReport()) {
		// MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
		// mca.setChosenOptionIndex(-1);
		// mca.setRemarks(null);
		// }
		// if (answer instanceof PointsAnswer
		// && answer.isRemoveAnswerFromReport()) {
		// PointsAnswer pa = (PointsAnswer) answer;
		// pa.setGivenPoints(-1);
		// pa.setRemarks(null);
		// }
		// }
	}

	public void checkReportHighlights() {
		this.highlightsSet = false;
		for (ReportPart part : this.reportParts) {
			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				for (Answer answer : group.getAnswers()) {
					if (answer.isHighlightAnswer())
						this.highlightsSet = true;
					if (answer instanceof OptionalQuestionsAnswer) {
						OptionalQuestionsAnswer opa = (OptionalQuestionsAnswer) answer;
						if (opa.getAnswers() != null) {
							for (Answer loopAnswer : opa
									.getAnswers()) {
								if (loopAnswer.isHighlightAnswer())
									this.highlightsSet = true;
							}

						}
					}
				}
			}
		}
	}
}
