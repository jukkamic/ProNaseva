package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.answer.ImportantPointsItem;

public interface ImpPtItemRepository extends
		JpaRepository<ImportantPointsItem, Long> {

}
