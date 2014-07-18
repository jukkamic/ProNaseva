package fi.testcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Testi;

@Repository
public class TestiDAO {


	@PersistenceContext
	EntityManager em; 
	
	@Transactional
	public void save(Testi testi) throws Exception {
			
		em.persist(testi);
		
		System.out.println("Persist: " + testi);
		
	}
	
	@Transactional
	public void ListTesti() {
		
	    Query q = em.createQuery("select t from Testi t");
	    List<Testi> testiList = q.getResultList();
	    System.out.println("Testiobjektien lukumäärä: " + testiList.size());
	    System.out.println("Listaus: ");
	    for(Testi testi : testiList) {
	    	System.out.println(testi);
	    }
	    
	}
	
}
