package fi.testcenter.domain.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.ImportantPointsQuestion;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.OptionalQuestions;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.question.TextQuestion;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.service.ReportService;
import fi.testcenter.web.ChosenQuestions;

@Entity
public class OptionalQuestionsAnswer extends Answer {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "OPTIONALANSWER_QUESTION", joinColumns = @JoinColumn(name = "OPTIONALANSWER_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	@OrderColumn
	List<Question> optionalQuestions;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "optionalQuestionsAnswer")
	@JoinColumn
	@OrderColumn
	List<Answer> optionalAnswers = new ArrayList<Answer>();

	public OptionalQuestionsAnswer() {
	}

	public OptionalQuestionsAnswer(Question question) {
		super(question);
	}

	public OptionalQuestionsAnswer(ReportQuestionGroup group, Question question) {
		super(group, question);
	}

	public List<Question> getQuestions() {
		return optionalQuestions;
	}

	public void setQuestions(List<Question> questions) {
		this.optionalQuestions = questions;
	}

	public List<Question> getOptionalQuestions() {
		return optionalQuestions;
	}

	public void setOptionalQuestions(List<Question> optionalQuestions) {
		this.optionalQuestions = optionalQuestions;
	}

	public List<Answer> getOptionalAnswers() {
		return optionalAnswers;
	}

	public void setOptionalAnswers(List<Answer> optionalAnswers) {
		this.optionalAnswers = optionalAnswers;
	}

	public OptionalQuestionsAnswer addOptionalQuestions(
			ChosenQuestions chosenQuestions, ReportService rs) {

		// Tehdään lista käyttäjän valitsemista kysymyksistä

		ArrayList<Answer> newAnswerList = new ArrayList<Answer>();
		ArrayList<Question> newQuestionList = new ArrayList<Question>();

		int optionalAnswerOrderNumber = (this.reportQuestionGroup.getAnswers()
				.indexOf(this)) + 1;

		for (int questionIndex : chosenQuestions.getChosenQuestions()) {
			Question question = ((OptionalQuestions) this.getQuestion())
					.getQuestions().get(questionIndex);

			newQuestionList.add(question);

			if (optionalQuestions == null
					|| !(optionalQuestions.contains(question))) {

				if (question instanceof MultipleChoiceQuestion) {
					newAnswerList.add(new MultipleChoiceAnswer(this, question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof PointsQuestion) {
					newAnswerList.add(rs.saveAnswer(new PointsAnswer(this,
							question, optionalAnswerOrderNumber++)));
				}
				if (question instanceof TextQuestion) {
					newAnswerList.add(new TextAnswer(this, question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof ImportantPointsQuestion) {
					newAnswerList.add(new ImportantPointsAnswer(this, question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof CostListingQuestion) {
					CostListingAnswer answer = new CostListingAnswer(this,
							question, optionalAnswerOrderNumber++);
					CostListingQuestion clq = (CostListingQuestion) question;
					List<String> answersInList = new ArrayList<String>();
					for (int i = 0; i < clq.getQuestionItems().size(); i++)
						answersInList.add(new String(""));
					answer.setAnswersIn(answersInList);
					newAnswerList.add(answer);

				}
			}

			else {

				for (Answer answer : optionalAnswers) {
					if (answer != null && answer.getQuestion() == question) {
						answer.setAnswerOrderNumber(optionalAnswerOrderNumber++);
						newAnswerList.add(answer);

					}

				}

			}
		}

		optionalQuestions = newQuestionList;
		OptionalQuestionsAnswer savedAnswer = new OptionalQuestionsAnswer();
		try {

			for (Answer answer : optionalAnswers) {
				if (answer != null) {
					optionalAnswers.set(optionalAnswers.indexOf(answer), null);
					rs.saveAnswer(this);
					rs.deleteAnswer(answer);
				}

			}
			optionalAnswers = newAnswerList;
			savedAnswer = (OptionalQuestionsAnswer) rs.saveAnswer(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedAnswer;
	}
}
