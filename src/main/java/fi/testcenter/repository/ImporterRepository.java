package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.Importer;

public interface ImporterRepository extends JpaRepository<Importer, Long> {

}
