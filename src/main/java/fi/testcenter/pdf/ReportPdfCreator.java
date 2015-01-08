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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.answer.Answer;
import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.ImportantPointsItem;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.PointsQuestion;
import fi.testcenter.domain.report.Report;
import fi.testcenter.domain.report.ReportPart;
import fi.testcenter.domain.report.ReportQuestionGroup;

@Component
public class ReportPdfCreator {

	private BaseFont baseFont;
	private BaseFont wingdings;

	private Font reportPartTitleFont;
	private Font groupTitleFont;
	private Font defaultTextFont;
	private Font boldFont;
	private Font italicFont;

	private Font wingdingFont;

	final int SUBQUESTIONINDENT = 25;

	@Autowired
	HeaderHelper headerHelper;

	Report report;

	public ReportPdfCreator() {
		try {
			this.baseFont = BaseFont.createFont("c:/windows/fonts/arial.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			this.wingdings = BaseFont.createFont(
					"c:/windows/fonts/wingding.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			this.reportPartTitleFont = new Font(baseFont, 20, Font.BOLD);

			this.groupTitleFont = new Font(baseFont, 16, Font.BOLD);
			this.defaultTextFont = new Font(baseFont, 11);
			this.boldFont = new Font(baseFont, 11, Font.BOLD);
			this.italicFont = new Font(baseFont, 11, Font.ITALIC);
			this.wingdingFont = new Font(wingdings, 12);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ByteArrayOutputStream generateReportPdf(Report report)
			throws IOException, DocumentException {

		this.report = report;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document doc = new Document(PageSize.A4, 50, 30, 120, 90);
		PdfWriter writer = PdfWriter.getInstance(doc, baos);

		writer.setPageEvent(new BackgroundImageHelper());

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

			doc.add(getPartTitle(part.getReportTemplatePart().getTitle()));

			for (ReportQuestionGroup group : part.getReportQuestionGroups()) {
				doc.add(getGroupTitle(group.getQuestionGroupOrderNumber(),
						group.getReportTemplateQuestionGroup().getTitle()));
				for (Answer answer : group.getAnswers()) {
					if (!answer.isRemoveAnswerFromReport()) {
						if (answer instanceof MultipleChoiceAnswer)
							doc.add(getMultipleChoiceAnswerParagraph((MultipleChoiceAnswer) answer));
						if (answer instanceof TextAnswer)
							doc.add(getTextAnswerParagraph((TextAnswer) answer));
						if (answer instanceof CostListingAnswer)
							doc.add(getCostListingParagraph((CostListingAnswer) answer));
						if (answer instanceof ImportantPointsAnswer)
							doc.add(getImportantPointsParagraph((ImportantPointsAnswer) answer));
						if (answer instanceof PointsAnswer)
							doc.add(getPointsAnswerParagraph((PointsAnswer) answer));
					}
				}

				if (group.getScore() != -1) {
					Paragraph totalScore = new Paragraph(new Chunk("Yhteensä: "
							+ group.getScore() + " / " + group.getMaxScore(),
							boldFont));
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

		Font font = new Font(baseFont, 24, Font.BOLD);
		Paragraph p = new Paragraph(new Chunk(report.getImporter().getName(),
				font));
		p.setSpacingBefore(40);
		kansilehti.add(p);

		p = new Paragraph(new Chunk("Korjaamotestiraportti", font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		font = new Font(baseFont, 18, Font.BOLD);
		String workshop = report.getWorkshop().getName();
		if (report.getWorkshop().getCity() != null
				&& report.getWorkshop().getCity().length() > 0)
			workshop += ", " + report.getWorkshop().getCity();
		p = new Paragraph(new Chunk(workshop, font));
		p.setSpacingBefore(60);
		kansilehti.add(p);

		p = new Paragraph(new Chunk(report.getReportDate(), font));
		p.setSpacingBefore(15);
		kansilehti.add(p);

		char overallSmiley = ' ';

		if (report.getOverallResultSmiley().equals(String.valueOf("SMILE")))
			overallSmiley = (char) 0x4A;
		if (report.getOverallResultSmiley().equals(String.valueOf("NEUTRAL")))
			overallSmiley = (char) 0x4B;
		if (report.getOverallResultSmiley().equals(String.valueOf("FROWN")))
			overallSmiley = (char) 0x4C;

		Font smileyFont = new Font(wingdings, 60);
		p = new Paragraph(new Chunk("Yleisarvosana:    ", font));
		Chunk smileyChunk = new Chunk(overallSmiley, smileyFont);
		smileyChunk.setTextRise(-16);
		p.add(smileyChunk);
		p.setSpacingBefore(60);
		kansilehti.add(p);

		return kansilehti;

	}

	public Paragraph getSummaryPage() {
		try {
			Font summarySmileys = new Font(wingdings, 30);

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
					"Tulosten yhteenveto", boldFont)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Pisteet", boldFont)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.BOTTOM);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Arvosana", boldFont)));
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
								defaultTextFont)));
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorder(0);
						table.addCell(cell);

						if (group.getMaxScore() > 0)
							cell = new PdfPCell(new Paragraph(new Chunk(
									group.getScore() + " / "
											+ group.getMaxScore(),
									defaultTextFont)));
						else
							cell = new PdfPCell(new Paragraph(new Chunk("--",
									defaultTextFont)));

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
							boldFont)));

					cell.setPaddingTop(15);
					cell.setPaddingBottom(15);
					cell.setBorder(0);

					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);

					cell = new PdfPCell(new Paragraph(new Chunk(groupsTotal
							+ " / " + groupsMax, boldFont)));
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
							defaultTextFont)));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setBorder(0);
					table.addCell(cell);

					if (part.getMaxScore() > 0)
						cell = new PdfPCell(new Paragraph(new Chunk(
								String.valueOf(part.getScorePercentage())
										+ " %", defaultTextFont)));
					else
						cell = new PdfPCell(new Paragraph(new Chunk("--",
								defaultTextFont)));

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
					new Paragraph(new Chunk("Yhteensä: ", boldFont)));
			cell.setPaddingTop(15);
			cell.setPaddingBottom(15);
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk(String.valueOf(report
					.getTotalScorePercentage()) + " %", boldFont)));
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

	public Paragraph getPartTitle(String title) {

		Paragraph p = new Paragraph(title, reportPartTitleFont);
		p.setSpacingBefore(10);
		p.setSpacingAfter(10);
		p.setIndentationRight(50);

		return p;
	}

	public Paragraph getGroupTitle(int orderNumber, String title) {

		Chunk partNumber = new Chunk(String.valueOf(orderNumber) + ". ",
				groupTitleFont);

		Paragraph p = new Paragraph();

		float orderNumberWidth = partNumber.getWidthPoint();
		p.setIndentationLeft(orderNumberWidth);
		p.setFirstLineIndent(-orderNumberWidth);
		p.setSpacingBefore(10);
		p.setSpacingAfter(10);
		p.setIndentationRight(50);
		p.add(partNumber);
		p.add(new Chunk(title, groupTitleFont));
		return p;
	}

	public Paragraph getMultipleChoiceAnswerParagraph(
			MultipleChoiceAnswer answer) {

		try {

			PdfPTable table = new PdfPTable(4);
			table.setKeepTogether(true);

			Paragraph para = new Paragraph();

			String questionOrderNumber = String.valueOf(answer
					.getReportQuestionGroup().getQuestionGroupOrderNumber())
					+ "." + String.valueOf(answer.getAnswerOrderNumber()) + ".";

			float indentLeft = 20;

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				indentLeft = 40;
				questionOrderNumber += String.valueOf(answer
						.getSubquestionAnswerOrderNumber()) + ". ";
				System.out.println("question order number "
						+ questionOrderNumber);
			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, boldFont);
			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					boldFont));
			Paragraph p = new Paragraph(questionPhrase);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { orderNumberIndent, 20,
					485 - indentLeft - orderNumberIndent, 40 });
			table.setSpacingBefore(5);

			// Kysymysteksti

			PdfPCell cell = new PdfPCell();
			cell.setColspan(3);

			cell.setBorder(0);

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			p.setIndentationLeft(orderNumberIndent);
			p.setFirstLineIndent(-orderNumberIndent);

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				System.out.println("indentLeft " + indentLeft);
				System.out.println("questionOrderNumberIndent "
						+ orderNumberIndent);
			}
			cell.addElement(p);

			table.addCell(cell);

			// Pisteet

			if (answer.getScore() != -1) {
				Chunk scoreChunk = new Chunk(String.valueOf(answer.getScore()
						+ " / " + answer.getMaxScore()), boldFont);
				Phrase scorePhrase = new Phrase(scoreChunk);
				p = new Paragraph(scorePhrase);

				cell = new PdfPCell(p);

			} else
				cell = new PdfPCell();

			cell.setBorder(0);
			cell.setPaddingTop(6);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

			table.addCell(cell);

			// Monivalinnat

			Chunk checkbox_unchecked = new Chunk((char) 0x6F, wingdingFont);
			Chunk checkbox_checked = new Chunk((char) 0xFE, wingdingFont);
			int index = 0;
			for (MultipleChoiceOption option : ((MultipleChoiceQuestion) answer
					.getQuestion()).getOptionsList()) {

				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

				if (answer.getChosenOptionIndex() == index++) {

					cell = new PdfPCell(new Paragraph(checkbox_checked));
					cell.setBorder(0);
					cell.setPaddingTop(3);
					cell.setPaddingBottom(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Paragraph(checkbox_unchecked));
					cell.setBorder(0);
					cell.setPaddingTop(3);
					cell.setPaddingBottom(3);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell);
				}
				cell = new PdfPCell(new Paragraph(
						option.getMultipleChoiceOption(), defaultTextFont));
				cell.setPaddingTop(3);
				cell.setPaddingBottom(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

			}

			cell = new PdfPCell(new Paragraph());
			cell.setBorder(0);
			table.addCell(cell);

			table.addCell(cell);

			para.add(table);

			if (answer.getRemarks() != null && answer.getRemarks().length() > 0) {
				Paragraph remarksPara = new Paragraph();
				remarksPara.setKeepTogether(true);
				remarksPara.setIndentationLeft(orderNumberIndent + 20);
				remarksPara.setIndentationRight(70);

				remarksPara.add(new Chunk("Huomiot:", italicFont));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

				remarksPara = new Paragraph();
				remarksPara.setIndentationLeft(orderNumberIndent + 20);
				remarksPara.setIndentationRight(70);
				remarksPara.setKeepTogether(true);

				remarksPara
						.add(new Chunk(answer.getRemarks(), defaultTextFont));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

			}

			return para;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Paragraph getPointsAnswerParagraph(PointsAnswer answer) {
		try {
			Paragraph para = new Paragraph();

			PdfPTable table = new PdfPTable(2);

			String questionOrderNumber = String.valueOf(answer
					.getReportQuestionGroup().getQuestionGroupOrderNumber())
					+ "." + String.valueOf(answer.getAnswerOrderNumber()) + ".";

			float indentLeft = 20;

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				indentLeft = 40;
				questionOrderNumber += String.valueOf(answer
						.getSubquestionAnswerOrderNumber()) + ". ";
				System.out.println("question order number "
						+ questionOrderNumber);
			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, boldFont);

			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			table.setWidthPercentage(100);
			table.setWidths(new float[] {
					475 - (indentLeft + orderNumberIndent), 40 });
			table.setSpacingBefore(5);

			table.setKeepTogether(true);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					boldFont));
			Paragraph p = new Paragraph(questionPhrase);

			float indent = questionNumberChunk.getWidthPoint();
			p.setIndentationLeft(indent);
			p.setFirstLineIndent(-indent);

			PdfPCell cell = new PdfPCell();

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);

			cell.addElement(p);
			table.addCell(cell);

			if (answer.getGivenPoints() != -1) {
				cell = new PdfPCell(new Paragraph(new Chunk(
						String.valueOf(answer.getGivenPoints())
								+ " / "
								+ String.valueOf(((PointsQuestion) answer
										.getQuestion()).getMaxPoints()),
						boldFont)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			} else
				cell = new PdfPCell();

			cell.setBorder(0);
			cell.setPaddingTop(6);
			table.addCell(cell);

			para.add(table);

			if (answer.getRemarks() != null && answer.getRemarks().length() > 0) {
				Paragraph remarksPara = new Paragraph();
				remarksPara.setKeepTogether(true);
				remarksPara.setIndentationLeft(20 + indentLeft + indent);
				remarksPara.setIndentationRight(70);

				remarksPara.add(new Chunk("Huomiot:", italicFont));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

				remarksPara = new Paragraph();
				remarksPara.setIndentationLeft(20 + indentLeft + indent);
				remarksPara.setIndentationRight(70);
				remarksPara.setKeepTogether(true);

				remarksPara
						.add(new Chunk(answer.getRemarks(), defaultTextFont));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

			}

			return para;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Paragraph getTextAnswerParagraph(TextAnswer answer) {
		try {
			Paragraph para = new Paragraph();
			PdfPTable table = new PdfPTable(2);

			String questionOrderNumber = String.valueOf(answer
					.getReportQuestionGroup().getQuestionGroupOrderNumber())
					+ "." + String.valueOf(answer.getAnswerOrderNumber()) + ".";

			float indentLeft = 20;

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				indentLeft = 40;
				questionOrderNumber += String.valueOf(answer
						.getSubquestionAnswerOrderNumber()) + ". ";
				System.out.println("question order number "
						+ questionOrderNumber);
			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, boldFont);
			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 475 - (indentLeft), 40 });
			table.setSpacingBefore(5);

			table.setKeepTogether(true);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					boldFont));
			Paragraph p = new Paragraph(questionPhrase);

			float indent = questionNumberChunk.getWidthPoint();
			p.setIndentationLeft(indent);
			p.setFirstLineIndent(-indent);

			para.add(p);

			Paragraph answerPara = new Paragraph(new Chunk(answer.getAnswer(),
					defaultTextFont));
			answerPara.setIndentationLeft(orderNumberIndent);
			para.add(answerPara);

			return para;

		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Paragraph getImportantPointsParagraph(ImportantPointsAnswer answer) {
		Font font = new Font(baseFont, 11);

		try {
			PdfPTable table = new PdfPTable(4);
			Paragraph para = new Paragraph();

			String questionOrderNumber = String.valueOf(answer
					.getReportQuestionGroup().getQuestionGroupOrderNumber())
					+ "." + String.valueOf(answer.getAnswerOrderNumber()) + ".";

			float indentLeft = 20;

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				indentLeft = 40;
				questionOrderNumber += String.valueOf(answer
						.getSubquestionAnswerOrderNumber()) + ". ";
				System.out.println("question order number "
						+ questionOrderNumber);
			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, boldFont);

			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					boldFont));
			Paragraph p = new Paragraph(questionPhrase);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { orderNumberIndent, 60,
					375 - (orderNumberIndent + indentLeft), 60 });
			table.setSpacingBefore(5);
			table.setSpacingAfter(5);

			// Kysymysteksti

			PdfPCell cell = new PdfPCell();
			cell.setColspan(3);

			cell.setBorder(0);

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			p.setIndentationLeft(orderNumberIndent);
			p.setFirstLineIndent(-orderNumberIndent);

			cell.addElement(p);

			table.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Tärkeys", boldFont)));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(10);
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Pisteet", boldFont)));
			cell.setBorder(0);
			cell.setPaddingTop(10);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			for (ImportantPointsItem item : answer.getAnswerItems()) {
				cell = new PdfPCell();
				cell.setBorder(0);
				table.addCell(cell);

				if (item.getImportance() != -1) {
					cell = new PdfPCell(new Paragraph(new Chunk(
							String.valueOf(item.getImportance()), font)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				} else
					cell = new PdfPCell();

				cell.setPaddingTop(2);
				cell.setPaddingBottom(2);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(item.getItem(),
						font)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingTop(2);
				cell.setPaddingBottom(2);
				table.addCell(cell);

				if (item.getScore() != -1) {
					cell = new PdfPCell(new Paragraph(new Chunk(
							String.valueOf(item.getScore()), font)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				} else
					cell = new PdfPCell();

				cell.setPaddingTop(2);
				cell.setPaddingBottom(2);
				table.addCell(cell);

			}

			if (answer.getRemarks() != null && answer.getRemarks().length() > 0) {
				Paragraph remarksHeader = new Paragraph("Huomiot:", italicFont);

				cell = new PdfPCell();
				cell.setBorder(0);
				table.addCell(cell);

				cell = new PdfPCell();
				cell.setPaddingTop(5);
				cell.setPaddingBottom(3);
				cell.setColspan(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				cell.addElement(remarksHeader);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

				Paragraph remarksText = new Paragraph(answer.getRemarks(), font);

				cell = new PdfPCell();
				cell.setColspan(3);
				cell.setPaddingTop(0);
				cell.setPaddingBottom(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				cell.addElement(remarksText);
				table.addCell(cell);

			}

			para.add(table);

			return para;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Paragraph getCostListingParagraph(CostListingAnswer answer) {

		try {
			Paragraph para = new Paragraph();

			PdfPTable table = new PdfPTable(3);
			table.setSpacingBefore(10);
			String questionOrderNumber = String.valueOf(answer
					.getReportQuestionGroup().getQuestionGroupOrderNumber())
					+ "." + String.valueOf(answer.getAnswerOrderNumber()) + ".";

			float indentLeft = 20;

			if (answer.getSubquestionAnswerOrderNumber() > 0) {
				indentLeft = 40;
				questionOrderNumber += String.valueOf(answer
						.getSubquestionAnswerOrderNumber()) + ". ";
				System.out.println("question order number "
						+ questionOrderNumber);
			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, boldFont);

			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					boldFont));
			Paragraph p = new Paragraph(questionPhrase);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { orderNumberIndent, 410, 90 });
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setSpacingBefore(5);
			table.setSpacingAfter(5);
			table.setKeepTogether(true);

			// Kysymysteksti

			PdfPCell cell = new PdfPCell();
			cell.setColspan(3);

			cell.setBorder(0);

			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			p.setIndentationLeft(orderNumberIndent);
			p.setFirstLineIndent(-orderNumberIndent);

			cell.addElement(p);

			table.addCell(cell);

			int index = 0;
			for (String item : ((CostListingQuestion) answer.getQuestion())
					.getQuestionItems()) {
				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(item,
						defaultTextFont)));
				cell.setPaddingTop(5);
				cell.setPaddingBottom(5);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(answer
						.getAnswersOut().get(index++), defaultTextFont)));
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPaddingTop(5);
				cell.setPaddingBottom(5);
				cell.setPaddingRight(5);
				table.addCell(cell);

			}

			cell = new PdfPCell(new Paragraph());
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk(
					((CostListingQuestion) answer.getQuestion()).getTotal(),
					boldFont)));
			cell.setPaddingTop(5);
			cell.setPaddingBottom(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk(answer.getTotalOut(),
					boldFont)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setPaddingTop(5);
			cell.setPaddingBottom(5);
			cell.setPaddingRight(5);
			table.addCell(cell);

			para.add(table);

			if (answer.getRemarks() != null && answer.getRemarks().length() > 0) {

				Paragraph remarksHeader = new Paragraph("Huomiot:", italicFont);

				cell = new PdfPCell();
				cell.setPaddingTop(5);
				cell.setPaddingBottom(3);
				cell.setColspan(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				cell.addElement(remarksHeader);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

				Paragraph remarksText = new Paragraph(answer.getRemarks(),
						defaultTextFont);

				cell = new PdfPCell();
				cell.setColspan(2);
				cell.setPaddingTop(0);
				cell.setPaddingBottom(3);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setBorder(0);
				cell.addElement(remarksText);
				table.addCell(cell);

			}

			return para;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
