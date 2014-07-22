package fi.testcenter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.testcenter.dao.ReportDAOMockVolvo;
import fi.testcenter.domain.Report;

@Service
public class ReportService {

	@Autowired
	private ReportDAOMockVolvo rd;

	public Report getReportTemplate() {
		return rd.getReportTemplate();
	}

}
