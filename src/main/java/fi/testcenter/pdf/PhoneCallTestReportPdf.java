package fi.testcenter.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.itextpdf.text.DocumentException;

import fi.testcenter.domain.report.PhoneCallTestReport;

@Component
public class PhoneCallTestReportPdf {

	public ByteArrayOutputStream generateReportPdf(PhoneCallTestReport report)
			throws IOException, DocumentException {

		return null;

	}
}
