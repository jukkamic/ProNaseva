package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Report {

	@Column
	@Temporal(TemporalType.DATE)
	private Date date;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private Importer importer;

	@Transient
	private Long importerId;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	private Workshop workshop;

	@Transient
	private Long workshopId;

	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_QUESTIONGROUP", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTIONGROUP_ID"))
	@OrderColumn(name = "INDEX")
	private List<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getImporterId() {
		return importerId;
	}

	public void setImporterId(Long importerId) {
		this.importerId = importerId;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}

}
