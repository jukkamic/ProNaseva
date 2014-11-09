package fi.testcenter.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class CostListingQuestion extends Question {

	String questionTopic;
	List<String> questions;
	String total;

	public String getQuestionTopic() {
		return questionTopic;
	}

	public void setQuestionTopic(String questionTopic) {
		this.questionTopic = questionTopic;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
