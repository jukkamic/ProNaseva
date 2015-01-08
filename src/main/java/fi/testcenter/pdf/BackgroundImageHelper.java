package fi.testcenter.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class BackgroundImageHelper extends PdfPageEventHelper {
	private Image img;

	public BackgroundImageHelper() {

		// try {
		// String path = new ClassPathResource("/printReportBackground.jpg")
		// .getFile().getAbsolutePath();
		// System.out.println(path);
		// this.img = Image.getInstance(path);
		//
		// img.scaleAbsolute(595, 842);
		// img.setAbsolutePosition(0, 0);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		try {

			// writer.getDirectContentUnder().addImage(img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
