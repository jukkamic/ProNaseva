package fi.testcenter.reportTemplatesForImporters;

import java.util.ArrayList;

import fi.testcenter.domain.question.DateQuestion;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.question.TextQuestion;
import fi.testcenter.domain.reportTemplate.PhoneCallTestReportTemplate;
import fi.testcenter.domain.reportTemplate.ReportTemplateQuestionGroup;

public class AudiCallTestTemplate {

	public static PhoneCallTestReportTemplate getReportTemplate() {
		PhoneCallTestReportTemplate template = new PhoneCallTestReportTemplate();

		template.setCurrent(true);
		template.setTemplateName("Audi-puhelinkyselyraportti");

		ArrayList<ReportTemplateQuestionGroup> questionGroups = new ArrayList<ReportTemplateQuestionGroup>();

		ReportTemplateQuestionGroup group = new ReportTemplateQuestionGroup();
		group.setTitle("Yleistä");

		ArrayList<Question> questionList = new ArrayList<Question>();

		questionList.add(new DateQuestion("Puhelun päivämäärä: "));

		questionList.add(new TextQuestion("Puhelun kellonaika: "));

		MultipleChoiceQuestion mcq = new MultipleChoiceQuestion(
				"Vastasiko keskustelukumppani omalla nimellään");
		ArrayList<MultipleChoiceOption> optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", -1));
		optionsList.add(new MultipleChoiceOption("Ei", -1));
		mcq.setOptionsList(optionsList);
		ArrayList<Question> subQuestionList = new ArrayList<Question>();
		subQuestionList.add(new TextQuestion("Nimi: "));
		mcq.setSubQuestions(subQuestionList);
		questionList.add(mcq);

		group.setQuestions(questionList);
		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Puheluun vastaaminen");

		questionList = new ArrayList<Question>();

		mcq = new MultipleChoiceQuestion("Montako kertaa puhelin soi?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("<=5", 2));
		optionsList.add(new MultipleChoiceOption(">5", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		mcq = new MultipleChoiceQuestion(
				"Kauanko aikaa kului, ennen kuin henkilökohtainen puhelu toteutui?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Minuuttia <=1", 2));
		optionsList.add(new MultipleChoiceOption(">1", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		group.setQuestions(questionList);
		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Asiakkaan tiedot");

		questionList = new ArrayList<Question>();

		mcq = new MultipleChoiceQuestion(
				"Tarkistettiinko ja päivitettiinkö asiakas- ja autotiedot: osoite?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);

		subQuestionList = new ArrayList<Question>();

		MultipleChoiceQuestion subMcq = new MultipleChoiceQuestion(
				"Puhelinnumero?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		subMcq.setOptionsList(optionsList);
		subQuestionList.add(subMcq);

		subMcq = new MultipleChoiceQuestion("Rekisterinumero?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		subMcq.setOptionsList(optionsList);
		subQuestionList.add(subMcq);

		subMcq = new MultipleChoiceQuestion("Alustanumero?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		subMcq.setOptionsList(optionsList);
		subQuestionList.add(subMcq);

		subMcq = new MultipleChoiceQuestion("Sähköpostiosoite?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		subMcq.setOptionsList(optionsList);
		subQuestionList.add(subMcq);

		mcq.setSubQuestions(subQuestionList);

		questionList.add(mcq);

		group.setQuestions(questionList);

		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Työmääräyksen rajoitukset");

		questionList = new ArrayList<Question>();

		mcq = new MultipleChoiceQuestion(
				"Kysyttiinkö nykyistä kilometrilukemaa?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		mcq = new MultipleChoiceQuestion("Kysyttiinkö lisätöistä?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		group.setQuestions(questionList);

		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Liikkuvuus");

		questionList = new ArrayList<Question>();

		mcq = new MultipleChoiceQuestion(
				"Tarjottiinko aktiivisesti jonkinlaista vaihtoehtoa sille, että matka ei katkeaisi auton ollessa korjaamolla?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList
				.add(new MultipleChoiceOption(
						"Kyllä, vuokra-auto ja viittaus ajokortin näyttämisen vaatimuksesta",
						"Kyllä, vuokra-auto ja viittaus<br>ajokortin näyttämisen vaatimuksesta",
						3));
		optionsList
				.add(new MultipleChoiceOption(
						"Ei, vuokra-auto, ei viittausta ajokortin näyttämisen vaatimuksesta",
						"Ei, vuokra-auto, ei viittausta<br>ajokortin näyttämisen vaatimuksesta",
						2));
		optionsList.add(new MultipleChoiceOption("Kyllä, muuta",
				"Kyllä, muuta<br>&nbsp", 3));
		optionsList.add(new MultipleChoiceOption("Ei, ei tarjousta",
				"Ei, ei tarjousta<br>&nbsp", 0));
		mcq.setOptionsList(optionsList);

		questionList.add(mcq);

		questionList.add(new TextQuestion("Sovittu liikkuvuus: "));

		group.setQuestions(questionList);

		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Asiakasinformaatio");

		questionList = new ArrayList<Question>();

		mcq = new MultipleChoiceQuestion(
				"Muistutettiinko sinua tuomaan mukanasi huoltovihko?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		mcq = new MultipleChoiceQuestion(
				"Kerrottiinko sinulle oman huoltoneuvojasi nimi?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 1));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);

		subQuestionList = new ArrayList<Question>();
		subQuestionList.add(new TextQuestion("Nimi: "));
		mcq.setSubQuestions(subQuestionList);
		questionList.add(mcq);

		mcq = new MultipleChoiceQuestion(
				"Tarjottiinko sinulle aktiivisesti töiden vastaanottoa auton luona (töiden vastaanottotapahtuma vieläkin henkilökohtaisemmin)?");
		optionsList = new ArrayList<MultipleChoiceOption>();
		optionsList.add(new MultipleChoiceOption("Kyllä", 4));
		optionsList.add(new MultipleChoiceOption("Ei", 0));
		mcq.setOptionsList(optionsList);
		questionList.add(mcq);

		group.setQuestions(questionList);

		questionGroups.add(group);

		group = new ReportTemplateQuestionGroup();
		group.setTitle("Huomioita");

		questionList = new ArrayList<Question>();
		questionList.add(new TextQuestion("Muita huomioita:"));
		group.setQuestions(questionList);
		questionGroups.add(group);

		template.setQuestionGroups(questionGroups);

		return template;

	}
}
