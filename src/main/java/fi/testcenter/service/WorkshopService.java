package fi.testcenter.service;

import java.util.List;

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

	@Transactional(readOnly = true)
	public List<Workshop> getWorkshops() {
		List<Workshop> workshops = wr.findAll();

		return workshops;
	}

	public Workshop findWorkshop(Long workshopID) {
		return wr.findOne(workshopID);
	}

}
