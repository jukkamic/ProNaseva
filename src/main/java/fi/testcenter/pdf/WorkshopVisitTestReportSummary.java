package fi.testcenter.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

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

import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.reportSummary.AnswerSummary;
import fi.testcenter.domain.reportSummary.MultipleChoiceAnswerSummary;
import fi.testcenter.domain.reportSummary.PointsAnswerSummary;
import fi.testcenter.domain.reportSummary.QuestionGroupSummary;
import fi.testcenter.domain.reportSummary.ReportInfo;
import fi.testcenter.domain.reportSummary.ReportPartSummary;
import fi.testcenter.domain.reportSummary.ReportSummary;

@Component
public class WorkshopVisitTestReportSummary {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	@Autowired
	ReportSummaryHeaderHelper headerHelper;

	@Autowired
	BackgroundImageHelper backgroundImageHelper;

	@Autowired
	PdfUtilityClass pdf;

	@Autowired
	ApplicationContext context;

	ReportSummary summary;

	public ByteArrayOutputStream generateWorkshopVisitTestReportSummary(
			ReportSummary summary) throws IOException, DocumentException {
		this.summary = summary;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document doc = new Document(PageSize.A4, 50, 30, 120, 90);
		PdfWriter writer = PdfWriter.getInstance(doc, baos);

		writer.setPageEvent(backgroundImageHelper);

		headerHelper.setReportSummary(summary);

		writer.setPageEvent(headerHelper);
		doc.open();
		doc.add(new Chunk(Chunk.NEWLINE));

		doc.add(getFrontPage());
		doc.newPage();

		doc.add(getTestedWorkshopsListing());
		doc.add(new Chunk(Chunk.NEWLINE));

		for (ReportPartSummary part : summary.getReportPartSummaries()) {
			if (part.isShowInSummary()) {
				Paragraph partTitle = new Paragraph();
				partTitle.setSpacingBefore(20);

				partTitle.add(new Chunk(
						part.getReportTemplatePart().getTitle(),
						pdf.GROUP_TITLE_FONT));
				if (part.getAverageScorePercengage() != -1) {
					partTitle.add(new Chunk(" - "
							+ String.valueOf(part.getAverageScorePercengage())
							+ " %", pdf.REPORT_PART_TITLE_FONT));
				}
				doc.add(partTitle);

				Paragraph groupSummaryPara = new Paragraph();
				groupSummaryPara.setIndentationLeft(20);
				for (QuestionGroupSummary group : part
						.getQuestionGroupSummaries()) {

					if (group.isShowInSummary()) {
						Paragraph groupTitlePara = new Paragraph(new Chunk(
								group.getReportTemplateQuestionGroup()
										.getTitle(), pdf.GROUP_TITLE_FONT));
						if (group.getAverageScorePercengage() != -1) {
							groupTitlePara.add(new Chunk(" - "
									+ group.getAverageScorePercengage() + " %",
									pdf.GROUP_TITLE_FONT));
						}
						groupTitlePara.setSpacingBefore(20);
						groupTitlePara.setSpacingAfter(20);
						groupSummaryPara.add(groupTitlePara);

						Paragraph answerSummaryPara = new Paragraph();
						answerSummaryPara.setIndentationLeft(20);
						for (AnswerSummary answer : group.getAnswerSummaries()) {

							if (answer instanceof MultipleChoiceAnswerSummary)
								answerSummaryPara
										.add(getMultipleChoiceAnswerSummaryParagraph((MultipleChoiceAnswerSummary) answer));
							if (answer instanceof PointsAnswerSummary)
								answerSummaryPara
										.add(getPointsAnswerSummaryParagraph((PointsAnswerSummary) answer));

						}
						groupSummaryPara.add(answerSummaryPara);
					}

				}
				doc.add(groupSummaryPara);
			}
		}

		doc.close();

		return baos;
	}

