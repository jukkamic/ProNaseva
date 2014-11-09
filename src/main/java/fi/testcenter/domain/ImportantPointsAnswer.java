package fi.testcenter.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class ImportantPointsAnswer extends Answer {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderColumn(name = "ORDERINDEX")
	List<ImpPointsAnswerItem> answerItems;

	public List<ImpPointsAnswerItem> getAnswerItems() {
		return answerItems;
	}

	public void setAnswerItems(List<ImpPointsAnswerItem> answerItems) {
		this.answerItems = answerItems;
	}

}
