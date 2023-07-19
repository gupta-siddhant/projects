# Data Converter
#### A Data Mesh Microservice

The Data Converter is an Akka HTTP server designed to convert incoming files / single data rows to Avro records and push them to Kafka. It provides two routes for handling requests:

1. `/batchReq/<url-encoded-s3-url>`

2. `/singleReq/<base64-encoded-json-data>`

Both the Routes are explained in detail below.

## Getting Started

Follow the instructions below to set up and run the Data Converter server.

### Prerequisites

- Scala 2.12
- Akka HTTP
- Kafka
- Avro
- S3 Bucket (if using batchReq route)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/data-converter.git
   ```

2. Navigate to the project directory:
   ```bash
   cd data-converter
   ```

3. Build the project:
   ```bash
   sbt compile
   ```

### Configuration

Make sure to configure the Kafka broker details and other necessary settings in the `application.conf` file.

### Usage

To start the Data Converter server, run the following command:
```bash
sbt run
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
