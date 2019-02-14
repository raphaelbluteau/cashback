# Cashback API using Spotify Web API

[![Build Status](https://travis-ci.com/raphaelbluteau/cashback.svg?branch=master)](https://travis-ci.com/raphaelbluteau/cashback)
[![BCH compliance](https://bettercodehub.com/edge/badge/raphaelbluteau/cashback?branch=master)](https://bettercodehub.com/)

To build and run the project, use Gradle:
```sh
gradle bootRun
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