	private Paragraph getFrontPage() {
		Paragraph kansilehti = new Paragraph();
		kansilehti.setIndentationLeft(90);

		Font font = new Font(pdf.BASE_FONT, 24, Font.BOLD);

		Paragraph p = new Paragraph(new Chunk(summary.getImporter().getName(),
				font));
		p.setSpacingBefore(40);
		kansilehti.add(p);

		p = new Paragraph(new Chunk("Raporttiyhteenveto", font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		return kansilehti;

	}

	private Paragraph getTestedWorkshopsListing() throws DocumentException {
		Paragraph listingParagraph = new Paragraph();
		Paragraph para = new Paragraph(new Chunk("Testatut korjaamot:",
				pdf.REPORT_PART_TITLE_FONT));

		para.setSpacingAfter(20);
		para.setSpacingBefore(5);

		listingParagraph.add(para);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(70);
		table.setWidths(new float[] { 2, 1 });

		table.getDefaultCell().setBorder(0);

		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(30);

		PdfPCell cell = new PdfPCell(new Paragraph(new Chunk("Korjaamo",
				pdf.GROUP_TITLE_FONT)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setBorder(Rectangle.BOTTOM);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(new Chunk("Kokonaistulos",
				pdf.GROUP_TITLE_FONT)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingBottom(5);
		cell.setBorder(Rectangle.BOTTOM);
		table.addCell(cell);

		for (ReportInfo info : summary.getReportInfo()) {

			para = new Paragraph(new Chunk(info.getWorkshop().getName()));
			if (info.getWorkshop().getCity() != null
					&& !info.getWorkshop().getCity().equals("")) {
				cell.addElement(new Chunk(", " + info.getWorkshop().getCity()));

			}
			cell = new PdfPCell(para);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingTop(5);
			cell.setPaddingBottom(5);
			cell.setBorder(0);

			table.addCell(cell);
			if (info.getTotalScorePercentage() != -1) {

				cell = new PdfPCell(new Paragraph(
						new Chunk(String.valueOf((info
								.getTotalScorePercentage())) + " %")));
				cell.setBorder(0);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(5);
				cell.setPaddingBottom(5);
				table.addCell(cell);
			} else
				table.completeRow();
		}
		listingParagraph.add(table);
		return listingParagraph;
	}

	private Paragraph getMultipleChoiceAnswerSummaryParagraph(
			MultipleChoiceAnswerSummary summary) throws DocumentException {
		Paragraph para = new Paragraph();
		PdfPTable table = new PdfPTable(2);

		table.setWidthPercentage(100);
		table.setWidths(new float[] { 260, 215 });
		table.getDefaultCell().setBorder(0);

		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(5);

		PdfPCell cell = new PdfPCell(new Paragraph(new Chunk(summary
				.getQuestion().getQuestion(), pdf.ITALIC)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingRight(10);
		cell.setBorder(0);
		table.addCell(cell);

		if (summary.getMaxScore() > 0 && summary.getAverageScore() != -1) {
			cell = new PdfPCell(new Paragraph(new Chunk(
					"Keskiarvo: " + summary.getAverageScore() + " / "
							+ summary.getMaxScore(), pdf.ITALIC)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setBorder(0);
			table.addCell(cell);
		}

		else {
			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);
		}

		para.add(table);
		Paragraph optionsPara = new Paragraph();
		optionsPara.setIndentationLeft(20);

		table = new PdfPTable(3);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 240, 107, 107 });

		for (Map.Entry<MultipleChoiceOption, Integer> entry : summary
				.getChosenOptionsCount().entrySet()) {
			cell = new PdfPCell(new Paragraph(new Chunk(entry.getKey()
					.getMultipleChoiceOption())));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingRight(10);
			cell.setBorder(0);
			table.addCell(cell);

			if (entry.getKey().getPoints() != -1) {
				cell = new PdfPCell(new Paragraph(new Chunk(entry.getKey()
						.getPoints() + " pistettä")));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingBottom(5);
				cell.setPaddingRight(10);
				cell.setBorder(0);
				table.addCell(cell);

			} else {
				cell = new PdfPCell();
				cell.setBorder(0);
				table.addCell(cell);
			}

			cell = new PdfPCell(new Paragraph(new Chunk(String.valueOf(entry
					.getValue()) + " kpl")));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setBorder(0);
			table.addCell(cell);

		}

		optionsPara.add(table);
		para.add(optionsPara);
		return para;
	}

	private Paragraph getPointsAnswerSummaryParagraph(
			PointsAnswerSummary summary) throws DocumentException {
		Paragraph para = new Paragraph();
		PdfPTable table = new PdfPTable(3);

		table.setWidthPercentage(100);
		table.setWidths(new float[] { 260, 60, 154 });
		table.getDefaultCell().setBorder(0);

		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(5);

		PdfPCell cell = new PdfPCell(new Paragraph(new Chunk(summary
				.getQuestion().getQuestion())));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingRight(10);
		cell.setBorder(0);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(new Chunk(String.valueOf(summary
				.getTimesAskedInReports()) + " kpl")));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPaddingBottom(5);
		cell.setPaddingRight(10);
		cell.setBorder(0);
		table.addCell(cell);

		if (summary.getTimesAskedInReports() > 0) {
			cell = new PdfPCell(new Paragraph(new Chunk("Keskiarvo: "
					+ String.valueOf(summary.getAverageScore()) + " / "
					+ String.valueOf(summary.getMaxScore()))));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingBottom(5);
			cell.setPaddingRight(10);
			cell.setBorder(0);
			table.addCell(cell);

		} else {
			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);
		}
		para.add(table);
		return para;
	}

}
