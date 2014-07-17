package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.dao.WorkshopDAO;
import fi.testcenter.domain.Workshop;

@Service
public class BasicInfoService {

	@Autowired
	WorkshopDAO wd;
	
	public List<Workshop> getWorkshops() {
		
		return wd.getWorkshops();
	}

}
