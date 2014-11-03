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

import fi.testcenter.domain.Report;
import fi.testcenter.domain.ReportHighlight;
import fi.testcenter.domain.ReportTemplate;
import fi.testcenter.repository.ImporterRepository;
import fi.testcenter.repository.ReportHighlightRepository;
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
	private MockReportTemplate rts;

	@Autowired
	private ReportHighlightRepository rhls;

	@PersistenceContext()
	EntityManager em;

	@Transactional(readOnly = true)
	public ReportTemplate getReportTemplate() {
		return rts.getReportTemplate("Volvo");
	}

	@Transactional
	public void saveReport(Report report) {

		rr.save(report);
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

		rr.delete(report);
	}

	public List<Report> getSearchReports() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r ORDER BY r.date DESC")
				.getResultList();

		ArrayList<Report> resultReports = new ArrayList<Report>();
		for (Object[] result : reports) {
			resultReports.add((Report) result[1]);
		}

		return resultReports;

	}

	public List<Report> getReportsAwaitingApproval() {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r WHERE r.reportStatus = 'AWAIT_APPROVAL' order by r.date DESC")
				.getResultList();

		ArrayList<Report> resultReports = new ArrayList<Report>();
		for (Object[] result : reports) {
			resultReports.add((Report) result[1]);
		}

		return resultReports;
	}

	public List<Report> getReportsByUserId(Long userId) {
		List<Object[]> reports = em
				.createQuery(
						"SELECT r.date, NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r WHERE r.user.id = :userId ORDER BY r.date DESC")
				.setParameter("userId", userId).getResultList();

		ArrayList<Report> resultReports = new ArrayList<Report>();
		for (Object[] result : reports) {
			resultReports.add((Report) result[1]);
		}
		return resultReports;

	}

	public List<Report> searchReports(SearchReportCriteria searchReportCriteria) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> q = cb.createQuery(Object[].class);
		Root<Report> report = q.from(Report.class);
		q.multiselect(report.get("date"), cb.construct(Report.class,
				report.get("id"), report.get("reportDate"),
				report.get("importer"), report.get("workshop"),
				report.get("user"), report.get("reportStatus")));

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
		List<Report> reportList = new ArrayList<Report>();

		for (Object[] result : resultList) {
			reportList.add((Report) result[1]);
		}

		return reportList;
	}

	public void deleteReportHighlights(List<ReportHighlight> highlights) {
		em.flush();
		rhls.deleteInBatch(highlights);
	}
}
