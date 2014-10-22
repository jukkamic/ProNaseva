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
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ReportTemplate reportTemplate;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_ANSWER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "ANSWER_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private List<Answer> answers = new ArrayList<Answer>();

	private int totalScorePercentage;

	private String vehicleModel;
	private String vehicleRegistrationNumber;
	private String vehicleRegistrationDate;
	private String vehicleMileage;

	@Column
	@Temporal(TemporalType.DATE)
	private Date date;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_IMPORTER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "IMPORTER_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private Importer importer;

	@Transient
	private Long importerId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_WORKSHOP", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "WORKSHOP_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private Workshop workshop;

	@Transient
	private Long workshopId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "REPORT_USER", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	@OrderColumn(name = "ORDERINDEX")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_QUESTIONGROUPSCORE", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTIONGROUPSCORE_ID"))
	@OrderColumn(name = "ORDERINDEX")
	List<QuestionGroupScore> questionGroupScore = new ArrayList<QuestionGroupScore>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORT_REPORTPARTSCORE", joinColumns = @JoinColumn(name = "REPORT_ID"), inverseJoinColumns = @JoinColumn(name = "REPORTPARTSCORE_ID"))
	@OrderColumn(name = "ORDERINDEX")
	List<ReportPartScore> reportPartScore = new ArrayList<ReportPartScore>();

	public Report() {
		this.date = new Date();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportTemplate getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public int getTotalScorePercentage() {
		return totalScorePercentage;
	}

	public void setTotalScorePercentage(int totalScorePercentage) {
		this.totalScorePercentage = totalScorePercentage;
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

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public Long getImporterId() {
		return importerId;
	}

	public void setImporterId(Long importerId) {
		this.importerId = importerId;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<QuestionGroupScore> getQuestionGroupScore() {
		return this.questionGroupScore;
	}

	public void setQuestionGroupScore(
			List<QuestionGroupScore> questionGroupScore) {
		this.questionGroupScore = questionGroupScore;
	}

	public List<ReportPartScore> getReportPartScore() {
		return reportPartScore;
	}

	public void setReportPartScore(List<ReportPartScore> reportPartScore) {
		this.reportPartScore = reportPartScore;
	}

}
