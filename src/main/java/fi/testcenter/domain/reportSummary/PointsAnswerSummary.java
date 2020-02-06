package fi.testcenter.domain.reportSummary;

public class PointsAnswerSummary extends AnswerSummary {

	int timesAskedInReports = 0;
	int[] answerCountForPoints;
	double averageScore = -1;
	int maxScore = 0;

	public int getTimesAskedInReports() {
		return timesAskedInReports;
	}

	public void setTimesAskedInReports(int timesAskedInReports) {
		this.timesAskedInReports = timesAskedInReports;
	}

	public int[] getAnswerCountForPoints() {
		return answerCountForPoints;
	}

	public void setAnswerCountForPoints(int[] answerCountForPoints) {
		this.answerCountForPoints = answerCountForPoints;
	}

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

}
