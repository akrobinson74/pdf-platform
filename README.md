# PdfPlatform

## Installation
1. Install SDKMan: https://sdkman.io/install
```
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
```
2. Install AdoptOpenJDK 16.0.1 : ```sdk install java 16.0.1.hs-adpt```
3. Install gradle 7.2: ```sdk install gradle 7.2```
4. Configure application.yml to reflect your Pg setup:
```
spring:
  datasource:
    url: jdbc:postgresql://localhost/pdf_platform
    username: dbadmin
    password: XXXXXXXXXXXXXXXXXXX
```
6. Create a db with same name listed in your application.properties:
```
postgres=# create database pdf_platform;
```
7. If all of the above worked (and you have PostgreSQL running on the standard port), you
should be able to run this appliation as follows:
```
$ cd $PROJECT_DIR
$ ./gradlew bootRun
```

## Using the GraphiQL interface
Presuming an exceptionless start to the application, the GraphiQL interface
should be available at: http://localhost:9000/graphiql and the processing endpoint is:
http://localhost:9000/graphql

Per the schema there are 2 query operations: `getPdf(id: ID!)`, `pdfsWithFilter(filterList: [FilterField!]!)`,
and pdfsWithFilter(filterList: [FilterField!]!)
(where ID is a Long/BigInt).  There are two mutation operation:
```
uploadPdfs: [PdfReference]
uploadPdfsWithMetadata(uploadMetadata: UploadInput!): [PdfReference]
```
Use Postman to upload .pdfs.  In the case of the uploadPdfs operation, the
resolver will split the inputFile name(s) on the '.' character to extract the
clientName, countryCode, reportType, and reportName from the string (eg. '' -> ())

See the following image and ask Adam if you need any help:
![Postman upload screenshot](src/main/resources/Postman-uploadPdfs.png?raw=true)

Enjoy!