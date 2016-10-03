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

The request should contain at least a header:
 ```
 Content-Type: application/json
 ```

And, as an example, the following body:

```
{
	"Header": {
		"JobBoardId": "totaljobs",
		"OriginalUrl": "http://aplitrack.com/hays/12345",
		"EffectiveUrl": "http://hays.co.uk/12345",
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

You should receive the following response:

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


