package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;

@Entity
public class OptionalQuestionsAnswer extends Answer {

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHOSENQUESTION_ID")
	List<Question> optionalQuestions;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "ANSWER_OPTANSWER", joinColumns = @JoinColumn(name = "ANSWER_ID"), inverseJoinColumns = @JoinColumn(name = "OPTANSWER_ID"))
	List<Answer> optionalAnswers;

	public OptionalQuestionsAnswer() {
	}

	public OptionalQuestionsAnswer(Question question) {
		super(question);
	}

	public OptionalQuestionsAnswer(Report report, Question question) {
		super(report, question);
	}

	public List<Answer> getAnswers() {
		return optionalAnswers;
	}

	public void setAnswers(List<Answer> answers) {
		this.optionalAnswers = answers;
	}

	public List<Question> getQuestions() {
		return optionalQuestions;
	}

	public void setQuestions(List<Question> questions) {
		this.optionalQuestions = questions;
	}

}
