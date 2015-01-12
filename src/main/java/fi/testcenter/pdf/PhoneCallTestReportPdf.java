package fi.testcenter.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
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

	PhoneCallTestReport report;

	public ByteArrayOutputStream generateReportPdf(PhoneCallTestReport report,
			Image backgroundImage) throws IOException, DocumentException {

		this.report = report;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document doc = new Document(PageSize.A4, 50, 30, 120, 90);
		PdfWriter writer = PdfWriter.getInstance(doc, baos);

		writer.setPageEvent(new BackgroundImageHelper(backgroundImage));

		headerHelper.setReport(report);

		headerHelper.setReportPartTitle(report.getReportTemplate()
				.getTemplateName());
		writer.setPageEvent(headerHelper);
		doc.open();
		doc.add(new Chunk(Chunk.NEWLINE));

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
			doc.add(new Paragraph(Chunk.NEWLINE));

		}

		doc.add(new Paragraph(Chunk.NEWLINE));

		doc.add(new Paragraph(new Chunk("Raportin pisteet yhteensä: "
				+ report.getTotalScore() + " / " + report.getTotalMaxScore(),
				pdf.GROUP_TITLE_FONT)));

		doc.close();
		return baos;

	}
}
