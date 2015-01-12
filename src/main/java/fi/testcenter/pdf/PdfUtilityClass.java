package fi.testcenter.pdf;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import fi.testcenter.domain.answer.CostListingAnswer;
import fi.testcenter.domain.answer.DateAnswer;
import fi.testcenter.domain.answer.ImportantPointsAnswer;
import fi.testcenter.domain.answer.ImportantPointsItem;
import fi.testcenter.domain.answer.MultipleChoiceAnswer;
import fi.testcenter.domain.answer.PointsAnswer;
import fi.testcenter.domain.answer.TextAnswer;
import fi.testcenter.domain.question.CostListingQuestion;
import fi.testcenter.domain.question.MultipleChoiceOption;
import fi.testcenter.domain.question.MultipleChoiceQuestion;
import fi.testcenter.domain.question.PointsQuestion;

@Component
public class PdfUtilityClass {

	Logger log = Logger.getLogger("fi.testcenter.web.ReportController");

	final BaseFont BASE_FONT = getBaseFont();
	final BaseFont WINGDINGS_BASE_FONT = getWingdingBaseFont();

	final Font REPORT_PART_TITLE_FONT = new Font(BASE_FONT, 20, Font.BOLD);;
	final Font GROUP_TITLE_FONT = new Font(BASE_FONT, 16, Font.BOLD);;
	final Font DEFAULT_FONT = new Font(BASE_FONT, 11);;
	final Font BOLD = new Font(BASE_FONT, 11, Font.BOLD);;
	final Font ITALIC = new Font(BASE_FONT, 11, Font.ITALIC);;

	final Font WINGDING_FONT = new Font(WINGDINGS_BASE_FONT, 12);;

	public PdfUtilityClass() {
	}

