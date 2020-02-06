package fi.testcenter.domain.reportTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class WorkshopVisitReportTemplate extends ReportTemplate {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "WS_VISITREPORTTEMPLATE_JOIN")
	@OrderColumn(name = "WS_VISITREPORTTEMPLATE_ORDER")
	private List<ReportTemplatePart> reportParts = new ArrayList<ReportTemplatePart>();

	public List<ReportTemplatePart> getReportParts() {
		return reportParts;
	}

	public void setReportParts(List<ReportTemplatePart> reportParts) {
		this.reportParts = reportParts;
	}

}
