# sprint-kafka-producer

This project was created to demostrate how to receive events from salesforce and put them in kafka. 

To be able to use it you need to:
1. Create an account on [https://developer.salesforce.com/](https://developer.salesforce.com/)
2. Create a custom object. As described [here](https://developer.salesforce.com/docs/atlas.en-us.api_streaming.meta/api_streaming/code_sample_java_create_object.htm)
3. Create a tab using the custom object. You can visit [this link](https://help.salesforce.com/articleView?id=creating_custom_object_tabs.htm&type=5).
4. Create a publishToSubscribe channel (this will use long polling)

```java
PushTopic pushTopic = new PushTopic();
pushTopic.Name = 'InvoiceStatementUpdates';
pushTopic.Query = 'SELECT Id, Name, Status__c, Description__c FROM Invoice_Statement__c';
pushTopic.ApiVersion = 44.0;
pushTopic.NotifyForOperationCreate = true;
pushTopic.NotifyForOperationUpdate = true;
pushTopic.NotifyForOperationUndelete = true;
pushTopic.NotifyForOperationDelete = true;
pushTopic.NotifyForFields = 'Referenced';
insert pushTopic;
```
5. Start the kafka broker provided in this project. 
```bash
docker-compose up
```
6. Start the application (providing user, password, token and channel to subscribe, optionally use the replayMode)
7. Once everything is ready

You will receive events as specified in the publishToTopic query. 

Examples of events:

Create:
```json
{"event":{"createdDate":"2018-10-18T14:53:33.777Z","replayId":5,"type":"created"},"sobject":{"Description__c":"Testing notifications","Id":"a001t000002SlMpAAK","Status__c":"Open","Name":"INV-0004"}}

```
Update: 
```json
{"event":{"createdDate":"2018-10-18T14:53:42.068Z","replayId":6,"type":"updated"},"sobject":{"Description__c":"Testing notifications","Id":"a001t000002SlMpAAK","Status__c":"Closed","Name":"INV-0004"}}
```
Delete: 
```json
{"event":{"createdDate":"2018-10-18T14:53:48.101Z","replayId":7,"type":"deleted"},"sobject":{"Id":"a001t000002SlMpAAK"}}
```

To enable datadog use the following: http://micrometer.io/docs/registry/datadog 
