package fi.testcenter.web;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.testcenter.dao.TestiDAO;
import fi.testcenter.domain.Importer;
import fi.testcenter.domain.Report;
import fi.testcenter.domain.Testi;
import fi.testcenter.domain.Workshop;
import fi.testcenter.service.BasicInfoService;
import fi.testcenter.service.ImporterService;
import fi.testcenter.service.QuestionService;
import fi.testcenter.web.form.BasicInfo;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"basicInfo", "report"})

public class BasicInfoController {
	
	
		

	
	@Autowired
	private ImporterService is;
	
	@Autowired
	private BasicInfoService ws;
	
	@Autowired
	private QuestionService qs;

	
	@Autowired
	TestiDAO tdao;
	
	
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, Model model) {
    	List<Workshop> workshops = new ArrayList<Workshop> ();
    	workshops = ws.getWorkshops();
    	model.addAttribute("workshops", workshops);
    	
    	List<Importer> importers = new ArrayList<Importer> ();
    	importers = is.getImporters();
    	model.addAttribute("importers", importers);
    	
    	model.addAttribute("basicInfo", new BasicInfo());
    	
        return "start";
    }
	
    @RequestMapping(value="/submitWorkshopImporter", method = RequestMethod.POST)
    public String submitWorkshopImporter(HttpServletRequest request, Model model, 
    		@ModelAttribute("basicInfo") BasicInfo bis, BindingResult result) {
    	
    	return "redirect:/basicInfo";
    }
    
    
    @RequestMapping(value="/basicInfo")
    public String prepareForm(HttpServletRequest request, Model model,
    		@ModelAttribute("basicInfo") BasicInfo bis) {
    	model.addAttribute("basicInfo", bis);
    	return "basicInfo";
    }
    		
    		
    @RequestMapping(value="/submitBasicInfo", method=RequestMethod.POST)
    public String submitBasicInfo(HttpServletRequest request, Model model,
    		@ModelAttribute("basicInfo") BasicInfo bi, BindingResult result) {

    	
    	Report report = new Report();
    	report.setImporter(bi.getImporter());
    	report.setWorkshop(bi.getWorkshop());
    	
    	model.addAttribute("report", report);
    	return "redirect:/prepareReport";
    }
    
    @RequestMapping(value="/prepareReport")
    public String prepareForm(HttpServletRequest request, Model model,
    		@ModelAttribute("report") Report report, BindingResult result) {

    	System.out.println(report.getWorkshop());    	
    	report.setQuestions(qs.getQuestions());
    	model.addAttribute("report", report);
    	return "newReport";
    }
    
    
    @Transactional
    @RequestMapping(value="/submitReport", method = RequestMethod.POST)
    public String submitReport(HttpServletRequest request, Model model, 
    		@ModelAttribute("report") Report report, BindingResult result) {
    	
    		Testi testi = new Testi();
    	 	    		
    	    testi.setSummary("This is a test");
    	    testi.setDescription("This is a javatesti");
    	    try {    	    
    	    tdao.save(testi);
    	    }
    	    catch(Exception e)
    	    {
    	    	e.printStackTrace();
    	    	System.out.println("Errori: " + e.getMessage());
    	    }
    	    tdao.ListTesti();
    	        	       	    
    	    return "newReport";
    }
    
    
}
