package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

}
