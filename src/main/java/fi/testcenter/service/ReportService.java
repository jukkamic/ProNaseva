package fi.testcenter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.User;
import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.PhoneCallTestReport;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;
import fi.testcenter.domain.reportSummary.AnswerSummary;
import fi.testcenter.domain.reportSummary.MultipleChoiceAnswerSummary;
import fi.testcenter.domain.reportSummary.PointsAnswerSummary;
import fi.testcenter.domain.reportSummary.QuestionGroupSummary;
import fi.testcenter.domain.reportSummary.ReportInfo;
import fi.testcenter.domain.reportSummary.ReportPartSummary;
import fi.testcenter.domain.reportSummary.ReportSummary;
import fi.testcenter.domain.reportTemplate.ReportTemplatePart;
import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;
import fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate;
import fi.testcenter.repository.AnswerRepository;
import fi.testcenter.repository.ImpPtItemRepository;
import fi.testcenter.repository.OptionalQuestionsAnswerRepository;
import fi.testcenter.repository.ReportQuestionGroupRepository;
import fi.testcenter.repository.ReportRepository;
import fi.testcenter.web.ReportSummarySearchCriteria;
import fi.testcenter.web.SearchReportCriteria;

@Service
public class ReportService {

	@Autowired
	private ReportRepository rr;

	@Autowired
	private OptionalQuestionsAnswerRepository oqar;

	@Autowired
	private UserAccountService us;

	@Autowired
	private WorkshopService ws;

	@Autowired
	private ImporterService is;

	@Autowired
	private ReportTemplateService rts;

	@Autowired
	private ReportQuestionGroupRepository rqgr;

	@Autowired
	private AnswerRepository ar;

	@Autowired
	private ImpPtItemRepository ipr;

	@PersistenceContext()
	EntityManager em;

	@Transactional
	public Report saveReport(Report report) {

		return rr.save(report);
	}

	@Transactional
	public List<Report> findAllReports() {
		return rr.findAll();
	}

	@Transactional
	public Report getReportById(Long id) {
		return rr.findOne(id);
	}

