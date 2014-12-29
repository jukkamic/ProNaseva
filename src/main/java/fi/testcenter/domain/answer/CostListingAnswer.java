package fi.testcenter.domain.answer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

//KORJAA VALUUTTA KÄYTTÄEN : http://tutorials.jenkov.com/java-internationalization/numberformat.html

@Entity
public class CostListingAnswer extends Answer {

	@Transient
	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	List<String> answersIn;
	List<BigDecimal> answers;
	List<String> answersOut;

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

	Float total;
	String remarks;

	public CostListingAnswer() {
	}

	public CostListingAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
	}

	public CostListingAnswer(Question question) {
		super(question);
	}

	public CostListingAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
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

	public void formatCurrency() {
		NumberFormat nf_in;
		NumberFormat nf_out;

		List<String> newAnswersOutList = new ArrayList<String>();
		List<BigDecimal> newAnswersList = new ArrayList<BigDecimal>();

		for (String currencyAnswer : answersIn) {

			if (currencyAnswer != null && currencyAnswer != ""
					&& currencyAnswer.length() > 0) {
				currencyAnswer = currencyAnswer.replace('.', ','); // KORVAA
																	// DESIMAALIPISTEEN.
				// LISÄTTÄVÄ SYÖTETTYJEN
				// SUMMIEN VALIDOINTI
				log.debug("Syötetty summa " + currencyAnswer);
				BigDecimal currencyIn = null;
				nf_in = NumberFormat.getNumberInstance(Locale.FRANCE);

				try {
					currencyIn = BigDecimal.valueOf(nf_in.parse(currencyAnswer)
							.doubleValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
				newAnswersList.add(currencyIn);
				nf_out = NumberFormat.getCurrencyInstance(Locale.FRANCE);
				nf_out.setMaximumFractionDigits(2);
				nf_out.setMinimumFractionDigits(2);
				newAnswersOutList.add(nf_out.format(currencyIn));

			} else {
				newAnswersList.add(BigDecimal.valueOf(0));
				newAnswersOutList.add("0 €");
			}
		}
		this.answers = newAnswersList;
		this.answersOut = newAnswersOutList;

	}
}
