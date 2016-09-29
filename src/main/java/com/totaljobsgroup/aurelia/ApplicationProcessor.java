package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import com.totaljobsgroup.aurelia.model.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProcessor {

    private final JobDB jobDB;
    private final JobUrlProcessor jobUrlProcessor;

    @Autowired
    public ApplicationProcessor(JobDB jobDB, JobUrlProcessor jobUrlProcessor) {
        this.jobDB = jobDB;
        this.jobUrlProcessor = jobUrlProcessor;
    }

    public AtsResponse processApplication(AtsRequest atsiRequest) {
        storeApplicantData(atsiRequest);
        return prepareResponse(atsiRequest);
    }

    private void storeApplicantData(AtsRequest atsiRequest) {
        Attachment cv = atsiRequest.getBody().getCV();
        String effectiveUrl = atsiRequest.getHeader().getEffectiveUrl();
        // ...
        // ... your logic here
        // ...
    }

    private AtsResponse prepareResponse(AtsRequest atsiRequest) {
        AtsResponse atsResponse;
        int jobId = jobUrlProcessor.retrieveJobId(atsiRequest.getHeader().getEffectiveUrl()).orElse(12345);
        if (jobDB.isJobAvailable(jobId)) {
            if (jobDB.doesJobRequireRedirection(jobId)) {
                atsResponse = AtsResponseBuilder.builder().withRedirection(jobDB.redirectionPage()).withStatus(AtsResponseHeader.Status.OK).build();
            } else {
                atsResponse = AtsResponseBuilder.builder().withStatus(AtsResponseHeader.Status.OK).build();
            }
        } else {
            atsResponse = AtsResponseBuilder.builder().withStatus(AtsResponseHeader.Status.ERROR).withErrorMsg("JOB_NOT_AVAILABLE").build();
        }
        return atsResponse;
    }

}
