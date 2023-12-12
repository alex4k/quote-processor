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
Upon a successful conversion: HTTP 201, with repeating the initial body plus userId, timestamp and landing page attributes:
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
Upon a successful conversion: HTTP 201 and the response:
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

### Method: Update a quote
PUT /quotes/{id}
JSON body example:
```javascript
{
    "content": "The only limit to our realization of tomorrow will be our doubts of today !!!. "       
}
```
#### Response
Upon a successful conversion: HTTP 200, with a quote details
```javascript
{
    "quoteId": 1,
    "content": "Updated Content",
    "createdTime": "2023-12-12T13:17:31Z",
    "totalVotes": 15,
    "lastVoted": "2023-12-10T18:15:00Z",
    "owner": {
        "userId": 1,
        "name": "BlazeRider",
        "landingPage": "path_to_user_page"
    }
}
```
### Method: get quotes
GET /quotes

#### Response
Upon a successful conversion: HTTP 200, with array of quotes
```javascript
[
  { "quoteId": 1,
    ...
  },
  { "quoteId": 2,
    ...
  }
...
]
```
### Method: get top 10 quotes
GET /quotes/top10

Returns top 10 rated quotes
#### Response
Upon a successful conversion: HTTP 200, with array of quotes

### Method: get worst 10 quotes
GET /quotes/worst10

Returns worst 10 rated quotes

#### Response
Upon a successful conversion: HTTP 200, with array of quotes.

### Method: get a random quote
GET /quotes/random

Returns a random quote

### Method: Delete a quote
DELETE /quotes/{id}

Deletes a quote with {id}. Also it removes votes for this quote. 

#### Response
Upon a successful conversion: HTTP 200, with string "Quote removed successfully".

### Method: Vote for the quote
POST /vote

Votes for the quote from the provided user
JSON body example:
```javascript
{
   "userId": 21,
   "quoteId": 1       
}
```
#### Response
Upon a successful conversion: HTTP 200, with string "Voted successfully".



