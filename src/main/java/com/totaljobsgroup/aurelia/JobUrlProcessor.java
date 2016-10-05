package com.totaljobsgroup.aurelia;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JobUrlProcessor {

    private static final int MAX_JOB_ID_LENGTH = 5;

    public Optional<Integer> retrieveJobId(String effectiveUrl) {
        String jobId = StringUtils.substringAfterLast(effectiveUrl, "/");
        jobId = StringUtils.right(jobId, MAX_JOB_ID_LENGTH);
        return NumberUtils.isNumber(jobId) ? Optional.of(Integer.parseInt(jobId)) : Optional.empty();
    }

}
