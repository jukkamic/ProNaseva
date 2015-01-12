package fi.testcenter.pdf;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import fi.testcenter.domain.report.WorkshopVisitReport;

@Component
public class HeaderHelper extends PdfPageEventHelper {

	PdfTemplate totalPages;
	WorkshopVisitReport report;
	String reportPartTitle;

	public void onOpenDocument(PdfWriter writer, Document document) {
		totalPages = writer.getDirectContent().createTemplate(30, 16);
	}

	public void onStartPage(PdfWriter writer, Document document) {
		if (document.getPageNumber() > 1) {
			try {
				BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf",
						BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

				Font importerWorkshopFont = new Font(bf, 14, Font.BOLD);

				PdfPTable header = new PdfPTable(3);

				header.setTotalWidth(390);
				header.setWidths(new float[] { 300, 60, 30 });
				header.setSpacingBefore(10);
				header.getDefaultCell().setBorder(0);

				Chunk chunk = new Chunk(report.getImporter().getName(),
						importerWorkshopFont);

				Paragraph p = new Paragraph(chunk);
				PdfPCell cell = new PdfPCell(p);
				cell.setPaddingBottom(15);
				cell.setBorder(0);
				header.addCell(cell);

				header.completeRow();

				String workshopName = report.getWorkshop().getName();
				if (report.getWorkshop().getCity() != null
						&& report.getWorkshop().getCity().length() > 0)
					workshopName = workshopName + ", "
							+ report.getWorkshop().getCity();
				chunk = new Chunk(workshopName, importerWorkshopFont);

				p = new Paragraph(chunk);
				cell = new PdfPCell(p);
				cell.setPaddingBottom(15);
				cell.setBorder(0);
				header.addCell(cell);

				header.completeRow();

				chunk = new Chunk(this.reportPartTitle, new Font(bf, 12,
						Font.ITALIC));
				p = new Paragraph(chunk);
				cell = new PdfPCell(p);
				cell.setPaddingBottom(10);
				cell.setBorder(Rectangle.BOTTOM);

				header.addCell(cell);

				chunk = new Chunk(String.format("Sivu %d /",
						writer.getPageNumber() - 1), new Font(bf, 12));
				p = new Paragraph(chunk);
				cell = new PdfPCell(p);
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
		ColumnText.showTextAligned(totalPages, Element.ALIGN_LEFT, new Phrase(
				String.valueOf(writer.getPageNumber() - 2)), 2, 2, 0);

	}

	public WorkshopVisitReport getReport() {
		return report;
	}

	public void setReport(WorkshopVisitReport report) {
		this.report = report;
	}

	public String getReportPartTitle() {
		return reportPartTitle;
	}

	public void setReportPartTitle(String reportPartTitle) {
		this.reportPartTitle = reportPartTitle;
	}
}
