package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Report {

	@Temporal(TemporalType.DATE)
	private Date date;

	@Id
	@GeneratedValue
	Integer id;

	private Importer importer;
	private Workshop workshop;
	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;

	@OneToMany(cascade = CascadeType.ALL)
	private List<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistration) {
		this.vehicleRegistrationNumber = vehicleRegistration;
	}

	public String getVehicleRegistrationDate() {
		return vehicleRegistrationDate;
	}

	public void setVehicleRegistrationDate(String vehicleRegistrationDate) {
		this.vehicleRegistrationDate = vehicleRegistrationDate;
	}

	public String getVehicleMileage() {
		return vehicleMileage;
	}

	public void setVehicleMileage(String vehicleMileage) {
		this.vehicleMileage = vehicleMileage;
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

	public List<QuestionGroup> getQuestionGroups() {
		return questionGroups;
	}

	public void setQuestionGroups(List<QuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Report() {
		this.date = new Date();
	}

}
