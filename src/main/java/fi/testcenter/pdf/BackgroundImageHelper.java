package fi.testcenter.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Component
public class BackgroundImageHelper extends PdfPageEventHelper {

	ResourceLoader resourceLoader;
	private Image img;

	@Autowired
	public BackgroundImageHelper(ResourceLoader resourceLoader) {

		this.resourceLoader = resourceLoader;

		try {
			Resource fileResource = resourceLoader
					.getResource("classpath:images/bg.jpg");

			this.img = Image.getInstance(fileResource.getURL());
			img.scaleAbsolute(595, 842);
			img.setAbsolutePosition(0, 0);

		} catch (Exception e) {
			e.printStackTrace();

		}

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
