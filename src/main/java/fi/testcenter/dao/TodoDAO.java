package fi.testcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Todo;

@Repository
public class TodoDAO {

	
	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void save(Todo todo) throws Exception {
		
		em.persist(todo);
				
		System.out.println("Persist: " + todo);
		
	}
	
	@Transactional
	public void ListTodos() {
	    Query q = em.createQuery("select t from Todo t");
	    List<Todo> todoList = q.getResultList();
	    System.out.println("Todo lukumäärä: " + todoList.size());
	    System.out.println("Listaus: ");
	    for(Todo todo : todoList) {
	    	System.out.println(todo);
	    }
	    
	}
	
}
