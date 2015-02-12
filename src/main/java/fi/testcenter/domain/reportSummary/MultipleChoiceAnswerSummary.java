package fi.testcenter.domain.reportSummary;

import java.util.Map;

import fi.testcenter.domain.question.MultipleChoiceOption;

public class MultipleChoiceAnswerSummary extends AnswerSummary {

	double averageScore = -1;
	int maxScore;
	Map<MultipleChoiceOption, Integer> chosenOptionsCount;

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public Map<MultipleChoiceOption, Integer> getChosenOptionsCount() {
		return chosenOptionsCount;
	}

	public void setChosenOptionsCount(
			Map<MultipleChoiceOption, Integer> chosenOptionsCount) {
		this.chosenOptionsCount = chosenOptionsCount;
	}

}
