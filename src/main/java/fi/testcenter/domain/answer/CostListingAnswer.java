package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.Report;

//KORJAA VALUUTTA KÄYTTÄEN : http://tutorials.jenkov.com/java-internationalization/numberformat.html

@Entity
public class CostListingAnswer extends Answer {

	@ElementCollection
	@OrderColumn
	List<Float> answers;
	Float total;
	String remarks;

	public CostListingAnswer() {
	}

	public CostListingAnswer(Question question) {
		super(question);
	}

	public CostListingAnswer(Report report, Question question) {
		super(report, question);
	}

	public List<Float> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Float> answers) {
		this.answers = answers;
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

}
