package fi.testcenter.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fi.testcenter.domain.Workshop;

@Component
public class WorkshopDAOMock implements WorkshopDAO {

	/* (non-Javadoc)
	 * @see fi.testcenter.dao.WorkshopDAO#getWorkshops()
	 */
	@Override
	public List<Workshop> getWorkshops() {
		
		List<Workshop> ws = new ArrayList<Workshop> ();
		ws.add(new Workshop("Jukan paja"));
		ws.add(new Workshop("Jarnon moottori ja kaasari"));
		ws.add(new Workshop("Artsin korjaamo"));
		
		return ws;
	}

}
