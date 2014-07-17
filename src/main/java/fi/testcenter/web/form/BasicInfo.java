package fi.testcenter.web.form;

import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Workshop;

public class BasicInfo {

	private Workshop workshop;
	private Importer importer;
	private String model;
	private Integer mileage;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getMileage() {
		return mileage;
	}
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
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
