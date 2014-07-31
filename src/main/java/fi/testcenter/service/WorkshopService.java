package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Workshop;
import fi.testcenter.repository.WorkshopRepository;

@Service
public class WorkshopService {

	@Autowired
	WorkshopRepository wr;

	@Transactional(readOnly = true)
	public List<Workshop> getWorkshops() {
		return wr.findAll();
	}

}
