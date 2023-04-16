# Elastic Stack with Docker

This repository contains a sample setup for the Elastic Stack (Elasticsearch, Logstash, and Kibana) using Docker Compose.

## Requirements

* Docker version 20.10 or higher
* Docker Compose version 1.29 or higher

## Usage

### Starting the Stack

1. Clone this repository.
2. Navigate to the repository folder in a terminal.
3. Run the following command to start the Elastic Stack:

   ```bash
   docker-compose up
   ```
4. Wait for Elasticsearch, Logstash, and Kibana to start up (this may take a few minutes).

## Stopping the Stack
To stop the Elastic Stack, press Ctrl+C in the terminal window where you started Docker Compose.

To stop and remove all containers, networks, and volumes created by Docker Compose, run the following command in the repository folder:

   ```bash
   docker-compose down
   ```
## Accessing Kibana

Kibana is accessible at http://localhost:5601 in a web browser.

## License

This repository is licensed under the MIT License. See the LICENSE file for details.



