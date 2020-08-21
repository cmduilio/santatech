# Challenge Meetups

Challenge Meetups is an API that allows organizations to create meetups.

## Clone repository
```bash
https://git-rba.hackerrank.com/git/be5fef19-648f-4f66-a067-49011ea835ec
```

## Installation

Install [NodeJs](https://nodejs.org/en/download/)

The package manager needed, npm, is installed with NodeJs.

## Check NodeJS and npm version

The NodeJS version need is 10.19.0 or superior.

The npm version need is 6.14.4 or superior.

```bash
node -v
```
```bash
npm -v
```

## Download dependencies

```bash
npm install
```

## Database

Use mongoDB to create a database for this API, in src/index.js you can modify the port and schema name (default schema name: meetup and port: 27017).

## Run

To run the api execute the command 
```bash
npm run-script run
```

## Endpoints

POST /user/create

```bash
curl --location --request POST 'http://localhost:3000/user/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan",
    "role": "admin"
}'
```
Response

```bash
{
    "user": {
        "meetups": [],
        "_id": "5f20a4e43edbde8d6a820f5f",
        "name": "Juan",
        "role": "admin",
        "__v": 0
    }
}
```

POST /meetup/create

Comment: Date has to be in the format YYYY-MM-DD and cannot be schedule after 5 days from current day because of limitations in the weather forecast API (https://openweathermap.org/api).

```bash
curl --location --request POST 'http://localhost:3000/meetup/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "date": "2020-07-30",
    "description": "Meetup test",
    "user_id": "5f20a4e43edbde8d6a820f5f"
}'
```

Response

```bash
{
    "meetup": {
        "users": [],
        "_id": "5f20a5bc986b4d8ed9fec585",
        "date": "2020-07-30",
        "description": "Meetup test",
        "__v": 0
    }
}
```

PUT /meetup/checkin

```bash
curl --location --request PUT 'http://localhost:3000/meetup/checkin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "user_id": "5f20a4e43edbde8d6a820f5f",
    "meetup_id": "5f20a5bc986b4d8ed9fec585"
} '
```

Response example:
```bash
{
    "user": {
        "meetups": [
            {
                "users": [],
                "_id": "5f20a5bc986b4d8ed9fec585",
                "date": "2020-07-30",
                "description": "Meetup test",
                "__v": 0
            }
        ],
        "_id": "5f20a702986b4d8ed9fec586",
        "name": "Pepe",
        "role": "user",
        "__v": 1
    }
}
```
PUT /meetup/join

```bash
curl --location --request PUT 'http://localhost:3000/meetup/join' \
--header 'Content-Type: application/json' \
--data-raw '{
    "user_id": "5f20a702986b4d8ed9fec586",
    "meetup_id": "5f20a5bc986b4d8ed9fec585"
} '
```

Response
```bash
{
    "meetup": {
        "users": [
            {
                "meetups": [
                    "5f20a5bc986b4d8ed9fec585"
                ],
                "_id": "5f20a702986b4d8ed9fec586",
                "name": "Pepe",
                "role": "user",
                "__v": 1
            }
        ],
        "_id": "5f20a5bc986b4d8ed9fec585",
        "date": "2020-07-30",
        "description": "Meetup test",
        "__v": 1
    }
}
```

GET /meetup/list

```bash
curl --location --request GET 'http://localhost:3000/meetup/list'
```

Response
```bash
[
    {
        "users": [
            "5f20a702986b4d8ed9fec586"
        ],
        "_id": "5f20a5bc986b4d8ed9fec585",
        "date": "2020-07-30",
        "description": "Meetup test",
        "__v": 1
    }
]
```

GET /birras

```bash
curl --location --request GET 'http://localhost:3000/birras/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "meetup_id": "5f20a5bc986b4d8ed9fec585",
    "user_id": "5f20a4e43edbde8d6a820f5f"
}'
```

Response
```bash
{
    "beersToBuy": 6
}
```

GET /meetup/weather

```bash
curl --location --request GET 'http://localhost:3000/meetup/weather' \
--header 'Content-Type: application/json' \
--data-raw '{
    "meetup_id": "5f20a5bc986b4d8ed9fec585",
    "user_id": "5f20a4e43edbde8d6a820f5f"
}'
```
Response
```bash
{
    "temperatureOfMeetupDay": 7.79
}
```