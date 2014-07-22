package fi.testcenter.web;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;

public class ReportBasicInfo {

	private Workshop workshop;
	private Importer importer;

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

}
