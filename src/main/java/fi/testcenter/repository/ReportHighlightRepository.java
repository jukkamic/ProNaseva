package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.report.ReportHighlight;

public interface ReportHighlightRepository extends
		JpaRepository<ReportHighlight, Long> {

}
