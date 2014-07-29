package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.domain.Workshop;
import fi.testcenter.repository.WorkshopRepository;

@Service
public class WorkshopService {

	@Autowired
	WorkshopRepository wr;

	public List<Workshop> getWorkshops() {
		return wr.findAll();
	}

}
