package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.repository.ImporterRepository;

@Service
public class ImporterService {

	@Autowired
	private ImporterRepository ir;

	@PersistenceContext()
	EntityManager em;

	@Transactional(readOnly = true)
	public List<Importer> getImporters() {
		return ir.findAll();
	}

	public Importer getImporterById(Long id) {
		return ir.findOne(id.longValue());
	}

	public void saveImporter(Importer importer) {
		ir.save(importer);
	}

	public void deleteImporter(Importer importer) {
		ir.delete(importer);
	}

	@Transactional
	public List<Importer> getImportersInAplhaOrder() {
		List<Object[]> importerList = em.createQuery(
				"SELECT i.name, i FROM Importer i ORDER BY i.name ASC")
				.getResultList();
		List<Importer> importerReturnList = new ArrayList<Importer>();
		for (Object[] listItem : importerList) {
			importerReturnList.add((Importer) listItem[1]);
		}
		return importerReturnList;
	}

}
