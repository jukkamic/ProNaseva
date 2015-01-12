package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.reportTemplate.ReportTemplate;
import fi.testcenter.reportTemplatesForImporters.AudiCallTestTemplate;
import fi.testcenter.reportTemplatesForImporters.AutoasiReportTemplate;
import fi.testcenter.reportTemplatesForImporters.VolvoReportTemplate;
import fi.testcenter.repository.ImporterRepository;
import fi.testcenter.repository.PhoneCallTestReportTemplateRepository;
import fi.testcenter.repository.ReportTemplateRepository;

@Service
public class ReportTemplateService {

	@PersistenceContext()
	private EntityManager em;

	Logger log = Logger.getLogger("fi.testcenter.web.ReportTemplateService");

	@Autowired
	private ReportTemplateRepository rtr;

	@Autowired
	private PhoneCallTestReportTemplateRepository callTestTemplateRepository;

	@Autowired
	private ImporterRepository ir;

	public ReportTemplate createReportTemplate(String name) {
		switch (name) {
		case "Volvo Auto raporttipohja":
			return VolvoReportTemplate.getReportTemplate();

		case "Autoasi raporttipohja":
			return AutoasiReportTemplate.getReportTemplate();

		case "Audi puhelutestiraportti":
			return AudiCallTestTemplate.getReportTemplate();
		}

		return null;

	}

	@Transactional
	public List<ReportTemplate> findCurrentReportTemplates() {
		String queryString = "SELECT rt FROM ReportTemplate rt WHERE rt.current = 'true'";
		ArrayList<ReportTemplate> returnList = new ArrayList<ReportTemplate>();

		List<ReportTemplate> resultList = em.createQuery(queryString)
				.getResultList();

		// for (ReportTemplate template : resultList) {
		// if (template instanceof WorkshopVisitReportTemplate) {
		// returnList.add((WorkshopVisitReportTemplate) template);
		// log.debug("WorkshopVisitReportTemplate");
		//
		// } else {
		// returnList.add((PhoneCallTestReportTemplate) template);
		// log.debug("PhoneCallTestReportTemplate");
		// }
		// }
		return resultList;
	}

	@Transactional
	public void saveNewReportTemplate(String name) {

		try {

			ReportTemplate savedTemplate = rtr.save(createReportTemplate(name));

			List<ReportTemplate> reportTemplateList = em
					.createQuery(
							"SELECT rt FROM ReportTemplate rt where rt.templateName = :templateName",
							ReportTemplate.class)
					.setParameter("templateName", name).getResultList();
			for (ReportTemplate oldTemplate : reportTemplateList) {
				if (oldTemplate.getId() != savedTemplate.getId()) {
					oldTemplate.setCurrent(false);
					rtr.save(oldTemplate);
				}

			}

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
							"SELECT rt FROM ReportTemplate rt WHERE rt.templateName = :name AND rt.current = true",
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
	public List<ReportTemplate> findImporterReportTemplateList(Long importerId) {
		List<ReportTemplate> templateList = new ArrayList<ReportTemplate>();
		ArrayList<ReportTemplate> returnList = new ArrayList<ReportTemplate>();
		try {
			Query query = em
					.createQuery("SELECT rt FROM ReportTemplate rt WHERE rt.importer.id = :id AND rt.current = true");
			query.setParameter("id", importerId);
			templateList = query.getResultList();

			// for (ReportTemplate template : templateList) {
			// if (template instanceof WorkshopVisitReportTemplate) {
			// returnList.add((WorkshopVisitReportTemplate) template);
			// log.debug("WorkshopVisitReportTemplate");
			//
			// } else
			// returnList.add((PhoneCallTestReportTemplate) template);
			// log.debug("PhoneCallTestReportTemplate");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateList;
	}

	@Transactional
	public void deleteReportTemplateById(Long id) {
		// ReportTemplate template = rtr.findOne(id);
		// String query =
		// "SELECT i FROM Importer i WHERE i.reportTemplateName = :TemplateName";
		// TypedQuery typedQuery = em.createQuery(query, Importer.class);
		// typedQuery.setParameter("TemplateName", template.getTemplateName());
		// List<Importer> importerList = typedQuery.getResultList();
		// for (Importer importer : importerList) {
		// importer.setReportTemplateName(null);
		// ir.save(importer);
		// }
		// rtr.delete(template);
		//
	}
}
