package fi.testcenter.domain;

public class TextfieldQuestion extends Question {

	private String question;
	private String answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String toString() {
		return new String("Kysymys: " + this.question + " - vastaus: "
				+ this.answer);
	}
}
