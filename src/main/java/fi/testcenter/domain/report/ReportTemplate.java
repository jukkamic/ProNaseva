package fi.testcenter.domain.report;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class ReportTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "REPORTTEMPLATE_ID")
	@OrderColumn(name = "REPORTPARTORDER")
	private List<ReportTemplatePart> reportParts = new ArrayList<ReportTemplatePart>();

	private String templateName;
	private boolean current;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ReportTemplatePart> getReportParts() {
		return reportParts;
	}

	public void setReportParts(List<ReportTemplatePart> reportParts) {
		this.reportParts = reportParts;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

}
