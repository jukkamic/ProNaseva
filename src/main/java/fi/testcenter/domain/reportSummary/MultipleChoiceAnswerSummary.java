package fi.testcenter.domain.reportSummary;

import java.util.Map;

public class MultipleChoiceAnswerSummary extends AnswerSummary {

	double averageScore;
	Map<String, Integer> chosenOptionsCount;

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public Map<String, Integer> getChosenOptionsCount() {
		return chosenOptionsCount;
	}

	public void setChosenOptionsCount(Map<String, Integer> chosenOptionsCount) {
		this.chosenOptionsCount = chosenOptionsCount;
	}

}
