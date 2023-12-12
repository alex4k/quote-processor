# quote-processor
Java REST microservice sample. It contains the logic to work with "Quote" objects at the backend side and provides REST API for the following:
- create/view users
- create/view/remove quotes, getting top, worst and rando quotes
- vote for quotes by users
- simple analysis for the vouting process in time

- [Building](#building)
- [Internal HTTP API](#http-api)
- [Starting the Service](#start-service)

## Building

Note: I have verified the applicaion on my Linux workstation with Ubuntu 22.04 and Java 17.

### Linux
- [Java 17] (https://adoptium.net/temurin/releases/?os=linux&version=17), installation instructions for latest version included in the link. Java 17 is the minimum version.
- [Maven] (https://maven.apache.org/download.cgi) It requires the Maven installed as well.

### Commands 
To build the application run the following command:
`mvn package`

To run just tests:
`mvn verify`

### Scripts

There is the script for cretaing the Docker image. To make it run

`docker build . -t quote-processor:latest`

To run the container execute the following:

`docker run -it -p 8080:8080 -name quote-processor-instance quote-processor:latest`

BTW: there is the ready image in Docker Hub - https://hub.docker.com/repository/docker/testjuin/quote-processor


