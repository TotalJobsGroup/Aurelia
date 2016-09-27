package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import com.totaljobsgroup.aurelia.model.Attachment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class AureliaController {

    @ResponseBody
    @RequestMapping("/")
    String home() {
        return "Hello World! My name is Aurelia. I'm an Applicant Tracking System.";
    }

    @ResponseBody
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    AtsResponse apply(@RequestBody AtsRequest atsiRequest) {
        storeApplicantData(atsiRequest);
        String backUrl = "https://ats.com/complete-page";
        return AtsResponseBuilder.builder().withRedirection(backUrl).withStatus(AtsResponseHeader.Status.OK).build();
    }

    private void storeApplicantData(AtsRequest atsiRequest) {
        Attachment cv = atsiRequest.getBody().getCV();
        String effectiveUrl = atsiRequest.getHeader().getEffectiveUrl();
        // ...
        // ... your logic
        // ...
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AureliaController.class, args);
    }

}
