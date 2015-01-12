package fi.testcenter.service;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.User;
import fi.testcenter.domain.Workshop;

public class ReportQueryObject {

	Long id;
	String testDate;
	Importer importer;
	Workshop workshop;
	User user;
	String reportStatus;
	String reportClass;

	public ReportQueryObject(Long id, String testDate, Importer importer,
			Workshop workshop, User user, String reportStatus,
			String reportClass) {
		this.id = id;
		this.testDate = testDate;
		this.importer = importer;
		this.workshop = workshop;
		this.user = user;
		this.reportStatus = reportStatus;
		this.reportClass = reportClass;

	}

	public ReportQueryObject(Long id, String testDate, Importer importer,
			Workshop workshop, User user, String reportStatus) {
		this.id = id;
		this.testDate = testDate;
		this.importer = importer;
		this.workshop = workshop;
		this.user = user;
		this.reportStatus = reportStatus;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReportClass() {
		return reportClass;
	}

	public void setReportClass(String reportClass) {
		this.reportClass = reportClass;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

}
