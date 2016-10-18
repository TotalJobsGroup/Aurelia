# Aurelia – ATSi Apply Reference Implementation
Aurelia is a reference implementation of an ATS endpoint for ATSi Apply. The purpose of Aurelia is to provide a working example of an ATS endpoint as a guide for developers integrating with ATSi Apply and should be used in conjunction with the ATSi Apply Consumer Guide.

ATSi Apply can significantly reduce applicant drop-off between the job board and an ATS, leading to more applications for the customers we share.

The ATSi Apply service takes applicant details and CVs collected by the job board and transfers them directly to integrated ATSs. The details can then be used to complete the application automatically or, where more information is required by the recruiter, to pre-populate an application form and pre-load the CV so the applicant only has to fill in the gaps.

The Aurelia implementation consumes example ATS Requests and produces example ATS Responses compliant with the ATSi Apply Consumer Guide.

Aurelia is written in Java language with Spring framework and uses the Gradle build tool, however it is possible to use any technology that supports RESTful services. The data model for requests and responses is defined in json schema and can be reused in any other technologies that support json schema.

The following steps will guide you through the process of building, running and testing the Aurelia application. To build and run the application you need Java 1.8 or later.

The application will be built with Gradle which will download all necessary dependencies. If you have a proxy in your organization you can configure it in the `gradle.properties` file which is placed in the main folder of the project. An example proxy configuration is present and commented out.

#### Building the application

###### Windows environment:
Just use the following command:
`gradlew clean uberjar`

###### Linux environment:
First check that the gradlew file is executable and then use the following command:
`./gradlew clean uberjar`

#### Running the application
To run the application use the following command:
```java -jar build\libs\aurelia-all-1.0-SNAPSHOT.jar```

The application starts on port number 8080. If you already have another service bound to that port you can change port number with -Dserver.port option:
```java -Dserver.port=9090 -jar build\libs\aurelia-all-1.0-SNAPSHOT.jar```

To check what ports are already bound you can use the following command: ```netstat -atn```

A long log file will appear that should end with:

```ruby
2016-09-27 15:44:44.285  INFO   --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2016-09-27 15:44:44.291  INFO   --- [           main] c.t.aurelia.AureliaController            : Started AureliaController in 4.023 seconds (JVM running for 4.413)
```

You can test it in your web browser by opening the following link:
http://localhost:8080

You should see:
> Hello World! My name is Aurelia. I'm an Applicant Tracking System.

The application is now up and running. You can test the endpoint by sending an HTTP POST request to http://localhost:8080/apply using Chrome Postman, curl or any other HTTP/REST client.

There are 3 scenarios in the reference implementation. The scenario depends on the request content. Here is the list of possible answers:
- OK status with redirection to example URL: https://ats.com/complete-page
- ERROR status with Error Message: JOB_NOT_AVAILABLE
- OK status without redirection

The scenario is selected based on the last 2 digits of the effectiveUrl sent in the request:
- If effectiveUrl ends with **45**: you will get status: OK with redirection
- If effectiveUrl ends with **89**: you will get status: ERROR with Error Message: JOB_NOT_AVAILABLE
- In all other cases you will get status: OK without redirection

This is of course only an example implementation. We provide three different scenarios with three different responses in the reference implementation. In your application you will need to implement the use cases and business rules appropriate for your ATS and your customers.

Here are example requests and responses that follow the above rules. Every request should contain at least a header:
```
Content-Type: application/json
```

This is a body of an example request where effectiveUrl ends with **45**:

