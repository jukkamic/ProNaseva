package fi.testcenter.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import fi.testcenter.domain.MultipleChoiceOption;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.TextareaQuestion;
import fi.testcenter.domain.TextfieldQuestion;

@Service
public class ReportTemplateService {

	public Report getReportTemplate(String template) {
		return getReportTemplateVolvo();
	}

	private Report getReportTemplateVolvo() {

		ArrayList<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

		// 1. Testiauton tiedot

		QuestionGroup questionGroup1 = new QuestionGroup();
		ArrayList<Question> questions1 = new ArrayList<Question>();
		questionGroup1.setTitle("Testiauton tiedot");

		TextfieldQuestion q1 = new TextfieldQuestion();
		q1.setQuestion("Ajoneuvon merkki");
		questions1.add(q1);

		TextfieldQuestion q2 = new TextfieldQuestion();
		q2.setQuestion("Ajoneuvon malli");
		questions1.add(q2);

		TextfieldQuestion q3 = new TextfieldQuestion();
		q3.setQuestion("Rekisteritunnus");
		questions1.add(q3);

		TextfieldQuestion q4 = new TextfieldQuestion();
		q4.setQuestion("Rekisteröinnin päivämäärä");
		questions1.add(q4);

		TextfieldQuestion q5 = new TextfieldQuestion();
		q5.setQuestion("Ajoneuvon mittarilukema");
		questions1.add(q5);

		TextfieldQuestion q6 = new TextfieldQuestion();
		q6.setQuestion("Maksettu palkkio");
		questions1.add(q6);

		questionGroup1.setQuestions(questions1);
		questionGroups.add(questionGroup1);

		// 2. Tarkastuskohteet

		QuestionGroup questionGroup2 = new QuestionGroup();
		questionGroup2.setTitle("Tarkastuskohteet");

		ArrayList<Question> questionList2 = new ArrayList<Question>();

		MultipleChoiceQuestion q7 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options1 = new ArrayList<MultipleChoiceOption>();

		q7.setQuestion("Jäähdytysnestemäärän tarkastus");
		options1.add(new MultipleChoiceOption("0 p", 0));
		options1.add(new MultipleChoiceOption("1 p", 1));
		options1.add(new MultipleChoiceOption("2 p", 2));
		q7.setOptions(options1);
		questionList2.add(q7);

		MultipleChoiceQuestion q9 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options2 = new ArrayList<MultipleChoiceOption>();
		q9.setQuestion("Rengaspaineiden tarkastus");
		options2.add(new MultipleChoiceOption("0 p", 0));
		options2.add(new MultipleChoiceOption("1 p", 1));
		options2.add(new MultipleChoiceOption("2 p", 2));
		q9.setOptions(options2);
		questionList2.add(q9);

		MultipleChoiceQuestion q11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options3 = new ArrayList<MultipleChoiceOption>();
		q11.setQuestion("Tuulilasinpyyhkimien kunnon tarkastus");
		options3.add(new MultipleChoiceOption("0 p", 0));
		options3.add(new MultipleChoiceOption("1 p", 1));
		options3.add(new MultipleChoiceOption("2 p", 2));
		q11.setOptions(options3);
		questionList2.add(q11);

		MultipleChoiceQuestion q13 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options4 = new ArrayList<MultipleChoiceOption>();
		q13.setQuestion("Ajovalojen suuntauksen tarkastus");
		options4.add(new MultipleChoiceOption("0 p", 0));
		options4.add(new MultipleChoiceOption("1 p", 1));
		options4.add(new MultipleChoiceOption("2 p", 2));
		q13.setOptions(options4);
		questionList2.add(q13);

		MultipleChoiceQuestion q15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options5 = new ArrayList<MultipleChoiceOption>();
		q15.setQuestion("Äänimerkin toiminnan tarkastus");
		options5.add(new MultipleChoiceOption("0 p", 0));
		options5.add(new MultipleChoiceOption("1 p", 1));
		options5.add(new MultipleChoiceOption("2 p", 2));
		q15.setOptions(options5);
		questionList2.add(q15);

		MultipleChoiceQuestion q17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options6 = new ArrayList<MultipleChoiceOption>();
		q17.setQuestion("Oliko Service 2.0 hengari ripustettu peiliin");
		options6.add(new MultipleChoiceOption("0 p", 0));
		options6.add(new MultipleChoiceOption("1 p", 1));
		options6.add(new MultipleChoiceOption("2 p", 2));
		q17.setOptions(options6);
		questionList2.add(q17);

		TextareaQuestion q18 = new TextareaQuestion();
		q18.setQuestion("Muita huomioita");
		questionList2.add(q18);

		questionGroup2.setQuestions(questionList2);
		questionGroups.add(questionGroup2);

		// 3. Palvelun pisteet

		QuestionGroup questionGroup3 = new QuestionGroup();
		questionGroup3.setTitle("Palvelun pisteet");
		ArrayList<Question> questionList3 = new ArrayList<Question>();

		MultipleChoiceQuestion q19 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options7 = new ArrayList<MultipleChoiceOption>();
		q19.setQuestion("Huoltoneuvojan tavoittaminen aikaa varattaessa");
		options7.add(new MultipleChoiceOption("Alle minuutissa", 2));
		options7.add(new MultipleChoiceOption("1-3 minuuttia", 1));
		options7.add(new MultipleChoiceOption("Yli 3 minuuttia", 0));
		q19.setOptions(options7);
		questionList3.add(q19);

		MultipleChoiceQuestion q21 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options8 = new ArrayList<MultipleChoiceOption>();
		q21.setQuestion("Puhelinnumeron kysyminen");
		options8.add(new MultipleChoiceOption("Aikaa varattaessa", 2));
		options8.add(new MultipleChoiceOption("Huoltoon tuotaessa", 1));
		options8.add(new MultipleChoiceOption("Ei kysytä", 0));
		q21.setOptions(options8);
		questionList3.add(q21);

		MultipleChoiceQuestion q23 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options9 = new ArrayList<MultipleChoiceOption>();
		q23.setQuestion("Lupa lisätöihin");
		options9.add(new MultipleChoiceOption(
				"Soitetaan tai kysytään huoltoon tuotaessa", 2));
		options9.add(new MultipleChoiceOption("Ei kysytä", 0));
		q23.setOptions(options9);
		questionList3.add(q23);

		MultipleChoiceQuestion q25 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options10 = new ArrayList<MultipleChoiceOption>();
		q25.setQuestion("Työmääräyksen allekirjoitus");
		options10.add(new MultipleChoiceOption("Pyydetään allekirjoitus", 2));
		options10.add(new MultipleChoiceOption(
				"Ei pyydetä/pyydetään huollon jälkeen", 0));
		q25.setOptions(options10);
		questionList3.add(q25);

		MultipleChoiceQuestion q27 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options11 = new ArrayList<MultipleChoiceOption>();
		q27.setQuestion("Työmääräyksen läpikäynti");
		options11
				.add(new MultipleChoiceOption(
						"Varmistetaan ajanvarauksessa sovitut asiat paperilta",
						"Varmistetaan ajanvarauksessa <br> sovitut asiat paperilta",
						2));
		options11.add(new MultipleChoiceOption(
				"Varmistetaan vain osittain/vain suullisesti",
				"Varmistetaan vain osittain/ <br> vain suullisesti", 1));
		options11.add(new MultipleChoiceOption("Ei varmisteta mitään",
				"Ei varmisteta mitään <br> &nbsp", 0));
		q27.setOptions(options11);
		questionList3.add(q27);

		MultipleChoiceQuestion q29 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options12 = new ArrayList<MultipleChoiceOption>();
		q29.setQuestion("Aikataulussa pysyminen");
		options12.add(new MultipleChoiceOption("Sovittu aika", 2));
		options12
				.add(new MultipleChoiceOption("Ilmoitetaan myöhästymisestä", 1));
		options12.add(new MultipleChoiceOption("Huolto myöhästyy", 0));
		q29.setOptions(options12);
		questionList3.add(q29);

		MultipleChoiceQuestion q31 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options13 = new ArrayList<MultipleChoiceOption>();
		q31.setQuestion("Laskun läpikäynti");
		options13.add(new MultipleChoiceOption("Käydään läpi riveittäin", 2));
		options13.add(new MultipleChoiceOption("Pääpiirteittäin", 1));
		options13.add(new MultipleChoiceOption("Ei ollenkaan", 0));
		q31.setOptions(options13);
		questionList3.add(q31);

		MultipleChoiceQuestion q33 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options14 = new ArrayList<MultipleChoiceOption>();
		q33.setQuestion("Huollon tarkastuslistan täyttö");
		options14.add(new MultipleChoiceOption("Täydellisesti",
				"Täydellisesti <br> &nbsp", 2));
		options14.add(new MultipleChoiceOption(
				"Puutteellisesti täytetty väärä huoltoseloste",
				"Puutteellisesti täytetty/<br> väärä huoltoseloste", 1));
		options14.add(new MultipleChoiceOption("Ei saada ollenkaan",
				"Ei saada ollenkaan <br> &nbsp", 0));
		q33.setOptions(options14);
		questionList3.add(q33);

		MultipleChoiceQuestion q35 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options15 = new ArrayList<MultipleChoiceOption>();
		q35.setQuestion("Kustannusarviossa pysyminen");
		options15.add(new MultipleChoiceOption("Sovittu summa", 2));
		options15.add(new MultipleChoiceOption("Ylittyy alle 15%", 1));
		options15.add(new MultipleChoiceOption("Ylittyy yli 15", 0));
		q35.setOptions(options15);
		questionList3.add(q35);

		MultipleChoiceQuestion q37 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options16 = new ArrayList<MultipleChoiceOption>();
		q37.setQuestion("Asiakaspalvelun toiminta");
		options16.add(new MultipleChoiceOption("Hyvä", 2));
		options16.add(new MultipleChoiceOption("Tyydyttävä", 1));
		options16.add(new MultipleChoiceOption("Heikko", 0));
		q37.setOptions(options16);
		questionList3.add(q37);

		TextareaQuestion q38 = new TextareaQuestion();
		q38.setQuestion("Muita huomioita");
		questionList3.add(q38);

		questionGroup3.setQuestions(questionList3);
		questionGroups.add(questionGroup3);

		Report report = new Report();
		report.setQuestionGroups(questionGroups);
		return report;
	}
}
