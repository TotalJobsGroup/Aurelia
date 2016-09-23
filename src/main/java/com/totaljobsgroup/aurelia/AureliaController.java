package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseBody;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import com.totaljobsgroup.aurelia.model.Attachment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.Optional;

@Controller
@EnableAutoConfiguration
public class AureliaController {

    @ResponseBody
    @RequestMapping("/")
    String home() {
        return "Hello World! My name is Aurelia. I'm the Applicant Tracking System.";
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

    static class AtsResponseBuilder {
        AtsResponseHeader.Status status = AtsResponseHeader.Status.OK;
        Optional<String> backUrl = Optional.<String>empty();

        public static AtsResponseBuilder builder() {
            return new AtsResponseBuilder();
        }

        public AtsResponseBuilder withStatus(AtsResponseHeader.Status status) {
            this.status = status;
            return this;
        }

        public AtsResponseBuilder withRedirection(String backUrl) {
            if (backUrl != null) {
                this.backUrl = Optional.of(backUrl);
            }
            return this;
        }

        public AtsResponse build() {
            AtsResponse atsResponse = new AtsResponse();
            atsResponse.setHeader(createResponseHeader(this.status));
            atsResponse.setBody(createResponseBody(this.backUrl));
            return atsResponse;
        }

        private AtsResponseHeader createResponseHeader(AtsResponseHeader.Status status) {
            AtsResponseHeader responseHeader = new AtsResponseHeader();
            responseHeader.setStatus(status);
            return responseHeader;
        }

        private AtsResponseBody createResponseBody(Optional<String> backUrl) {
            AtsResponseBody responseBody = new AtsResponseBody();
            responseBody.setBackUrl(backUrl.map(u -> URI.create(u)).orElse(URI.create("")));
            responseBody.setRedirect(backUrl.map(u -> AtsResponseBody.Redirect.YES).orElse(AtsResponseBody.Redirect.NO));
            return responseBody;
        }

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AureliaController.class, args);
    }

}
