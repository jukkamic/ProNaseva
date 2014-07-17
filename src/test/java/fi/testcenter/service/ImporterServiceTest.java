package fi.testcenter.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.testcenter.dao.ImporterDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class ImporterServiceTest {

	@Autowired
	private ImporterDAO importerDAO;
	
/*	@Test
	public void getImporters() {
		List<String> importers = importerDAO.getImporters();
		assertNotNull(importers);
	}*/
}
