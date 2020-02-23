Sales Order API

The aim of this exercise is to implement a water ordering API so farmers can request water to irrigate their farms. Farmers can use this API to place water orders, view existing water orders and cancel water orders before they are delivered.

A basic water order has the following attributes:
• farmId – A unique ID for identifying a farm.
• Start date time – The date and time when water should be delivered.
• Duration – The duration of the order (e.g. Duration of 3 hours means water will flow into the
farm for 3 hours from the start date time).

The solution exposes the APIs. The proposed architecture of the solution uses Spring Boot to expose the REST APIs which are subsequently consumed by the UI. This makes sure that the backend remains compatible with both web and mobile as front end. The following REST APIs have been exposed:

createSalesOrder
cancelSalesOrder
findSalesOrderByFarmId
findSalesOrderBySalesOrderId
findSalesOrderByFarmIdAndStartDateTime

Assmptions:
1. Maven is preinstalled.
2. Chosen stack is Java/SpringBoot 2.0.8.
3. Uses postman and Spring Test Cases for integration testing.
4. Java is preinstalled.

Build and Execution:
1. Change into springwiz-sales-order-api.
2. Run mvn install.
3. Run java -jar ./springwiz-salesorder-web/target/springwizsalesorderapp.jar

Features:
1. REST API built in Java/SpringBoot which accepts new orders from a farmer.
2. Allows cancellelation of an existing Sales Order.
3. Farmers can find existing Sales Orders using Farm Id, SalesOrder Id and Delivery Date.
4. An eventing mechanism is baked into the API which publishes them to the console.

Scalability:
The Solution could be deployed on Cloud or a Clustering Solution such as Kubernetes in order to scale the 
API to handle the requisite load. The Solution also would benefit from the Eventual Consistency, Partition
Tolerance and Replication features of Cassandra Data Store.

Improvements:
1. Use Spring Batch to process the Events from an external event store. Use RabbitMQ as an external event store.
2. Extent the publisher to use kafka as an Event store for a high availability solution.
3. Publish the notifications to not just console but also to other destinations such as mobiles devices and email
   addresses.
4. Use a database to serialize the Sales Orders,use eventual consistency(couch db), if transactions are not needed
   and high volume is anticipated.
