# Cashback API using Spotify Web API

[![Build Status](https://travis-ci.com/raphaelbluteau/cashback.svg?branch=master)](https://travis-ci.com/raphaelbluteau/cashback)
[![Coverage Status](https://coveralls.io/repos/github/raphaelbluteau/cashback/badge.svg?branch=master)](https://coveralls.io/github/raphaelbluteau/cashback?branch=master)
[![BCH compliance](https://bettercodehub.com/edge/badge/raphaelbluteau/cashback?branch=master)](https://bettercodehub.com/)

This Spring Boot API collects some data from Spotify Web API and provides endpoints to sell albums and calculate cashback over every operation.

To build and run the project, use Gradle:
```sh
gradle bootRun
```

If you feel the need to run this application without hitting Spotify API for test purposes, you can use the mock profile:
```sh
gradle bootRun -Dspring.profiles.active=mock
```

## Endpoints usage examples:

### Getting albums by genre

GET /albums?genre=ROCK&amp; size=10&amp; page=1 HTTP/1.1

Host: localhost:8080

Content-Type: application/json

cache-control: no-cache

Postman-Token: 40550238-841f-4cc8-85a0-3d38960de3c6

### Getting album by id

GET /albums/545 HTTP/1.1

Host: localhost:8080

cache-control: no-cache


### Selling albums (album ids required)

POST /orders HTTP/1.1

Host: localhost:8080

Content-Type: application/json

cache-control: no-cache

[

	{
	
		"id": 696
		
	}
	
]


### Getting order by id

GET /orders/321 HTTP/1.1

Host: localhost:8080

cache-control: no-cache


### Getting orders by period

GET /orders?begin=2019-01-01T01:30:00.000-02:00&amp; end=2019-02-10T10:59:00.000-02:00&amp; size=1&amp; page=1 HTTP/1.1

Host: localhost:8080

cache-control: no-cache
