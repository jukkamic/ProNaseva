package fi.testcenter.domain;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class ImportantPointsQuestion extends Question {

	String question;
	List<String> questionItems;
	int maxScoreForQuestionItem;
	int numberOfItemsToChoose;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

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
