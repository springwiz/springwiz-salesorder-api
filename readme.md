Solution:

Backend(Enterprise APIs):
The Backend of the solution exposes the Enterprise APIs. The proposed architecture of the solution uses Spring Boot 
to expose the REST APIs which are subsequently consumed by the UI. This makes sure that the backend remains compatible 
with both web and mobile as front end. The following REST APIs have been exposed:

findInventory
AdjustInventory
getProduct
changeProduct
getProductPrice
getStoreDetails

The REST Apis could further rely further on the concept of HATEOAS to propagate the representations back to the client 
which could be used in the UI to direct navigation, however this is yet to be done and could be taken up as further 
enhancements. 

UI(Angular2):
The Enterprise APIs exposed by backend are consumed by Angular UI.

Data Store(Cassandra):
The Solution relies on Cassandra data storage to store the Catalog and Inventory Data. The Enterprise APIs use the
Cassandra driver to look up and write the data entities into Cassandra data store. The seed scripts have been written
in Java which could load the data in the form of CSV Files into Cassandra Database. The seed scripts use the BatchStatement
and the Asynchronous execution of the Insert Statements in order to handle very large CSV Files.

Schema Details:
btp_category:         id, name, description
btp_product:          id, name, description, category_id, color, make, size, price
btp_store_location:   id, name, description, address, city, country, zipcode, longitude, latitude
btp_store:            id, name, description, address, city, country, zipcode, longitude, latitude
btp_inventory_supply: id, store_id, product_id, quantity

Scalability:
The Solution could be deployed on Cloud or a Clustering Solution such as Kubernetes or Docker Swarm in order to scale the 
Enterprise APIs to handle the requisite load. The Solution also would benefit from the Eventual Consistency, Partition
Tolerance and Replication features of Cassandra Data Store.