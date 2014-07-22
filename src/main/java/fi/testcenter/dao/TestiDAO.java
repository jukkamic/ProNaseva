package fi.testcenter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.testcenter.domain.Testi;

@Repository
public class TestiDAO {

	Logger log = Logger.getLogger("fi.testcenter");

	@PersistenceContext
	EntityManager em;

	@Transactional
	public void save(Testi testi) throws Exception {
		em.persist(testi);
		log.debug("Persist: " + testi);
	}

	@Transactional
	public void ListTesti() {

		Query q = em.createQuery("select t from Testi t");
		List<Testi> testiList = q.getResultList();
		log.debug("Testiobjektien lukumäärä: " + testiList.size());
		log.debug("Listaus: ");
		for (Testi testi : testiList) {
			log.debug(testi);
		}

	}

}
