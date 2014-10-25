package fi.testcenter.service;

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
		List<Report> reports = em
				.createQuery(
						"SELECT NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r",
						Report.class).getResultList();
		return reports;

	}

	public List<Report> getReportsAwaitingApproval() {
		List<Report> reports = em
				.createQuery(
						"SELECT NEW fi.testcenter.domain.Report(r.id, r.reportDate, r.importer, r.workshop, r.user, r.reportStatus) FROM Report r WHERE r.reportStatus = 'AWAIT_APPROVAL'",
						Report.class).getResultList();
		return reports;
	}

}
