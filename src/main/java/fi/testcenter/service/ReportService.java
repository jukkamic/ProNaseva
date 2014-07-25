package fi.testcenter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

	@Transactional
	public List<Report> findAllReports() {

		Query query = em.createQuery("SELECT r FROM Report r", Report.class);

		return query.getResultList();
	}

	@Transactional
	public Report getReportById(Integer id) {
		TypedQuery<Report> query = em.createQuery(
				"SELECT r FROM Report r WHERE r.id = ?1", Report.class);
		query.setParameter(1, id);
		return query.getSingleResult();

	}

}
