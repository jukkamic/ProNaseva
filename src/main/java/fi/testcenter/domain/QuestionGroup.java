package fi.testcenter.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class QuestionGroup {

	@Id
	@Column(name = "QGROUP_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	private String title;

	@OneToMany(mappedBy = "questionGroup", cascade = CascadeType.ALL)
	private List<Question> questions;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
