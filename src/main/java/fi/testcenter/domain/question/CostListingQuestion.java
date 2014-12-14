package fi.testcenter.domain.question;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class CostListingQuestion extends Question {

	List<String> questionItems;
	String total;

	public List<String> getQuestionItems() {
		return questionItems;
	}

	public void setQuestionItems(List<String> questionItems) {
		this.questionItems = questionItems;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
