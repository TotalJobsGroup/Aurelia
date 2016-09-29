package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsRequestBody;
import com.totaljobsgroup.aurelia.model.AtsRequestHeader;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseBody;
import com.totaljobsgroup.aurelia.model.Attachment;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ApplicationProcessorTest {

    @Test
    @Parameters(method = "requestResponseParameters")
    public void shouldPrepareProperResponse(boolean redirect, boolean jobAvailable, AtsResponseBody.Redirect expectedRedirect, String expectedErrorMsg, String expectedRedirectUrl) {
        // given
        String redirectionPage = "http://redirection.page";
        URI expectedBackUrl = URI.create(expectedRedirectUrl);
        AtsRequest atsRequestMock = prepareAtsRequestMock();
        JobDB jobDBMock = prepareJobDBMock(redirect, jobAvailable, redirectionPage);
        ApplicationProcessor objectUnderTest = new ApplicationProcessor(jobDBMock, new JobUrlProcessor());

        // when
        AtsResponse atsResponse = objectUnderTest.processApplication(atsRequestMock);

        // then
        assertEquals(expectedRedirect, atsResponse.getBody().getRedirect());
        assertEquals(expectedErrorMsg, atsResponse.getHeader().getErrorMsg());
        assertEquals(expectedBackUrl, atsResponse.getBody().getBackUrl());
    }

    private Object[] requestResponseParameters() {
        return new Object[]{
                new Object[]{true, true, AtsResponseBody.Redirect.YES, null, "http://redirection.page"},
                new Object[]{true, false, AtsResponseBody.Redirect.NO, "JOB_NOT_AVAILABLE", ""},
                new Object[]{false, true, AtsResponseBody.Redirect.NO, null, ""},
                new Object[]{false, false, AtsResponseBody.Redirect.NO, "JOB_NOT_AVAILABLE", ""},
        };
    }

    private AtsRequest prepareAtsRequestMock() {
        AtsRequest atsRequestMock = mock(AtsRequest.class);
        AtsRequestHeader atsRequestHeaderMock = prepareAtsRequestHeaderMock();
        when(atsRequestMock.getHeader()).thenReturn(atsRequestHeaderMock);
        AtsRequestBody atsRequestBodyMock = prepareAtsRequestBodyMock();
        when(atsRequestMock.getBody()).thenReturn(atsRequestBodyMock);
        return atsRequestMock;
    }

    private AtsRequestHeader prepareAtsRequestHeaderMock() {
        AtsRequestHeader atsRequestHeaderMock = mock(AtsRequestHeader.class);
        when(atsRequestHeaderMock.getEffectiveUrl()).thenReturn("http://effectiveUrl");
        return atsRequestHeaderMock;
    }

    private AtsRequestBody prepareAtsRequestBodyMock() {
        AtsRequestBody atsRequestBodyMock = mock(AtsRequestBody.class);
        Attachment attachmentMock = mock(Attachment.class);
        when(atsRequestBodyMock.getCV()).thenReturn(attachmentMock);
        return atsRequestBodyMock;
    }

    private JobDB prepareJobDBMock(boolean redirection, boolean jobAvailable, String redirectionPage) {
        JobDB jobDBMock = mock(JobDB.class);
        when(jobDBMock.doesJobRequireRedirection(anyInt())).thenReturn(redirection);
        when(jobDBMock.isJobAvailable(anyInt())).thenReturn(jobAvailable);
        when(jobDBMock.redirectionPage()).thenReturn(redirectionPage);
        return jobDBMock;
    }

}