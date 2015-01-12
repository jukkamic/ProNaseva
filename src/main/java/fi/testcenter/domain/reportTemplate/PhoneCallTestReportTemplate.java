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
public class PhoneCallTestReportTemplate extends ReportTemplate {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PHONECALLREPORT_JOIN")
	@OrderColumn(name = "CALLTESTREPORTTEPLATE_ORDER")
	List<ReportTemplateQuestionGroup> questionGroups = new ArrayList<ReportTemplateQuestionGroup>();

	public PhoneCallTestReportTemplate() {
	}

	public List<ReportTemplateQuestionGroup> getQuestionGroups() {
		return questionGroups;
	}

	public void setQuestionGroups(
			List<ReportTemplateQuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}

}
