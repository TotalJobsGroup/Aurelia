package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AureliaController {

    private final ApplicationProcessor applicationProcessor;

    @Autowired
    public AureliaController(ApplicationProcessor applicationProcessor) {
        this.applicationProcessor = applicationProcessor;
    }

    @ResponseBody
    @RequestMapping("/")
    String home() {
        return "Hello World! My name is Aurelia. I'm an Applicant Tracking System.";
    }

    @ResponseBody
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    AtsResponse apply(@RequestBody AtsRequest atsiRequest) {
        return applicationProcessor.processApplication(atsiRequest);
    }


}
