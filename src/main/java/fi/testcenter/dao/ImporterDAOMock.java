package fi.testcenter.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fi.testcenter.domain.Importer;

@Component
public class ImporterDAOMock implements ImporterDAO {

	@Override
	public List<Importer> getImporters() {
		List<Importer> importers = new ArrayList<Importer>();
		add(importers, "Volvo Auto Oy Ab");
		add(importers, "Tesla");
		add(importers, "Bugatti");
		add(importers, "Lamborghini");
		return importers;
	}

	private void add(List<Importer> importers, String name) {
		importers.add(new Importer(name));
	}

}
