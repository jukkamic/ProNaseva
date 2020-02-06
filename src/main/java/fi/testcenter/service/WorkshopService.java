package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.domain.Workshop;
import fi.testcenter.repository.WorkshopRepository;

@Service
public class WorkshopService {

	Logger log = Logger.getLogger("fi.testcenter.service.WorkshopService");

	@Autowired
	WorkshopRepository wr;

	@Autowired
	ReportService rs;

	@Autowired
	ImporterService is;

	@Autowired
	UserAccountService us;

	@PersistenceContext()
	EntityManager em;

	@Transactional(readOnly = true)
	public List<Workshop> getWorkshops() {
		List<Workshop> workshops = wr.findAll();

		return workshops;
	}

	@Transactional
	public Workshop findWorkshop(Long workshopID) {
		return wr.findOne(workshopID);
	}

	@Transactional
	public List<Workshop> findWorkshopsInAplhaOrder() {
		List<Object[]> workshopList = em.createQuery(
				"SELECT w.name, w FROM Workshop w ORDER BY w.name ASC")
				.getResultList();
		List<Workshop> workshopReturnList = new ArrayList<Workshop>();
		for (Object[] listItem : workshopList) {
			workshopReturnList.add((Workshop) listItem[1]);
		}
		return workshopReturnList;
	}

	@Transactional
	public void saveWorkshop(Workshop workshop) {
		try {
			wr.save(workshop);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional
	public Workshop findWorkshopById(Long id) {
		return wr.findOne(id);
	}

	@Transactional
	public void deleteWorkshop(Workshop workshop) {
		Long reportCount = new Long(0);
		try {
			reportCount = rs.findReportCountByWorkshopId(workshop.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (reportCount > 0) {
			workshop.setActive(false);
			wr.save(workshop);
		} else {
			try {
				Query query = em
						.createQuery("SELECT i FROM Importer i WHERE :workshop MEMBER OF i.workshops");
				query.setParameter("workshop", workshop);
				List<Importer> importers = query.getResultList();

				if (importers != null && !importers.isEmpty()) {
					for (Importer importer : importers) {
						List<Workshop> newList = new ArrayList<Workshop>();
						for (Workshop listWorkshop : importer.getWorkshops()) {
							if (!listWorkshop.getId().equals(workshop.getId()))
								newList.add(listWorkshop);
							importer.setWorkshops(newList);
							importer = is.saveImporter(importer);
						}
					}

				}

				query = em
						.createQuery("SELECT u FROM User u WHERE :workshop MEMBER OF u.workshops");
				query.setParameter("workshop", workshop);
				List<User> users = query.getResultList();

				if (users != null && !users.isEmpty()) {
					for (User user : users) {
						List<Workshop> newList = new ArrayList<Workshop>();
						for (Workshop listWorkshop : user.getWorkshops()) {
							if (!listWorkshop.getId().equals(workshop.getId()))
								newList.add(listWorkshop);
							user.setWorkshops(newList);
							user = us.saveUser(user);
						}
					}

				}

				wr.delete(workshop.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Transactional(readOnly = true)
	public List<Workshop> findActiveWorkshops() {
		List<Object[]> workshopList = em
				.createQuery(
						"SELECT w.name, w FROM Workshop w WHERE w.active = TRUE ORDER BY w.name ASC")
				.getResultList();
		List<Workshop> workshopReturnList = new ArrayList<Workshop>();
		for (Object[] listItem : workshopList) {
			workshopReturnList.add((Workshop) listItem[1]);
		}
		return workshopReturnList;
	}
}
