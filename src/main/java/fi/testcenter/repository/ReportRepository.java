package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
