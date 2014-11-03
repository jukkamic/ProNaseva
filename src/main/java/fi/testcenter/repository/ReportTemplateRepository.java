package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.ReportTemplate;

public interface ReportTemplateRepository extends
		JpaRepository<ReportTemplate, Long> {

}