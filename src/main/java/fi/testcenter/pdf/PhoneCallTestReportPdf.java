package fi.testcenter.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.DateAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.report.PhoneCallTestReport;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Component
public class PhoneCallTestReportPdf {

	@Autowired
	PdfUtilityClass pdf;

	@Autowired
	HeaderHelper headerHelper;

	@Autowired
	BackgroundImageHelper backgroundImageHelper;

	PhoneCallTestReport report;

	public ByteArrayOutputStream generateReportPdf(PhoneCallTestReport report)
			throws IOException, DocumentException {

		this.report = report;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document doc = new Document(PageSize.A4, 50, 30, 120, 90);
		PdfWriter writer = PdfWriter.getInstance(doc, baos);

		writer.setPageEvent(backgroundImageHelper);

		headerHelper.setReport(report);

		headerHelper.setReportPartTitle(report.getReportTemplate()
				.getTemplateName());
		writer.setPageEvent(headerHelper);
		doc.open();

		doc.add(new Chunk(Chunk.NEWLINE));
		doc.add(getFrontPage());
		doc.newPage();

		for (ReportQuestionGroup group : report.getReportQuestionGroups()) {
			doc.add(pdf.getGroupTitle(group.getQuestionGroupOrderNumber(),
					group.getReportTemplateQuestionGroup().getTitle()));

			for (Answer answer : group.getAnswers()) {
				if (!answer.isRemoveAnswerFromReport()) {

					if (answer instanceof MultipleChoiceAnswer)
						doc.add(pdf
								.getMultipleChoiceAnswerParagraph((MultipleChoiceAnswer) answer));
					if (answer instanceof TextAnswer)
						doc.add(pdf.getTextAnswerParagraph((TextAnswer) answer));
					if (answer instanceof CostListingAnswer)
						doc.add(pdf
								.getCostListingParagraph((CostListingAnswer) answer));
					if (answer instanceof ImportantPointsAnswer)
						doc.add(pdf
								.getImportantPointsParagraph((ImportantPointsAnswer) answer));
					if (answer instanceof PointsAnswer)
						doc.add(pdf
								.getPointsAnswerParagraph((PointsAnswer) answer));
					if (answer instanceof DateAnswer)
						doc.add(pdf.getDateAnswerParagraph((DateAnswer) answer));
				}
			}

			if (group.getScore() != -1) {
				Paragraph totalScore = new Paragraph(new Chunk("Yhteensä: "
						+ group.getScore() + " / " + group.getMaxScore(),
						pdf.BOLD));
				totalScore.setAlignment(Element.ALIGN_RIGHT);
				totalScore.setSpacingBefore(10);
				doc.add(totalScore);

			}

			doc.add(new Paragraph(Chunk.NEWLINE));

			if (writer.getVerticalPosition(true) < 150)
				doc.newPage();
		}

		doc.add(new Paragraph(Chunk.NEWLINE));

		doc.add(new Paragraph(new Chunk("Raportin pisteet yhteensä: "
				+ report.getTotalScore() + " / " + report.getTotalMaxScore(),
				pdf.GROUP_TITLE_FONT)));

		doc.close();
		return baos;

	}

	private Paragraph getFrontPage() {
		Paragraph kansilehti = new Paragraph();
		kansilehti.setIndentationLeft(90);

		Font font = new Font(pdf.BASE_FONT, 24, Font.BOLD);
		Paragraph p = new Paragraph(new Chunk(report.getImporter().getName(),
				font));
		p.setSpacingBefore(40);
		kansilehti.add(p);

		p = new Paragraph(new Chunk("Puhelintestiraportti", font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		font = new Font(pdf.BASE_FONT, 18, Font.BOLD);
		String workshop = report.getWorkshop().getName();
		if (report.getWorkshop().getCity() != null
				&& report.getWorkshop().getCity().length() > 0)
			workshop += ", " + report.getWorkshop().getCity();
		p = new Paragraph(new Chunk(workshop, font));
		p.setSpacingBefore(60);
		kansilehti.add(p);

		p = new Paragraph(new Chunk(report.getTestDateString(), font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		p = new Paragraph(new Chunk("Pisteet : " + report.getTotalScore()
				+ " / " + report.getTotalMaxScore(), font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		return kansilehti;

	}
}
