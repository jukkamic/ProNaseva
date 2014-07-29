package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Report;
import fi.testcenter.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository rr;

	@Autowired
	private ReportTemplateService rts;

	public Report getReportTemplate() {
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

	// @Transactional
	// public List getReportSearchList() {
	//
	// Query query = em
	// .createQuery("SELECT r.id, r.workshop, r.importer FROM Report r");
	//
	// return query.getResultList();
	// }

	@Transactional
	public Report getReportById(Long id) {
		return rr.findOne(id);
	}

}
