package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate;

public interface PhoneCallTestReportTemplateRepository extends
		JpaRepository<PhoneCallTestReportTemplate, Long> {

}