package fi.testcenter.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQuery(name = "getLoginUser", query = "SELECT u FROM User u WHERE u.userName = :userName")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String firstName;
	private String lastName;

	@Column(unique = true, nullable = false)
	private String userName;
	@Column(nullable = false)
	private String password;
	private String email;

	private String role;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable
	private List<Workshop> workshops = new ArrayList<Workshop>();

	@Transient
	private List<Long> chosenWorkshopIds = new ArrayList<Long>();

	@ManyToOne
	private Importer importer;

	@Transient
	private Long chosenImporterId;

	private boolean enabled = true;

	public void user() {
		this.enabled = true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public Long getChosenImporterId() {
		return chosenImporterId;
	}

	public void setChosenImporterId(Long chosenImporterId) {
		this.chosenImporterId = chosenImporterId;
	}

}
