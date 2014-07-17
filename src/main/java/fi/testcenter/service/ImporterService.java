package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.dao.ImporterDAO;
import fi.testcenter.domain.Importer;

@Service
public class ImporterService {
	
	@Autowired
	private ImporterDAO importerDAO;
	
	public List<Importer> getImporters() {
		return importerDAO.getImporters();
	}

}
