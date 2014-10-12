package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class ReportPart {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private String title;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "REPORTPART_QUESTIONGROUP", joinColumns = @JoinColumn(name = "REPORTPART_ID"), inverseJoinColumns = @JoinColumn(name = "QUESTIONGROUP_ID"))
	@OrderColumn(name = "INDEX")
	private List<QuestionGroup> questionGroups = new ArrayList<QuestionGroup>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionGroup> getQuestionGroups() {
		return questionGroups;
	}

	public void setQuestionGroups(List<QuestionGroup> questionGroups) {
		this.questionGroups = questionGroups;
	}

}