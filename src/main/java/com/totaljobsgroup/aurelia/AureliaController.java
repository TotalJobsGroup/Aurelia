package com.totaljobsgroup.aurelia;

import com.totaljobsgroup.aurelia.model.AtsRequest;
import com.totaljobsgroup.aurelia.model.AtsResponse;
import com.totaljobsgroup.aurelia.model.AtsResponseHeader;
import com.totaljobsgroup.aurelia.model.Attachment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,
//        RabbitAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class,
//        JpaRepositoriesAutoConfiguration.class,
//        DataSourceAutoConfiguration.class,
//        DataSourceTransactionManagerAutoConfiguration.class})
public class AureliaController {

    @ResponseBody
    @RequestMapping("/")
    String home() {
        System.out.println(System.getProperty("logging.level.org.springframework"));
        System.out.println(System.getenv("logging.level.org.springframework"));
        return "Hello World! My name is Aurelia. I'm the Applicant Tracking System.";
    }

    @ResponseBody
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    AtsResponse apply(@RequestBody AtsRequest atsiRequest) {
        storeApplicantData(atsiRequest);
        String backUrl = "https://ats.com/complete-page";
        return AtsResponseBuilder.builder().withRedirection(backUrl).withStatus(AtsResponseHeader.Status.OK).build();
    }

    private void storeApplicantData(AtsRequest atsiRequest) {
        Attachment cv = atsiRequest.getBody().getCV();
        String effectiveUrl = atsiRequest.getHeader().getEffectiveUrl();
        // ...
        // ... your logic
        // ...
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AureliaController.class, args);
    }

}
