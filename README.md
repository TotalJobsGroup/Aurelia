# Aurelia
Aurelia is an example of ATS Endpoint that can consume example ATS Request and produce example ATS Response both valid from TotalJobs ATSi Consumer Guide.

#### Building the application
To build the application use a following command:
```gradlew clean uberjar```

#### Running the application
To run the application use a following command:
```java -jar build\libs\aurelia-all-1.0-SNAPSHOT.jar```

There will be a long log file, which should end with:

```ruby
13:26:07.950 [main] INFO org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer - Tomcat started on port(s): 8080 (http)
13:26:07.956 [main] INFO com.totaljobsgroup.aurelia.AureliaController - Started AureliaController in 8.055 seconds (JVM running for 8.352)
```

You can test it in your web browser by opening a following link:
http://localhost:8080

You should see:
 > Hello World! My name is Aurelia. I'm the Applicant Tracking System.

The application is now up and running. You can test the endpoint by sending HTTP POST request to http://localhost:8080/apply using e.g. Chrome Postman, curl or any other HTTP/REST client you want.

The request should contain at least a header:
 ```
 Content-Type: application/json
 ```

And example following body:

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

You should receive a following response:

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


