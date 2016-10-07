package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsRequestBody;
import com.totaljobsgroup.aurelia.model.AtsRequestHeader;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {AureliaController.class})
public class AureliaControllerIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldGetStatusOK() {
        // given
        AtsRequest atsRequest = prepareAtsRequest();

        // when
        ResponseEntity<AtsResponse> atsResponseResponseEntity = testRestTemplate.postForEntity("/apply", atsRequest, AtsResponse.class);

        // then
        AtsResponse atsResponse = atsResponseResponseEntity.getBody();
        Assert.assertEquals(HttpStatus.OK, atsResponseResponseEntity.getStatusCode());
        Assert.assertEquals(AtsResponseHeader.Status.OK, atsResponse.getHeader().getStatus());
        Assert.assertNotNull(atsResponse.getHeader());
        Assert.assertNotNull(atsResponse.getBody());
    }

    private AtsRequest prepareAtsRequest() {
        AtsRequest atsRequest = new AtsRequest();
        atsRequest.setHeader(new AtsRequestHeader());
        atsRequest.setBody(new AtsRequestBody());
        return atsRequest;
    }

}