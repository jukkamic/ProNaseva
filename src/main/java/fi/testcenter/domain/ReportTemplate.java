package fi.testcenter.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ReportTemplate {

	@Id
	private Long id;

	private List<Long> questionGroupIds;

	public ReportTemplate() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getQuestionGroupIds() {
		return questionGroupIds;
	}

	public void setQuestionGroupIds(List<Long> questionGroupIds) {
		this.questionGroupIds = questionGroupIds;
	}

}
