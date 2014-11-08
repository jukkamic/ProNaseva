package fi.testcenter.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Workshop;
import fi.testcenter.repository.WorkshopRepository;

@Service
public class WorkshopService {

	Logger log = Logger.getLogger("fi.testcenter.service.WorkshopService");

	@Autowired
	WorkshopRepository wr;

	@Autowired
	ReportService rs;

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
	public List<Workshop> getWorkshopsInAplhaOrder() {
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
	public Workshop getWorkshopById(Long id) {
		return wr.findOne(id);
	}

	@Transactional
	public void deleteWorkshop(Workshop workshop) {
		Long reportCount = new Long(0);
		try {
			reportCount = rs.getReportsByWorkshopId(workshop.getId());
			log.debug("report count" + reportCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (reportCount > 0) {
			workshop.setActive(false);
			wr.save(workshop);
		} else {
			try {
				wr.delete(workshop.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Transactional(readOnly = true)
	public List<Workshop> getActiveWorkshops() {
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
