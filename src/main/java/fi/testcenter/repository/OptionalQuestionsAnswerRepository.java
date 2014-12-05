package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.answer.OptionalQuestionsAnswer;

public interface OptionalQuestionsAnswerRepository extends
		JpaRepository<OptionalQuestionsAnswer, Long> {

}
