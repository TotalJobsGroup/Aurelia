# Aurelia
Aurelia is an example of ATS Endpoint that can consume an example ATS Request and produce an example ATS Response, both compliant with TotalJobs ATSi Apply Consumer Guide.
It is a reference implementation of the endpoint written in Java language with Spring framework and Gradle build tool, however it is possible to use any technology that supports RESTful services.
The data model for requests and responses is defined in json schema and can be reused in any other technologies that supports json schema.
The following steps will guide you through the process of building, running and testing the application.
To build and run the application you need Java 1.8 or later.

The application will be built with Gradle that will download all necessary dependencies. If you have a proxy in your organization you can configure it in `gradle.properties` file which is placed in the main folder of the project.
You can find there an example proxy configuration which is now commented out.

#### Building the application

###### Windows environment:
Just use the following command:
`gradlew clean uberjar`

###### Linux environment:
First check if gradlew file is executable and then use the following command:
`./gradlew clean uberjar`

#### Running the application
To run the application use the following command:
```java -jar build\libs\aurelia-all-1.0-SNAPSHOT.jar```

The application starts on port number 8080. If you have some other servive bound to that port you can change port number with -Dserver.port option, i.e.:
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

The application is now up and running. You can test the endpoint by sending HTTP POST request to http://localhost:8080/apply using e.g. Chrome Postman, curl or any other HTTP/REST client you want.

There are 3 scenarios in the given implementation. Depends on the request content here is the list of possible answers:
- OK status with redirection to example URL: https://ats.com/complete-page
- ERROR status with Error Message: JOB_NOT_AVAILABLE
- OK status without redirection

The implementation chooses the scenario based on effectiveUrl which is sent in the request. The crucial are last 2 digits of effectiveUrl:
- effectiveUrl ends with **45**: you will get status: OK with redirection
- effectiveUrl ends with **89**: you will get status: ERROR with Error Message: JOB_NOT_AVAILABLE
- in other cases you will get status: OK without redirection

This is of course only example implementation. The purpouse is to provive 3 different scenarios with 3 different responces in the refference implementation. In your application of course rules will be different.

Here are example requests and responses that follows the above rules. Every request should contain at least a header:
```
Content-Type: application/json
```

This is a body of example request with effectiveUrl that ends with **45**:

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

If you change the effectiveUrl to ends with **89**:

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

And if you change effectiveUrl to ends with number any but not **45** and **89**, i.e. **12**:

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
