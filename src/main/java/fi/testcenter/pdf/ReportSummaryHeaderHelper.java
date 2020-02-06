package fi.testcenter.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.reportSummary.ReportSummary;

@Component
public class ReportSummaryHeaderHelper extends PdfPageEventHelper {

	@Autowired
	PdfUtilityClass pdf;

	PdfTemplate totalPages;
	ReportSummary reportSummary;

	public void setReportSummary(ReportSummary reportSummary) {
		this.reportSummary = reportSummary;
	}

	public void onOpenDocument(PdfWriter writer, Document document) {
		totalPages = writer.getDirectContent().createTemplate(30, 16);
	}

	public void onStartPage(PdfWriter writer, Document document) {
		if (document.getPageNumber() > 1) {
			try {

				Font importerFont = new Font(pdf.BASE_FONT, 14, Font.BOLD);

				PdfPTable header = new PdfPTable(3);

				header.setTotalWidth(390);
				header.setWidths(new float[] { 300, 60, 30 });
				header.setSpacingBefore(10);
				header.getDefaultCell().setBorder(0);

				Chunk chunk = new Chunk(reportSummary.getImporter().getName(),
						importerFont);

				Paragraph p = new Paragraph(chunk);
				PdfPCell cell = new PdfPCell(p);
				cell.setPaddingBottom(15);
				cell.setBorder(0);
				header.addCell(cell);

				header.completeRow();

				chunk = new Chunk(String.format("Raporttiyhteenveto", new Font(
						pdf.BASE_FONT, 12)));

				p = new Paragraph(chunk);
				cell = new PdfPCell(p);
				cell.setPaddingBottom(15);
				cell.setBorder(0);
				header.addCell(cell);

				header.completeRow();

				p = new Paragraph();
				cell = new PdfPCell(p);
				cell.setPaddingBottom(10);
				cell.setBorder(Rectangle.BOTTOM);

				header.addCell(cell);

				int page = writer.getPageNumber() - 1;

				chunk = new Chunk(String.format("Sivu %d /", page, new Font(
						pdf.BASE_FONT, 12)));
				p = new Paragraph(chunk);
				cell = new PdfPCell(p);
				cell.setPaddingBottom(10);
				cell.setBorder(Rectangle.BOTTOM);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				header.addCell(cell);

				cell = new PdfPCell(Image.getInstance(totalPages));
				cell.setBorder(Rectangle.BOTTOM);
				header.addCell(cell);

				header.writeSelectedRows(0, -1, 175, 818,
						writer.getDirectContent());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		int totalPageCount;
		totalPageCount = writer.getPageNumber() - 2;

		ColumnText.showTextAligned(totalPages, Element.ALIGN_LEFT, new Phrase(
				String.valueOf(totalPageCount)), 2, 2, 0);

	}

}
