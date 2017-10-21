Trade validator


To run this simple trade validation application please use "gradle run" or import the project to IntelliJ IDEA and run TradeValidatorApp from there.

Implemented API allows to:
1. Perform bulk validation of array of trades via /validateTrades POST request.
2. Validate single trades via /validateTrade.

Online API documentation is available at https://app.swaggerhub.com/apis/sjasinski/TradeValidator/1.0.0

To facilitate testing sample test files are available in test_data subdirectory, these can be sent via POST using curl e.g.:

curl -X POST -d @forward.json http://localhost:8080/validateTrade --header "Content-Type:application/json"

curl -X POST -d @trades.json http://localhost:8080/validateTrades --header "Content-Type:application/json"

For the purpose of this exercise all validation rules were fully covered with JUnit tests (100% method and 100% line coverage).

Assumed current date is "2016-08-10" and can be redefined by changing the value of ValidationUtils.CURRENT_DATE.
Please note that this date is different than the original one present in the description.
It was changed solely for consistency with provided data to facilitate testing of validation rules.

It was also assumed that, as in case of the provided test data, valueDate is present and validated only for Spot and Forward trades.


Additionally: 
1. Providing high availability of the service and its scalability was briefly described in "High availability and scalability of the service.docx" in the main directory.
2. Online documentation of the REST API exposed by the service was created using Swagger and is available at https://app.swaggerhub.com/apis/sjasinski/TradeValidator/1.0.0
3. A simple HTML / Bootstrap GUI client is available for testing purposes, it is located in html_client subdirectory.


Stanislaw Jasinski