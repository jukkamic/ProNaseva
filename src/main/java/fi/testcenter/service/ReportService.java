package fi.testcenter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.PhoneCallTestReport;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;
import fi.testcenter.domain.reportSummary.AnswerSummary;
import fi.testcenter.domain.reportSummary.MultipleChoiceAnswerSummary;
import fi.testcenter.domain.reportSummary.QuestionGroupSummary;
import fi.testcenter.domain.reportSummary.ReportPartSummary;
import fi.testcenter.domain.reportSummary.ReportSummary;
import fi.testcenter.domain.reportTemplate.ReportTemplatePart;
import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;
import fi.testcenter.domain.reportTemplate.WorkshopVisitReportTemplate;
import fi.testcenter.repository.AnswerRepository;
import fi.testcenter.repository.ImpPtItemRepository;
import fi.testcenter.repository.ImporterRepository;
import fi.testcenter.repository.OptionalQuestionsAnswerRepository;
import fi.testcenter.repository.ReportQuestionGroupRepository;
import fi.testcenter.repository.ReportRepository;
import fi.testcenter.repository.WorkshopRepository;
import fi.testcenter.web.ReportSummarySearchCriteria;
import fi.testcenter.web.SearchReportCriteria;

@Service
public class ReportService {

	@Autowired
	private ReportRepository rr;

	@Autowired
	private WorkshopRepository wr;

	@Autowired
	private ImporterRepository ir;

	@Autowired
	private OptionalQuestionsAnswerRepository oqar;

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
	public List<Report> findReportsAwaitingApproval() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.testDate, NEW fi.testcenter.service.report.WorkshopVisitReport(r.id, r.testDate, r.importer, r.workshop, r.user, r.reportStatus) FROM WorkshopVisitReport r WHERE r.reportStatus = 'AWAIT_APPROVAL' order by r.testDate DESC")
				.getResultList();

		ArrayList<Report> resultReports = new ArrayList<Report>();
		for (Object[] result : reports) {
			resultReports.add((Report) result[1]);
		}

		return resultReports;
	}

	@Transactional
	public List<ReportQueryObject> findUserOwnReportList(Long userId) {

		List<Object[]> reports = em
				.createQuery(
						"SELECT r.testDate, NEW fi.testcenter.service.ReportQueryObject(r.id, r.testDateString, r.importer, r.workshop, r.user, r.reportStatus, TYPE(r)) "
								+ "FROM Report r WHERE r.user.id = :userId ORDER BY r.testDate DESC")
				.setParameter("userId", userId).getResultList();

		ArrayList<ReportQueryObject> resultReports = new ArrayList<ReportQueryObject>();
		for (Object[] result : reports) {
			resultReports.add((ReportQueryObject) result[1]);
		}

		return resultReports;

	}

	@Transactional
	public List<Report> findReportsByUserId(Long userId) {
		//
		// List<Object[]> reports = em
		// .createQuery(
		// "SELECT r.testDate, NEW fi.testcenter.service.ReportQueryObject(r.id, r.testDateString, r.importer, r.workshop, r.user, r.reportStatus, TYPE(r)) "
		// + "FROM Report r WHERE r.user.id = :userId ORDER BY r.testDate DESC")
		// .setParameter("userId", userId).getResultList();
		//
		// ArrayList<ReportQueryObject> resultReports = new
		// ArrayList<ReportQueryObject>();
		// for (Object[] result : reports) {
		// resultReports.add((ReportQueryObject) result[1]);
		// }
		// for (ReportQueryObject o : resultReports)
		// log.debug("query object class: " + o.getReportClass());
		return null;

	}

	@Transactional
	public Long findReportsByWorkshopId(Long id) {
		TypedQuery<Long> query = em.createNamedQuery("workshopReportCount",
				Long.class);
		Long result = (Long) query.setParameter("workshopId", id)
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
							.getReportQuestionGroups().get(groupIndex++);

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

				for (Question question : group.getQuestions()) {
					if (question instanceof MultipleChoiceQuestion) {

						groupSummary.getAnswerSummaries().add(
								(AnswerSummary) getMultipleChoiceAnswerSummary(
										(MultipleChoiceQuestion) question,
										summaryReports));

					}
				}
				partSummary.getQuestionGroupSummaries().add(groupSummary);

			}
			summary.getReportPartSummaries().add(partSummary);
			partIndex++;
		}

		return summary;
	}

	private MultipleChoiceAnswerSummary getMultipleChoiceAnswerSummary(
			MultipleChoiceQuestion mcq, List<Report> reports) {
		MultipleChoiceAnswerSummary mcaSummary = new MultipleChoiceAnswerSummary();
		Map<String, Integer> chosenOptionsCount = new LinkedHashMap<String, Integer>();
		mcaSummary.setQuestion(mcq);

		int maxScore;
		double totalScore = 0;
		int count = 0;

		for (MultipleChoiceOption option : mcq.getOptionsList()) {
			chosenOptionsCount.put(option.getMultipleChoiceOption(),
					Integer.valueOf(0));
		}

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
											int addToCount = chosenOptionsCount
													.get(chosenOption
															.getMultipleChoiceOption())
													.intValue() + 1;
											chosenOptionsCount
													.put(chosenOption
															.getMultipleChoiceOption(),
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
		mcaSummary.setAverageScore(Math.round(totalScore / count * 100) / 100);
		mcaSummary.setChosenOptionsCount(chosenOptionsCount);

		return mcaSummary;
	}
}
