package fi.testcenter.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import fi.testcenter.domain.MultipleChoiceOption;
import fi.testcenter.domain.MultipleChoiceQuestion;
import fi.testcenter.domain.Question;
import fi.testcenter.domain.QuestionGroup;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.ReportPart;
import fi.testcenter.domain.SubQuestion;
import fi.testcenter.domain.TextareaQuestion;
import fi.testcenter.domain.TextfieldQuestion;

@Service
public class ReportTemplateService {

	public Report getReportTemplate(String template) {
		return getReportTemplateVolvo();
	}

	private Report getReportTemplateVolvo() {

		Report report = new Report();
		ArrayList<ReportPart> reportParts = new ArrayList<ReportPart>();

		ReportPart reportPart1 = new ReportPart();
		reportPart1.setTitle("Yhteenveto");

		ArrayList<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

		// Yhteenveto
		// Yhteenveto - Testiauton tiedot

		QuestionGroup questionGroup1 = new QuestionGroup();
		ArrayList<Question> questions1 = new ArrayList<Question>();
		questionGroup1.setTitle("Testiauton tiedot");

		TextfieldQuestion q1 = new TextfieldQuestion();
		q1.setQuestion("Ajoneuvon merkki");

		// // ArrayList<SubQuestion> subQuestionList1 = new
		// ArrayList<SubQuestion>();
		// // SubQuestion subQ1 = new SubQuestion();
		// // TextfieldQuestion question60 = new TextfieldQuestion();
		// // question60.setQuestion("Eka ikinä alakysymys");
		// // subQ1.setQuestion(question60);
		// // subQuestionList1.add(subQ1);
		// //
		// // ArrayList<MultipleChoiceOption> options40 = new
		// ArrayList<MultipleChoiceOption>();
		// // SubQuestion subQ2 = new SubQuestion();
		// // MultipleChoiceQuestion question61 = new MultipleChoiceQuestion();
		// //
		// question61.setQuestion("Toimiiko Jarnon monivalinta-alakysymykset?");
		// // options40.add(new MultipleChoiceOption("Varmasti toimii kysymys",
		// 2));
		// // options40.add(new MultipleChoiceOption("Hyvin todennäköistä", 1));
		// // options40.add(new MultipleChoiceOption("Vähän epäilyttää kyllä",
		// 2));
		// // question61.setOptions(options40);
		// // subQ2.setQuestion(question61);
		// // subQuestionList1.add(subQ2);
		// //
		// // SubQuestion subQ3 = new SubQuestion();
		// // TextareaQuestion question62 = new TextareaQuestion();
		// // question62
		// //
		// .setQuestion("Tuleeko ProNasevasta seuraava suomalainen menestystarina?");
		// // subQ3.setQuestion(question62);
		// // subQuestionList1.add(subQ3);
		//
		// q1.setSubQuestions(subQuestionList1);

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
		q5.setQuestion("Mittarilukema");
		questions1.add(q5);

		TextfieldQuestion q6 = new TextfieldQuestion();
		q6.setQuestion("Maksettu palkkio");
		questions1.add(q6);

		TextfieldQuestion q50 = new TextfieldQuestion();
		q50.setQuestion("Raportin toimitusosoite");
		questions1.add(q50);

		questionGroup1.setQuestions(questions1);
		questionGroups.add(questionGroup1);

		// Yhteenveto - Tarkastuskohteet

		QuestionGroup questionGroup2 = new QuestionGroup();
		questionGroup2.setTitle("Tarkastuskohteet");

		ArrayList<Question> questionList2 = new ArrayList<Question>();

		MultipleChoiceQuestion q40 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options17 = new ArrayList<MultipleChoiceOption>();
		q40.setQuestion("Varapyörän ilmanpaineen tarkastus");
		options17.add(new MultipleChoiceOption("0 p", 0));
		options17.add(new MultipleChoiceOption("1 p", 1));
		options17.add(new MultipleChoiceOption("2 p", 2));
		q40.setOptions(options17);
		questionList2.add(q40);

		MultipleChoiceQuestion q41 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options18 = new ArrayList<MultipleChoiceOption>();
		q41.setQuestion("Akun tarkastus");
		options18.add(new MultipleChoiceOption("0 p", 0));
		options18.add(new MultipleChoiceOption("1 p", 1));
		options18.add(new MultipleChoiceOption("2 p", 2));
		q41.setOptions(options18);
		questionList2.add(q41);

		MultipleChoiceQuestion q42 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> options19 = new ArrayList<MultipleChoiceOption>();
		q42.setQuestion("Seisontavalon tarkastus");
		options19.add(new MultipleChoiceOption("0 p", 0));
		options19.add(new MultipleChoiceOption("1 p", 1));
		options19.add(new MultipleChoiceOption("2 p", 2));
		q42.setOptions(options19);
		questionList2.add(q42);

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
		q9.setQuestion("Muu vika");
		options2.add(new MultipleChoiceOption("0 p", 0));
		options2.add(new MultipleChoiceOption("1 p", 1));
		options2.add(new MultipleChoiceOption("2 p", 2));
		q9.setOptions(options2);
		questionList2.add(q9);

		TextareaQuestion q18 = new TextareaQuestion();
		q18.setQuestion("Muita huomioita");
		questionList2.add(q18);

		questionGroup2.setQuestions(questionList2);
		questionGroups.add(questionGroup2);

		// Yhteenveto - Palvelun pisteet

		QuestionGroup questionGroup3 = new QuestionGroup();
		questionGroup3.setTitle("TCT-palvelun pisteet");
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

		reportPart1.setQuestionGroups(questionGroups);
		reportParts.add(reportPart1);

		// RAPORTIN OSA A - TYÖN VASTAANOTTO

		ReportPart reportPart2 = new ReportPart();
		reportPart2.setTitle("Osa A - Ajanvaraus korjaamolle");

		ArrayList<QuestionGroup> questionGroups2 = new ArrayList<QuestionGroup>();
		QuestionGroup questionGroup4 = new QuestionGroup();

		questionGroup4.setTitle("Ajanvaraus korjaamolle");
		ArrayList<Question> questionList4 = new ArrayList<Question>();

		TextfieldQuestion q44 = new TextfieldQuestion();
		q44.setQuestion("Ajanvarauksen päivämäärä");
		questionList4.add(q44);

		TextfieldQuestion a2 = new TextfieldQuestion();
		a2.setQuestion("Ajanvarauksen kellonaika");
		questionList4.add(a2);

		MultipleChoiceQuestion a3 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA3 = new ArrayList<MultipleChoiceOption>();
		a3.setQuestion("Miten huoltoaika varattiin?");
		optionsA3.add(new MultipleChoiceOption("Puhelimitse", -1));
		optionsA3.add(new MultipleChoiceOption("Korjaamolla", -1));
		optionsA3.add(new MultipleChoiceOption("Internetissä", -1));
		a3.setOptions(optionsA3);
		questionList4.add(a3);

		TextfieldQuestion a4 = new TextfieldQuestion();
		a4.setQuestion("Jos ajanvaraus tapahtui puhelimitse, mistä tietolähteestä korjaamon numero katsottiin?");

		SubQuestion subQa5 = new SubQuestion();
		ArrayList<SubQuestion> subQListA5 = new ArrayList<SubQuestion>();
		ArrayList<MultipleChoiceOption> optionsA5 = new ArrayList<MultipleChoiceOption>();
		MultipleChoiceQuestion a5 = new MultipleChoiceQuestion();
		a5.setQuestion("Onnistuiko ajanvaraus ensimmäisellä yrityksellä?");
		optionsA5.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA5.add(new MultipleChoiceOption("Ei", 0));
		a5.setOptions(optionsA5);
		subQa5.setQuestion(a5);
		subQListA5.add(subQa5);
		a4.setSubQuestions(subQListA5);
		questionList4.add(a4);

		MultipleChoiceQuestion a6 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA6 = new ArrayList<MultipleChoiceOption>();
		a6.setQuestion("Asiakkaan tavoittaessa huoltoneuvojan, kuinka monta kertaa puhelin hälytti ennen vastaamista?");
		optionsA6.add(new MultipleChoiceOption("Alle 5", 2));
		optionsA6.add(new MultipleChoiceOption("Yli 5", 0));
		a6.setOptions(optionsA6);

		ArrayList<SubQuestion> subQListA6 = new ArrayList<SubQuestion>();

		ArrayList<MultipleChoiceOption> optionsA7 = new ArrayList<MultipleChoiceOption>();
		SubQuestion subQa7 = new SubQuestion();

		MultipleChoiceQuestion a7 = new MultipleChoiceQuestion();
		a7.setQuestion("Onnistuiko ajanvaraus ensimmäisellä yrityksellä?");
		optionsA7.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA7.add(new MultipleChoiceOption("Ei", 0));
		a7.setOptions(optionsA7);
		subQa7.setQuestion(a7);
		subQListA6.add(subQa7);

		SubQuestion subQa8 = new SubQuestion();
		MultipleChoiceQuestion a8 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA8 = new ArrayList<MultipleChoiceOption>();
		a8.setQuestion("Yhdistettiinkö puhelu useammin kuin kerran uudelleen?");
		optionsA8.add(new MultipleChoiceOption("Kyllä", 0));
		optionsA8.add(new MultipleChoiceOption("Ei", 2));
		a8.setOptions(optionsA8);
		subQa8.setQuestion(a8);
		subQListA6.add(subQa8);

		SubQuestion subQa9 = new SubQuestion();
		MultipleChoiceQuestion a9 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA9 = new ArrayList<MultipleChoiceOption>();
		a9.setQuestion("Huoltoneuvojan tavoittaminen?");
		optionsA9.add(new MultipleChoiceOption("Alle 1 minuutti", 2));
		optionsA9.add(new MultipleChoiceOption("1-3 minuuttia", 1));
		optionsA9.add(new MultipleChoiceOption("Yli 3 minuuttia", 0));
		a9.setOptions(optionsA9);
		subQa9.setQuestion(a9);
		subQListA6.add(subQa9);

		a6.setSubQuestions(subQListA6);
		questionList4.add(a6);

		MultipleChoiceQuestion a10 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA10 = new ArrayList<MultipleChoiceOption>();
		a10.setQuestion("Odotusaika ennen huoltoneuvojan tapaamista varattaessa aika korjaamolla:");
		optionsA10.add(new MultipleChoiceOption("Alle 1 minuutti", 2));
		optionsA10.add(new MultipleChoiceOption("1-3 minuuttia", 1));
		optionsA10.add(new MultipleChoiceOption("Yli 3 minuuttia", 0));
		a10.setOptions(optionsA10);
		questionList4.add(a10);

		MultipleChoiceQuestion a11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA11 = new ArrayList<MultipleChoiceOption>();
		a11.setQuestion("Varattaessa aika internetistä, saiko asiakas vastauksen/vahvistuksen vuorokauden sisällä?");
		optionsA11.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA11.add(new MultipleChoiceOption("Ei", 0));
		a11.setOptions(optionsA11);
		questionList4.add(a11);

		questionGroup4.setQuestions(questionList4);
		questionGroups2.add(questionGroup4);

		// A-5 - Asiakastietojen hallinta

		QuestionGroup questionGroup5 = new QuestionGroup();

		questionGroup5.setTitle("Asiakastietojen hallinta");
		ArrayList<Question> questionList5 = new ArrayList<Question>();

		MultipleChoiceQuestion a12 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA12 = new ArrayList<MultipleChoiceOption>();
		a12.setQuestion("Olivatko asiakas- ja autotiedot jo liikkeen tiedossa?");
		optionsA12.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA12.add(new MultipleChoiceOption("Ei", -1));
		a12.setOptions(optionsA12);

		ArrayList<SubQuestion> subQListA12 = new ArrayList<SubQuestion>();

		SubQuestion subQa13 = new SubQuestion();
		MultipleChoiceQuestion a13 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA13 = new ArrayList<MultipleChoiceOption>();
		a13.setQuestion("Jos kyllä: varmistettiinko puhelinnumero?");
		optionsA13.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA13.add(new MultipleChoiceOption("Ei", -1));
		a13.setOptions(optionsA13);
		subQa13.setQuestion(a13);
		subQListA12.add(subQa13);

		SubQuestion subQa14 = new SubQuestion();
		MultipleChoiceQuestion a14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA14 = new ArrayList<MultipleChoiceOption>();
		a14.setQuestion("Jos kyllä: tunnistettiinko auto rekisterinumerolla?");
		optionsA14.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA14.add(new MultipleChoiceOption("Ei", -1));
		a14.setOptions(optionsA14);
		subQa14.setQuestion(a14);
		subQListA12.add(subQa14);

		SubQuestion subQa15 = new SubQuestion();
		MultipleChoiceQuestion a15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA15 = new ArrayList<MultipleChoiceOption>();
		a15.setQuestion("Jos kyllä: kysyttiinkö/varmistettiinko sähköpostiosoite?");
		optionsA15
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", -1));
		optionsA15.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", -1));
		optionsA15.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakkaalla ei ole sähköpostiosoitetta",
				"Kyllä, mutta asiakkaalla<br>ei ole sähköpostiosoitetta", -1));
		optionsA15.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakas ei halunnut antaa osoitetta",
				"Kyllä, mutta asiakas ei<br>halunnut antaa osoitetta", -1));
		a15.setOptions(optionsA15);
		subQa15.setQuestion(a15);
		subQListA12.add(subQa15);

		a12.setSubQuestions(subQListA12);
		questionList5.add(a12);

		MultipleChoiceQuestion a17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA17 = new ArrayList<MultipleChoiceOption>();
		a17.setQuestion("Jos asiakas- ja autotiedot eivät olleet liikkeellä, kysyttiinkö osoite?");
		optionsA17.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA17.add(new MultipleChoiceOption("Ei", 0));
		a17.setOptions(optionsA17);

		ArrayList<SubQuestion> subQListA17 = new ArrayList<SubQuestion>();

		SubQuestion subQa18 = new SubQuestion();
		MultipleChoiceQuestion a18 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA18 = new ArrayList<MultipleChoiceOption>();
		a18.setQuestion("Kysyttiinkö puhelinnumero?");
		optionsA18.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA18.add(new MultipleChoiceOption("Ei", 0));
		a18.setOptions(optionsA18);
		subQa18.setQuestion(a18);
		subQListA17.add(subQa18);

		SubQuestion subQa19 = new SubQuestion();
		MultipleChoiceQuestion a19 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA19 = new ArrayList<MultipleChoiceOption>();
		a19.setQuestion("Kysyttiinkö rekisterinumero?");
		optionsA19.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA19.add(new MultipleChoiceOption("Ei", 0));
		a19.setOptions(optionsA19);
		subQa19.setQuestion(a19);
		subQListA17.add(subQa19);

		SubQuestion subQa20 = new SubQuestion();
		MultipleChoiceQuestion a20 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA20 = new ArrayList<MultipleChoiceOption>();
		a20.setQuestion("Kysyttiinkö sähköpostiosoite?");
		optionsA20
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA20.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsA20.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakkaalla ei ole sähköpostiosoitetta",
				"Kyllä, mutta asiakkaalla ei<br>ole sähköpostiosoitetta", 2));
		optionsA20.add(new MultipleChoiceOption(
				"Kyllä, mutta ei halunnut antaa osoitetta",
				"Kyllä, mutta asiakas ei<br>halunnut antaa osoitetta", 2));
		a20.setOptions(optionsA20);
		subQa20.setQuestion(a20);
		subQListA17.add(subQa20);

		a17.setSubQuestions(subQListA17);

		questionList5.add(a17);
		questionGroup5.setQuestions(questionList5);
		questionGroups2.add(questionGroup5);

		// A-3 - Tilattava huolto

		QuestionGroup questionGroup6 = new QuestionGroup();
		ArrayList<Question> questionList6 = new ArrayList<Question>();

		questionGroup6.setTitle("Tilattava huolto");

		TextfieldQuestion a31 = new TextfieldQuestion();
		a31.setQuestion("Tilattu huolto (esim 60000 km");
		questionList6.add(a31);

		TextfieldQuestion a32 = new TextfieldQuestion();
		a32.setQuestion("Huollon päivämäärä");
		questionList6.add(a32);

		TextfieldQuestion a33 = new TextfieldQuestion();
		a33.setQuestion("Kellonaika");
		questionList6.add(a33);

		MultipleChoiceQuestion a34 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA34 = new ArrayList<MultipleChoiceOption>();
		a34.setQuestion("Kysyttiinkö asikkaalta ajokilometrimäärä?");
		optionsA34
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA34.add(new MultipleChoiceOption("Ei", "Kyllä <br> &nbsp", 0));
		optionsA34.add(new MultipleChoiceOption("Asiakas kertoi kysymättä",
				"Asiakas kertoi<br>kysymättä", -1));
		a34.setOptions(optionsA34);

		MultipleChoiceQuestion a35 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA35 = new ArrayList<MultipleChoiceOption>();
		a35.setQuestion("Kysyttiinkö asiakkaalta onko autossa jotain lisätöitä (korjauksia)?");
		optionsA35
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA35.add(new MultipleChoiceOption("Ei", "Kyllä <br> &nbsp", 0));
		optionsA35.add(new MultipleChoiceOption("Asiakas kertoi kysymättä",
				"Asiakas kertoi<br>kysymättä", -1));
		a35.setOptions(optionsA35);

		MultipleChoiceQuestion a36 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA36 = new ArrayList<MultipleChoiceOption>();
		a36.setQuestion("Saiko asiakas mielestään huoltoajan kohtuullisen ajan sisällä?");
		optionsA36.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA36.add(new MultipleChoiceOption("Ei", 0));
		a36.setOptions(optionsA36);

		questionGroup6.setQuestions(questionList6);
		questionGroups2.add(questionGroup6);

		// A-4 - Sijaiskulkuneuvo

		QuestionGroup questionGroup7 = new QuestionGroup();
		ArrayList<Question> questionList7 = new ArrayList<Question>();

		questionGroup7.setTitle("Sijaiskulkuneuvo");

		MultipleChoiceQuestion a37 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA37 = new ArrayList<MultipleChoiceOption>();
		a37.setQuestion("Tarjottiinko vaihtoehtoista kulkumuotoa?");
		optionsA37
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA37.add(new MultipleChoiceOption("Ei", "Kyllä <br> &nbsp", 0));
		optionsA37.add(new MultipleChoiceOption("Asiakas kertoi odottavansa",
				"Asiakas kertoi<br>odottavansa", -1));
		a37.setOptions(optionsA37);
		questionList7.add(a37);

		MultipleChoiceQuestion a38 = new MultipleChoiceQuestion();
		a38.setMultipleSelectionsAllowed(true);
		ArrayList<MultipleChoiceOption> optionsA38 = new ArrayList<MultipleChoiceOption>();
		a38.setQuestion("Tarjotut vaihtoehdot");
		optionsA38.add(new MultipleChoiceOption("Sijaisauto", -1));
		optionsA38
				.add(new MultipleChoiceOption("Nouto- ja palautuspalvelu", -1));
		optionsA38.add(new MultipleChoiceOption("Taksikuljetus", -1));
		optionsA38
				.add(new MultipleChoiceOption("Julkiset liikennevälineet", -1));

		ArrayList<SubQuestion> subQListA38 = new ArrayList<SubQuestion>();
		SubQuestion subQa38 = new SubQuestion();

		TextfieldQuestion a39 = new TextfieldQuestion();
		a39.setQuestion("Jokin muu, mikä?");
		subQa38.setQuestion(a39);

		subQListA38.add(subQa38);
		a38.setSubQuestions(subQListA38);

		questionList7.add(a38);

		questionGroup7.setQuestions(questionList7);
		questionGroups2.add(questionGroup7);

		// A-5 - Asiakkaan informoiminen

		QuestionGroup questionGroup8 = new QuestionGroup();
		ArrayList<Question> questionList8 = new ArrayList<Question>();

		questionGroup8.setTitle("Asiakkaan informoiminen");

		MultipleChoiceQuestion a40 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA40 = new ArrayList<MultipleChoiceOption>();
		a40.setQuestion("Muistutettiinko asiakasta ottamaan huoltokirja mukaan?");
		optionsA40.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA40.add(new MultipleChoiceOption("Ei", 0));
		a40.setOptions(optionsA40);
		questionList8.add(q40);

		MultipleChoiceQuestion a41 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA41 = new ArrayList<MultipleChoiceOption>();
		a41.setQuestion("Nimettiinkö huoltoneuvoja / vastasiko huoltoneuvoja nimellä selkeästi puhelimeen / "
				+ "kävikö nimi ilmi (mikäli varattiin paikanpäällä)?");
		optionsA41.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA41.add(new MultipleChoiceOption("Ei", 0));
		a41.setOptions(optionsA41);

		ArrayList<SubQuestion> subQListA41 = new ArrayList<SubQuestion>();

		SubQuestion subQa42 = new SubQuestion();
		TextfieldQuestion a42 = new TextfieldQuestion();
		a42.setQuestion("Huoltoneuvojan nimi?");
		subQa42.setQuestion(a42);
		subQListA41.add(subQa42);
		a41.setSubQuestions(subQListA41);

		questionList8.add(a41);

		MultipleChoiceQuestion a43 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA43 = new ArrayList<MultipleChoiceOption>();
		a43.setQuestion("Autossa on huolto-/leasing-sopimus?");
		optionsA43.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA43.add(new MultipleChoiceOption("Ei", -1));
		a43.setOptions(optionsA43);
		questionList8.add(a43);

		MultipleChoiceQuestion a44 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA44 = new ArrayList<MultipleChoiceOption>();
		a44.setQuestion("Kertoiko huoltoneuvoja kysymättä kustannusarvion?");
		optionsA44.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA44.add(new MultipleChoiceOption("Ei", 0));
		a44.setOptions(optionsA44);

		ArrayList<SubQuestion> subQListA44 = new ArrayList<SubQuestion>();

		SubQuestion subQa45 = new SubQuestion();

		MultipleChoiceQuestion a45 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA45 = new ArrayList<MultipleChoiceOption>();
		a45.setQuestion("Olisiko asiakas halunnut huoltoneuvojan kertovan kustannusarvion kysymättä?");
		optionsA45.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA45.add(new MultipleChoiceOption("Ei", -1));
		a45.setOptions(optionsA45);
		subQa45.setQuestion(a45);
		subQListA44.add(subQa45);

		SubQuestion subQa46 = new SubQuestion();
		MultipleChoiceQuestion a46 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA46 = new ArrayList<MultipleChoiceOption>();
		a46.setQuestion("Kysyikö asiakas kustannusarvion??");
		optionsA46.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA46.add(new MultipleChoiceOption("Ei", -1));
		a46.setOptions(optionsA46);
		subQa46.setQuestion(a46);
		subQListA44.add(subQa46);

		SubQuestion subQa47 = new SubQuestion();
		MultipleChoiceQuestion a47 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA47 = new ArrayList<MultipleChoiceOption>();
		a47.setQuestion("Jos asiakas kysyi tai hänelle kerrottiin kustannusarvio: saiko selkeän/tarkan "
				+ "kustannusarvion?");
		optionsA47.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA47.add(new MultipleChoiceOption("Ei", 0));
		a47.setOptions(optionsA47);
		subQa47.setQuestion(a47);
		subQListA44.add(subQa47);

		SubQuestion subQa48 = new SubQuestion();
		TextfieldQuestion a48 = new TextfieldQuestion();
		a48.setQuestion("Kustannusarvio huollolle?");
		subQa48.setQuestion(a48);
		subQListA44.add(subQa48);

		a44.setSubQuestions(subQListA44);
		questionList8.add(a44);

		MultipleChoiceQuestion a49 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA49 = new ArrayList<MultipleChoiceOption>();
		a49.setQuestion("Kertoiko huoltoneuvoja kysymättä huollon valmistumisajan?");
		optionsA49.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA49.add(new MultipleChoiceOption("Ei", 0));
		a49.setOptions(optionsA49);

		ArrayList<SubQuestion> subQListA49 = new ArrayList<SubQuestion>();

		SubQuestion subQa49 = new SubQuestion();

		MultipleChoiceQuestion a50 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA50 = new ArrayList<MultipleChoiceOption>();
		a50.setQuestion("Jos ei kertonut: kysyikö asiakas itse?");
		optionsA50.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA50.add(new MultipleChoiceOption("Ei", -1));
		a50.setOptions(optionsA50);
		subQa49.setQuestion(a50);
		subQListA49.add(subQa49);

		SubQuestion subQa50 = new SubQuestion();
		MultipleChoiceQuestion a51 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA51 = new ArrayList<MultipleChoiceOption>();
		a51.setQuestion("Jos asiakas kysyi/asiakkaalle kerrottiin, saiko asiakas riittävän selkeän"
				+ "valmistumisajan?");
		optionsA51.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA51.add(new MultipleChoiceOption("Ei", 0));
		a51.setOptions(optionsA51);
		subQa50.setQuestion(a51);
		subQListA49.add(subQa50);

		SubQuestion subQa51 = new SubQuestion();
		TextfieldQuestion a52 = new TextfieldQuestion();
		a52.setQuestion("Luvattu valmistumisaika (auto noudettavissa) kello");
		subQa51.setQuestion(a52);
		subQListA49.add(subQa51);

		a49.setSubQuestions(subQListA49);

		questionList8.add(a49);

		MultipleChoiceQuestion a53 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA53 = new ArrayList<MultipleChoiceOption>();
		a53.setQuestion("Tilattiinko huollon lisäksi lisätöitä?");
		optionsA53.add(new MultipleChoiceOption("Kyllä", -1));
		optionsA53.add(new MultipleChoiceOption("Ei", -1));
		a53.setOptions(optionsA53);

		ArrayList<SubQuestion> subQListA53 = new ArrayList<SubQuestion>();

		SubQuestion subQa54 = new SubQuestion();

		TextfieldQuestion q54 = new TextfieldQuestion();
		q54.setQuestion("Tilatut lisätyöt:");
		subQa54.setQuestion(q54);
		subQListA53.add(subQa54);

		SubQuestion subQa55 = new SubQuestion();
		MultipleChoiceQuestion a55 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA55 = new ArrayList<MultipleChoiceOption>();
		a55.setQuestion("Ehdittiinkö korjaamolla tekemään tilatut lisätyöt?");
		optionsA55.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA55.add(new MultipleChoiceOption("Ei", 0));
		a55.setOptions(optionsA55);
		subQa55.setQuestion(a55);
		subQListA53.add(subQa55);

		SubQuestion subQa56 = new SubQuestion();
		MultipleChoiceQuestion a56 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA56 = new ArrayList<MultipleChoiceOption>();
		a56.setQuestion("Kysyikö huoltoneuvoja tarkentavia tietoja tilatuista lisätöistä?"
				+ "Jäikö tunne, että asiakas otetaan tosissaan?");
		optionsA56.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA56.add(new MultipleChoiceOption("Ei", 0));
		optionsA56.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		a56.setOptions(optionsA56);
		subQa56.setQuestion(a56);
		subQListA53.add(subQa56);

		a53.setSubQuestions(subQListA53);
		questionList8.add(a53);

		MultipleChoiceQuestion a57 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA57 = new ArrayList<MultipleChoiceOption>();
		a57.setQuestion("Lähetettiinkö tekstiviestinä varmistus/muistutus huollosta?");
		optionsA57.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA57.add(new MultipleChoiceOption("Ei", 0));
		a57.setOptions(optionsA57);
		questionList8.add(a57);

		MultipleChoiceQuestion a58 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA58 = new ArrayList<MultipleChoiceOption>();
		a58.setQuestion("Saiko asiakas varmistuksen/muistustutuksen tilatuista lisätöistä?");
		optionsA58.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA58.add(new MultipleChoiceOption("Ei", 0));
		a58.setOptions(optionsA58);
		questionList8.add(a58);

		MultipleChoiceQuestion a59 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA59 = new ArrayList<MultipleChoiceOption>();
		a59.setQuestion("Saiko asiakas toimintaohjeet ilmoittaa uusista lisätöistä?");
		optionsA59.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA59.add(new MultipleChoiceOption("Ei", 0));
		a59.setOptions(optionsA59);
		questionList8.add(a59);

		questionGroup8.setQuestions(questionList8);
		questionGroups2.add(questionGroup8);

		reportPart2.setQuestionGroups(questionGroups2);

		reportParts.add(reportPart2);

		report.setReportParts(reportParts);

		return report;
	}
}
