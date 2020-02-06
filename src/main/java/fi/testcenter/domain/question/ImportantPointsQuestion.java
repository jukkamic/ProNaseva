package fi.testcenter.domain.question;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class ImportantPointsQuestion extends Question {

	List<String> questionItems;
	int maxScoreForQuestionItem;
	int numberOfItemsToChoose;

	public List<String> getQuestionItems() {
		return questionItems;
	}

	public void setQuestionItems(List<String> questionItems) {
		this.questionItems = questionItems;
	}

	public int getMaxScoreForQuestionItem() {
		return maxScoreForQuestionItem;
	}

	public void setMaxScoreForQuestionItem(int maxScoreForQuestionItem) {
		this.maxScoreForQuestionItem = maxScoreForQuestionItem;
	}

	public int getNumberOfItemsToChoose() {
		return numberOfItemsToChoose;
	}

	public void setNumberOfItemsToChoose(int numberOfItemsToChoose) {
		this.numberOfItemsToChoose = numberOfItemsToChoose;
	}

}
