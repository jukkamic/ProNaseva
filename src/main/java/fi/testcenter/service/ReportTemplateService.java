package fi.testcenter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.ReportTemplate;
import fi.testcenter.reportTemplate.AutoasiReportTemplate;
import fi.testcenter.reportTemplate.VolvoReportTemplate;
import fi.testcenter.repository.ImporterRepository;
import fi.testcenter.repository.ReportTemplateRepository;

@Service
public class ReportTemplateService {

	@PersistenceContext()
	private EntityManager em;

	@Autowired
	private ReportTemplateRepository rtr;

	@Autowired
	private ImporterRepository ir;

	public ReportTemplate findReportTemplate(String name) {
		switch (name) {
		case "Volvo Auto raporttipohja":
			return VolvoReportTemplate.getReportTemplate();

		case "Autoasi raporttipohja":
			return AutoasiReportTemplate.getReportTemplate();
		}
		return null;
	}

	public List<ReportTemplate> findCurrentReportTemplates() {
		String query = "SELECT rt FROM ReportTemplate rt WHERE rt.current = 'true'";
		return em.createQuery(query, ReportTemplate.class).getResultList();
	}

	@Transactional
	public void saveNewReportTemplate(String name) {
		ReportTemplate template = new ReportTemplate();
		try {
			template = rtr.save(findReportTemplate(name));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public ReportTemplate findReportTemplateByName(String name) {
		ReportTemplate template = new ReportTemplate();
		try {
			TypedQuery query = em
					.createQuery(
							"SELECT rt FROM ReportTemplate rt WHERE rt.templateName = :name",
							ReportTemplate.class);
			query.setParameter("name", name);
			template = (ReportTemplate) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return template;
	}

	@Transactional
	public List<ReportTemplate> findUnusedReportTemplates() {
		String query = "SELECT rt FROM ReportTemplate rt WHERE NOT EXISTS (SELECT r FROM Report r WHERE r.reportTemplate = rt)";
		return em.createQuery(query, ReportTemplate.class).getResultList();
	}

	@Transactional
	public void deleteReportTemplateById(Long id) {
		ReportTemplate template = rtr.findOne(id);
		String query = "SELECT i FROM Importer i WHERE i.reportTemplateName = :TemplateName";
		TypedQuery typedQuery = em.createQuery(query, Importer.class);
		typedQuery.setParameter("TemplateName", template.getTemplateName());
		List<Importer> importerList = typedQuery.getResultList();
		for (Importer importer : importerList) {
			importer.setReportTemplateName(null);
			ir.save(importer);
		}
		rtr.delete(template);

	}
}
