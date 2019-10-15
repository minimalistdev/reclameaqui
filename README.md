# ReclameAqui

## Project Overview

Project to create Complains with Locale and Company.

The project is on delivery continuous using the Bitbucket Pipeline to build, test and deploy on Heroku.

## Requirements

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


## Doc

There is a Postman file on /postman to import on postman with all calls

## Environment Variables

```
MONGODB_URI={mongodb://127.0.0.1:27017/db}
```

## Tests

```shell
gradle test
```

## Running

```shell
gradle bootRun
```
