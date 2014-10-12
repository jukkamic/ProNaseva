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
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "QUESTION_SUBQUESTION", joinColumns = @JoinColumn(name = "QUESTION_ID"), inverseJoinColumns = @JoinColumn(name = "SUBQUESTION_ID"))
	@OrderColumn(name = "INDEX")
	List<SubQuestion> subQuestions = new ArrayList<SubQuestion>();

	public Question() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SubQuestion> getSubQuestions() {
		return subQuestions;
	}

	public void setSubQuestions(List<SubQuestion> subQuestions) {
		this.subQuestions = subQuestions;
	}

}
