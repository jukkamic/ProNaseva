package fi.testcenter.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Report {

    @Temporal(TemporalType.DATE)
	private Date date;
    
   @Id
    @GeneratedValue
    Integer id;
    
	private Importer importer;
    
	private Workshop workshop;
    
	@Transient
	private List<Question> questions;	
		
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
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
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
