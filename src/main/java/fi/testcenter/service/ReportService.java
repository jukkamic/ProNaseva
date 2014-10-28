package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Report;
import fi.testcenter.domain.ReportTemplate;
import fi.testcenter.repository.ImporterRepository;
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
		return null;
	}
}
