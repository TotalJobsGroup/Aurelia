package com.totaljobsgroup.aurelia;

import org.springframework.stereotype.Component;

@Component
public class JobDB {

    public boolean doesJobRequireRedirection(int jobId) {
        // example logic - every job with id that ends with 45 need redirection
        return (jobId % 100 == 45);
    }

    public boolean isJobAvailable(int jobId) {
        // example logic - every job with id that ends with 89 is not available
        return (jobId % 100 != 89);
    }

    public String redirectionPage() {
        // example redirection url
        return "https://ats.com/complete-page";
    }

}
