package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.QuestionGroup;

public interface QuestionGroupRepository extends
		JpaRepository<QuestionGroup, Long> {

}
