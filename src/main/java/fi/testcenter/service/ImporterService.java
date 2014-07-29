package fi.testcenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.domain.Importer;
import fi.testcenter.repository.ImporterRepository;

@Service
public class ImporterService {

	@Autowired
	private ImporterRepository importerRepository;

	public List<Importer> getImporters() {
		return importerRepository.findAll();
	}

	private void add(List<Importer> importers, String name) {
		importers.add(new Importer(name));
	}

}
