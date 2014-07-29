package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

	// public List getReportSearchList() {
	//
	// Query query = em
	// .createQuery("SELECT r.id, r.workshop, r.importer FROM Report r");
	//
	// return query.getResultList();
	// }
}
