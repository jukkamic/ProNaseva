package fi.testcenter.domain;

import java.util.ArrayList;
import java.util.List;

public class EditReportAnswers {

	private List<Answer> formTextAnswers = new ArrayList<Answer>();

	public List<Answer> getFormTextAnswers() {
		return formTextAnswers;
	}

	public void setFormTextAnswers(List<Answer> formTextAnswers) {
		this.formTextAnswers = formTextAnswers;
	}

}
