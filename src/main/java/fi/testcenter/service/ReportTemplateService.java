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
		optionsA13.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA13.add(new MultipleChoiceOption("Ei", 0));
		a13.setOptions(optionsA13);
		subQa13.setQuestion(a13);
		subQListA12.add(subQa13);

		SubQuestion subQa14 = new SubQuestion();
		MultipleChoiceQuestion a14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA14 = new ArrayList<MultipleChoiceOption>();
		a14.setQuestion("Jos kyllä: tunnistettiinko auto rekisterinumerolla?");
		optionsA14.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA14.add(new MultipleChoiceOption("Ei", 0));
		a14.setOptions(optionsA14);
		subQa14.setQuestion(a14);
		subQListA12.add(subQa14);

		SubQuestion subQa15 = new SubQuestion();
		MultipleChoiceQuestion a15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA15 = new ArrayList<MultipleChoiceOption>();
		a15.setQuestion("Jos kyllä: kysyttiinkö/varmistettiinko sähköpostiosoite?");
		optionsA15
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA15.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsA15.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakkaalla ei ole sähköpostiosoitetta",
				"Kyllä, mutta asiakkaalla<br>ei ole sähköpostiosoitetta", 2));
		optionsA15.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakas ei halunnut antaa osoitetta",
				"Kyllä, mutta asiakas ei<br>halunnut antaa osoitetta", 2));
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
		a31.setQuestion("Tilattu huolto (esim 60000 km)");
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
		optionsA34.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsA34.add(new MultipleChoiceOption("Asiakas kertoi kysymättä",
				"Asiakas kertoi<br>kysymättä", -1));
		a34.setOptions(optionsA34);
		questionList6.add(a34);

		MultipleChoiceQuestion a35 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA35 = new ArrayList<MultipleChoiceOption>();
		a35.setQuestion("Kysyttiinkö asiakkaalta onko autossa jotain lisätöitä (korjauksia)?");
		optionsA35
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsA35.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsA35.add(new MultipleChoiceOption("Asiakas kertoi kysymättä",
				"Asiakas kertoi<br>kysymättä", -1));
		a35.setOptions(optionsA35);
		questionList6.add(a35);

		MultipleChoiceQuestion a36 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA36 = new ArrayList<MultipleChoiceOption>();
		a36.setQuestion("Saiko asiakas mielestään huoltoajan kohtuullisen ajan sisällä?");
		optionsA36.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA36.add(new MultipleChoiceOption("Ei", 0));
		a36.setOptions(optionsA36);
		questionList6.add(a36);

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
		optionsA37.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
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
		a38.setOptions(optionsA38);

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

		MultipleChoiceQuestion a555 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsA555 = new ArrayList<MultipleChoiceOption>();
		a555.setQuestion("Muistutettiinko asiakasta ottamaan huoltokirja mukaan?");
		optionsA555.add(new MultipleChoiceOption("Kyllä", 2));
		optionsA555.add(new MultipleChoiceOption("Ei", 0));
		a555.setOptions(optionsA555);
		questionList8.add(a555);

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
		a46.setQuestion("Kysyikö asiakas kustannusarvion?");
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

		// Osa B - Työnvastaanotto
		// B-1 - Työnvastaanotossa

		ReportPart reportPart3 = new ReportPart();
		reportPart3.setTitle("Osa B - Työnvastaanotto");

		ArrayList<QuestionGroup> questionGroups3 = new ArrayList<QuestionGroup>();
		QuestionGroup questionGroup9 = new QuestionGroup();

		questionGroup9.setTitle("Työnvastaanotossa");
		ArrayList<Question> questionList9 = new ArrayList<Question>();

		TextfieldQuestion b1 = new TextfieldQuestion();
		b1.setQuestion("Päivämäärä, jolle huolto on varattu:");
		questionList9.add(b1);

		TextfieldQuestion b2 = new TextfieldQuestion();
		b2.setQuestion("Kellonaika, jolle huolto on varattu:");
		questionList9.add(b2);

		TextfieldQuestion b3 = new TextfieldQuestion();
		b3.setQuestion("Kellonaika, jolloin asiakas saapui työnvastaanottoon:");
		questionList9.add(b3);

		MultipleChoiceQuestion b4 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB4 = new ArrayList<MultipleChoiceOption>();
		b4.setQuestion("Löytyikö korjaamon pihalta vapaa asiakaspysäköintipaikka?");
		optionsB4.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB4.add(new MultipleChoiceOption("Ei", 0));
		b4.setOptions(optionsB4);
		questionList9.add(b4);

		MultipleChoiceQuestion b5 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB5 = new ArrayList<MultipleChoiceOption>();
		b5.setQuestion("Löysikö asikas helposti korjaamon työnvastaanottoon?");
		optionsB5.add(new MultipleChoiceOption("Kyllä", 4));
		optionsB5.add(new MultipleChoiceOption("Ei", 0));
		b5.setOptions(optionsB5);
		questionList9.add(b5);

		MultipleChoiceQuestion b6 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB6 = new ArrayList<MultipleChoiceOption>();
		b6.setQuestion("Pääsikö asiakas suoraan huoltoneuvojan palveltavaksi?");
		optionsB6.add(new MultipleChoiceOption("Kyllä", 4));
		optionsB6.add(new MultipleChoiceOption("Ei", 0));
		b6.setOptions(optionsB6);

		ArrayList<SubQuestion> subQListb7 = new ArrayList<SubQuestion>();

		SubQuestion subQb7 = new SubQuestion();

		MultipleChoiceQuestion b7 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB7 = new ArrayList<MultipleChoiceOption>();
		b7.setQuestion("Jos ei, niin miten pitkään asiakas joutui odottamaan?");
		optionsB7.add(new MultipleChoiceOption("1-3 minuuttia", 3));
		optionsB7.add(new MultipleChoiceOption("3-5 minuuttia", 2));
		optionsB7.add(new MultipleChoiceOption("5-10 minuuttia", 1));
		optionsB7.add(new MultipleChoiceOption("Yli 10 minuuttia", 0));
		b7.setOptions(optionsB7);
		subQb7.setQuestion(b7);
		subQListb7.add(subQb7);

		SubQuestion subQb8 = new SubQuestion();

		MultipleChoiceQuestion b8 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB8 = new ArrayList<MultipleChoiceOption>();
		b8.setQuestion("Mistä jonotus johtui?");
		optionsB8.add(new MultipleChoiceOption("Jonoa", -1));
		optionsB8.add(new MultipleChoiceOption(
				"Henkilökuntaa ei ollut paikalla", -1));
		optionsB8.add(new MultipleChoiceOption(
				"Huoltoneuvoja puhui puhelimessa", -1));
		b8.setOptions(optionsB8);
		subQb8.setQuestion(b8);
		subQListb7.add(subQb8);

		b6.setSubQuestions(subQListb7);

		questionList9.add(b6);

		questionGroup9.setQuestions(questionList9);
		questionGroups3.add(questionGroup9);

		// B-2 - Keskustelu työnvastaanotossa

		QuestionGroup questionGroup10 = new QuestionGroup();
		ArrayList<Question> questionList10 = new ArrayList<Question>();

		questionGroup10.setTitle("Keskustelu työnvastaanotossa");

		MultipleChoiceQuestion b9 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB9 = new ArrayList<MultipleChoiceOption>();
		b9.setQuestion("Kävikö palvelleen huoltoneuvojan nimi ilmi?");
		optionsB9.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB9.add(new MultipleChoiceOption("Ei", 0));
		b9.setOptions(optionsB9);

		SubQuestion subQb9 = new SubQuestion();
		ArrayList<SubQuestion> subQListb9 = new ArrayList<SubQuestion>();

		TextfieldQuestion b10 = new TextfieldQuestion();
		b10.setQuestion("Huoltoneuvojan nimi:");
		subQb9.setQuestion(b10);
		subQListb9.add(subQb9);
		b9.setSubQuestions(subQListb9);

		questionList10.add(b9);

		MultipleChoiceQuestion b11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB11 = new ArrayList<MultipleChoiceOption>();
		b11.setQuestion("Varmistettiinko ajanvarauksessa sovitut asiat"
				+ "(huolto, tilatut lisätyöt, hinta- ja aika-arvio)?");
		optionsB11.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB11.add(new MultipleChoiceOption("Ei", 0));
		b11.setOptions(optionsB11);
		questionList10.add(b11);

		MultipleChoiceQuestion b12 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB12 = new ArrayList<MultipleChoiceOption>();
		b12.setQuestion("Pyydettiinkö asiakasta ottamaan huoltokirja mukaan "
				+ "saapuessaan työnvastaanottoon?");
		optionsB12
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsB12.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsB12.add(new MultipleChoiceOption(
				"Asiakas otti huoltokirjan pyytämättä",
				"Asiakas otti <br> huoltokirjan pyytämättä", -1));
		b12.setOptions(optionsB12);

		SubQuestion subQb12 = new SubQuestion();

		ArrayList<SubQuestion> subQListB12 = new ArrayList<SubQuestion>();

		MultipleChoiceQuestion b13 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB13 = new ArrayList<MultipleChoiceOption>();
		b13.setQuestion("Jos ei: kysyikö korjaamon edustaja luvan ottaa se autosta?");
		optionsB13.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB13.add(new MultipleChoiceOption("Ei", 0));
		b13.setOptions(optionsB13);
		subQb12.setQuestion(b13);
		subQListB12.add(subQb12);
		b12.setSubQuestions(subQListB12);

		questionList10.add(b12);

		MultipleChoiceQuestion b14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB14 = new ArrayList<MultipleChoiceOption>();
		b14.setQuestion("Onnistuiko keskustelu häiriöittä?");
		optionsB14.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB14.add(new MultipleChoiceOption("Ei", 0));
		b14.setOptions(optionsB13);

		SubQuestion subQb14 = new SubQuestion();

		ArrayList<SubQuestion> subQListb14 = new ArrayList<SubQuestion>();

		MultipleChoiceQuestion b15 = new MultipleChoiceQuestion();
		b15.setMultipleSelectionsAllowed(true);
		ArrayList<MultipleChoiceOption> optionsB15 = new ArrayList<MultipleChoiceOption>();
		b15.setQuestion("Jos ei: Mikä häiritsi?");
		optionsB15.add(new MultipleChoiceOption("Toinen asiakas", -1));
		optionsB15.add(new MultipleChoiceOption("Puhelinkeskustelu", -1));
		optionsB15.add(new MultipleChoiceOption("Toinen työntekijä", -1));
		optionsB15.add(new MultipleChoiceOption("Korjaamon meteli", -1));
		optionsB15.add(new MultipleChoiceOption("Yleinen meteli", -1));
		b15.setOptions(optionsB15);
		subQb14.setQuestion(b15);
		subQListb14.add(subQb14);
		b14.setSubQuestions(subQListb14);

		questionList10.add(b14);

		questionGroup10.setQuestions(questionList10);
		questionGroups3.add(questionGroup10);

		// B-3 - Töiden kirjaaminen

		QuestionGroup questionGroup11 = new QuestionGroup();
		ArrayList<Question> questionList11 = new ArrayList<Question>();

		questionGroup11.setTitle("Töiden kirjaaminen");

		MultipleChoiceQuestion b16 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB16 = new ArrayList<MultipleChoiceOption>();
		b16.setQuestion("Olikö työmääräys valmiina?");
		optionsB16.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB16.add(new MultipleChoiceOption("Ei", 0));
		b16.setOptions(optionsB16);
		questionList11.add(b16);

		MultipleChoiceQuestion b17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB17 = new ArrayList<MultipleChoiceOption>();
		b17.setQuestion("Kysyttiinkö lupaa jollekin seuraavista lisätöistä?");
		optionsB17.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB17.add(new MultipleChoiceOption("Ei", 0));
		b17.setOptions(optionsB17);

		SubQuestion subQb17 = new SubQuestion();

		ArrayList<SubQuestion> subQListB17 = new ArrayList<SubQuestion>();

		MultipleChoiceQuestion b18 = new MultipleChoiceQuestion();
		b18.setMultipleSelectionsAllowed(true);
		ArrayList<MultipleChoiceOption> optionsB18 = new ArrayList<MultipleChoiceOption>();
		b18.setQuestion("Jos kyllä, mille lisätöille?");
		optionsB18.add(new MultipleChoiceOption("Pyyhkijänsulkien vaihto", -1));
		optionsB18.add(new MultipleChoiceOption("Lasinpesunesteen lisäys", -1));
		optionsB18.add(new MultipleChoiceOption("Polttimoiden vaihto", -1));
		optionsB18.add(new MultipleChoiceOption("Jarrupalojen vaihto", -1));
		optionsB18.add(new MultipleChoiceOption("Muulle", -1));
		optionsB18.add(new MultipleChoiceOption(
				"Kysyttiin lupa tietylle summalle", -1));
		optionsB18.add(new MultipleChoiceOption(
				"Autossa on huolto- tai leasingsopimus", -1));
		b18.setOptions(optionsB18);
		subQb17.setQuestion(b18);
		subQListB17.add(subQb17);
		b17.setSubQuestions(subQListB17);
		questionList11.add(b17);

		MultipleChoiceQuestion b19 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB19 = new ArrayList<MultipleChoiceOption>();
		b19.setQuestion("Tarjottiinko asiakkaalle suositeltuja lisätöitä?");
		optionsB19.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB19.add(new MultipleChoiceOption("Ei", 0));
		optionsB19.add(new MultipleChoiceOption("Ei kaikkia mahdollisia", 1));
		b19.setOptions(optionsB19);

		SubQuestion subQb19 = new SubQuestion();

		ArrayList<SubQuestion> subQListB19 = new ArrayList<SubQuestion>();

		TextfieldQuestion b20 = new TextfieldQuestion();
		b20.setQuestion("Jos ei / ei kaikkia mahdollisia: mitä ei tarjottu?");
		subQb19.setQuestion(b20);
		subQListB19.add(subQb19);
		b19.setSubQuestions(subQListB19);

		questionList11.add(b19);

		MultipleChoiceQuestion b21 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB21 = new ArrayList<MultipleChoiceOption>();
		b21.setQuestion("Oliko tilattuja lisätöitä huollon lisäksi?");
		optionsB21.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB21.add(new MultipleChoiceOption("Ei", -1));
		b21.setOptions(optionsB21);

		SubQuestion subQb21 = new SubQuestion();

		ArrayList<SubQuestion> subQListB21 = new ArrayList<SubQuestion>();

		MultipleChoiceQuestion b22 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB22 = new ArrayList<MultipleChoiceOption>();
		b22.setQuestion("Tuliko niihin muutoksia?");
		optionsB22.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB22.add(new MultipleChoiceOption("Ei", -1));
		b22.setOptions(optionsB22);
		subQb21.setQuestion(b22);
		subQListB21.add(subQb21);
		b21.setSubQuestions(subQListB21);

		questionList11.add(b21);

		MultipleChoiceQuestion b23 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB23 = new ArrayList<MultipleChoiceOption>();
		b23.setQuestion("Oliko tilattujen lisätöiden lisäksi uusia lisätöitä?");
		optionsB23.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB23.add(new MultipleChoiceOption("Ei", -1));
		b23.setOptions(optionsB23);
		questionList11.add(b23);

		TextfieldQuestion b24 = new TextfieldQuestion();
		b24.setQuestion("Tilatut lisätyöt (autoa jätettäessä mainitut):");
		questionList11.add(b24);

		MultipleChoiceQuestion b25 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB25 = new ArrayList<MultipleChoiceOption>();
		b25.setQuestion("Ehditäänkö korjaamolla tekemään uudet lisätyöt?");
		optionsB25.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB25.add(new MultipleChoiceOption("Ei", 0));
		b25.setOptions(optionsB25);
		questionList11.add(b25);

		MultipleChoiceQuestion b26 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB26 = new ArrayList<MultipleChoiceOption>();
		b26.setQuestion("Kysyikö huoltoneuvoja tarkentavia tietoja uusista lisätöistä? "
				+ "Jäikö tunne, että asiakas otetaan tosissaan");
		optionsB26.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB26.add(new MultipleChoiceOption("Ei", 0));
		optionsB26.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		b26.setOptions(optionsB26);
		questionList11.add(b26);

		MultipleChoiceQuestion b27 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB27 = new ArrayList<MultipleChoiceOption>();
		b27.setQuestion("Kirjattiinko työmääräykseen tehtävä huolto?");
		optionsB27.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB27.add(new MultipleChoiceOption("Ei", 0));
		b27.setOptions(optionsB27);

		ArrayList<SubQuestion> subQListB27 = new ArrayList<SubQuestion>();
		SubQuestion subQb27 = new SubQuestion();

		MultipleChoiceQuestion b28 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB28 = new ArrayList<MultipleChoiceOption>();
		b28.setQuestion("Kirjattiinko luvat lisätöille?");
		optionsB28.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB28.add(new MultipleChoiceOption("Ei", 0));
		optionsB28.add(new MultipleChoiceOption("Ei pyydetty lupaa", -1));
		b28.setOptions(optionsB28);
		subQb27.setQuestion(b28);
		subQListB27.add(subQb27);

		SubQuestion subQb28 = new SubQuestion();
		MultipleChoiceQuestion b29 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB29 = new ArrayList<MultipleChoiceOption>();
		b29.setQuestion("Kirjattiinko asiakkaan kertomat lisäkorjaukset?");
		optionsB29.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB29.add(new MultipleChoiceOption("Ei", 0));
		optionsB29.add(new MultipleChoiceOption("Ei kaikkia", 1));
		b29.setOptions(optionsB29);
		subQb28.setQuestion(b29);
		subQListB27.add(subQb28);

		SubQuestion subQb30 = new SubQuestion();
		MultipleChoiceQuestion b30 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB30 = new ArrayList<MultipleChoiceOption>();
		b30.setQuestion("Kirjattiinko selventäviä tietoja mekaanikolle lisäkorjauksesta?");
		optionsB30.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB30.add(new MultipleChoiceOption("Ei", 0));
		optionsB30.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		b30.setOptions(optionsB30);
		subQb30.setQuestion(b30);
		subQListB27.add(subQb30);

		b27.setSubQuestions(subQListB27);

		questionList11.add(b27);

		MultipleChoiceQuestion b31 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB31 = new ArrayList<MultipleChoiceOption>();
		b31.setQuestion("Kysyttiinkö/varmistettiinko puhelinnumero?");
		optionsB31.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB31.add(new MultipleChoiceOption("Ei", 0));
		b31.setOptions(optionsB31);
		questionList11.add(b31);

		MultipleChoiceQuestion b32 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB32 = new ArrayList<MultipleChoiceOption>();
		b32.setQuestion("Kysyttiinkö/varmistettiinko sähköpostiosoite?");
		optionsB32
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsB32.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsB32.add(new MultipleChoiceOption(
				"Kyllä, mutta asiakkaalla ei ole sähköpostiosoitetta",
				"Kyllä, mutta asiakkaalla ei<br>ole sähköpostiosoitetta", -1));
		optionsB32.add(new MultipleChoiceOption(
				"Kyllä mutta asiakas ei halunnut antaa osoitetta",
				"Kyllä, mutta asiakas ei<br>halunnut antaa osoitetta", -1));
		b32.setOptions(optionsB32);
		questionList11.add(b32);

		MultipleChoiceQuestion b33 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB33 = new ArrayList<MultipleChoiceOption>();
		b33.setQuestion("Käytiinkö työmääräys läpi paperilta/tietokoneelta asiakkaalle?");
		optionsB33.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB33.add(new MultipleChoiceOption("Ei", 0));
		b33.setOptions(optionsB33);
		questionList11.add(b33);

		MultipleChoiceQuestion b34 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB34 = new ArrayList<MultipleChoiceOption>();
		b34.setQuestion("Pyydettiinkö työmääräykseen allekirjoitus?");
		optionsB34.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB34.add(new MultipleChoiceOption("Ei", 0));
		b34.setOptions(optionsB34);
		questionList11.add(b34);

		MultipleChoiceQuestion b35 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB35 = new ArrayList<MultipleChoiceOption>();
		b35.setQuestion("Tarjottiinko kopiota työmääräyksestä?");
		optionsB35
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsB35.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsB35.add(new MultipleChoiceOption(
				"Työnvastaanotossa ohje, jonka mukaan saa pyydettäessä",
				"Työnvastaanotossa ohje, jonka<br>mukaan saa pyydettäessä", 1));
		b35.setOptions(optionsB35);
		questionList11.add(b35);

		MultipleChoiceQuestion b36 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB36 = new ArrayList<MultipleChoiceOption>();
		b36.setQuestion("Autossa on huolto-/leasing-sopimus?");
		optionsB36.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB36.add(new MultipleChoiceOption("Ei", -1));
		b36.setOptions(optionsB36);
		questionList11.add(b36);

		MultipleChoiceQuestion b37 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB37 = new ArrayList<MultipleChoiceOption>();
		b37.setQuestion("Kertoiko huoltoneuvoja kysymättä kustannusarvion?");
		optionsB37
				.add(new MultipleChoiceOption("Kyllä", "Kyllä <br> &nbsp", 2));
		optionsB37.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsB37.add(new MultipleChoiceOption(
				"Ajanvarauksessa sovittua kustannusarviota ei varmistettu",
				"Ajanvarauksessa sovittua<br>kustannusarviota ei varmistettu",
				2));
		b37.setOptions(optionsB37);

		ArrayList<SubQuestion> subQListb37 = new ArrayList<SubQuestion>();

		SubQuestion subQb37 = new SubQuestion();

		MultipleChoiceQuestion b38 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB38 = new ArrayList<MultipleChoiceOption>();
		b38.setQuestion("Jos ei kerrottu/varmistettu: kysyikö asiakas itse huollon kustannusarvion?");
		optionsB38.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB38.add(new MultipleChoiceOption("Ei", -1));
		b38.setOptions(optionsB38);
		subQb37.setQuestion(b38);
		subQListb37.add(subQb37);

		SubQuestion subQb38 = new SubQuestion();
		MultipleChoiceQuestion b39 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB39 = new ArrayList<MultipleChoiceOption>();
		b39.setQuestion("Saiko asiakas halutessaan riittävän selkeän/tarkan kustannusarvion?");
		optionsB39.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB39.add(new MultipleChoiceOption("Ei", 0));
		b39.setOptions(optionsB39);
		subQb38.setQuestion(b39);
		subQListb37.add(subQb38);

		SubQuestion subQb39 = new SubQuestion();
		TextfieldQuestion b40 = new TextfieldQuestion();
		b40.setQuestion("Kustannusarvio huollolle:");
		subQb39.setQuestion(b40);
		subQListb37.add(subQb39);

		b37.setSubQuestions(subQListb37);
		questionList11.add(b37);

		MultipleChoiceQuestion b41 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB41 = new ArrayList<MultipleChoiceOption>();
		b41.setQuestion("Kertoiko huoltoneuvoja kysymättä huollon valmistumisajan?");
		optionsB41.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB41.add(new MultipleChoiceOption("Ei", 0));
		optionsB41.add(new MultipleChoiceOption(
				"Ajanvarauksessa sovittua valmistumisaikaa ei varmistettu", 0));
		b41.setOptions(optionsB41);

		ArrayList<SubQuestion> subQListb41 = new ArrayList<SubQuestion>();

		SubQuestion subQb41 = new SubQuestion();

		MultipleChoiceQuestion b42 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB42 = new ArrayList<MultipleChoiceOption>();
		b42.setQuestion("Jos ei kysytty: kysyikö asiakas itse?");
		optionsB42.add(new MultipleChoiceOption("Kyllä", -1));
		optionsB42.add(new MultipleChoiceOption("Ei", -1));
		b42.setOptions(optionsB42);
		subQb41.setQuestion(b42);
		subQListb41.add(subQb41);

		SubQuestion subQb42 = new SubQuestion();
		MultipleChoiceQuestion b43 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB43 = new ArrayList<MultipleChoiceOption>();
		b43.setQuestion("Saiko asiakas halutessaan riittävän selkeän valmistumisajan?");
		optionsB43.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB43.add(new MultipleChoiceOption("Ei", 0));
		b43.setOptions(optionsB43);
		subQb42.setQuestion(b43);
		subQListb41.add(subQb42);

		b41.setSubQuestions(subQListb41);

		questionList11.add(b41);

		TextfieldQuestion b44 = new TextfieldQuestion();
		b44.setQuestion("Luvattu valmistumisaika (auto noudettavissa) kello: ");
		questionList11.add(b44);

		MultipleChoiceQuestion b45 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsB45 = new ArrayList<MultipleChoiceOption>();
		b45.setQuestion("Luvattiinko huollon valmistumisesta ilmoittaa?");
		optionsB45.add(new MultipleChoiceOption("Kyllä", 2));
		optionsB45.add(new MultipleChoiceOption("Ei", 0));
		b45.setOptions(optionsB45);
		questionList11.add(b45);

		questionGroup11.setQuestions(questionList11);
		questionGroups3.add(questionGroup11);

		reportPart3.setQuestionGroups(questionGroups3);

		reportParts.add(reportPart3);

		// Osa C - Auton luovutus

		ReportPart reportPart4 = new ReportPart();
		reportPart4.setTitle("Osa C - Yhteydenotto huollon aikana");

		ArrayList<QuestionGroup> questionGroups4 = new ArrayList<QuestionGroup>();

		QuestionGroup questionGroup12 = new QuestionGroup();
		ArrayList<Question> questionList12 = new ArrayList<Question>();

		questionGroup12.setTitle("Yhteydenotto auton ollessa korjaamolla");

		MultipleChoiceQuestion c1 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC1 = new ArrayList<MultipleChoiceOption>();
		c1.setQuestion("Havaittiinko auton ollessa korjaamolla korjausta vaativia vikoja?");
		optionsC1.add(new MultipleChoiceOption("Kyllä", -1));
		optionsC1.add(new MultipleChoiceOption("Ei", -1));
		c1.setOptions(optionsC1);

		ArrayList<SubQuestion> subQListC1 = new ArrayList<SubQuestion>();

		SubQuestion subQC1 = new SubQuestion();

		MultipleChoiceQuestion c2 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC2 = new ArrayList<MultipleChoiceOption>();
		c2.setQuestion("Jos havaittiin, ottiko huoltoneuvoja asiakkaaseen yhteyttä auton ollessa "
				+ "korjaamolla ja ehdotti tarvittavaa korjausta?");
		optionsC2.add(new MultipleChoiceOption("Kyllä kaikista",
				"Kyllä kaikista<br> &nbsp", 2));
		optionsC2.add(new MultipleChoiceOption("Kyllä osasta",
				"Kyllä osasta <br> &nbsp", 1));
		optionsC2.add(new MultipleChoiceOption("Ei", "Ei <br> &nbsp", 0));
		optionsC2.add(new MultipleChoiceOption(
				"Lupa pyydetty huoltoon tuotaessa",
				"Lupa pyydetty huoltoon<br>tuotaessa", -1));
		c2.setOptions(optionsC2);

		subQC1.setQuestion(c2);
		subQListC1.add(subQC1);

		SubQuestion subQc3 = new SubQuestion();

		TextfieldQuestion c4 = new TextfieldQuestion();
		c4.setQuestion("Huoltoneuvojan nimi:");
		subQc3.setQuestion(c4);

		subQListC1.add(subQc3);

		SubQuestion subQc4 = new SubQuestion();

		TextfieldQuestion c41 = new TextfieldQuestion();
		c41.setQuestion("Korjausta vaativat viat:");
		subQc4.setQuestion(c41);
		subQListC1.add(subQc4);

		c1.setSubQuestions(subQListC1);

		questionList12.add(c1);

		MultipleChoiceQuestion c5 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC5 = new ArrayList<MultipleChoiceOption>();
		c5.setQuestion("Kerrottiinko havaitun lisäkorjauksen/korjausten kustannusarviota?");
		optionsC5.add(new MultipleChoiceOption("Kyllä kaikille", 2));
		optionsC5.add(new MultipleChoiceOption("Kyllä osalle", 1));
		optionsC5.add(new MultipleChoiceOption("Ei", 0));
		c5.setOptions(optionsC5);

		ArrayList<SubQuestion> subQListC5 = new ArrayList<SubQuestion>();

		SubQuestion subQc5 = new SubQuestion();

		TextfieldQuestion c6 = new TextfieldQuestion();
		c6.setQuestion("Kerrottu hinta:");
		subQc5.setQuestion(c6);
		subQListC5.add(subQc5);

		c5.setSubQuestions(subQListC5);
		questionList12.add(c5);

		MultipleChoiceQuestion c7 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC7 = new ArrayList<MultipleChoiceOption>();
		c7.setQuestion("Hyväksyikö asiakas lisäkorjauksen/korjaukset tehtäväksi huollon yhteydessä?");
		optionsC7.add(new MultipleChoiceOption("Kyllä kaikki", -1));
		optionsC7.add(new MultipleChoiceOption("Kyllä osan", -1));
		optionsC7.add(new MultipleChoiceOption("Ei", -1));
		c7.setOptions(optionsC7);

		ArrayList<SubQuestion> subQListC7 = new ArrayList<SubQuestion>();

		SubQuestion subQc7 = new SubQuestion();

		MultipleChoiceQuestion c8 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC8 = new ArrayList<MultipleChoiceOption>();
		c8.setQuestion("Jos hyväksyi, kerrottiinko korjauksen valmistumisajankohta?");
		optionsC8.add(new MultipleChoiceOption("Kyllä", 2));
		optionsC8.add(new MultipleChoiceOption("Ei", 0));
		optionsC8.add(new MultipleChoiceOption("Ei vaikuta valmistumisaikaan",
				-11));
		c8.setOptions(optionsC8);

		subQc7.setQuestion(c8);
		subQListC7.add(subQc7);

		SubQuestion subQc8 = new SubQuestion();

		TextfieldQuestion c9 = new TextfieldQuestion();
		c9.setQuestion("Kellonaika jolloin auton voi noutaa:");
		subQc8.setQuestion(c9);
		subQListC7.add(subQc8);

		SubQuestion subQc9 = new SubQuestion();

		MultipleChoiceQuestion c10 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC10 = new ArrayList<MultipleChoiceOption>();
		c10.setQuestion("Jos ei kerrottu valmistumisaikaa, kerrotiinko asiakkaalle korjauksen "
				+ "laiminlyömisen mahdolliset seuraukset?");
		optionsC10.add(new MultipleChoiceOption("Kyllä", 2));
		optionsC10.add(new MultipleChoiceOption("Ei", 0));
		c10.setOptions(optionsC10);

		subQc9.setQuestion(c10);
		subQListC7.add(subQc9);
		c7.setSubQuestions(subQListC7);

		questionList12.add(c7);

		MultipleChoiceQuestion c11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC11 = new ArrayList<MultipleChoiceOption>();
		c11.setQuestion("Ilmoitettiinko huollon valmistumisesta?");
		optionsC11.add(new MultipleChoiceOption("Kyllä", "Kyllä<br> &nbsp", 2));
		optionsC11.add(new MultipleChoiceOption("Ei", "Ei<br> &nbsp", 0));
		optionsC11.add(new MultipleChoiceOption(
				"Sovittu että valmistumisesta ei tarvitse ilmoittaa",
				"Sovittu että valmistumisesta<br>ei tarvitse ilmoittaa", -1));
		c11.setOptions(optionsC11);

		questionList12.add(c11);

		questionGroup12.setQuestions(questionList12);
		questionGroups4.add(questionGroup12);

		reportPart4.setQuestionGroups(questionGroups4);

		reportParts.add(reportPart4);

		// Osa D - Auton luovutus

		ReportPart reportPart5 = new ReportPart();
		reportPart5.setTitle("Osa D - Auton luovutus");

		ArrayList<QuestionGroup> questionGroups5 = new ArrayList<QuestionGroup>();

		QuestionGroup questionGroup13 = new QuestionGroup();
		ArrayList<Question> questionList13 = new ArrayList<Question>();

		questionGroup13.setTitle("Työnvastaanotossa auton luovutus");

		TextfieldQuestion c12 = new TextfieldQuestion();
		c12.setQuestion("Luvattu valmistumisaika");
		questionList13.add(c12);

		TextfieldQuestion c13 = new TextfieldQuestion();
		c13.setQuestion("Asiakas saapui työnvastaanottoon");
		questionList13.add(c13);

		MultipleChoiceQuestion c14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC14 = new ArrayList<MultipleChoiceOption>();
		c14.setQuestion("Valmistuiko huolto sovittuun aikaan mennessä?");
		optionsC14.add(new MultipleChoiceOption("Kyllä", "Kyllä<br> &nbsp", 2));
		optionsC14
				.add(new MultipleChoiceOption(
						"Ei/huoltoneuvoja ei kysyttäessä osannut antaa valmistumisaikaa",
						"Ei/huoltoneuvoja ei kysyttäessä<br>osannut antaa valmistumisaikaa",
						0));
		optionsC14.add(new MultipleChoiceOption("Ei sovittua valmistumisaikaa",
				-1));
		c14.setOptions(optionsC14);

		questionList13.add(c14);

		MultipleChoiceQuestion c15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC15 = new ArrayList<MultipleChoiceOption>();
		c15.setQuestion("Pääsikö asiakas suoraan huoltoneuvojan palveltavaksi?");
		optionsC15.add(new MultipleChoiceOption("Kyllä", 4));
		optionsC15.add(new MultipleChoiceOption("Ei", 0));
		c15.setOptions(optionsC15);

		ArrayList<SubQuestion> subQListC15 = new ArrayList<SubQuestion>();

		SubQuestion subQc15 = new SubQuestion();

		MultipleChoiceQuestion c16 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC16 = new ArrayList<MultipleChoiceOption>();
		c16.setQuestion("Jos ei, kuinka pitkään asiakas joutui odottamaan?");
		optionsC16.add(new MultipleChoiceOption("1-3 minuuttia", 3));
		optionsC16.add(new MultipleChoiceOption("3-5 minuuttia", 2));
		optionsC16.add(new MultipleChoiceOption("5-10 minuuttia", 1));
		optionsC16.add(new MultipleChoiceOption("Yli 10 minuuttia", 0));
		c16.setOptions(optionsC16);

		subQc15.setQuestion(c16);
		subQListC15.add(subQc15);

		SubQuestion subQc17 = new SubQuestion();
		MultipleChoiceQuestion c17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsC17 = new ArrayList<MultipleChoiceOption>();
		c17.setQuestion("Mistä jonotus johtui?");
		optionsC17.add(new MultipleChoiceOption("Jonoa", -1));
		optionsC17.add(new MultipleChoiceOption(
				"Henkilökuntaa ei ollut paikalla", -1));
		optionsC17.add(new MultipleChoiceOption(
				"Huoltoneuvoja puhui puhelimessa", -1));

		c17.setOptions(optionsC17);
		subQc17.setQuestion(c17);
		subQListC15.add(subQc17);

		SubQuestion subQc18 = new SubQuestion();
		TextfieldQuestion c18 = new TextfieldQuestion();
		c18.setQuestion("Huoltoneuvojan nimi:");
		subQc18.setQuestion(c18);
		subQListC15.add(subQc18);

		c15.setSubQuestions(subQListC15);
		questionList13.add(c15);

		questionGroup13.setQuestions(questionList13);

		questionGroups5.add(questionGroup13);

		// D - 3 - Lasku

		QuestionGroup questionGroup14 = new QuestionGroup();
		ArrayList<Question> questionList14 = new ArrayList<Question>();

		questionGroup14.setTitle("Lasku");

		MultipleChoiceQuestion d1 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD1 = new ArrayList<MultipleChoiceOption>();
		d1.setQuestion("Oliko lasku valmiina asiakkaan saapuessa?");
		optionsD1.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD1.add(new MultipleChoiceOption("Ei", 0));
		optionsD1.add(new MultipleChoiceOption(
				"Asiakas saapui sovittua aikaisemmin", -1));
		d1.setOptions(optionsD1);
		questionList14.add(d1);

		MultipleChoiceQuestion d2 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD2 = new ArrayList<MultipleChoiceOption>();
		d2.setQuestion("Laskun läpikäynti?");
		optionsD2.add(new MultipleChoiceOption("Käydään läpi riveittäin", 6));
		optionsD2.add(new MultipleChoiceOption("Pääpiirteittäin", 3));
		optionsD2.add(new MultipleChoiceOption("Ei ollenkaan", 0));
		d2.setOptions(optionsD2);
		questionList14.add(d2);

		MultipleChoiceQuestion d3 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD3 = new ArrayList<MultipleChoiceOption>();
		d3.setQuestion("Vastasiko lasku annettua kustannusarviota huomioiden sovitut lisätyöt?");
		optionsD3.add(new MultipleChoiceOption("Tarkasti (+/- 10eur)", 8));
		optionsD3.add(new MultipleChoiceOption("Alitti enemmän kuin 10eur", 6));
		optionsD3.add(new MultipleChoiceOption(
				"Ylitti yli 10eur, mutta alle 15 %", 2));
		optionsD3.add(new MultipleChoiceOption("Ylitti yli 15 %", 1));
		optionsD3.add(new MultipleChoiceOption("Ei saatu vaikka kysyttiin", 0));
		optionsD3.add(new MultipleChoiceOption("Ei kysytty", -1));
		optionsD3.add(new MultipleChoiceOption("Huolto-/leasingsopimus", -1));
		d3.setOptions(optionsD3);
		questionList14.add(d3);

		MultipleChoiceQuestion d4 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD4 = new ArrayList<MultipleChoiceOption>();
		d4.setQuestion("Onko laskulle merkitty huomautukset?");
		optionsD4.add(new MultipleChoiceOption("Kyllä kaikki", 2));
		optionsD4.add(new MultipleChoiceOption("Kyllä osa", 1));
		optionsD4.add(new MultipleChoiceOption("Ei", 0));
		optionsD4.add(new MultipleChoiceOption("Ei huomautettavaa", -1));
		d4.setOptions(optionsD4);
		questionList14.add(d4);

		questionGroup14.setQuestions(questionList14);
		questionGroups5.add(questionGroup14);

		// D - 4 - Asiakkaan neuvonta

		QuestionGroup questionGroup15 = new QuestionGroup();

		questionGroup15.setTitle("Asiakkaan neuvonta");

		ArrayList<Question> questionList15 = new ArrayList<Question>();

		MultipleChoiceQuestion d5 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD5 = new ArrayList<MultipleChoiceOption>();
		d5.setQuestion("Kerrottiinko seuraavan huollon ajankohdasta?");
		optionsD5.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD5.add(new MultipleChoiceOption("Ei", 0));

		d5.setOptions(optionsD5);
		questionList15.add(d5);

		MultipleChoiceQuestion d6 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD6 = new ArrayList<MultipleChoiceOption>();
		d6.setQuestion("Kerrottinko seuraavaan huoltoon liittyvistä lisätöistä?");
		optionsD6.add(new MultipleChoiceOption("Kyllä kaikista", 2));
		optionsD6.add(new MultipleChoiceOption("Kyllä osasta", 1));
		optionsD6.add(new MultipleChoiceOption("Ei", 0));
		optionsD6.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		d6.setOptions(optionsD6);
		questionList15.add(d6);

		MultipleChoiceQuestion d7 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD7 = new ArrayList<MultipleChoiceOption>();
		d7.setQuestion("Kerrottinko seuraavaan huoltoon liittyvistä korjaustarpeista?");
		optionsD7.add(new MultipleChoiceOption("Kyllä kaikista", 2));
		optionsD7.add(new MultipleChoiceOption("Kyllä osasta", 1));
		optionsD7.add(new MultipleChoiceOption("Ei", 0));
		optionsD7.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		d7.setOptions(optionsD7);
		questionList15.add(d7);

		MultipleChoiceQuestion d8 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD8 = new ArrayList<MultipleChoiceOption>();
		d8.setQuestion("Annettiinko huollon tarkastuslista?");
		optionsD8.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD8.add(new MultipleChoiceOption("Ei", 0));
		d8.setOptions(optionsD8);
		questionList15.add(d8);

		MultipleChoiceQuestion d9 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD9 = new ArrayList<MultipleChoiceOption>();
		d9.setQuestion("Esiteltiinkö huollon tarkastuslistan merkintöjä?");
		optionsD9.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD9.add(new MultipleChoiceOption("Ei", 0));
		d9.setOptions(optionsD9);
		questionList15.add(d9);

		MultipleChoiceQuestion d90 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD90 = new ArrayList<MultipleChoiceOption>();
		d90.setQuestion("Esiteltiinkö huoltokirjan leima?");
		optionsD90.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD90.add(new MultipleChoiceOption("Ei", 0));
		optionsD90.add(new MultipleChoiceOption("Ei leimattu", -1));
		d90.setOptions(optionsD90);
		questionList15.add(d90);

		MultipleChoiceQuestion d100 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD100 = new ArrayList<MultipleChoiceOption>();
		d100.setQuestion("Kerrottiinko huolletun auton sijainti pysäköintialueella?");
		optionsD100.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD100.add(new MultipleChoiceOption("Ei", 0));
		optionsD100.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		d100.setOptions(optionsD100);
		questionList15.add(d100);

		MultipleChoiceQuestion d10 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD10 = new ArrayList<MultipleChoiceOption>();
		d10.setQuestion("Kerrottiinko huolletun auton sijainti pysäköintialueella?");
		optionsD10.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD10.add(new MultipleChoiceOption("Ei", 0));
		optionsD10.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		d10.setOptions(optionsD10);
		questionList15.add(d10);

		MultipleChoiceQuestion d11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD11 = new ArrayList<MultipleChoiceOption>();
		d11.setQuestion("Ehdittiinkö korjaamolla tekemään kaikki sovitut työt?");
		optionsD11.add(new MultipleChoiceOption("Kyllä", 4));
		optionsD11.add(new MultipleChoiceOption("Ei", 0));
		d11.setOptions(optionsD11);
		questionList15.add(d11);

		MultipleChoiceQuestion d12 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD12 = new ArrayList<MultipleChoiceOption>();
		d12.setQuestion("Jos korjaamolla havaittiin korjausta vaativia vikoja, mutta ei ehditty tekemään/"
				+ "ei ollut tarvittavia osia, tarjottiinko uutta aikaa?");
		optionsD12.add(new MultipleChoiceOption("Kyllä", 2));
		optionsD12.add(new MultipleChoiceOption("Ei", 0));
		d12.setOptions(optionsD12);
		questionList15.add(d12);

		questionGroup15.setQuestions(questionList15);
		questionGroups5.add(questionGroup15);

		// D 5 - Asiakaspalveluhenkinen toiminta

		QuestionGroup questionGroup16 = new QuestionGroup();

		questionGroup16.setTitle("Asiakaspalveluhenkinen toiminta");

		ArrayList<Question> questionList16 = new ArrayList<Question>();

		MultipleChoiceQuestion d13 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD13 = new ArrayList<MultipleChoiceOption>();
		d13.setQuestion("Asiakkaalle jäänyt tunne korjaamon toiminnasta");
		optionsD13.add(new MultipleChoiceOption("Hyvä", 8));
		optionsD13.add(new MultipleChoiceOption("Tyydyttävä", 3));
		optionsD13.add(new MultipleChoiceOption("Heikko", 0));
		d13.setOptions(optionsD13);
		questionList16.add(d13);

		questionGroup16.setQuestions(questionList16);
		questionGroups5.add(questionGroup16);
		reportPart5.setQuestionGroups(questionGroups5);
		reportParts.add(reportPart5);

		// Osa E Korjaamon yhteistyö

		ReportPart reportPart6 = new ReportPart();
		reportPart6.setTitle("Osa E - Korjaamon yhteistyö");

		ArrayList<QuestionGroup> questionGroups6 = new ArrayList<QuestionGroup>();

		QuestionGroup questionGroup17 = new QuestionGroup();

		questionGroup17.setTitle("Asiakaspalveluhenkinen toiminta");

		ArrayList<Question> questionList17 = new ArrayList<Question>();

		MultipleChoiceQuestion d14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD14 = new ArrayList<MultipleChoiceOption>();
		d14.setQuestion("Työmääräykseen kirjattu asiakkaalle luvattu valmistumisaika");
		optionsD14.add(new MultipleChoiceOption("Kyllä", -1));
		optionsD14.add(new MultipleChoiceOption("Ei", -1));
		optionsD14.add(new MultipleChoiceOption("Ei sovittua valmistumisaikaa",
				-1));
		d14.setOptions(optionsD14);
		questionList17.add(d14);

		MultipleChoiceQuestion d15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD15 = new ArrayList<MultipleChoiceOption>();
		d15.setQuestion("Asiakkaan nimi ja puhelinnumero kirjattu");
		optionsD15.add(new MultipleChoiceOption("Kyllä", -1));
		optionsD15.add(new MultipleChoiceOption("Ei", -1));
		d15.setOptions(optionsD15);
		questionList17.add(d15);

		MultipleChoiceQuestion d16 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD16 = new ArrayList<MultipleChoiceOption>();
		d16.setQuestion("Asiakkaan allekirjoitus löytyy");
		optionsD16.add(new MultipleChoiceOption("Kyllä", -1));
		optionsD16.add(new MultipleChoiceOption("Ei", -1));
		d16.setOptions(optionsD16);
		questionList17.add(d16);

		MultipleChoiceQuestion d17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD17 = new ArrayList<MultipleChoiceOption>();
		d17.setQuestion("Työn vastaanottaneen henkilön nimi työmääräyksessä");
		optionsD17.add(new MultipleChoiceOption("Kyllä", -1));
		optionsD17.add(new MultipleChoiceOption("Ei", -1));
		d17.setOptions(optionsD17);
		questionList17.add(d17);

		MultipleChoiceQuestion d18 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsD18 = new ArrayList<MultipleChoiceOption>();
		d18.setQuestion("Onko yhteydenotosta asiakkaaseen tehty merkintä");
		optionsD18.add(new MultipleChoiceOption("Kyllä", -1));
		optionsD18.add(new MultipleChoiceOption("Ei", -1));
		optionsD18.add(new MultipleChoiceOption("Ei tarvetta yhteydenottoon",
				-1));
		d18.setOptions(optionsD18);
		questionList17.add(d18);

		questionGroup17.setQuestions(questionList17);
		questionGroups6.add(questionGroup17);

		// E 2 - Mekaanikon tekninen suoritus

		QuestionGroup questionGroup18 = new QuestionGroup();

		questionGroup18.setTitle("Mekaanikon tekninen suoritus");

		ArrayList<Question> questionList18 = new ArrayList<Question>();

		TextfieldQuestion e0 = new TextfieldQuestion();
		e0.setQuestion("Mekaanikon nimi:");
		questionList18.add(e0);

		MultipleChoiceQuestion e1 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE1 = new ArrayList<MultipleChoiceOption>();
		e1.setQuestion("Huollon tarkastuslista täytetty oikein");
		optionsE1.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE1.add(new MultipleChoiceOption("Ei", -1));
		e1.setOptions(optionsE1);
		questionList18.add(e1);

		MultipleChoiceQuestion e2 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE2 = new ArrayList<MultipleChoiceOption>();
		e2.setQuestion("Työmääräykseen merkitty todelliset ajokilometrit");
		optionsE2.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE2.add(new MultipleChoiceOption("Ei", -1));
		e2.setOptions(optionsE2);
		questionList18.add(e2);

		MultipleChoiceQuestion e3 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE3 = new ArrayList<MultipleChoiceOption>();
		e3.setQuestion("Tarvittavat huollon asiakirjat työmääräyksen mukana");
		optionsE3.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE3.add(new MultipleChoiceOption("Ei", -1));
		e3.setOptions(optionsE3);
		questionList18.add(e3);

		MultipleChoiceQuestion e4 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE4 = new ArrayList<MultipleChoiceOption>();
		e4.setQuestion("Huomautukset/lisätyöt merkitty selvällä käsialalla");
		optionsE4.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE4.add(new MultipleChoiceOption("Ei", -1));
		e4.setOptions(optionsE4);
		questionList18.add(e4);

		MultipleChoiceQuestion e5 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE5 = new ArrayList<MultipleChoiceOption>();
		e5.setQuestion("Huomautukset/lisätyöt merkitty ymmärrettävästi");
		optionsE5.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE5.add(new MultipleChoiceOption("Ei", -1));
		e5.setOptions(optionsE5);
		questionList18.add(e5);

		MultipleChoiceQuestion e6 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE6 = new ArrayList<MultipleChoiceOption>();
		e6.setQuestion("Tarpeelliset huomautukset kerrottu asiakkaalle");
		optionsE6.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE6.add(new MultipleChoiceOption("Ei", -1));
		optionsE6.add(new MultipleChoiceOption(
				"Ei asiakkaalle kerrottavia huomautuksia", -1));
		e6.setOptions(optionsE6);
		questionList18.add(e6);

		MultipleChoiceQuestion e7 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE7 = new ArrayList<MultipleChoiceOption>();
		e7.setQuestion("Lisätöissä/korjauksissa käytetyt varaosat merkitty laskulle");
		optionsE7.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE7.add(new MultipleChoiceOption("Ei", -1));
		optionsE7.add(new MultipleChoiceOption(
				"Ei varaosia vaativia lisätöitä/korjauksia", -1));
		e7.setOptions(optionsE7);
		questionList18.add(e7);

		MultipleChoiceQuestion e8 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE8 = new ArrayList<MultipleChoiceOption>();
		e8.setQuestion("Huolletun auton sijainti merkitty paikoitusalueella");
		optionsE8.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE8.add(new MultipleChoiceOption("Ei", -1));
		optionsE8.add(new MultipleChoiceOption("Ei tarpeellista", -1));
		e8.setOptions(optionsE8);
		questionList18.add(e8);

		questionGroup18.setQuestions(questionList18);
		questionGroups6.add(questionGroup18);

		// E 3 - Varaosapalvelun toimivuus

		QuestionGroup questionGroup19 = new QuestionGroup();

		questionGroup19.setTitle("Varaosapalvelun toimivuus");

		ArrayList<Question> questionList19 = new ArrayList<Question>();

		MultipleChoiceQuestion e9 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE9 = new ArrayList<MultipleChoiceOption>();
		e9.setQuestion("Tarvittavat osat oli kerätty valmiiksi");
		optionsE9.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE9.add(new MultipleChoiceOption("Ei", -1));
		optionsE9.add(new MultipleChoiceOption("Varaosia ei kerätä", -1));
		e9.setOptions(optionsE9);
		questionList19.add(e9);

		MultipleChoiceQuestion e10 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE10 = new ArrayList<MultipleChoiceOption>();
		e10.setQuestion("Huoltoon tarvittavat varaosat olivat oikeat");
		optionsE10.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE10.add(new MultipleChoiceOption("Ei", -1));
		e10.setOptions(optionsE10);
		questionList19.add(e10);

		MultipleChoiceQuestion e11 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE11 = new ArrayList<MultipleChoiceOption>();
		e11.setQuestion("Ennalta tilattuihin lisätöihin tarvittavat osat olivat saatavilla");
		optionsE11.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE11.add(new MultipleChoiceOption("Ei", -1));
		optionsE11.add(new MultipleChoiceOption(
				"Ei ennalta tilattuja varaosia vaativia lisätöitä", -1));
		e11.setOptions(optionsE11);
		questionList19.add(e11);

		MultipleChoiceQuestion e12 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE12 = new ArrayList<MultipleChoiceOption>();
		e12.setQuestion("Huollon aikana tulleisiin lisätöihin tarvittavat osat olivat saatavilla");
		optionsE12.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE12.add(new MultipleChoiceOption("Ei", -1));
		optionsE12.add(new MultipleChoiceOption("Ei tarvetta varaosille", -1));
		e12.setOptions(optionsE12);
		questionList19.add(e12);

		MultipleChoiceQuestion e13 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE13 = new ArrayList<MultipleChoiceOption>();
		e13.setQuestion("Tarvittavien varaosien palvelunopeus riittävä");
		optionsE13.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE13.add(new MultipleChoiceOption("Ei", -1));
		optionsE13.add(new MultipleChoiceOption(
				"Ei tarvetta asioida varaosissa", -1));
		e13.setOptions(optionsE13);
		questionList19.add(e13);

		questionGroup19.setQuestions(questionList19);
		questionGroups6.add(questionGroup19);

		// E 3 - Varaosapalvelun toimivuus

		QuestionGroup questionGroup20 = new QuestionGroup();

		questionGroup20
				.setTitle("Huoltoneuvojan ja mekaanikon yhteistyön toimivuus");

		ArrayList<Question> questionList20 = new ArrayList<Question>();

		MultipleChoiceQuestion e14 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE14 = new ArrayList<MultipleChoiceOption>();
		e14.setQuestion("Pääsikö mekaanikko heti asioimaan huoltoneuvojan kanssa");
		optionsE14.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE14.add(new MultipleChoiceOption("Ei", -1));
		optionsE14.add(new MultipleChoiceOption(
				"Ei tarvetta asioida huoltoneuvojan kanssa", -1));
		e14.setOptions(optionsE14);
		questionList20.add(e14);

		MultipleChoiceQuestion e15 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE15 = new ArrayList<MultipleChoiceOption>();
		e15.setQuestion("Löytyikö auto työmääräykseen/avaimenperälappuun merkitystä paikasta");
		optionsE15.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE15.add(new MultipleChoiceOption("Ei", -1));
		optionsE15.add(new MultipleChoiceOption(
				"Ei tarpeellista kysyä auton sijaintia asiakkaalta", -1));
		e15.setOptions(optionsE15);
		questionList20.add(e15);

		MultipleChoiceQuestion e16 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE16 = new ArrayList<MultipleChoiceOption>();
		e16.setQuestion("Oliko työmääräyksen teksti ymmärrettävässä muodossa");
		optionsE16.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE16.add(new MultipleChoiceOption("Ei", -1));
		e16.setOptions(optionsE16);
		questionList20.add(e16);

		MultipleChoiceQuestion e17 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE17 = new ArrayList<MultipleChoiceOption>();
		e17.setQuestion("Oliko työmääräyksessä riittävästi tietoa");
		optionsE17.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE17.add(new MultipleChoiceOption("Ei", -1));
		e17.setOptions(optionsE17);
		questionList20.add(e17);

		MultipleChoiceQuestion e18 = new MultipleChoiceQuestion();
		ArrayList<MultipleChoiceOption> optionsE18 = new ArrayList<MultipleChoiceOption>();
		e18.setQuestion("Oliko työmääräykseen merkitty kaikki tilatut työt");
		optionsE18.add(new MultipleChoiceOption("Kyllä", -1));
		optionsE18.add(new MultipleChoiceOption("Ei", -1));
		e18.setOptions(optionsE18);
		questionList20.add(e18);

		questionGroup20.setQuestions(questionList20);
		questionGroups6.add(questionGroup20);

		reportPart6.setQuestionGroups(questionGroups6);
		reportParts.add(reportPart6);

		report.setReportParts(reportParts);

		return report;
	}
}
