package fi.testcenter.domain.answer;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class CostListingAnswer extends Answer {

	List<Float> answers;
	Float total;

	public List<Float> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Float> answers) {
		this.answers = answers;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

}
