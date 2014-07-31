package fi.testcenter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import fi.testcenter.service.QuestionGroupService;

@Controller
@RequestMapping("/questions")
public class InitQuestionGroupController {

	@Autowired
	private QuestionGroupService qgs;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void initQuestionGroups() {
		qgs.createQuestionGroups();
	}

}
