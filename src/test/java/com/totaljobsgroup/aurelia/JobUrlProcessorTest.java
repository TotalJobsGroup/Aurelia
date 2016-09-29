package com.totaljobsgroup.aurelia;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class JobUrlProcessorTest {

    @Test
    @Parameters({
            "http://recruitmentcompany.co.uk/12345, true, 12345",
            "http://recruitmentcompany.co.uk/56789, true, 56789",
            "http://recruitmentcompany.co.uk/56789a, false, 0",
            "http://recruitmentcompany.co.uk/12345/, false, 0",
            "http://recruitmentcompany.co.uk/12345/0, true, 0"
    })
    public void shouldRetrieveJobId(String url, boolean notNull, int jobId) {
        // given
        JobUrlProcessor objectUnderTest = new JobUrlProcessor();

        // when
        Optional<Integer> actual = objectUnderTest.retrieveJobId(url);

        // then
        assertEquals(notNull, actual.isPresent());
        assertEquals(jobId, actual.orElse(jobId).intValue());
    }

}