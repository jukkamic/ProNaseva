package fi.testcenter.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String title;

	private LinkedHashMap<Question, Answer> questionsAnswers;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Question, Answer> getQuestionsAnswers() {
		return questionsAnswers;
	}

	public void setQuestionsAnswers(
			LinkedHashMap<Question, Answer> questionsAnswers) {
		this.questionsAnswers = questionsAnswers;
	}
}
