# Data Converter
#### A Data Mesh Microservice

The Data Converter is an Akka HTTP server designed to convert incoming files / single data rows to Avro records and push them to Kafka. It provides two routes for handling requests:

1. `/batchReq/<url-encoded-s3-url>`

2. `/singleReq/<base64-encoded-json-data>`

Both the Routes are explained in detail below.

## Getting Started

Follow the instructions below to set up and run the Data Converter server.

### Prerequisites

- Scala 2.12 and Java 11
- sbt 1.9.2
- Akka HTTP, Akka Actor and Akka Stream (Corresponding libraries)
- Kafka - Local, msk or confluent kafka cluster, along with kafka-client and serialization/deserialization libraries
- Avro
- S3 Bucket (if using batchReq route) - The ec2 instance on which data converter is deployed should have the read access to the s3 file shared via batchReq route

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/gupta-siddhant/projects.git
   ```

2. Navigate to the project directory:
   ```bash
   cd projects/data-converter
   ```

3. Build the project:
   ```bash
   sbt clean compile assembly
   ```

### Configuration

Make sure to configure the Kafka broker details and other necessary settings in the `application.conf` file.

### Usage

To start the Data Converter server, run the following command:
```bash
java -Dconfig.file=/<path-to>/application.conf -jar immo-DataConverter-1.0.0.jar
```

## Routes

### Batch Request
- **Route**: `/batchReq/<url-encoded-s3-url>`
- **Method**: GET
- **Description**: This route converts the data rows from file located at the specified S3 URL to Avro records and pushes them to Kafka in batches.
- **Supported File Types**: json, csv and xml

### Single Request
- **Route**: `/singleReq/<base64-encoded-json-data>`
- **Method**: GET
- **Description**: This route converts the base64-encoded JSON data row to Avro record and pushes them to Kafka individually.
