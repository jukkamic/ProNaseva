package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Importer;
import fi.testcenter.repository.ImporterRepository;

@Service
public class ImporterService {

	@Autowired
	private ImporterRepository importerRepository;

	@Transactional(readOnly = true)
	public List<Importer> getImporters() {
		return importerRepository.findAll();
	}

}
