package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.report.ReportQuestionGroup;

public interface ReportQuestionGroupRepository extends
		JpaRepository<ReportQuestionGroup, Long> {

}
