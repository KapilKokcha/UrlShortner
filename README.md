# URL Shortener

URL Shortener is a simple spring boot application that allows you to shorten long URLs with specified expiry time.

## Setup Instructions

To run the application locally, follow these setup instructions:

There are 2 ways to run the application:

A. Using Doker

Make sure you have Docker and Docker Compose installed on your machine.

- [Docker](https://www.docker.com/get-started)

### Getting Started

1. Clone the repository:

    ```bash
    git clone https://github.com/KapilKokcha/UrlShortner.git
    cd UrlShortner
    ```
2. Build and Run:

   ```bash
   docker-compose build
   docker-compose up
   ```

   This will start both the Spring Boot application and the MySQL database in separate containers. Access your Spring Boot app at [http://localhost:8080](http://localhost:8080). 

B. Import this project in an IDE or use cli maven commands.

## API Signatures

1. Register

    ```bash
    curl --location --request POST 'http://localhost:8080/api/register' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "username" : "user1",
            "password" : "1234"
        }'
    ```

2. Log-In

    ```bash
    curl --location --request POST 'http://localhost:8080/api/login' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "username" : "user1",
            "password" : "1234"
        }'
    ```

3. Create Short URL

    ```bash
    curl --location --request POST 'http://localhost:8080/api/shortenUrl' \
        --header 'Authorization: Bearer <AUTH-TOKEN>' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "originalUrl" : "https://developer.mozilla.org/en-US/docs/Web/API/EventTarget/removeEventListener",
            "expiryTimeInHour" : 1
        }' 
    ```

4. Access Short URL

    ```bash
    curl --location --request GET 'http://localhost:8080/api/originalRedirect?shortUrl=YmQyMGI0'
    ```
