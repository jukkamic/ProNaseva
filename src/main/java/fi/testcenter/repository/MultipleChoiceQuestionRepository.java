package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.MultipleChoiceQuestion;

public interface MultipleChoiceQuestionRepository extends
		JpaRepository<MultipleChoiceQuestion, Long> {

}