	@Transactional
	public void deleteReport(Report report) {
		if (report instanceof WorkshopVisitReport) {
			for (ReportPart part : ((WorkshopVisitReport) report)
					.getReportParts()) {
				removeOptionalAnswers(part.getReportQuestionGroups());
			}

		}
		if (report instanceof PhoneCallTestReport)
			removeOptionalAnswers(((PhoneCallTestReport) report)
					.getReportQuestionGroups());
		rr.delete(report);
		if (report.getUser().isEnabled() == false) {
			Query query = em
					.createQuery("SELECT COUNT(r) FROM Report r WHERE r.user = :user");
			query.setParameter("user", report.getUser());
			Long reportCount = (Long) query.getSingleResult();
			try {
				if (reportCount.equals(0))
					us.deleteUser(report.getUser());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (report.getWorkshop().isActive() == false) {
			Query query = em
					.createQuery("SELECT COUNT(r.id) FROM Report r WHERE r.workshop = :workshop");
			query.setParameter("workshop", report.getWorkshop());
			Long reportCount = (Long) query.getSingleResult();
			try {
				if (reportCount.equals(0))
					System.out.println("equals 0");
				ws.deleteWorkshop(report.getWorkshop());
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (report.getImporter().isActive() == false) {
				query = em
						.createQuery("SELECT COUNT(r.id) FROM Report r WHERE r.importer = :importer");
				query.setParameter("importer", report.getImporter());
				reportCount = (Long) query.getSingleResult();
				try {
					if (reportCount.equals(0))
						System.out.println("equals 0");
					is.deleteImporter(report.getImporter());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Transactional
	public void removeOptionalAnswers(List<ReportQuestionGroup> groups) {
		for (ReportQuestionGroup group : groups) {
			for (Answer answer : group.getAnswers()) {
				if (answer instanceof OptionalQuestionsAnswer) {
					OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) answer;
					List<Answer> deleteList = oqa.getOptionalAnswers();
					oqa.setOptionalAnswers(null);
					oqa.setReportQuestionGroup(null);
					if (deleteList != null) {
						try {
							ar.save(oqa);
							// deleteAnswers(deleteList);
							ar.deleteInBatch(deleteList);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			}
		}

	}

	@Transactional
	public List<Report> findSearchReports() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.testDate, NEW fi.testcenter.domain.Report(r.id, r.testDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r ORDER BY r.testDate DESC")
				.getResultList();

		ArrayList<Report> resultReports = new ArrayList<Report>();
		for (Object[] result : reports) {
			resultReports.add((Report) result[1]);
		}

		return resultReports;

	}

	@Transactional
	public List<ReportQueryObject> findReportsAwaitingApproval() {

		List<Object[]> reports = em
				.createQuery(
						"SELECT r.testDate, NEW fi.testcenter.service.ReportQueryObject(r.id, r.testDateString, r.importer, r.workshop, r.user, r.reportStatus, TYPE(r)) "
								+ "FROM Report r WHERE r.reportStatus = 'AWAIT_APPROVAL' ORDER BY r.testDate DESC")
				.getResultList();

		ArrayList<ReportQueryObject> resultReports = new ArrayList<ReportQueryObject>();
		for (Object[] result : reports) {
			resultReports.add((ReportQueryObject) result[1]);
		}

		return resultReports;

	}

	@Transactional
	public List<ReportQueryObject> findUserOwnReportList(Long userId) {

		List<Object[]> reports = em
				.createQuery(
						"SELECT r.testDate, NEW fi.testcenter.service.ReportQueryObject(r.id, r.testDateString, r.importer, r.workshop, r.user, r.reportStatus, TYPE(r)) "
								+ "FROM Report r WHERE r.user.id = :userId AND r.reportStatus != 'APPROVED' ORDER BY r.testDate DESC")
				.setParameter("userId", userId).getResultList();

		ArrayList<ReportQueryObject> resultReports = new ArrayList<ReportQueryObject>();
		for (Object[] result : reports) {
			resultReports.add((ReportQueryObject) result[1]);
		}

		return resultReports;

	}

	@Transactional
	public List<ReportQueryObject> findClientUserOwnReports(User user) {
		Query query = em
				.createQuery("SELECT r.testDate, NEW fi.testcenter.service.ReportQueryObject(r.id, r.testDateString, r.importer, r.workshop, r.user, r.reportStatus, TYPE(r)) "
						+ "FROM Report r WHERE r.workshop IN :workshops AND r.importer = :importer AND r.reportStatus = 'APPROVED' ORDER BY r.testDate DESC");

		query.setParameter("workshops", user.getWorkshops());
		query.setParameter("importer", user.getImporter());

		List<Object[]> reports = query.getResultList();

		ArrayList<ReportQueryObject> resultReports = new ArrayList<ReportQueryObject>();
		if (reports != null) {

			for (Object[] result : reports) {
				resultReports.add((ReportQueryObject) result[1]);
			}
		}

		return resultReports;

	}

	@Transactional
	public Long findReportCountByWorkshopId(Long id) {
		TypedQuery<Long> query = em
				.createQuery(
						"SELECT COUNT(r) FROM Report r WHERE r.workshop.id = :workshopId",
						Long.class);
		Long result = (Long) query.setParameter("workshopId", id)
				.getSingleResult();

		return result;

	}

	@Transactional
	public Long findReportCountByImporterId(Long id) {
		TypedQuery<Long> query = em
				.createQuery(
						"SELECT COUNT(r) FROM Report r WHERE r.importer.id = :importerId",
						Long.class);
		Long result = (Long) query.setParameter("importerId", id)
				.getSingleResult();

		return result;

	}

	@Transactional
	public List<ReportQueryObject> findReportsBySearchCriteria(
			SearchReportCriteria searchReportCriteria) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
		Root<Report> report = q.from(Report.class);
		q.multiselect(report.get("testDate"), report.type(), cb.construct(
				ReportQueryObject.class, report.get("id"),
				report.get("testDateString"), report.get("importer"),
				report.get("workshop"), report.get("user"),
				report.get("reportStatus")));

		Predicate criteria = cb.conjunction();
		if (searchReportCriteria.getUserId() != null) {
			ParameterExpression<Long> p = cb.parameter(Long.class, "userId");
			criteria = cb.and(criteria,
					cb.equal(report.get("user").get("id"), p));
		}

		if (searchReportCriteria.getWorkshopId() != null) {
			ParameterExpression<Long> p = cb
					.parameter(Long.class, "workshopId");
			criteria = cb.and(criteria,
					cb.equal(report.get("workshop").get("id"), p));
		}

		if (searchReportCriteria.getImporterId() != null) {
			ParameterExpression<Long> p = cb
					.parameter(Long.class, "importerId");
			criteria = cb.and(criteria,
					cb.equal(report.get("importer").get("id"), p));
		}

		if ((searchReportCriteria.getStartDate() != null && searchReportCriteria
				.getStartDate() != "")
				&& (searchReportCriteria.getEndDate() == null || searchReportCriteria
						.getEndDate() == "")) {
			ParameterExpression<Date> p = cb.parameter(Date.class, "startDate");
			Path<Date> datePath = report.get("testDate");
			criteria = cb.and(criteria, cb.greaterThanOrEqualTo(datePath, p));
		}

		if (searchReportCriteria.getStartDate() != null
				&& searchReportCriteria.getStartDate() != ""
				&& searchReportCriteria.getEndDate() != null
				&& searchReportCriteria.getEndDate() != "") {
			ParameterExpression<Date> startDate = cb.parameter(Date.class,
					"startDate");
			ParameterExpression<Date> endDate = cb.parameter(Date.class,
					"endDate");
			Path<Date> datePath = report.get("date");
			criteria = cb.and(criteria,
					cb.between(datePath, startDate, endDate));
		}

		if ((searchReportCriteria.getStartDate() == null || searchReportCriteria
				.getStartDate() == "")
				&& searchReportCriteria.getEndDate() != null
				&& searchReportCriteria.getEndDate() != "") {

			ParameterExpression<Date> endDate = cb.parameter(Date.class,
					"endDate");
			Path<Date> datePath = report.get("testDate");
			criteria = cb
					.and(criteria, cb.lessThanOrEqualTo(datePath, endDate));
		}

		q.where(criteria);
		q.orderBy(cb.desc(report.get("testDate")));

		// SET PARAMETERS

		TypedQuery<Object[]> typedQuery = em.createQuery(q);
		if (searchReportCriteria.getUserId() != null)
			typedQuery.setParameter("userId", searchReportCriteria.getUserId());

		if (searchReportCriteria.getWorkshopId() != null)
			typedQuery.setParameter("workshopId",
					searchReportCriteria.getWorkshopId());

		if (searchReportCriteria.getImporterId() != null)
			typedQuery.setParameter("importerId",
					searchReportCriteria.getImporterId());

		if ((searchReportCriteria.getStartDate() != null && searchReportCriteria
				.getStartDate() != "")
				&& (searchReportCriteria.getEndDate() == null || searchReportCriteria
						.getEndDate() == "")) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date queryDate = new Date();
			try {
				queryDate = dateFormat.parse(searchReportCriteria
						.getStartDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
			typedQuery.setParameter("startDate", queryDate);

		}

		if (searchReportCriteria.getStartDate() != null
				&& searchReportCriteria.getStartDate() != ""
				&& searchReportCriteria.getEndDate() != null
				&& searchReportCriteria.getEndDate() != "") {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date queryStartDate = new Date();
			Date queryEndDate = new Date();
			try {
				queryStartDate = dateFormat.parse(searchReportCriteria
						.getStartDate());
				queryEndDate = dateFormat.parse(searchReportCriteria
						.getEndDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
			typedQuery.setParameter("startDate", queryStartDate);
			typedQuery.setParameter("endDate", queryEndDate);

		}

		if ((searchReportCriteria.getStartDate() == null || searchReportCriteria
				.getStartDate() == "")
				&& searchReportCriteria.getEndDate() != null
				&& searchReportCriteria.getEndDate() != "") {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			Date queryEndDate = new Date();
			try {
				queryEndDate = dateFormat.parse(searchReportCriteria
						.getEndDate());
			} catch (Exception e) {
				e.printStackTrace();
			}
			typedQuery.setParameter("endDate", queryEndDate);

		}

		List<Object[]> resultList = typedQuery.getResultList();
		List<ReportQueryObject> reportList = new ArrayList<ReportQueryObject>();

		for (Object[] result : resultList) {

			ReportQueryObject resultObject = (ReportQueryObject) result[2];
			if (result[1].toString().equals(
					"class fi.testcenter.domain.report.WorkshopVisitReport"))
				resultObject.setReportClass("WorkshopVisitReport");
			else
				resultObject.setReportClass("PhoneCallTestReport");
			reportList.add(resultObject);
		}

		return reportList;
	}

	@Transactional
	public void deleteOptionalAnswers(List<Answer> answers) {

		ar.deleteInBatch(answers);

	}

	@Transactional
	public Answer saveAnswer(Answer answer) {
		return ar.save(answer);
	}

	// @Transactional
	// public void deleteAnswers(List<Answer> answers) {
	// for (Answer answer : answers) {
	// if (answer instanceof ImportantPointsAnswer
	// && ((ImportantPointsAnswer) answer).getAnswerItems() != null) {
	// ipr.deleteInBatch(((ImportantPointsAnswer) answer)
	// .getAnswerItems());
	// }
	//
	// }
	// ar.deleteInBatch(answers);
	// }

	// @Transactional
	// public OptionalQuestionsAnswer saveOptionalQuestionsAnswer(
	// OptionalQuestionsAnswer oqa) {
	//
	// return oqar.save(oqa);
	// }

	// @Transactional
	// public void deleteOptionalAnswer(OptionalQuestionsAnswer answer) {
	//
	// oqar.delete(answer);
	// }

	@Transactional
	public void deleteAnswer(Answer answer) {
		ar.delete(answer);
	}

	@Transactional
	public ReportSummary generateReportSummary(
			ReportSummarySearchCriteria criteria) {
		// SIISTITTÄVÄ HAKUJA MYÖHEMMIN NIIN ETTÄ KESKIARVOT HAETAAN SQL-HAULLA
		TypedQuery query = em
				.createQuery(
						"SELECT r FROM Report r WHERE r.importer = :importer AND r.reportTemplate = :template",
						WorkshopVisitReport.class);
		query.setParameter("importer", criteria.getImporter());
		query.setParameter("template", criteria.getTemplate());

		List<Report> summaryReports = query.getResultList();
		ReportSummary summary = new ReportSummary();
		summary.setImporter(criteria.getImporter());

		List<ReportInfo> reportInfos = new ArrayList<ReportInfo>();
		for (Report report : summaryReports) {
			ReportInfo info = new ReportInfo();
			info.setWorkshop(report.getWorkshop());
			info.setTotalScorePercentage(report.getTotalScorePercentage());
			reportInfos.add(info);

		}
		summary.setReportInfo(reportInfos);

		int partIndex = 0;

		// REPORTPARTSUMMARY-LOOP

		for (ReportTemplatePart part : ((WorkshopVisitReportTemplate) criteria
				.getTemplate()).getReportParts()) {
			ReportPartSummary partSummary = new ReportPartSummary();
			partSummary.setReportTemplatePart(part);
			int totalScoreForReportParts = 0;
			int partCount = 0;

			for (Report summaryReport : summaryReports) {
				WorkshopVisitReport wsReport = (WorkshopVisitReport) summaryReport;
				ReportPart summaryReportPart = wsReport.getReportParts().get(
						partIndex);

				if (summaryReportPart.getScore() != -1) {

					totalScoreForReportParts += summaryReportPart
							.getScorePercentage();
					partCount++;
				}
			}

			if (partCount > 0) {
				partSummary.setAverageScorePercengage(totalScoreForReportParts
						/ partCount);

			}

			// QUESTIONGROUPSUMMARY-LOOP

			int groupIndex = 0;
			for (ReportTemplateQuestionGroup group : part.getQuestionGroups()) {
				QuestionGroupSummary groupSummary = new QuestionGroupSummary();
				groupSummary.setReportTemplateQuestionGroup(group);

				// LASKETAAN KYSYMYSRYHMÄN PISTEET RAPORTEISTA

				int totalScoreForQuestionGroups = 0;
				int groupCount = 0;
				for (Report summaryReport : summaryReports) {
					WorkshopVisitReport wsReport = (WorkshopVisitReport) summaryReport;
					ReportPart summaryReportPart = wsReport.getReportParts()
							.get(partIndex);
					ReportQuestionGroup summaryReportQuestionGroup = summaryReportPart
							.getReportQuestionGroups().get(groupIndex);

					if (summaryReportQuestionGroup.getScore() != -1) {
						totalScoreForQuestionGroups += summaryReportQuestionGroup
								.getScorePercentage();

						groupCount++;
					}

				}
				if (groupCount > 0)
					groupSummary
							.setAverageScorePercengage(totalScoreForQuestionGroups
									/ groupCount);

				// TEHDÄÄN KYSYMYSRYHMÄN KYSYMYSTEN VASTAUSYHTEENVEDOT

				int questionIndex = 0;
				for (Question question : group.getQuestions()) {
					if (question instanceof MultipleChoiceQuestion) {

						groupSummary.getAnswerSummaries().add(
								(AnswerSummary) getMultipleChoiceAnswerSummary(
										(MultipleChoiceQuestion) question,
										summaryReports));
						groupSummary.setShowInSummary(true);
						partSummary.setShowInSummary(true);

					}

					if (question instanceof OptionalQuestions) {
						OptionalQuestions oq = (OptionalQuestions) question;

						int optionalAnswerQuestionsIndex = 0;
						for (Question optionalQuestion : oq.getQuestions()) {

							if (optionalQuestion instanceof PointsQuestion) {

								groupSummary
										.getAnswerSummaries()
										.add((AnswerSummary) getPointsAnswerSummary(
												(PointsQuestion) optionalQuestion,
												partIndex, groupIndex,
												questionIndex,
												optionalAnswerQuestionsIndex,
												summaryReports));
								groupSummary.setShowInSummary(true);
								partSummary.setShowInSummary(true);
							}
							optionalAnswerQuestionsIndex++;
						}
						questionIndex++;

					}
					if (!question.getSubQuestions().isEmpty()) {
						for (Question subQuestion : question.getSubQuestions()) {
							if (subQuestion instanceof MultipleChoiceQuestion) {

								groupSummary
										.getAnswerSummaries()
										.add((AnswerSummary) getMultipleChoiceAnswerSummary(
												(MultipleChoiceQuestion) subQuestion,
												summaryReports));
								groupSummary.setShowInSummary(true);
								partSummary.setShowInSummary(true);

							}

						}
					}
					questionIndex++;
				}
				partSummary.getQuestionGroupSummaries().add(groupSummary);
				groupIndex++;

			}
			summary.getReportPartSummaries().add(partSummary);
			partIndex++;
		}

		return summary;
	}

	private PointsAnswerSummary getPointsAnswerSummary(PointsQuestion question,
			int partIndex, int groupIndex, int questionIndex,
			int optionalAnswerQuestionsIndex, List<Report> reports) {
		PointsAnswerSummary summary = new PointsAnswerSummary();
		summary.setQuestion(question);
		int totalScore = 0;
		int maxScore = 0;
		int count = 0;
		int[] pointCounts = new int[question.getMaxPoints() + 1];
		for (int i = 0; i < pointCounts.length; i++) {
			pointCounts[i] = 0;
		}

		for (Report report : reports) {
			if (report instanceof WorkshopVisitReport) {
				WorkshopVisitReport wsReport = (WorkshopVisitReport) report;
				OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) wsReport
						.getReportParts().get(partIndex)
						.getReportQuestionGroups().get(groupIndex).getAnswers()
						.get(questionIndex);

				for (Answer listAnswer : oqa.getOptionalAnswers()) {
					PointsAnswer answer = null;
					if (listAnswer.getQuestion().getId()
							.equals(question.getId())) {

						answer = (PointsAnswer) listAnswer;

					}

					if (answer != null && answer.getGivenPoints() != -1) {

						pointCounts[answer.getGivenPoints()] = pointCounts[answer
								.getGivenPoints()] + 1;
						totalScore += answer.getGivenPoints();
						count++;
						maxScore = ((PointsQuestion) answer.getQuestion())
								.getMaxPoints();
					}
				}

			}
			double average = (double) totalScore / count;
			average = Math.round(average * 10) / 10;
			summary.setAverageScore(average);
			summary.setAnswerCountForPoints(pointCounts);
			summary.setTimesAskedInReports(count);
			summary.setMaxScore(maxScore);
		}
		return summary;

	}

	private MultipleChoiceAnswerSummary getMultipleChoiceAnswerSummary(
			MultipleChoiceQuestion mcq, List<Report> reports) {
		MultipleChoiceAnswerSummary mcaSummary = new MultipleChoiceAnswerSummary();
		Map<MultipleChoiceOption, Integer> chosenOptionsCount = new LinkedHashMap<MultipleChoiceOption, Integer>();
		mcaSummary.setQuestion(mcq);

		double totalScore = 0;
		int count = 0;

		for (MultipleChoiceOption option : mcq.getOptionsList()) {
			chosenOptionsCount.put(option, Integer.valueOf(0));
		}

		int maxScore = 0;
		for (Report report : reports) {
			if (report instanceof WorkshopVisitReport) {

				WorkshopVisitReport wsReport = (WorkshopVisitReport) report;
				for (ReportPart part : wsReport.getReportParts()) {
					for (ReportQuestionGroup group : part
							.getReportQuestionGroups()) {
						for (Answer answer : group.getAnswers()) {
							if (answer instanceof MultipleChoiceAnswer) {

								if (answer.getQuestion().getId()
										.equals(mcq.getId())) {

									MultipleChoiceAnswer mca = (MultipleChoiceAnswer) answer;
									maxScore = mca.getMaxScore();
									if (!mca.isRemoveAnswerFromReport()) {

										for (MultipleChoiceOption chosenOption : mca
												.getChosenOptions()) {

											MultipleChoiceOption key = new MultipleChoiceOption();

											for (Map.Entry<MultipleChoiceOption, Integer> entry : chosenOptionsCount
													.entrySet()) {
												if (entry
														.getKey()
														.getMultipleChoiceOption()
														.equals(chosenOption
																.getMultipleChoiceOption()))
													key = entry.getKey();
											}

											int addToCount = chosenOptionsCount
													.get(key).intValue() + 1;
											chosenOptionsCount
													.put(key,
															Integer.valueOf(addToCount));

										}
										if (mca.getScore() != -1) {
											totalScore += mca.getScore();
											count++;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (count > 0)
			mcaSummary
					.setAverageScore(Math.round(totalScore / count * 100) / 100);
		mcaSummary.setChosenOptionsCount(chosenOptionsCount);
		mcaSummary.setMaxScore(maxScore);

		return mcaSummary;
	}
}
