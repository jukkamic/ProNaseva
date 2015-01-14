package fi.testcenter.domain.answer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

//KORJAA VALUUTTA KÄYTTÄEN : http://tutorials.jenkov.com/java-internationalization/numberformat.html

@Entity
public class CostListingAnswer extends Answer {

	List<String> answersIn;
	List<BigDecimal> answers;
	List<String> answersOut;

	String totalIn;
	BigDecimal total;
	String totalOut;

	String remarks;

	public CostListingAnswer() {
	}

	public CostListingAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
	}

	public CostListingAnswer(OptionalQuestionsAnswer optionalQuestionsAnswer,
			Question question, int answerOrderNumber) {
		super(optionalQuestionsAnswer, question, answerOrderNumber);

	}

	public CostListingAnswer(Question question) {
		super(question);
	}

	public CostListingAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<BigDecimal> getAnswers() {
		return answers;
	}

	public void setAnswers(List<BigDecimal> answers) {
		this.answers = answers;
	}

	public List<String> getAnswersIn() {
		return answersIn;
	}

	public void setAnswersIn(List<String> answersIn) {
		this.answersIn = answersIn;
	}

	public List<String> getAnswersOut() {
		return answersOut;
	}

	public void setAnswersOut(List<String> answersOut) {
		this.answersOut = answersOut;
	}

	public String getTotalIn() {
		return totalIn;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotalIn(String totalIn) {
		this.totalIn = totalIn;
	}

	public String getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(String totalOut) {
		this.totalOut = totalOut;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void formatCurrencies() {
		List<String> newAnswersOutList = new ArrayList<String>();
		List<BigDecimal> newAnswersList = new ArrayList<BigDecimal>();

		for (String currencyAnswer : answersIn) {

			BigDecimal currencyIn = getCurrencyIn(currencyAnswer);
			newAnswersList.add(currencyIn);
			newAnswersOutList.add(getCurrencyOut(currencyIn));
		}
		this.answers = newAnswersList;
		this.answersOut = newAnswersOutList;
		this.total = getCurrencyIn(totalIn);
		this.totalOut = getCurrencyOut(total);
	}

	private BigDecimal getCurrencyIn(String amount) {
		if (amount != null && amount != "" && amount.length() > 0) {
			amount = amount.replace('.', ','); // KORVAA
												// DESIMAALIPISTEEN.
												// LISÄTTÄVÄ SYÖTETTYJEN
												// SUMMIEN VALIDOINTI

			BigDecimal currencyIn = null;
			NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.FRANCE);

			try {
				currencyIn = BigDecimal.valueOf(nf_in.parse(amount)
						.doubleValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return currencyIn.setScale(2, RoundingMode.HALF_UP);
		} else
			return BigDecimal.valueOf(0);
	}

	private String getCurrencyOut(BigDecimal amount) {
		if (amount == null)
			return "0 €";
		NumberFormat nf_out = NumberFormat.getCurrencyInstance(Locale.FRANCE);
		nf_out.setMaximumFractionDigits(2);
		nf_out.setMinimumFractionDigits(2);
		return nf_out.format(amount);

	}
}
