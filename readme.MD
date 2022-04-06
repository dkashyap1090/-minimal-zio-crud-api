## A minimal-zio-crud-api application

### This repo contains a simple example of rest api with all ZIO modules
#### ZIO
#### zhttp
#### zio-sql
#### zio-config
#### zio-json
#### zio-test


## About application

# Introduction
This repo contains a simple example of CRUD Operation

Examples in repo include:
1. GET
2. PUT
3. POST
4. DELETE

## To Start The Application On Your System
1. clone this repo
2. ```git clone git@github.com:dkashyap1090/-minimal-zio-crud-api.git```
3. cd -minimal-zio-crud-api
4. docker-compose up 
5. sbt run 
6. Examples 

   a. GET

Requests:

```curl -i http://localhost:8080/employee/6fa059fa-44ca-4fa6-9401-f550792eb040```
      
Response

```
HTTP/1.1 200 OK
content-type: application/json
content-length: 71

{"id":"6fa059fa-44ca-4fa6-9401-f550792eb040","name":"Tom","role":"CEO"}
 ```

a. DELETE

Requests:

```curl -i -X DELETE http://localhost:8080/employee/6fa059fa-44ca-4fa6-9401-f550792eb040```

Response

```
HTTP/1.1 200 OK
content-type: application/json
content-length: 1

1
 ```

a. POST

Requests:

```curl -i -X POST -H 'Content-Type: application/json' -d '{ "id": "6fa059fa-44ca-4fa6-9401-f550792eb040", "name": "Tom", "role": "CEO" }' http://localhost:8080/employee/```

Response

```
HTTP/1.1 201 Created
content-length: 0
 ```

a. PUT

Requests:

```curl -i -X PUT -H 'Content-Type: application/json' -d '{ "id": "6fa059fa-44ca-4fa6-9401-f550792eb040", "name": "Tom", "role": "CEO" }' http://localhost:8080/employee/```

Response

```
{
   "id": "6fa059fa-44ca-4fa6-9401-f550792eb040",
  "name": "Tom",
  "role": "CEO"
 }
 ```