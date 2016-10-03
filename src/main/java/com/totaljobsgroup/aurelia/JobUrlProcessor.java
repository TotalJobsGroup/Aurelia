package com.totaljobsgroup.aurelia;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JobUrlProcessor {

    private static final int MAX_JOB_ID_LENGTH = 5;

    public Optional<Integer> retrieveJobId(String effectiveUrl) {
        if (effectiveUrl != null && effectiveUrl.contains("/")) {
            int slashIndex = effectiveUrl.lastIndexOf("/");
            if (slashIndex < effectiveUrl.length() - 1) {
                String jobId = effectiveUrl.substring(slashIndex + 1);
                if (jobId.length() > MAX_JOB_ID_LENGTH) {
                    jobId = StringUtils.right(jobId, MAX_JOB_ID_LENGTH);
                }
                if (NumberUtils.isNumber(jobId)) {
                    return Optional.of(Integer.parseInt(jobId));
                }
            }
        }
        return Optional.empty();
    }

}
