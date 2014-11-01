package fi.testcenter.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_REPORTHIGHLIGHTS", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "REPORTHIGHLIGHTS_ID"))
	@OrderColumn(name = "ORDERINDEX")
	List<ReportHighlight> reportHighlights = new ArrayList<ReportHighlight>();

	List<String> reportPartSmileys = new ArrayList<String>();

	List<String> questionGroupSmileys = new ArrayList<String>();

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

	// ASETETAAN HIGHLIGHT-VASTAUKSET KÄYTTÄJÄN RAPORTINMUOKKAUKSESSA TEKEMIEN
	// JA ANSWER-OLIOIHIN TALLENNETTUJEN VALINTOJEN MUKAAN

	public void setHighlightAnswers() {
		ArrayList<ReportHighlight> reportHighlightList = new ArrayList<ReportHighlight>();
		int answerIndexCounter = 0;

		for (ReportPart reportPart : this.reportTemplate.getReportParts()) {
			int questionGroupCounter = 1;
			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {
				int questionCounter = 1;
				for (Question question : questionGroup.getQuestions()) {
					if (this.answers.get(answerIndexCounter)
							.isHighlightAnswer() == true) {

						System.out.println(answerIndexCounter);
						ReportHighlight highlight = new ReportHighlight(
								reportPart, questionGroup,
								this.answers.get(answerIndexCounter));
						highlight
								.setQuestionGroupOrderNumber(questionGroupCounter);
						highlight.setQuestionOrderNumber(questionCounter);
						reportHighlightList.add(highlight);
						System.out
								.println("Highlight-kysymyslooppi, indeksi : "
										+ answerIndexCounter
										+ "Kysymysryhmä + " + questionGroup);

					}
					answerIndexCounter++;
					int subQuestionCounter = 1;
					for (Question subQuestion : question.getSubQuestions()) {
						if (this.answers.get(answerIndexCounter)
								.isHighlightAnswer() == true) {
							ReportHighlight highlight = new ReportHighlight(
									reportPart, questionGroup,
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
			} else {
				reportPartScoreObject = new ReportPartScore();
			}

			reportPartScoreObject.setReportPart(reportPart);

			for (QuestionGroup questionGroup : reportPart.getQuestionGroups()) {

				QuestionGroupScore questionGroupScoreObject;

				if (questionGroupScore.size() > 0) {
					questionGroupScoreObject = questionGroupScore
							.get(questionGroupIndex);
				} else {
					questionGroupScoreObject = new QuestionGroupScore();
				}

				questionGroupScoreObject.setQuestionGroup(questionGroup);

				for (Question question : questionGroup.getQuestions()) {

					if (question instanceof MultipleChoiceQuestion) {
						MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) question;
						MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answers
								.get(answerIndexCounter);

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

}
