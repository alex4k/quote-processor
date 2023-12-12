# quote-processor
Java REST microservice sample. It contains the logic to work with "Quote" objects at the backend side and provides REST API for the following:
- create/view users
- create/view/remove quotes, getting top, worst and rando quotes
- vote for quotes by users
- simple analysis for the vouting process in time

- [Building](#building)
- [Public HTTP API](#http-api)

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

## HTTP-API

### Method: create a user
POST /users

JSON body example:
```javascript
{  "name": "User Name",
   "email": "UserNamed@test.com",
   "password": "password"
}
```
#### Response
Upon a successful conversion: HTTP 200, with repeating the initial body plus userId, timestamp and landing page attributes:
```javascript
{  "userId": 1,
   "name": "User Name",
   "email": "UserNamed@test.com",
   "password": "password",
   "landingPage": "path_to_user_page",
   "created_time": "2023-12-08T15:08:00Z"
}
```
### Method: get a user
GET /users/{id}

#### Response
Upon a successful conversion: HTTP 200, with user details:
```javascript
{   "userId": 1,
   "name": "User Name",
   "email": "UserNamed@test.com",
   "password": "password",
    "landingPage": "path_to_user_page",
   "created_time": "2023-12-08T15:08:00Z"
}
```

### Method: get users
GET /users

#### Response
Upon a successful conversion: HTTP 200, with array of users
```javascript
[
  { "userId": 1,
     "name": "User Name",
    ...
  },
  { "userId": 2,
    "name": "User1",
    ...
  }
...
]
```
### Method: create a quote
POST /quotes

JSON body example:
```javascript
{
    "content": "The only limit to our realization of tomorrow will be our doubts of today !!!. ",    
     "owner": {
            "userId": 1                 
    }
}
```
#### Response
Upon a successful conversion: HTTP 200 and the response:
```javascript
{
    "quoteId": 31,
    "content": "The only limit to our realization of tomorrow will be our doubts of today !!!. ",
    "createdTime": "2023-12-12T13:10:45Z",
    "totalVotes": 0,
    "lastVoted": "2023-12-12T13:10:45Z",
    "owner": {
        "userId": 1,
        "name": "BlazeRider",
        "landingPage": "path_to_user_page"
    }
}
```
