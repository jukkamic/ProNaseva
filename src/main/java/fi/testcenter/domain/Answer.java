package fi.testcenter.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	private boolean showScore;
	private int score;
	private int maxScore;

	public Answer() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isShowScore() {
		return showScore;
	}

	public void setShowScore(boolean showScore) {
		this.showScore = showScore;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

}
