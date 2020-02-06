package fi.testcenter.domain.reportSummary;

import fi.testcenter.domain.Workshop;

public class ReportInfo {

	Workshop workshop;
	int totalScorePercentage = -1;

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public int getTotalScorePercentage() {
		return totalScorePercentage;
	}

	public void setTotalScorePercentage(int totalScorePercentage) {
		this.totalScorePercentage = totalScorePercentage;
	}

}
