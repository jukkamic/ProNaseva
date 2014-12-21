package fi.testcenter.domain.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

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

	@Transient
	Logger log = Logger.getLogger("fi.testcenter.domain.report");

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "OPTIONALANSWER_QUESTION", joinColumns = @JoinColumn(name = "OPTIONALANSWER_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTION_ID"))
	List<Question> optionalQuestions;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "OPTIONALANSWER_ANSWER", joinColumns = @JoinColumn(name = "OPTIONALANSWER_ID"), inverseJoinColumns = @JoinColumn(name = "ANSWER_ID"))
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
					newAnswerList.add(new MultipleChoiceAnswer(question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof PointsQuestion) {
					newAnswerList.add(new PointsAnswer(question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof TextQuestion) {
					newAnswerList.add(new TextAnswer(question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof ImportantPointsQuestion) {
					newAnswerList.add(new ImportantPointsAnswer(question,
							optionalAnswerOrderNumber++));
				}
				if (question instanceof CostListingQuestion) {
					CostListingAnswer answer = new CostListingAnswer(question,
							optionalAnswerOrderNumber++);
					CostListingQuestion clq = (CostListingQuestion) question;
					List<Float> answerList = new ArrayList<Float>();
					for (int i = 0; i < clq.getQuestionItems().size(); i++)
						answerList.add(new Float(0));
					answer.setAnswers(answerList);
					newAnswerList.add(answer);

				}
			}

			else {

				for (Answer answer : optionalAnswers) {
					if (answer != null && answer.getQuestion() == question) {
						newAnswerList.add(answer);
						optionalAnswers.set(optionalAnswers.indexOf(answer),
								null);
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
					rs.saveOptionalQuestionsAnswer(this);
					rs.deleteAnswer(answer);
				}

			}
			optionalAnswers = newAnswerList;
			savedAnswer = rs.saveOptionalQuestionsAnswer(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return savedAnswer;
	}
}
