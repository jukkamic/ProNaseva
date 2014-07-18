package fi.testcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Report;

@Repository
public class ReportDAO {


	@PersistenceContext
	EntityManager em; 
	
	@Transactional
	public void save(Report report) throws Exception {
			
		em.persist(report);
		
		System.out.println("Persist report: " + report);
		
	}
	
	@Transactional
	public void ListTesti() {
		
	    Query q = em.createQuery("select r from Report r");
	    List<Report> reportList = q.getResultList();
	    System.out.println("Raporttiobjektien lukumäärä: " + reportList.size());
	    System.out.println("Listaus: ");
	    for(Report report : reportList) {
	    	System.out.println(report);
	    }
	    
	}
	
}