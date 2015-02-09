package fi.testcenter.web;

import java.util.List;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;

public class ReportSummarySearchCriteria {

	private Importer importer;
	private List<Workshop> workshops;
	private String startDate;
	private String endDate;

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public List<Workshop> getWorkshops() {
		return workshops;
	}

	public void setWorkshops(List<Workshop> workshops) {
		this.workshops = workshops;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
