package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.ReportHighlight;

public interface ReportHighlightRepository extends
		JpaRepository<ReportHighlight, Long> {

}
