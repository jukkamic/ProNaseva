package fi.testcenter.domain.reportSummary;

import java.util.ArrayList;
import java.util.List;

import fi.testcenter.domain.Importer;

public class ReportSummary {

	List<ReportPartSummary> reportPartSummaries = new ArrayList<ReportPartSummary>();
	Importer importer;
	List<ReportInfo> reportInfo = new ArrayList<ReportInfo>();

	public List<ReportPartSummary> getReportPartSummaries() {
		return reportPartSummaries;
	}

	public void setReportPartSummaries(
			List<ReportPartSummary> reportPartSummaries) {
		this.reportPartSummaries = reportPartSummaries;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public List<ReportInfo> getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(List<ReportInfo> reportInfo) {
		this.reportInfo = reportInfo;
	}

}
