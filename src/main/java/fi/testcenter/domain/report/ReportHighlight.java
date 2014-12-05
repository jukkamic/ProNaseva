package fi.testcenter.domain.report;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.OptionalQuestionsAnswer;

@Entity
public class ReportHighlight {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	private ReportPart reportPart;

	@ManyToOne(fetch = FetchType.EAGER)
	private QuestionGroup questionGroup;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "ANSWER_ID")
	private Answer answer;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OPTIONALANSWER_ID")
	private OptionalQuestionsAnswer optionalAnswer;

	@ManyToOne
	Report report;

	int questionGroupOrderNumber;
	int questionOrderNumber;

	int printReportQuestionOrderNumber; // Tulostettaessa huomioidaan raportista
										// pois jätetyt kysymykset
										// numeroinnissa.
	int subQuestionOrderNumber;

	ReportHighlight() {

	}

	ReportHighlight(Report report, ReportPart reportPart,
			QuestionGroup questionGroup, Answer answer) {
		this.report = report;
		this.reportPart = reportPart;
		this.questionGroup = questionGroup;
		this.answer = answer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ReportPart getReportPart() {
		return reportPart;
	}

	public void setReportPart(ReportPart reportPart) {
		this.reportPart = reportPart;
	}

	public QuestionGroup getQuestionGroup() {
		return questionGroup;
	}

	public void setQuestionGroup(QuestionGroup questionGroup) {
		this.questionGroup = questionGroup;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public int getQuestionGroupOrderNumber() {
		return questionGroupOrderNumber;
	}

	public void setQuestionGroupOrderNumber(int questionGroupOrderNumber) {
		this.questionGroupOrderNumber = questionGroupOrderNumber;
	}

	public int getQuestionOrderNumber() {
		return questionOrderNumber;
	}

	public void setQuestionOrderNumber(int questionOrderNumber) {
		this.questionOrderNumber = questionOrderNumber;
	}

	public int getSubQuestionOrderNumber() {
		return subQuestionOrderNumber;
	}

	public void setSubQuestionOrderNumber(int subQuestionOrderNumber) {
		this.subQuestionOrderNumber = subQuestionOrderNumber;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public int getPrintReportQuestionOrderNumber() {
		return printReportQuestionOrderNumber;
	}

	public void setPrintReportQuestionOrderNumber(
			int printReportQuestionOrderNumber) {
		this.printReportQuestionOrderNumber = printReportQuestionOrderNumber;
	}

	public OptionalQuestionsAnswer getOptionalAnswer() {
		return optionalAnswer;
	}

	public void setOptionalAnswer(OptionalQuestionsAnswer optionalAnswer) {
		this.optionalAnswer = optionalAnswer;
	}

}
