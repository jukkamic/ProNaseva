package fi.testcenter.domain.answer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
import fi.testcenter.service.ReportService;
import fi.testcenter.web.ChosenQuestions;

@Entity
public class OptionalQuestionsAnswer extends Answer {

	@Transient
	Logger log = Logger.getLogger("fi.testcenter.domain.report");

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn
	List<Question> optionalQuestions;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn
	@OrderColumn
	List<Answer> optionalAnswers;

	public OptionalQuestionsAnswer() {
	}

	public OptionalQuestionsAnswer(Question question) {
		super(question);
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

	public void addOptionalQuestions(ChosenQuestions chosenQuestions,
			ReportService rs) {

		// Tehdään lista käyttäjän valitsemista kysymyksistä

		ArrayList<Answer> newAnswerList = new ArrayList<Answer>();
		ArrayList<Question> newQuestionList = new ArrayList<Question>();

		for (int questionIndex : chosenQuestions.getChosenQuestions()) {
			Question question = ((OptionalQuestions) this.getQuestion())
					.getQuestions().get(questionIndex);

			newQuestionList.add(question);
			if (optionalQuestions == null
					|| !(optionalQuestions.contains(question))) {

				if (question instanceof MultipleChoiceQuestion) {
					newAnswerList.add(new MultipleChoiceAnswer(question));
				}
				if (question instanceof PointsQuestion) {
					newAnswerList.add(new PointsAnswer(question));
				}
				if (question instanceof TextQuestion) {
					newAnswerList.add(new TextAnswer(question));
				}
				if (question instanceof ImportantPointsQuestion) {
					newAnswerList.add(new ImportantPointsAnswer(question));
				}
				if (question instanceof CostListingQuestion) {
					CostListingAnswer answer = new CostListingAnswer(question);
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
					if (answer.getQuestion() == question)
						newAnswerList.add(answer);
				}

			}
		}

		optionalQuestions = newQuestionList;
		try {
			List<Answer> deleteAnswers = optionalAnswers;
			optionalAnswers = newAnswerList;
			rs.saveOptionalQuestionsAnswer(this);
			rs.deleteAnswers(deleteAnswers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
