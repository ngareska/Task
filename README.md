# Evaluation Task
Welcome to the Credit Card Validation Web Application README. This application offers a simple way to validate credit card details using standard algorithms.

## Installation
### Prerequisites
Before running this application, ensure you have the following prerequisites:

- Java Development Kit (JDK) 17 or higher
- Apache Maven
- Spring Boot 3.1.4

### Build setup
1. Clone this repository to your local machine:

> git clone https://github.com/your-username/credit-card-validation.git

2. Navigate to the project directory:

> cd credit-card-validation

3. Build and run the application:

> mvn spring-boot:run

4. Access the application in your web browser at http://localhost:9091/credit-card.

## Usage
- Enter the credit card number, expiry date (MM/YY), and CVV.
- Click the "Submit" button to validate the credit card details.
- If the validation is successful, a <span style="color: green;">✔</span>
  will be displayed; otherwise, an ❌ will be displayed on the form.
## Project Components

### CreditCard Class
The `CreditCard class` represents the structure of credit card data. It includes methods to set and retrieve card number, expiry date, and CVV.

### CreditCardController
The `CreditCardController` is responsible for handling incoming requests and managing the credit card validation process.
Here's a brief overview of its functionality:

- `GET /credit-card`: Displays the credit card validation form in the web application.
- `POST /api/validate-credit-card`: Accepts a JSON request containing credit card data, validates it, and returns a JSON response indicating the validation result.

The controller uses methods like `validateCreditCardData`, `isAmericanExpress`, `isValidExpiryDate`, and `isValidLuhnAlgorithm` to perform various checks on the credit card data.

### credit-card-form.html
The `credit-card-form.html` template provides the user interface for entering credit card details. It includes the following key components:

- `cardNumber`, `expiryDate`, and `cvv` input fields within a form.
- JavaScript code that handles form submission, makes an API request, and displays the validation result.
- A `pop-up` that appears to show the validation result.

The JavaScript code interacts with the HTML elements to validate credit card details and provide feedback to the user.
# API Endpoint
The application also provides an API endpoint for credit card validation. You can send a POST request to the following endpoint:

`Endpoint: /api/validate-credit-card`\
`Method: POST`\
`Request Body: JSON object containing cardNumber, expiryDate, and cvv fields.`

Example Request Body:

```JSON
{
  "cardNumber": "4532 0151 1283 0364",
  "expiryDate": "12/23",
  "cvv": "123"
}
```
Example Response (Success):

```JSON
{
"success": true,
"message": "Successful!"
}
```
Example Request Body:

```JSON
{
  "cardNumber": "4532 0151 1283 0365",
  "expiryDate": "32/23",
  "cvv": "123"
}
```

Example Response (Failure):

```JSON
{
"success": false,
"message": "Invalid credit card details. Please check your input."
}
```

## Features
- Credit card number validation using the Luhn algorithm.
- Expiry date validation to ensure it's in the future and in the format MM/YY.
- CVV validation based on the card type (American Express or others).


