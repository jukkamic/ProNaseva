package fi.testcenter.service;

import java.util.List;

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

}
