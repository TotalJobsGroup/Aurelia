package com.totaljobsgroup.aurelia;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class JobDBTest {

    @Test
    @Parameters({
            "12345, true",
            "12346, false",
            "12300, false",
            "12389, false",
            "12399, false",
            "45, true"
    })
    public void shouldRequireRedirection(int jobId, boolean expectedShouldRequireRedirection) {
        // given
        JobDB jobDB = new JobDB();

        // when
        boolean actualShouldRequireRedirection = jobDB.doesJobRequireRedirection(jobId);

        // then
        assertEquals(actualShouldRequireRedirection, expectedShouldRequireRedirection);
    }

    @Test
    @Parameters({
            "56789, false",
            "56788, true",
            "12300, true",
            "89, false",
            "12399, true",
            "12345, true"
    })
    public void shouldJobBeAvailable(int jobId, boolean expectedIsJobAvailable) {
        // given
        JobDB jobDB = new JobDB();

        // when
        boolean actualIsJobAvailable = jobDB.isJobAvailable(jobId);

        // then
        assertEquals(actualIsJobAvailable, expectedIsJobAvailable);
    }

}