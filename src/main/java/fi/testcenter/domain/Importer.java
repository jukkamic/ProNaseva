package fi.testcenter.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import fi.testcenter.domain.reportTemplate.ReportTemplate;

@Entity
public class Importer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;
	private String streetAddress;
	private String poBox;
	private String zipCode;
	private String city;
	private String email;
	private String telNum;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<ReportTemplate> reportTemplates = new ArrayList<ReportTemplate>();

	@Transient
	private List<String> chosenTemplates = new ArrayList<String>();

	@Transient
	private List<Long> chosenWorkshopIds = new ArrayList<Long>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<Workshop> workshops = new ArrayList<Workshop>();

	private boolean active;

	public Importer() {
		active = true;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public List<ReportTemplate> getReportTemplates() {
		return reportTemplates;
	}

	public List<String> getChosenTemplates() {
		return chosenTemplates;
	}

	public void setChosenTemplates(List<String> chosenTemplates) {
		this.chosenTemplates = chosenTemplates;
	}

	public void setReportTemplates(List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPoBox() {
		return poBox;
	}

	public void setPoBox(String poBox) {
		this.poBox = poBox;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String toString() {
		return this.name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Workshop> getWorkshops() {
		return workshops;
	}

	public void setWorkshops(List<Workshop> workshops) {
		this.workshops = workshops;
	}

	public List<Long> getChosenWorkshopIds() {
		return chosenWorkshopIds;
	}

	public void setChosenWorkshopIds(List<Long> chosenWorkshopIds) {
		this.chosenWorkshopIds = chosenWorkshopIds;
	}

}
