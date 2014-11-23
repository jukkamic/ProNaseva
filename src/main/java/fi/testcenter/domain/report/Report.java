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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
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

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	private ReportTemplate reportTemplate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_ANSWER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "ANSWER_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private List<Answer> answers = new ArrayList<Answer>();

	private int totalScorePercentage;

	private String reportStatus; // 'DRAFT', 'AWAIT_APPROVAL', or 'APPROVED'
	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;
	private String overallResultSmiley;
	private boolean smileysSet = false;

	@Column
	@Temporal(TemporalType.DATE)
	private Date date = new Date();

	private String reportDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_IMPORTER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "IMPORTER_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private Importer importer;

	@Transient
	private Long importerId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_WORKSHOP", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "WORKSHOP_ID"))
	private Workshop workshop;

	@Transient
	private Long workshopId;

	@Transient
	private Long optionalQId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_USER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_QUESTIONGROUPSCORE", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTIONGROUPSCORE_ID"))
	@OrderColumn(name = "ORDERINDEX")
	List<QuestionGroupScore> questionGroupScore = new ArrayList<QuestionGroupScore>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_REPORTPARTSCORE", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "REPORTPARTSCORE_ID"))
	@OrderColumn(name = "ORDERINDEX")
	List<ReportPartScore> reportPartScore = new ArrayList<ReportPartScore>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "report")
	@OrderColumn(name = "ORDERINDEX")
	List<ReportHighlight> reportHighlights = new ArrayList<ReportHighlight>();

	List<String> reportPartSmileys = new ArrayList<String>();

	List<String> questionGroupSmileys = new ArrayList<String>();

	public Report() {

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		this.reportDate = DATE_FORMAT.format(new Date());
		this.reportStatus = "DRAFT";

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

	public void setQuestionGroupSmileys(List<String> questionGroupSmileys) {
		this.questionGroupSmileys = questionGroupSmileys;
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

	public void setUser(User user) {
		this.user = user;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public List<ReportHighlight> getReportHighlights() {
		return reportHighlights;
	}

	public void setReportHighlights(List<ReportHighlight> reportHighlights) {
		this.reportHighlights = reportHighlights;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<QuestionGroupScore> getQuestionGroupScore() {
		return this.questionGroupScore;
	}

	public void setQuestionGroupScore(
			List<QuestionGroupScore> questionGroupScore) {
		this.questionGroupScore = questionGroupScore;
	}

	public List<ReportPartScore> getReportPartScore() {
		return reportPartScore;
	}

	public void setReportPartScore(List<ReportPartScore> reportPartScore) {
		this.reportPartScore = reportPartScore;
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

	public List<String> getReportPartSmileys() {
		return reportPartSmileys;
	}

	public void setReportPartSmileys(List<String> reportPartSmileys) {
		this.reportPartSmileys = reportPartSmileys;
	}

	public List<String> getQuestionGroupSmileys() {
		return questionGroupSmileys;
	}

	public void setQuestionPartSmileys(List<String> questionGroupSmileys) {
		this.questionGroupSmileys = questionGroupSmileys;
	}

	public boolean isSmileysSet() {
		return smileysSet;
	}

	public void setSmileysSet(boolean smileysSet) {
		this.smileysSet = smileysSet;
	}

	// ASETETAAN HIGHLIGHT-VASTAUKSET KÄYTTÄJÄN RAPORTINMUOKKAUKSESSA TEKEMIEN
	// JA ANSWER-OLIOIHIN TALLENNETTUJEN VALINTOJEN MUKAAN

	public void setHighlightAnswers(ReportService rs) {

		ArrayList<ReportHighlight> reportHighlightList = new ArrayList<ReportHighlight>();
		int answerIndexCounter = 0;

		if (reportHighlights.size() > 0) {

			try {
				rs.deleteReportHighlights(reportHighlights);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (ReportPart reportPart : this.reportTemplate.getReportParts()) {
			int questionGroupCounter = 1;
			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {
				int questionCounter = 1;
				for (Question question : questionGroup.getQuestions()) {
					if (this.answers.get(answerIndexCounter)
							.isHighlightAnswer() == true) {

						System.out.println(answerIndexCounter);
						ReportHighlight highlight = new ReportHighlight(this,
								reportPart, questionGroup,
								this.answers.get(answerIndexCounter));
						highlight
								.setQuestionGroupOrderNumber(questionGroupCounter);
						highlight.setQuestionOrderNumber(questionCounter);

						reportHighlightList.add(highlight);

					}
					answerIndexCounter++;
					int subQuestionCounter = 1;
					for (Question subQuestion : question.getSubQuestions()) {
						if (this.answers.get(answerIndexCounter)
								.isHighlightAnswer() == true) {
							ReportHighlight highlight = new ReportHighlight(
									this, reportPart, questionGroup,
									this.answers.get(answerIndexCounter));
							highlight
									.setQuestionGroupOrderNumber(questionGroupCounter);
							highlight.setQuestionOrderNumber(questionCounter);
							highlight
									.setSubQuestionOrderNumber(subQuestionCounter);

							reportHighlightList.add(highlight);

						}
						subQuestionCounter++;
						answerIndexCounter++;
					}

					questionCounter++;
				}
				questionGroupCounter++;
			}
		}

		this.reportHighlights = reportHighlightList;

	}

	// LASKETAAN RAPORTIN PISTEET MONIVALINTOJEN POHJALTA:

	public void calculateReportScore() {
		int reportTotalScore = 0;
		int reportMaxScore = 0;
		int answerIndexCounter = 0;
		int reportPartIndex = 0;
		int questionGroupIndex = 0;
		List<QuestionGroupScore> questionGroupScoreList = new ArrayList<QuestionGroupScore>();
		List<ReportPartScore> reportPartScoreList = new ArrayList<ReportPartScore>();

		for (ReportPart reportPart : this.reportTemplate.getReportParts()) {
			ReportPartScore reportPartScoreObject;
			if (reportPartScore.size() > 0) {
				reportPartScoreObject = reportPartScore.get(reportPartIndex);
				reportPartScoreObject.setScore(0);
				reportPartScoreObject.setMaxScore(0);
				reportPartScoreObject.setScorePercentage(0);

			} else {
				reportPartScoreObject = new ReportPartScore();
			}

			reportPartScoreObject.setReportPart(reportPart);

			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {

				QuestionGroupScore questionGroupScoreObject;

				if (questionGroupScore.size() > 0) {
					questionGroupScoreObject = questionGroupScore
							.get(questionGroupIndex);
					questionGroupScoreObject.setScore(0);
					questionGroupScoreObject.setMaxScore(0);
					questionGroupScoreObject.setScorePercentage(0);
				} else {
					questionGroupScoreObject = new QuestionGroupScore();
				}

				questionGroupScoreObject.setQuestionGroup(questionGroup);

				for (Question question : questionGroup.getQuestions()) {

					if (question instanceof MultipleChoiceQuestion) {
						MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
						MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answers
								.get(answerIndexCounter);
						mca.setShowScore(false);

						int maxScore = 0;
						for (MultipleChoiceOption option : mcq.getOptions()) {
							if (option.getPoints() != -1
									&& option.getPoints() > maxScore) {
								maxScore = option.getPoints();

							}
						}

						mca.setMaxScore(maxScore); // Asetetaan
													// monivalintakysymyksen
													// maksimipistemäärä

						// Lasketaan pisteet jos käyttäjä on tehnyt valinnan ja
						// monivalinta ei ole sellainen, jota ei pisteytetä
						// (pistemäärä -1)

						if (mca.getChosenOptionIndex() != -1
								&& mcq.getOptions()
										.get(mca.getChosenOptionIndex())
										.getPoints() != -1) {

							mca.setShowScore(true);
							mca.setScore(mcq.getOptions()
									.get(mca.getChosenOptionIndex())
									.getPoints());

							// Lisätään kysymysryhmän pisteisiin ja asetetaan
							// kysymysryhmän pisteet näkyviksi raportissa

							questionGroupScoreObject.setShowScore(true);
							questionGroupScoreObject
									.setMaxScore(questionGroupScoreObject
											.getMaxScore() + maxScore);

							questionGroupScoreObject
									.setScore(questionGroupScoreObject
											.getScore()
											+ mcq.getOptions()
													.get(mca.getChosenOptionIndex())
													.getPoints());

							reportPartScoreObject.setShowScore(true);
						}

					}

					if (question instanceof PointsQuestion) {

						PointsQuestion pointsQuestion = (PointsQuestion) question;
						PointsAnswer pointsAnswer = (PointsAnswer) answers
								.get(answerIndexCounter);
						if (pointsAnswer.getGivenPoints() != -1) {
							questionGroupScoreObject.setShowScore(true);
							questionGroupScoreObject
									.setMaxScore(questionGroupScoreObject
											.getMaxScore()
											+ pointsQuestion.getMaxPoints());
							questionGroupScoreObject
									.setScore(questionGroupScoreObject
											.getScore()
											+ pointsAnswer.getGivenPoints());
							reportPartScoreObject.setShowScore(true);

						}
					}

					answerIndexCounter++;

					// subQuestions loop

					for (Question subQuestion : question.getSubQuestions()) {

						if (subQuestion instanceof MultipleChoiceQuestion) {
							MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) subQuestion;
							MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answers
									.get(answerIndexCounter);

							// Lasketan maksimipisteet

							int maxScore = 0;
							for (MultipleChoiceOption option : mcq.getOptions()) {
								if (option.getPoints() != -1
										&& option.getPoints() > maxScore) {
									maxScore = option.getPoints();
								}
							}

							mca.setMaxScore(maxScore);

							// Lasketaan pisteet jos käyttäjä on tehnyt valinnan
							// ja
							// monivalinta ei ole sellainen, jota ei pisteytetä
							// (pistemäärä -1)

							if (mca.getChosenOptionIndex() != -1
									&& mcq.getOptions()
											.get(mca.getChosenOptionIndex())
											.getPoints() != -1) {

								mca.setShowScore(true);
								mca.setScore(mcq.getOptions()
										.get(mca.getChosenOptionIndex())
										.getPoints());

								// Lisätään kysymysryhmän pisteisiin ja
								// asetetaan
								// kysymysryhmän ja raportin osan pisteet
								// näkyviksi raportissa

								questionGroupScoreObject.setShowScore(true);
								questionGroupScoreObject
										.setMaxScore(questionGroupScoreObject
												.getMaxScore() + maxScore);

								questionGroupScoreObject
										.setScore(questionGroupScoreObject
												.getScore()
												+ mcq.getOptions()
														.get(mca.getChosenOptionIndex())
														.getPoints());

								reportPartScoreObject.setShowScore(true);

							}

						}

						answerIndexCounter++;
					}

				}

				// Lisätään kysymysryhmän pisteet Report-luokan olioon.

				questionGroupScoreObject.calculateScorePercentage();
				questionGroupScoreList.add(questionGroupScoreObject);

				// Lisätään kysymysryhmän pisteet ja maksimipisteet raportin
				// osan pisteisiin

				reportPartScoreObject.setScore(reportPartScoreObject.getScore()
						+ questionGroupScoreObject.getScore());
				reportPartScoreObject
						.setMaxScore(reportPartScoreObject.getMaxScore()
								+ questionGroupScoreObject.getMaxScore());

				questionGroupIndex++;
				if (questionGroup.getOptionalQuestions().size() > 0)
					answerIndexCounter++;

			}

			// Lisätään raportin osan pisteet Report-luokan olioon.

			reportPartScoreObject.calculateScorePercentage();
			reportPartScoreList.add(reportPartScoreObject);

			reportTotalScore = reportTotalScore
					+ reportPartScoreObject.getScore();
			reportMaxScore = reportMaxScore
					+ reportPartScoreObject.getMaxScore();

			reportPartIndex++;
		}

		questionGroupScore = questionGroupScoreList;
		reportPartScore = reportPartScoreList;

		totalScorePercentage = (int) Math.round((double) reportTotalScore
				/ (double) reportMaxScore * 100);

	}

	public void prepareAnswerList() {

		List<Answer> reportAnswerList = new ArrayList<Answer>();

		for (ReportPart reportPart : reportTemplate.getReportParts()) {
			reportPartSmileys.add("");
			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {
				questionGroupSmileys.add("");
				for (Question question : questionGroup.getQuestions()) {

					if (question instanceof TextQuestion) {

						Answer answer = new TextAnswer();
						answer.setQuestion(question);
						reportAnswerList.add(answer);

					}

					if (question instanceof MultipleChoiceQuestion) {
						Answer answer = new MultipleChoiceAnswer();
						answer.setQuestion(question);
						reportAnswerList.add(answer);

					}
					if (question instanceof CostListingQuestion) {

						CostListingAnswer answer = new CostListingAnswer();
						CostListingQuestion clq = (CostListingQuestion) question;

						answer.setQuestion(question);
						List<Float> answerList = new ArrayList<Float>();
						for (int i = 0; i < clq.getQuestions().size(); i++)
							answerList.add(new Float(0));
						answer.setAnswers(answerList);

						reportAnswerList.add(answer);

					}
					if (question instanceof ImportantPointsQuestion) {
						ImportantPointsAnswer answer = new ImportantPointsAnswer();
						ImportantPointsQuestion listQuestion = (ImportantPointsQuestion) question;
						answer.setQuestion(question);
						List<ImportantPointsItem> answerItems = new ArrayList<ImportantPointsItem>();
						for (int i = 0; i < listQuestion.getQuestionItems()
								.size(); i++)
							answerItems.add(new ImportantPointsItem());
						answer.setAnswerItems(answerItems);
						reportAnswerList.add(answer);
					}
					if (question instanceof PointsQuestion) {
						Answer answer = new PointsAnswer();
						answer.setQuestion(question);
						reportAnswerList.add(answer);

					}

					if (!question.getSubQuestions().isEmpty()) {
						for (Question subQuestion : question.getSubQuestions()) {
							if (subQuestion instanceof TextQuestion) {
								Answer answer = new TextAnswer();
								answer.setQuestion(subQuestion);
								reportAnswerList.add(answer);

							}

							if (subQuestion instanceof MultipleChoiceQuestion) {
								Answer answer = new MultipleChoiceAnswer();
								answer.setQuestion(subQuestion);
								reportAnswerList.add(answer);

							}
						}
					}

				}
				List<Question> optionalQuestions = questionGroup
						.getOptionalQuestions();
				if (optionalQuestions.size() > 0)
					reportAnswerList.add(new OptionalQuestionsAnswer());

			}
		}
		answers = reportAnswerList;

	}
}
