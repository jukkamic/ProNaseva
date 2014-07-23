package fi.testcenter.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.dao.ReportDAOMockVolvo;
import fi.testcenter.domain.Report;

@Service
public class ReportService {

	@Autowired
	private ReportDAOMockVolvo rd;

	@PersistenceContext
	EntityManager em;

	public Report getReportTemplate() {
		return rd.getReportTemplate();
	}

	@Transactional
	public void saveReport(Report report) {
		em.persist(report);
	}

}
