package fi.testcenter.dao;

import java.util.List;

import fi.testcenter.domain.Workshop;

public interface WorkshopDAO {

	public abstract List<Workshop> getWorkshops();

}