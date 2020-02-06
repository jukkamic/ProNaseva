package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.repository.ImporterRepository;

@Service
public class ImporterService {

	@Autowired
	private ImporterRepository ir;

	@Autowired
	private ReportService rs;

	@PersistenceContext()
	EntityManager em;

	@Autowired
	private UserAccountService us;

	@Transactional(readOnly = true)
	public List<Importer> findAllImporters() {
		return ir.findAll();
	}

	@Transactional(readOnly = true)
	public List<Importer> findActiveImporters() {
		Query query = em
				.createQuery("SELECT i FROM Importer i WHERE i.active = true");

		return query.getResultList();
	}

	@Transactional
	public List<Importer> findActiveImportersInAplhaOrder() {
		List<Object[]> importerList = em
				.createQuery(
						"SELECT i.name, i FROM Importer i WHERE i.active = true ORDER BY i.name ASC")
				.getResultList();
		List<Importer> importerReturnList = new ArrayList<Importer>();
		for (Object[] listItem : importerList) {
			importerReturnList.add((Importer) listItem[1]);
		}
		return importerReturnList;
	}

	public Importer findImporterById(Long id) {
		return ir.findOne(id.longValue());
	}

	public Importer saveImporter(Importer importer) {
		return ir.save(importer);
	}

	public void deleteImporter(Importer importer) {

		Long reportCount = new Long(0);
		try {
			reportCount = rs.findReportCountByImporterId(importer.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (reportCount > 0) {
			importer.setActive(false);
			ir.save(importer);
		} else {
			try {
				importer.setWorkshops(null);
				importer.setReportTemplates(null);
				importer = ir.save(importer);
				List<User> userList = us.findAllUsers();
				for (User user : userList) {
					if (user.getImporter() != null
							&& user.getImporter().getId() == importer.getId()) {
						user.setImporter(null);
						us.saveUser(user);
					}
				}
				ir.delete(importer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Transactional
	public List<Importer> findImportersInAplhaOrder() {
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
