# clearpoint-api-testing

## Pre-requisites
* Make sure maven and Docker are installed.
* Make sure java11+ is installed.

### Start the API service
* Checkout the sre-assessment project
```
git clone https://github.com/ClearPointNZ/sre-assessment
cd sre-assessment
docker-compose up
```

### Run the tests

```
mvn test
```