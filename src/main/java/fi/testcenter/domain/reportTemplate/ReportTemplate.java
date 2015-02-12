package fi.testcenter.domain.reportTemplate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ReportTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column
	@Temporal(TemporalType.DATE)
	private Date templateCreatedDate;

	private String templateName;

	private boolean current;

	public ReportTemplate() {
		this.templateCreatedDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTemplateCreatedDate() {
		return templateCreatedDate;
	}

	public void setTemplateCreatedDate(Date templateCreatedDate) {
		this.templateCreatedDate = templateCreatedDate;
	}

}
