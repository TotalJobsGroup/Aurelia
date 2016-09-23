package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsRequestBody;
import com.totaljobsgroup.aurelia.model.AtsRequestHeader;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import com.totaljobsgroup.aurelia.model.Attachment;
import com.totaljobsgroup.aurelia.model.Attachment.FileType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.totaljobsgroup.aurelia.model.Attachment.FileType.TXT;

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
        Assert.assertNotNull(atsResponse.getBody().getBackUrl().toString());
        Assert.assertNotNull(atsResponse.getBody().getRedirect());
    }

    private AtsRequest prepareAtsRequest() {
        AtsRequest atsRequest = new AtsRequest();
        atsRequest.setHeader(prepareAtsRequestHeader());
        atsRequest.setBody(prepareAtsRequestBody());
        return atsRequest;
    }

    private AtsRequestHeader prepareAtsRequestHeader() {
        AtsRequestHeader atsRequestHeader = new AtsRequestHeader();
        atsRequestHeader.setJobBoardId("totaljobs");
        atsRequestHeader.setOriginalUrl("http://aplitrack.com/hays/12345");
        atsRequestHeader.setEffectiveUrl("http://hays.co.uk/12345");
        atsRequestHeader.setJobBoardName("Totaljobs");
        return atsRequestHeader;
    }

    private AtsRequestBody prepareAtsRequestBody() {
        AtsRequestBody atsRequestBody = new AtsRequestBody();
        atsRequestBody.setEmail("ivan.krasinski@gmail.com");
        atsRequestBody.setMessageToRecruiter("Hello it's me");
        atsRequestBody.setAdditionalDocuments(prepareAdditionalDocuments());
        atsRequestBody.setCoverLetter(prepareCoverLetter());
        atsRequestBody.setCV(prepareCV());
        atsRequestBody.setAdditionalProperty("Title", "Mr");
        atsRequestBody.setAdditionalProperty("FirstName", "Ivan");
        atsRequestBody.setAdditionalProperty("Surname", "Krasinski");
        atsRequestBody.setAdditionalProperty("JobTitle", "Manager");
        atsRequestBody.setAdditionalProperty("SalaryType", "Annual");
        atsRequestBody.setAdditionalProperty("SalaryRangeMin", "40000");
        atsRequestBody.setAdditionalProperty("SalaryRangeMax", "50000");
        atsRequestBody.setAdditionalProperty("SalaryCurrency", "GBP");
        atsRequestBody.setAdditionalProperty("WorkEligibility", "UK");
        return atsRequestBody;
    }

    private List<Attachment> prepareAdditionalDocuments() {
        List<Attachment> additionalDocuments = new ArrayList<>();
        additionalDocuments.add(prepareCertificate());
        additionalDocuments.add(prepareLicence());
        return additionalDocuments;
    }

    private Attachment prepareCertificate() {
        return prepareAttachment(TXT, "VGhpcyBpcyBteSBDZXJ0aWZpY2F0ZQ==");
    }

    private Attachment prepareLicence() {
        return prepareAttachment(TXT, "VGhpcyBpcyBteSBMaWNlbmNl");
    }

    private Attachment prepareCV() {
        return prepareAttachment(TXT, "VGhpcyBpcyBteSBDVi4=");
    }

    private Attachment prepareCoverLetter() {
        return prepareAttachment(TXT, "VGhpcyBpcyBteSBDb3ZlciBMZXR0ZXIu");
    }

    private Attachment prepareAttachment(FileType fileType, String base64Content) {
        Attachment attachment = new Attachment();
        attachment.setFileType(fileType);
        attachment.setBinaryData(base64Content);
        return attachment;
    }

}