package fi.testcenter.domain.reportSummary;

import java.util.ArrayList;
import java.util.List;

public class ReportSummary {

	List<ReportPartSummary> reportPartSummaries = new ArrayList<ReportPartSummary>();

	public List<ReportPartSummary> getReportPartSummaries() {
		return reportPartSummaries;
	}

	public void setReportPartSummaries(
			List<ReportPartSummary> reportPartSummaries) {
		this.reportPartSummaries = reportPartSummaries;
	}

}