	public static BaseFont getBaseFont() {

		try {
			return BaseFont.createFont("c:/windows/fonts/arial.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static BaseFont getWingdingBaseFont() {
		try {
			return BaseFont.createFont("c:/windows/fonts/wingding.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Paragraph getPartTitle(String title) {

		Paragraph p = new Paragraph(title, REPORT_PART_TITLE_FONT);
		p.setSpacingBefore(10);
		p.setSpacingAfter(10);
		p.setIndentationRight(50);

		return p;
	}

	public Paragraph getGroupTitle(int orderNumber, String title) {

		Chunk partNumber = new Chunk(String.valueOf(orderNumber) + ". ",
				GROUP_TITLE_FONT);

		Paragraph p = new Paragraph();

		float orderNumberWidth = partNumber.getWidthPoint();
		p.setIndentationLeft(orderNumberWidth);
		p.setFirstLineIndent(-orderNumberWidth);
		p.setSpacingBefore(10);
		p.setSpacingAfter(10);
		p.setIndentationRight(50);
		p.add(partNumber);
		p.add(new Chunk(title, GROUP_TITLE_FONT));
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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);
			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					BOLD));
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

			cell.addElement(p);

			table.addCell(cell);

			// Pisteet

			if (answer.getScore() != -1) {
				Chunk scoreChunk = new Chunk(String.valueOf(answer.getScore()
						+ " / " + answer.getMaxScore()), BOLD);
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

			Chunk checkbox_unchecked = new Chunk((char) 0x6F, WINGDING_FONT);
			Chunk checkbox_checked = new Chunk((char) 0xFE, WINGDING_FONT);
			int index = 0;
			for (MultipleChoiceOption option : ((MultipleChoiceQuestion) answer
					.getQuestion()).getOptionsList()) {

				cell = new PdfPCell(new Paragraph());
				cell.setBorder(0);
				table.addCell(cell);

				if (answer.getChosenOptionIndex() == index++
						|| (answer.getChosenSelections() != null && Arrays
								.asList(answer.getChosenSelections()).contains(
										option.getMultipleChoiceOption()))) {

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
						option.getMultipleChoiceOption(), DEFAULT_FONT));
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

				remarksPara.add(new Chunk("Huomiot:", ITALIC));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

				remarksPara = new Paragraph();
				remarksPara.setIndentationLeft(orderNumberIndent + 20);
				remarksPara.setIndentationRight(70);
				remarksPara.setKeepTogether(true);

				remarksPara.add(new Chunk(answer.getRemarks(), DEFAULT_FONT));
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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);

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
					BOLD));
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
										.getQuestion()).getMaxPoints()), BOLD)));
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

				remarksPara.add(new Chunk("Huomiot:", ITALIC));
				remarksPara.setSpacingBefore(0);
				remarksPara.setSpacingAfter(3);

				para.add(remarksPara);

				remarksPara = new Paragraph();
				remarksPara.setIndentationLeft(20 + indentLeft + indent);
				remarksPara.setIndentationRight(70);
				remarksPara.setKeepTogether(true);

				remarksPara.add(new Chunk(answer.getRemarks(), DEFAULT_FONT));
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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);
			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 475 - (indentLeft), 40 });
			table.setSpacingBefore(5);

			table.setKeepTogether(true);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					BOLD));
			Paragraph p = new Paragraph(questionPhrase);

			float indent = questionNumberChunk.getWidthPoint();
			p.setIndentationLeft(indent);
			p.setFirstLineIndent(-indent);

			para.add(p);

			Paragraph answerPara = new Paragraph(new Chunk(answer.getAnswer(),
					DEFAULT_FONT));
			answerPara.setIndentationLeft(orderNumberIndent);
			para.add(answerPara);

			return para;

		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public Paragraph getDateAnswerParagraph(DateAnswer answer) {
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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);
			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			table.setWidthPercentage(100);
			table.setWidths(new float[] { 475 - (indentLeft), 40 });
			table.setSpacingBefore(5);

			table.setKeepTogether(true);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					BOLD));
			Paragraph p = new Paragraph(questionPhrase);

			float indent = questionNumberChunk.getWidthPoint();
			p.setIndentationLeft(indent);
			p.setFirstLineIndent(-indent);

			para.add(p);

			Paragraph answerPara = new Paragraph(new Chunk(
					answer.getDateString(), DEFAULT_FONT));
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
		Font font = new Font(BASE_FONT, 11);

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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);

			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					BOLD));
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

			cell = new PdfPCell(new Paragraph(new Chunk("Tärkeys", BOLD)));
			cell.setBorder(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingBottom(5);
			cell.setPaddingTop(10);
			table.addCell(cell);

			cell = new PdfPCell();
			cell.setBorder(0);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk("Pisteet", BOLD)));
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
				Paragraph remarksHeader = new Paragraph("Huomiot:", ITALIC);

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

			} else
				questionOrderNumber += " ";

			Chunk questionNumberChunk = new Chunk(questionOrderNumber, BOLD);

			float orderNumberIndent = questionNumberChunk.getWidthPoint();

			Phrase questionPhrase = new Phrase(questionNumberChunk);

			para.setIndentationLeft(indentLeft);

			questionPhrase.add(new Chunk(answer.getQuestion().getQuestion(),
					BOLD));
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

				cell = new PdfPCell(
						new Paragraph(new Chunk(item, DEFAULT_FONT)));
				cell.setPaddingTop(5);
				cell.setPaddingBottom(5);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				cell = new PdfPCell(new Paragraph(new Chunk(answer
						.getAnswersOut().get(index++), DEFAULT_FONT)));
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
					BOLD)));
			cell.setPaddingTop(5);
			cell.setPaddingBottom(5);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Paragraph(new Chunk(answer.getTotalOut(),
					BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setPaddingTop(5);
			cell.setPaddingBottom(5);
			cell.setPaddingRight(5);
			table.addCell(cell);

			para.add(table);

			if (answer.getRemarks() != null && answer.getRemarks().length() > 0) {

				Paragraph remarksHeader = new Paragraph("Huomiot:", ITALIC);

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
						DEFAULT_FONT);

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
