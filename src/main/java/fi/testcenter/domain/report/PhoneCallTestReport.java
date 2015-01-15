package fi.testcenter.domain.report;

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
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.PointsQuestion;
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

		super.setTestDate(new Date());
		super.setReportStatus("DRAFT");

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

	// LASKETAAN RAPORTIN PISTEET MONIVALINTOJEN POHJALTA:

	public void calculateReportScore() {

		int reportTotalScore = 0;
		int reportMaxScore = 0;

		for (ReportQuestionGroup questionGroup : this.getReportQuestionGroups()) {

			questionGroup.setScore(-1);
			questionGroup.setMaxScore(0);

			for (Answer answer : questionGroup.getAnswers()) {
				if (answer.isRemoveAnswerFromReport() != true) {
					if (answer instanceof MultipleChoiceAnswer) {
						MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
						mca.setScoreForChosenOptions();

						// Lisätään kysymysryhmän pisteisiin ja
						// asetetaan
						// kysymysryhmän pisteet näkyviksi raportissa

						if (mca.getScore() != -1) {
							if (questionGroup.getScore() == -1)
								questionGroup.setScore(0);

							questionGroup.setMaxScore(questionGroup
									.getMaxScore() + mca.getMaxScore());
							questionGroup.setScore(questionGroup.getScore()
									+ mca.getScore());

						}

					}

					if (answer instanceof PointsAnswer) {

						PointsQuestion pointsQuestion = (PointsQuestion) answer
								.getQuestion();
						PointsAnswer pointsAnswer = (PointsAnswer) answer;
						if (pointsAnswer.getGivenPoints() != -1) {

							if (questionGroup.getScore() == -1)
								questionGroup.setScore(0);

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
							for (Question optionalQuestion : oqa.getQuestions()) {
								if (optionalQuestion instanceof MultipleChoiceQuestion) {
									MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
									mca.setScoreForChosenOptions();

									// Lisätään kysymysryhmän pisteisiin ja
									// asetetaan
									// kysymysryhmän pisteet näkyviksi
									// raportissa

									if (mca.getScore() != -1) {
										if (questionGroup.getScore() == -1)
											questionGroup.setScore(0);

										questionGroup.setMaxScore(questionGroup
												.getMaxScore()
												+ mca.getMaxScore());
										questionGroup.setScore(questionGroup
												.getScore() + mca.getScore());

									}

								}

								if (optionalQuestion instanceof PointsQuestion) {

									PointsQuestion pointsQuestion = (PointsQuestion) optionalQuestion;
									PointsAnswer pointsAnswer = (PointsAnswer) oqa
											.getOptionalAnswers().get(
													optionalAnswersCounter++);
									if (pointsAnswer.getGivenPoints() != -1) {

										if (questionGroup.getScore() == -1)
											questionGroup.setScore(0);

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
			if (questionGroup.getScore() != -1) {

				questionGroup.setScorePercentage((int) Math
						.round((double) questionGroup.getScore()
								/ (double) questionGroup.getMaxScore() * 100));
				if (reportTotalScore == -1)
					reportTotalScore = 0;
				reportTotalScore += questionGroup.getScore();
				reportMaxScore += questionGroup.getMaxScore();
			}

		}

		super.setTotalScore(reportTotalScore);
		super.setTotalMaxScore(reportMaxScore);
		super.setTotalScorePercentage((int) Math
				.round((double) reportTotalScore / (double) reportMaxScore
						* 100));

	}

	public void setMultipleChoiceAnswers(ReportService rs) {

		for (ReportQuestionGroup group : this.reportQuestionGroups) {

			for (Answer answer : group.getAnswers()) {
				if (answer instanceof MultipleChoiceAnswer) {
					MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
					int mcaIndex = group.getAnswers().indexOf(mca);
					ArrayList<MultipleChoiceOption> newChosenOptionList = new ArrayList<MultipleChoiceOption>();
					if (mca.getChosenOptionsIndex() != null) {
						for (int index : mca.getChosenOptionsIndex()) {
							if (index != -1) {
								newChosenOptionList
										.add(((MultipleChoiceQuestion) answer
												.getQuestion())
												.getOptionsList().get(index));
							}
						}

						mca.setChosenOptions(newChosenOptionList);
						mca = (MultipleChoiceAnswer) rs.saveAnswer(mca);
						group.getAnswers().set(mcaIndex, mca);

					}
				}
			}
		}

	}
}
