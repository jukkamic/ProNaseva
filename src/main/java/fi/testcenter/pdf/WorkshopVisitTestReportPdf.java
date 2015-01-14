package fi.testcenter.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;
import fi.testcenter.domain.report.WorkshopVisitReport;

@Component
public class WorkshopVisitTestReportPdf {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	@Autowired
	HeaderHelper headerHelper;

	@Autowired
	BackgroundImageHelper backgroundImageHelper;

	@Autowired
	PdfUtilityClass pdf;

	@Autowired
	ApplicationContext context;

	WorkshopVisitReport report;

	public ByteArrayOutputStream generateReportPdf(WorkshopVisitReport report)
			throws IOException, DocumentException {

		this.report = report;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document doc = new Document(PageSize.A4, 50, 30, 120, 90);
		PdfWriter writer = PdfWriter.getInstance(doc, baos);

		writer.setPageEvent(backgroundImageHelper);

		headerHelper.setReport(report);
		headerHelper.setReportPartTitle("Tulostiivistelmä");
		writer.setPageEvent(headerHelper);
		doc.open();
		doc.add(new Chunk(Chunk.NEWLINE));

		doc.add(getFrontPage());

		doc.newPage();
		doc.add(getSummaryPage());

		if (report.getReportParts() != null
				&& report.getReportParts().size() > 0) {
			headerHelper.setReportPartTitle(report.getReportParts().get(0)
					.getReportTemplatePart().getTitle());
		}

		doc.newPage();

		for (ReportPart part : report.getReportParts()) {

			doc.add(pdf.getPartTitle(part.getReportTemplatePart().getTitle()));

			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				doc.add(pdf.getGroupTitle(group.getQuestionGroupOrderNumber(),
						group.getReportTemplateQuestionGroup().getTitle()));

				for (Answer answer : group.getAnswers()) {
					if (!answer.isRemoveAnswerFromReport()) {

						if (answer instanceof MultipleChoiceAnswer)
							doc.add(pdf
									.getMultipleChoiceAnswerParagraph((MultipleChoiceAnswer) answer));
						if (answer instanceof TextAnswer)
							doc.add(pdf
									.getTextAnswerParagraph((TextAnswer) answer));
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
				if (part.getReportQuestionGroups().indexOf(group) == part
						.getReportQuestionGroups().size() - 1
						&& report.getReportParts().size() > (report
								.getReportParts().indexOf(part) + 1))
					headerHelper.setReportPartTitle(report.getReportParts()
							.get(report.getReportParts().indexOf(part) + 1)
							.getReportTemplatePart().getTitle());

				doc.newPage();

			}

		}

		doc.close();
		return baos;

	}

	public Paragraph getFrontPage() {
		Paragraph kansilehti = new Paragraph();
		kansilehti.setIndentationLeft(90);

		Font font = new Font(pdf.BASE_FONT, 24, Font.BOLD);
		Paragraph p = new Paragraph(new Chunk(report.getImporter().getName(),
				font));
		p.setSpacingBefore(40);
		kansilehti.add(p);

		p = new Paragraph(new Chunk("Korjaamotestiraportti", font));
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

		char overallSmiley = ' ';

		if (report.getOverallResultSmiley() != null) {
			if (report.getOverallResultSmiley().equals(String.valueOf("SMILE")))
				overallSmiley = (char) 0x4A;
			if (report.getOverallResultSmiley().equals(
					String.valueOf("NEUTRAL")))
				overallSmiley = (char) 0x4B;
			if (report.getOverallResultSmiley().equals(String.valueOf("FROWN")))
				overallSmiley = (char) 0x4C;

			Font smileyFont = new Font(pdf.WINGDINGS_BASE_FONT, 60);
			p = new Paragraph(new Chunk("Yleisarvosana:    ", font));
			Chunk smileyChunk = new Chunk(overallSmiley, smileyFont);
			smileyChunk.setTextRise(-16);
			p.add(smileyChunk);

			p.setSpacingBefore(60);
			kansilehti.add(p);

		}
		return kansilehti;

	}

	public Paragraph getSummaryPage() {
		try {
			Font summarySmileys = new Font(pdf.WINGDINGS_BASE_FONT, 30);

			Paragraph para = new Paragraph();
			para.setIndentationLeft(90);
			para.setIndentationRight(30);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 235, 105, 105 });

			table.getDefaultCell().setBorder(0);

			para.add(new Chunk(Chunk.NEWLINE));
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setSpacingBefore(30);

			PdfPCell cell = new PdfPCell(new Paragraph(new Chunk(
					"Tulosten yhteenveto", pdf.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Pisteet", pdf.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Arvosana", pdf.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			for (ReportPart part : report.getReportParts()) {
				int groupsTotal = 0;
				int groupsMax = 0;
				for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
					if (group.getReportTemplateQuestionGroup()
							.isShowInReportSummary()) {
						cell = new PdfPCell(new Paragraph(new Chunk(group
								.getReportTemplateQuestionGroup().getTitle(),
								pdf.DEFAULT_FONT)));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(0);
						table.addCell(cell);

						if (group.getMaxScore() > 0)
							cell = new PdfPCell(new Paragraph(new Chunk(
									group.getScore() + " / "
											+ group.getMaxScore(),
									pdf.DEFAULT_FONT)));
						else
							cell = new PdfPCell(new Paragraph(new Chunk("--",
									pdf.DEFAULT_FONT)));

						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(0);
						table.addCell(cell);
						if (group.getScoreSmiley() != null) {
							char smiley = ' ';

							if (group.getScoreSmiley().equals(
									String.valueOf("SMILE")))
								smiley = (char) 0x4A;
							if (group.getScoreSmiley().equals(
									String.valueOf("NEUTRAL")))
								smiley = (char) 0x4B;
							if (group.getScoreSmiley().equals(
									String.valueOf("FROWN")))
								smiley = (char) 0x4C;

							cell = new PdfPCell(new Paragraph(new Chunk(smiley,
									summarySmileys)));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setBorder(0);
							table.addCell(cell);

						}

						else {
							cell = new PdfPCell();
							cell.setBorder(0);
							table.addCell(cell);
						}
						groupsTotal += group.getScore();
						groupsMax += group.getMaxScore();
					}

				}

				if (groupsMax > 0) {
					cell = new PdfPCell(new Paragraph(new Chunk("Yhteensä: ",
							pdf.DEFAULT_FONT)));

					cell.setPaddingTop(15);
					cell.setPaddingBottom(15);
					cell.setBorder(0);

					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph(new Chunk(groupsTotal
							+ " / " + groupsMax, pdf.DEFAULT_FONT)));
					cell.setPaddingTop(15);
					cell.setPaddingBottom(15);
					cell.setBorder(0);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);

					table.addCell(cell);
					table.completeRow();
				}

			}

			for (ReportPart part : report.getReportParts()) {

				if (part.getReportTemplatePart()
						.isShowScoreInReportHighlights()) {
					cell = new PdfPCell(new Paragraph(new Chunk(part
							.getReportTemplatePart().getTitle(),
							pdf.DEFAULT_FONT)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(0);
					table.addCell(cell);

					if (part.getMaxScore() > 0)
						cell = new PdfPCell(new Paragraph(new Chunk(
								String.valueOf(part.getScorePercentage())
										+ " %", pdf.DEFAULT_FONT)));
					else
						cell = new PdfPCell(new Paragraph(new Chunk("--",
								pdf.DEFAULT_FONT)));

					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(0);
					table.addCell(cell);

					if (part.getScoreSmiley() != null
							&& part.getScoreSmiley() != "") {
						char smiley = ' ';

						if (part.getScoreSmiley().equals(
								String.valueOf("SMILE")))
							smiley = (char) 0x4A;
						if (part.getScoreSmiley().equals(
								String.valueOf("NEUTRAL")))
							smiley = (char) 0x4B;
						if (part.getScoreSmiley().equals(
								String.valueOf("FROWN")))
							smiley = (char) 0x4C;

						cell = new PdfPCell(new Paragraph(new Chunk(smiley,
								summarySmileys)));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(0);
						table.addCell(cell);

					} else {
						cell = new PdfPCell();
						cell.setBorder(0);
						table.addCell(cell);
					}
				}
			}

			cell = new PdfPCell(
					new Paragraph(new Chunk("Yhteensä: ", pdf.BOLD)));
			cell.setPaddingTop(15);
			cell.setPaddingBottom(15);
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk(String.valueOf(report
					.getTotalScorePercentage()) + " %", pdf.BOLD)));
			cell.setPaddingTop(15);
			cell.setPaddingBottom(15);
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(cell);
			table.completeRow();

			para.add(table);

			return para;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
