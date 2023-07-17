# Reservation manager
Recruitment task for KLG Solutions by Jakub Zmuda, 02.07.2023 (update 17.07.2023)

## Requirements

The requirements of the application are described in `zadanie_referencyjne_Java_Spring.pdf` file (in Polish).

## Accessing and running the application 

The application is deployed into AWS Elastic Beanstalk and can be accessed under the following link:
http://reservation-manager-klg.eu-central-1.elasticbeanstalk.com

Alternatively, there are several ways to compile and run the app locally:

- In IDE: you can open cloned repository in your favourite IDE (e.g. IntelliJ IDEA) and run it from there.
- Maven: navigate into the root catalogue of this repository, open terminal and run the following command: `./mvnw spring-boot:run`
- Docker: navigate into the root catalogue of this repository, open terminal and run the following command: `docker-compose up --build`

If you have successfully run the app locally, open your browser http://localhost:8080, and you should see main page of the app.

## Data loading script

On the start of the application, HSQL database tables are filled with some sample data from `src/main/java/resources/data.sql` file. 
Content of _objects_for_rent_ and _people_ tables cannot be changed at runtime.
When you open the app in the browser, you should see names of objects and people, so you can check if data was uploaded correctly. 

## Using the application

### REST Endpoints

There are four endpoints available to make requests (replace &lt;address> with either cloud or local address):

- GET &lt;address>/reservations/object/{name}

    use this endpoint to retrieve all reservations for object with name _name_. 


- GET &lt;address>/reservations/renter/{name}

    use this endpoint to retrieve all reservations for renter with name _name_.


- POST &lt;address>/reservations

  use this endpoint to create new reservation. Example structure of the request:
```
{
    "object": {
        "name": "farm stay"
    },
    "landlord": {
        "name": "Geralt of rivia"
    },
    "renter": {
        "name": "James Bond"
    },
    "startDate": "2023-05-08",
    "endDate": "2023-05-13",
    "cost": 1000
}
```
For _object_, _landlord_ and _renter_ field you have to provide `name` field (case-insensitive, also all other fields will be ignored)

- PUT &lt;address>/reservations

  use this endpoint to update existing reservation. Example structure of the request:
```
{
    "id": 1,
    "object": {
        "name": "farm stay"
    },
    "landlord": {
        "name": "Geralt of rivia"
    },
    "renter": {
        "name": "James Bond"
    },
    "startDate": "2023-05-08",
    "endDate": "2023-05-13",
    "cost": 1000
}
```
As before, for _object_, _landlord_ and _renter_ field you have to provide `name` field. Additionally, you have to provide `id` of the reservation you want to update.


You can check all possible object and person names in the main page of the application.

### Reports

You can generate two kinds of HTML reports:

1. days and number of reservations of object for a range of time

    navigate to &lt;address>/reports/object in your browser and provide 3 parameters:
    - _object_ - name of the object (case-insensitive)
    - _from_ - lower bound of the range of time in format YYYY-MM-DD
    - _to_ - upper bound of the range of time in format YYYY-MM-DD
   
   example: &lt;address>/reports/object?object=farm%20stay&from=2023-01-01&to=2023-06-30


2. for every landlord, number of distinct object he rented, how many guests he had in total and his total income

    navigate to &lt;address>/reports/landlords in your browser and provide 2 parameters:
    - _from_ - lower bound of the range of time in format YYYY-MM-DD
    - _to_ - upper bound of the range of time in format YYYY-MM-DD
    
    example: &lt;address>/reports/landlords?from=2023-01-01&to=2023-07-01

## Tests

You can run unit tests either in you favourite IDE or by running command `./mvnw test`.