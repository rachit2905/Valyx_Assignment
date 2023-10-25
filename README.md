This is the backend server made with the help of java springboot to set up gmail api in springboot this tutorial can be followed https://www.youtube.com/watch?v=PVCEPmcFA38&t=840s
After integration of gmail API with the backend server I parsed the pdf with the help of maven libraries such as pdfBox and jsoup,rest-assured
Then I used the data parsed from the pdf to convert it into transactions object and stored it in MySQL database from where all the transaction data culd be accessed by API endpoints
Link for the documentation of all the APIs developed for this project:https://studio-ws.apicur.io/sharing/00a23977-cae1-4153-bad2-144eebd4bcad
The Source code for the project can be accessed from the following path src/main/java/io/swagger
api folder contains all the configuration and declarations of APIs and APIs controllers
model contains the object model for the transaction to be saved in the database
Repository is the folder for JPA repository for our code to interact with our database
Service contains the bussiness logic for the API endpoints
Here is the google drive link for the screenshots of the results:
