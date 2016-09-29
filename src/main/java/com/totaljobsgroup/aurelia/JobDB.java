package com.totaljobsgroup.aurelia;

import org.springframework.stereotype.Component;

@Component
public class JobDB {

    public boolean doesJobRequireRedirection(int jobId) {
        // example logic - every job with id that ends with 45 need redirection
        if (jobId % 100 == 45) {
            return true;
        }
        return false;
    }

    public boolean isJobAvailable(int jobId) {
        // example logic - every job with id that ends with 89 is not available
        if (jobId % 100 == 89) {
            return false;
        }
        return true;
    }

    public String redirectionPage() {
        // example redirection url
        return "https://ats.com/complete-page";
    }

}
