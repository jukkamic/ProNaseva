package fi.testcenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.testcenter.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
