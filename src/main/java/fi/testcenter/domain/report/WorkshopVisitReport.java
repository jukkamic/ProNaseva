package fi.testcenter.domain.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.domain.Workshop;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.reportTemplate.ReportTemplatePart;
import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;
import fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate;
import fi.testcenter.service.ReportService;

@Entity
// @NamedQueries({
// @NamedQuery(name = "workshopReportCount", query =
// "SELECT COUNT(r) FROM WorkshopVisitReport r WHERE r.workshop.id = :workshopId"),
// @NamedQuery(name = "getReportsByImporterId", query =
// "SELECT r FROM WorkshopVisitReport r WHERE r.importer.id = :importerId"),
// @NamedQuery(name = "getReportsByUserId", query =
// "SELECT r FROM WorkshopVisitReport r WHERE r.user.id = :userId") })
public class WorkshopVisitReport extends Report {

	@Transient
	Logger log = Logger.getLogger("fi.testcenter.domain.report");

	private int totalScorePercentage;

	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;
	private String overallResultSmiley;
	private boolean smileysSet = false;
	private boolean highlightsSet = false;

	@ManyToOne
	@JoinColumn
	private WorkshopVisitReportTemplate reportTemplate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "report")
	@OrderColumn
	private List<ReportPart> reportParts = new ArrayList<ReportPart>();

	public WorkshopVisitReport() {

		setTestDate(new Date());
		super.setReportStatus("DRAFT");

	}

	public WorkshopVisitReport(WorkshopVisitReportTemplate reportTemplate,
			ReportService rs) {

		this.reportTemplate = reportTemplate;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		super.setTestDate(new Date());
		super.setReportStatus("DRAFT");
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
			reportPart.setReportQuestionGroups(reportQuestionGroupList);
			reportParts.add(reportPart);
		}

	}

	public WorkshopVisitReport(Long id, String reportDate, Importer importer,
			Workshop workshop, User user, String reportStatus) {
		super.setId(id);
		super.setTestDateString(reportDate);
		super.setImporter(importer);
		super.setWorkshop(workshop);
		super.setUser(user);
		super.setReportStatus(reportStatus);
	}

	public WorkshopVisitReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(WorkshopVisitReportTemplate reportTemplate) {
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

	public boolean isHighlightsSet() {
		return highlightsSet;
	}

	public void setHighlightsSet(boolean highlightsSet) {
		this.highlightsSet = highlightsSet;
	}

	public List<ReportPart> getReportParts() {
		return reportParts;
	}

	public void setReportParts(List<ReportPart> reportParts) {
		this.reportParts = reportParts;
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

	public WorkshopVisitReport calculateReportScore(ReportService rs) {

		for (ReportPart reportPart : this.reportParts) {
			reportPart.setMaxScore(0);
			reportPart.setScore(-1);

			for (ReportQuestionGroup questionGroup : reportPart
					.getReportQuestionGroups()) {

				questionGroup.setScore(-1);
				questionGroup.setMaxScore(0);

				for (Answer answer : questionGroup.getAnswers()) {
					if (answer.isRemoveAnswerFromReport() != true) {
						if (answer instanceof MultipleChoiceAnswer) {
							MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
							MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) mca
									.getQuestion();

							int questionMaxScore = 0;
							for (MultipleChoiceOption option : mcq
									.getOptionsList()) {
								if (option.getPoints() != -1
										&& option.getPoints() > questionMaxScore) {
									questionMaxScore = option.getPoints();

								}
							}

							mca.setMaxScore(questionMaxScore); // Asetetaan
							// monivalintakysymyksen
							// maksimipistemäärä

							// Lasketaan pisteet jos käyttäjä on tehnyt valinnan
							// ja monivalinta ei ole sellainen, jonka vastausta
							// ei pisteytetä (pistemäärä -1)

							if (mca.getChosenOptionIndex() != -1
									&& mcq.getOptionsList()
											.get(mca.getChosenOptionIndex())
											.getPoints() != -1) {

								mca.setScore(mcq.getOptionsList()
										.get(mca.getChosenOptionIndex())
										.getPoints());

								// Lisätään kysymysryhmän pisteisiin ja
								// asetetaan
								// kysymysryhmän pisteet näkyviksi raportissa

								if (questionGroup.getScore() == -1)
									questionGroup.setScore(0);
								if (reportPart.getScore() == -1)
									reportPart.setScore(0);

								questionGroup.setMaxScore(questionGroup
										.getMaxScore() + mca.getMaxScore());
								questionGroup
										.setScore(questionGroup.getScore()
												+ mcq.getOptionsList()
														.get(mca.getChosenOptionIndex())
														.getPoints());

							}

						}

						if (answer instanceof PointsAnswer) {

							PointsQuestion pointsQuestion = (PointsQuestion) answer
									.getQuestion();
							PointsAnswer pointsAnswer = (PointsAnswer) answer;
							if (pointsAnswer.getGivenPoints() != -1) {

								if (questionGroup.getScore() == -1)
									questionGroup.setScore(0);
								if (reportPart.getScore() == -1)
									reportPart.setScore(0);
								questionGroup.setMaxScore(questionGroup
										.getMaxScore()
										+ pointsQuestion.getMaxPoints());
								questionGroup.setScore(questionGroup.getScore()
										+ pointsAnswer.getGivenPoints());

							}
						}

						if (answer instanceof OptionalQuestionsAnswer) {
							OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) answer;
							int optionalAnswersCounter = 0;
							if (oqa.getQuestions() != null) {
								for (Question optionalQuestion : oqa
										.getQuestions()) {
									if (optionalQuestion instanceof MultipleChoiceQuestion) {
										MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) optionalQuestion;
										MultipleChoiceAnswer mca = (MultipleChoiceAnswer) oqa
												.getAnswers()
												.get(optionalAnswersCounter++);

										int maxScore = 0;
										for (MultipleChoiceOption option : mcq
												.getOptionsList()) {
											if (option.getPoints() != -1
													&& option.getPoints() > maxScore) {
												maxScore = option.getPoints();

											}
										}

										mca.setMaxScore(maxScore); // Asetetaan
										// monivalintakysymyksen
										// maksimipistemäärä

										// Lasketaan pisteet jos käyttäjä on
										// tehnyt
										// valinnan
										// ja
										// monivalinta ei ole sellainen, jota ei
										// pisteytetä
										// (pistemäärä -1)

										if (mca.getChosenOptionIndex() != -1
												&& mcq.getOptionsList()
														.get(mca.getChosenOptionIndex())
														.getPoints() != -1) {

											mca.setScore(mcq
													.getOptionsList()
													.get(mca.getChosenOptionIndex())
													.getPoints());
											if (questionGroup.getScore() == -1)
												questionGroup.setScore(0);

											if (reportPart.getScore() == -1)
												reportPart.setScore(0);
											// Lisätään kysymysryhmän pisteisiin
											// ja
											// asetetaan
											// kysymysryhmän pisteet näkyviksi
											// raportissa

											questionGroup
													.setMaxScore(questionGroup
															.getMaxScore()
															+ maxScore);

											questionGroup
													.setScore(questionGroup
															.getScore()
															+ mcq.getOptionsList()
																	.get(mca.getChosenOptionIndex())
																	.getPoints());

										}

									}

									if (optionalQuestion instanceof PointsQuestion) {

										PointsQuestion pointsQuestion = (PointsQuestion) optionalQuestion;
										PointsAnswer pointsAnswer = (PointsAnswer) oqa
												.getAnswers()
												.get(optionalAnswersCounter++);
										if (pointsAnswer.getGivenPoints() != -1) {

											if (questionGroup.getScore() == -1)
												questionGroup.setScore(0);
											if (reportPart.getScore() == -1)
												reportPart.setScore(0);
											questionGroup
													.setMaxScore(questionGroup
															.getMaxScore()
															+ pointsQuestion
																	.getMaxPoints());
											questionGroup
													.setScore(questionGroup
															.getScore()
															+ pointsAnswer
																	.getGivenPoints());

										}
									}

								}
							}
						}

					}

				}

				// Lisätään kysymysryhmän pisteet ja maksimipisteet raportin
				// osan pisteisiin

				if (questionGroup.getScore() != -1) {
					reportPart.setScore(reportPart.getScore()
							+ questionGroup.getScore());
					reportPart.setMaxScore(reportPart.getMaxScore()
							+ questionGroup.getMaxScore());
					reportPart.setScorePercentage((int) Math
							.round((double) reportPart.getScore()
									/ (double) reportPart.getMaxScore() * 100));

					questionGroup.setScorePercentage((int) Math
							.round((double) questionGroup.getScore()
									/ (double) questionGroup.getMaxScore()
									* 100));
				}

			}

			if (reportPart.getScore() != -1)
				reportPart.setScorePercentage((int) Math
						.round((double) reportPart.getScore()
								/ (double) reportPart.getMaxScore() * 100));

		}

		int reportTotalScore = 0;
		int reportMaxScore = 0;
		for (ReportPart part : reportParts) {
			if (part.getScore() != -1) {
				reportTotalScore += part.getScore();
				reportMaxScore += part.getMaxScore();

			}
		}

		totalScorePercentage = (int) Math.round((double) reportTotalScore
				/ (double) reportMaxScore * 100);
		return (WorkshopVisitReport) rs.saveReport(this);

	}

	public void setRemovedQuestions() {
		for (ReportPart part : reportParts) {
			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				for (Answer answer : group.getAnswers()) {
					if (answer instanceof MultipleChoiceAnswer
							&& answer.isRemoveAnswerFromReport()) {
						MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
						mca.setChosenOptionIndex(-1);
						mca.setRemarks(null);
					}
					if (answer instanceof PointsAnswer
							&& answer.isRemoveAnswerFromReport()) {
						PointsAnswer pa = (PointsAnswer) answer;
						pa.setGivenPoints(-1);
						pa.setRemarks(null);
					}
				}
			}
		}
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
							for (Answer loopAnswer : opa.getAnswers()) {
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
