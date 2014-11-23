package fi.testcenter.domain.answer;

import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;

@Entity
public class OptionalQuestionsAnswer extends Answer {

	Question question;
	Answer answer;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
}
