package fi.testcenter.web;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;

public class ReportBasicInfo {

	private Workshop workshop;
	private Importer importer;
	private Long workshopID;
	private Long importerID;

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

	public Long getWorkshopID() {
		return workshopID;
	}

	public void setWorkshopID(Long workshopID) {
		this.workshopID = workshopID;
	}

	public Long getImporterID() {
		return importerID;
	}

	public void setImporterID(Long importerID) {
		this.importerID = importerID;
	}

}
