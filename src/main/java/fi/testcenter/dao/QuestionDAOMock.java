package fi.testcenter.dao;

import java.util.ArrayList;
import java.util.List;

import fi.testcenter.domain.MultipleChoiceOption;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.TextfieldQuestion;

public class QuestionDAOMock implements QuestionDAO {

	@Override
	public List<Question> getQuestions() {

		List<Question> questions = new ArrayList<Question>();
		questions.add(new Question("eka kysymys"));
		questions.add(new Question("toka kysymys"));

		return questions;
	}

	public List<QuestionGroup> getQuestionGroups() {
		ArrayList<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

		QuestionGroup questionGroup1 = new QuestionGroup();
		questionGroup1.setTitle("Tarkastuskohteet");

		ArrayList<Question> questionList1 = new ArrayList<Question>();
		MultipleChoiceQuestion firstQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options1 = new ArrayList<MultipleChoiceOption>();

		firstQ.setQuestion("Jäähdytysnestemäärän tarkastus");
		options1.add(new MultipleChoiceOption("0 p", 0));
		options1.add(new MultipleChoiceOption("1 p", 1));
		options1.add(new MultipleChoiceOption("2 p", 2));
		firstQ.setOptions(options1);
		questionList1.add(firstQ);

		MultipleChoiceQuestion secondQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options2 = new ArrayList<MultipleChoiceOption>();
		secondQ.setQuestion("Rengaspaineiden tarkastus");
		options2.add(new MultipleChoiceOption("0 p", 0));
		options2.add(new MultipleChoiceOption("1 p", 1));
		options2.add(new MultipleChoiceOption("2 p", 2));
		secondQ.setOptions(options2);

		questionList1.add(secondQ);

		TextfieldQuestion thirdQ = new TextfieldQuestion();
		thirdQ.setQuestion("Muita huomioita");
		questionList1.add(thirdQ);

		questionGroup1.setQuestions(questionList1);
		questionGroups.add(questionGroup1);

		QuestionGroup questionGroup2 = new QuestionGroup();
		questionGroup2.setTitle("Palvelun pisteet");

		ArrayList<Question> questionList2 = new ArrayList<Question>();
		MultipleChoiceQuestion fourthQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options3 = new ArrayList<MultipleChoiceOption>();
		fourthQ.setQuestion("Huoltoneuvojan tavoittaminen aikaa varattaessa");
		options3.add(new MultipleChoiceOption("Alle minuutissa", 2));
		options3.add(new MultipleChoiceOption("1-3 minuuttia", 1));
		options3.add(new MultipleChoiceOption("Yli 3 minuuttia", 0));
		fourthQ.setOptions(options3);
		questionList2.add(fourthQ);

		MultipleChoiceQuestion fifthQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options4 = new ArrayList<MultipleChoiceOption>();
		fifthQ.setQuestion("Lupa lisätöihin");
		options4.add(new MultipleChoiceOption(
				"Soitetaan tai kysytään huoltoon tuotaessa", 1));
		options4.add(new MultipleChoiceOption("Ei kysytä", 0));
		fifthQ.setOptions(options4);
		questionList2.add(fifthQ);

		TextfieldQuestion sixthQ = new TextfieldQuestion();
		sixthQ.setQuestion("Muita huomioita");
		questionList2.add(sixthQ);

		questionGroup2.setQuestions(questionList2);
		questionGroups.add(questionGroup2);

		QuestionGroup questionGroup3 = new QuestionGroup();
		questionGroup3.setTitle("Asiakkaan informoiminen");

		ArrayList<Question> questionList3 = new ArrayList<Question>();
		MultipleChoiceQuestion seventhQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options5 = new ArrayList<MultipleChoiceOption>();
		seventhQ.setQuestion("Muistutettiinko asiakasta ottamaan huoltokirja mukaan?");
		options5.add(new MultipleChoiceOption("Kyllä", 2));
		options5.add(new MultipleChoiceOption("Ei", 0));
		seventhQ.setOptions(options5);
		questionList3.add(seventhQ);

		MultipleChoiceQuestion eightQ = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options6 = new ArrayList<MultipleChoiceOption>();
		eightQ.setQuestion("Nimettiinkö huoltoneuvoja / vastasiko huoltoneuvoja nimellä selkeästi puhelimeen");
		options6.add(new MultipleChoiceOption("Kyllä", 2));
		options6.add(new MultipleChoiceOption("Ei", 0));
		eightQ.setOptions(options6);
		questionList3.add(eightQ);

		TextfieldQuestion ninethQ = new TextfieldQuestion();
		ninethQ.setQuestion("Muita huomioita");
		questionList2.add(ninethQ);

		questionGroup3.setQuestions(questionList3);
		questionGroups.add(questionGroup3);

		return questionGroups;

	}

}
