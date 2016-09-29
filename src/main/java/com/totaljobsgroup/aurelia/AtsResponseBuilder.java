package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseBody;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;

import java.net.URI;
import java.util.Optional;

class AtsResponseBuilder {
    AtsResponseHeader.Status status = AtsResponseHeader.Status.OK;
    Optional<String> backUrl = Optional.<String>empty();
    private String errorMsg;

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

    public AtsResponseBuilder withErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public AtsResponse build() {
        AtsResponse atsResponse = new AtsResponse();
        atsResponse.setHeader(createResponseHeader(this.status, this.errorMsg));
        atsResponse.setBody(createResponseBody(this.backUrl));
        return atsResponse;
    }

    private AtsResponseHeader createResponseHeader(AtsResponseHeader.Status status, String errorMsg) {
        AtsResponseHeader responseHeader = new AtsResponseHeader();
        responseHeader.setStatus(status);
        responseHeader.setErrorMsg(errorMsg);
        return responseHeader;
    }

    private AtsResponseBody createResponseBody(Optional<String> backUrl) {
        AtsResponseBody responseBody = new AtsResponseBody();
        responseBody.setBackUrl(backUrl.map(u -> URI.create(u)).orElse(URI.create("")));
        responseBody.setRedirect(backUrl.map(u -> AtsResponseBody.Redirect.YES).orElse(AtsResponseBody.Redirect.NO));
        return responseBody;
    }

}