```
{
  "Header": {
    "JobBoardId": "totaljobs",
    "OriginalUrl": "http://trackingservice.com/recruitmentcompany/12345",
    "EffectiveUrl": "http://recruitmentcompany.co.uk/12345",
    "JobBoardName": "Totaljobs"
  },
  "Body": {
    "Email": "ivan.krasinski@gmail.com",
    "MessageToRecruiter": "Hello it's me",
    "AdditionalDocuments": [{
      "FileName": "Certificate.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDZXJ0aWZpY2F0ZQ=="
      },
      {
      "FileName": "Licence.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBMaWNlbmNl"
      }
    ],
    "CoverLetter": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDb3ZlciBMZXR0ZXIu"
    },
    "CV": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDVi4="
    },
    "Title": "Mr",
    "FirstName": "Ivan",
    "Surname": "Krasinski",
    "JobTitle": "Manager",
    "SalaryType": "Annual",
    "SalaryRangeMin": "40000",
    "SalaryRangeMax": "50000",
    "SalaryCurrency": "GBP",
    "WorkEligibility": "UK"
  }
}
```

Following the rules described above we will receive status: OK with redirection:

```
{
  "Header": {
    "Status": "OK"
  },
  "Body": {
    "BackUrl": "https://ats.com/complete-page",
    "Redirect": "YES"
  }
}
```

If you change the effectiveUrl to end with **89**:

```
{
  "Header": {
    "JobBoardId": "totaljobs",
    "OriginalUrl": "http://trackingservice.com/recruitmentcompany/12345",
    "EffectiveUrl": "http://recruitmentcompany.co.uk/12389",
    "JobBoardName": "Totaljobs"
  },
  "Body": {
    "Email": "ivan.krasinski@gmail.com",
    "MessageToRecruiter": "Hello it's me",
    "AdditionalDocuments": [{
      "FileName": "Certificate.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDZXJ0aWZpY2F0ZQ=="
      },
      {
      "FileName": "Licence.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBMaWNlbmNl"
      }
    ],
    "CoverLetter": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDb3ZlciBMZXR0ZXIu"
    },
    "CV": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDVi4="
    },
    "Title": "Mr",
    "FirstName": "Ivan",
    "Surname": "Krasinski",
    "JobTitle": "Manager",
    "SalaryType": "Annual",
    "SalaryRangeMin": "40000",
    "SalaryRangeMax": "50000",
    "SalaryCurrency": "GBP",
    "WorkEligibility": "UK"
  }
}
```

You will get status: ERROR with Error Message JOB_NOT_AVAILABLE:

```
{
  "Header": {
    "Status": "ERROR",
    "ErrorMsg": "JOB_NOT_AVAILABLE"
  },
  "Body": {
    "BackUrl": "",
    "Redirect": "NO"
  }
}
```

And if you change effectiveUrl to end with number other than **45** and **89**, e.g. **12**:

```
{
  "Header": {
    "JobBoardId": "totaljobs",
    "OriginalUrl": "http://trackingservice.com/recruitmentcompany/12345",
    "EffectiveUrl": "http://recruitmentcompany.co.uk/12312",
    "JobBoardName": "Totaljobs"
  },
  "Body": {
    "Email": "ivan.krasinski@gmail.com",
    "MessageToRecruiter": "Hello it's me",
    "AdditionalDocuments": [{
      "FileName": "Certificate.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDZXJ0aWZpY2F0ZQ=="
      },
      {
      "FileName": "Licence.txt",
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBMaWNlbmNl"
      }
    ],
    "CoverLetter": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDb3ZlciBMZXR0ZXIu"
    },
    "CV": {
      "FileType": "txt",
      "BinaryData": "VGhpcyBpcyBteSBDVi4="
    },
    "Title": "Mr",
    "FirstName": "Ivan",
    "Surname": "Krasinski",
    "JobTitle": "Manager",
    "SalaryType": "Annual",
    "SalaryRangeMin": "40000",
    "SalaryRangeMax": "50000",
    "SalaryCurrency": "GBP",
    "WorkEligibility": "UK"
  }
}
```

You will get status: OK without redirection:

```
{
  "Header": {
    "Status": "OK"
  },
  "Body": {
    "BackUrl": "",
    "Redirect": "NO"
  }
}
```
