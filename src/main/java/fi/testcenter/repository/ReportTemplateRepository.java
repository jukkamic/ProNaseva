package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.reportTemplate.ReportTemplate;

public interface ReportTemplateRepository extends
		JpaRepository<ReportTemplate, Long> {

}