package fi.testcenter.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.Workshop;
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
	private ReportTemplateService rts;

	@Transactional(readOnly = true)
	public Report getReportTemplate() {
		return rts.getReportTemplate("Volvo");
	}

	@Transactional
	public void saveReport(Report report) {
		log.debug("report workshop id: " + report.getWorkshop().getId());
		Workshop workshop = wr.findOne(report.getWorkshopId());
		log.debug("workshop ID: " + workshop.getId());
		report.setWorkshop(workshop);

		Importer importer = ir.findOne(report.getImporterId());
		report.setImporter(importer);

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
