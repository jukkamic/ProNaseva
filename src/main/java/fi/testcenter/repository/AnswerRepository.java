package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.answer.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
