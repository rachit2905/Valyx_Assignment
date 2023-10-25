# Gmail API Integration and Transaction Management with Spring Boot

This project demonstrates the integration of the Gmail API with a Spring Boot backend server. The goal is to parse PDF attachments from Gmail messages and convert the data into transaction objects, which are stored in a MySQL database. The project also provides a set of API endpoints for accessing transaction data.

## Getting Started

Follow the tutorial video to set up the Gmail API integration with Spring Boot: [Tutorial Video](https://www.youtube.com/watch?v=PVCEPmcFA38&t=840s)

## Parsing PDFs and Data Processing

The project utilizes Maven libraries such as PDFBox, Jsoup, and Rest-Assured for parsing PDF attachments and extracting data.

## API Documentation

You can find the complete documentation for all the APIs developed in this project here: [API Documentation](https://studio-ws.apicur.io/sharing/00a23977-cae1-4153-bad2-144eebd4bcad)

## Source Code

The project's source code is organized as follows:

- **api**: Contains configuration and declarations of APIs and API controllers.
- **model**: Contains the object model for transactions to be saved in the database.
- **Repository**: Contains JPA repository classes for interacting with the MySQL database.
- **Service**: Contains the business logic for the API endpoints.

## Screenshots

View screenshots of the project results [here](https://drive.google.com/file/d/1sPn_XbDGKDOxveDcCl6imfDaDoKQ3Giv/view?usp=sharing).

## Usage

To run the project locally, follow these steps:

1. Clone the repository.
2. Configure your Gmail API credentials.
3. Set up the MySQL database and configure the connection in the application properties.
4. Run the Spring Boot application.
5. Access the provided API endpoints to manage and retrieve transaction data.



## Acknowledgments

- Thanks to the creators of the libraries and tutorials that made this project possible.
