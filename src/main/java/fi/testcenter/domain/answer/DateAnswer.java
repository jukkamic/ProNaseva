package fi.testcenter.domain.answer;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fi.testcenter.domain.question.Question;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Entity
public class DateAnswer extends Answer {

	@Temporal(TemporalType.DATE)
	Date date;

	String dateString = new String();

	public DateAnswer() {
	}

	public DateAnswer(Question question) {
		super(question);
	}

	public DateAnswer(Question question, int answerOrderNumber) {
		super(question, answerOrderNumber);
	}

	public DateAnswer(ReportQuestionGroup reportQuestionGroup,
			Question question, int answerOrderNumber) {
		super(reportQuestionGroup, question, answerOrderNumber);
	}

	public void setDateString(String date) {

		this.dateString = date;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

		try {
			this.date = DATE_FORMAT.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDateString() {
		return dateString;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
		dateString = DATE_FORMAT.format(date);
	}

}
