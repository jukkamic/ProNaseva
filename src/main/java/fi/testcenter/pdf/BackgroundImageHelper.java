package fi.testcenter.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class BackgroundImageHelper extends PdfPageEventHelper {
	private Image img;

	public BackgroundImageHelper(Image img) {

		this.img = img;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		try {

			writer.getDirectContentUnder().addImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
