package fi.testcenter.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;
import fi.testcenter.repository.AnswerRepository;
import fi.testcenter.repository.ImpPtItemRepository;
import fi.testcenter.repository.ImporterRepository;
import fi.testcenter.repository.OptionalQuestionsAnswerRepository;
import fi.testcenter.repository.ReportQuestionGroupRepository;
import fi.testcenter.repository.ReportRepository;
import fi.testcenter.repository.WorkshopRepository;
import fi.testcenter.web.SearchReportCriteria;

@Service
public class ReportService {

	private Logger log = Logger
			.getLogger("fi.testcenter.service.ReportService");

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
	public void deleteReport(WorkshopVisitReport report) {
		for (ReportPart part : report.getReportParts()) {
			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				for (Answer answer : group.getAnswers()) {
					if (answer instanceof OptionalQuestionsAnswer) {
						OptionalQuestionsAnswer oqa = (OptionalQuestionsAnswer) answer;
						List<Answer> deleteList = oqa.getAnswers();
						oqa.setAnswers(null);
						oqa.setReportQuestionGroup(null);
						if (deleteList != null) {
							try {
								saveOptionalQuestionsAnswer(oqa);
								deleteAnswers(deleteList);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
				}
			}
		}
		rr.delete(report);
	}

	@Transactional
	public List<WorkshopVisitReport> findSearchReports() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM WorkshopVisitReport r ORDER BY r.date DESC")
				.getResultList();

		ArrayList<WorkshopVisitReport> resultReports = new ArrayList<WorkshopVisitReport>();
		for (Object[] result : reports) {
			resultReports.add((WorkshopVisitReport) result[1]);
		}

		return resultReports;

	}

	@Transactional
	public List<WorkshopVisitReport> findReportsAwaitingApproval() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.report.WorkshopVisitReport(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM WorkshopVisitReport r WHERE r.reportStatus = 'AWAIT_APPROVAL' order by r.date DESC")
				.getResultList();

		ArrayList<WorkshopVisitReport> resultReports = new ArrayList<WorkshopVisitReport>();
		for (Object[] result : reports) {
			resultReports.add((WorkshopVisitReport) result[1]);
		}

		return resultReports;
	}

	@Transactional
	public List<WorkshopVisitReport> findReportsByUserId(Long userId) {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.report.WorkshopVisitReport(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM WorkshopVisitReport r WHERE r.user.id = :userId ORDER BY r.date DESC")
				.setParameter("userId", userId).getResultList();

		ArrayList<WorkshopVisitReport> resultReports = new ArrayList<WorkshopVisitReport>();
		for (Object[] result : reports) {
			resultReports.add((WorkshopVisitReport) result[1]);
		}
		return resultReports;

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
	public List<WorkshopVisitReport> findReportsBySearchCriteria(
			SearchReportCriteria searchReportCriteria) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
		Root<WorkshopVisitReport> report = q.from(WorkshopVisitReport.class);
		q.multiselect(report.get("date"), cb.construct(
				WorkshopVisitReport.class, report.get("id"),
				report.get("reportDate"), report.get("importer"),
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
			Path<Date> datePath = report.get("date");
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
			Path<Date> datePath = report.get("date");
			criteria = cb
					.and(criteria, cb.lessThanOrEqualTo(datePath, endDate));
		}

		q.where(criteria);
		q.orderBy(cb.desc(report.get("date")));

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
		List<WorkshopVisitReport> reportList = new ArrayList<WorkshopVisitReport>();

		for (Object[] result : resultList) {
			reportList.add((WorkshopVisitReport) result[1]);
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

	@Transactional
	public void deleteAnswers(List<Answer> answers) {
		for (Answer answer : answers) {
			if (answer instanceof ImportantPointsAnswer
					&& ((ImportantPointsAnswer) answer).getAnswerItems() != null) {
				ipr.deleteInBatch(((ImportantPointsAnswer) answer)
						.getAnswerItems());
			}

		}
		ar.deleteInBatch(answers);
	}

	@Transactional
	public OptionalQuestionsAnswer saveOptionalQuestionsAnswer(
			OptionalQuestionsAnswer oqa) {

		return oqar.save(oqa);
	}

	@Transactional
	public void deleteOptionalAnswer(OptionalQuestionsAnswer answer) {

		oqar.delete(answer);
	}

	@Transactional
	public void deleteAnswer(Answer answer) {
		ar.delete(answer);
	}

}
