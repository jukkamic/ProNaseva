package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class ImportantPointsAnswer extends Answer {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "IMPORTANTPOINTANSWER")
	@OrderColumn
	List<ImportantPointsItem> answerItems;

	String remarks;

	public ImportantPointsAnswer() {
	}

	public ImportantPointsAnswer(Question question) {
		super(question);
	}

	public ImportantPointsAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question) {
		super(reportQuestionGroup, question);
	}

	public List<ImportantPointsItem> getAnswerItems() {
		return answerItems;
	}

	public void setAnswerItems(List<ImportantPointsItem> answerItems) {
		this.answerItems = answerItems;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
