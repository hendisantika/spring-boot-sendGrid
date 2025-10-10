# SendGrid Setup Guide

## Overview

This Spring Boot application is configured to send emails using SendGrid's API.

## Prerequisites

1. SendGrid account (sign up at https://sendgrid.com/)
2. SendGrid API Key with Mail Send permissions
3. Verified sender email address in SendGrid

## Configuration

### 1. Get Your SendGrid API Key

- Log in to SendGrid dashboard
- Navigate to Settings > API Keys
- Create a new API Key with "Mail Send" permissions
- Copy the API key (it will only be shown once)

### 2. Verify Your Sender Email

- In SendGrid dashboard, go to Settings > Sender Authentication
- Create a Single Sender and verify your email address
- Use this email as the "from" address

### 3. Configure the Application

You can configure the application in two ways:

#### Option A: Using Environment Variables (Recommended)

```bash
export SENDGRID_API_KEY="your_actual_api_key_here"
export SENDGRID_FROM_EMAIL="your-verified-email@example.com"
export SENDGRID_TO_EMAIL="recipient@example.com"

./mvnw spring-boot:run
```

#### Option B: Update application.properties

Edit `src/main/resources/application.properties`:

```properties
sendgrid.api.key=your_actual_api_key_here
sendgrid.from.email=your-verified-email@example.com
sendgrid.to.email=recipient@example.com
```

**Note:** Never commit your actual API key to version control!

## Running the Application

### Build the Project

```bash
./mvnw clean package
```

### Run the Application

```bash
# Using environment variables
SENDGRID_API_KEY="your_api_key" ./mvnw spring-boot:run

# Or run the JAR
java -jar target/spring-boot-sendGrid-0.0.1-SNAPSHOT.jar
```

## Testing the Email Functionality

### Available Endpoints

#### 1. Send Plain Text Email

```bash
curl -X GET http://localhost:8080/api/send-text
```

This endpoint sends a simple text email using the configured from/to addresses.

#### 2. Send Email with Template

```bash
curl -X GET http://localhost:8080/api/send
```

This endpoint sends an email using a SendGrid dynamic template.
You'll need to:

- Create a template in SendGrid dashboard
- Get the template ID
- Set it in `SENDGRID_TEMPLATE_ID` environment variable or application.properties

### Expected Responses

**Success:**

```
Email sent successfully! Status Code: 202
```

**Failure (Invalid API Key):**

```
Failed to send email. Status Code: 401, Body: {"errors":[{"message":"The provided authorization grant is invalid, expired, or revoked",...}]}
```

**Failure (Unverified Sender):**

```
Failed to send email. Status Code: 403, Body: {"errors":[{"message":"The from address does not match a verified Sender Identity",...}]}
```

## Project Structure

```
src/main/java/com/hendisantika/
├── SpringBootSendGridApplication.java  # Main application
├── controller/
│   └── MailController.java            # REST endpoints
└── service/
    └── MailService.java               # SendGrid integration logic
```

## Dependencies

- Spring Boot 3.5.6
- SendGrid Java Library 4.10.3
- Lombok (for logging)

## Troubleshooting

### 401 Unauthorized

- Verify your API key is correct
- Ensure the API key has Mail Send permissions

### 403 Forbidden

- Verify your sender email in SendGrid dashboard
- Ensure the "from" email matches your verified sender

### Connection Issues

- Check your internet connection
- Verify SendGrid service status at https://status.sendgrid.com/

## Security Best Practices

1. **Never hardcode API keys** in source code
2. Use environment variables or secure secret management
3. Add `.env` files to `.gitignore`
4. Rotate API keys periodically
5. Use minimum required permissions for API keys

## Next Steps

1. Set up SendGrid dynamic templates for richer emails
2. Add error handling and retry logic
3. Implement email queuing for better performance
4. Add unit and integration tests
5. Monitor email delivery and track metrics in SendGrid dashboard

## Support

- SendGrid Documentation: https://docs.sendgrid.com/
- SendGrid Support: https://support.sendgrid.com/
- SendGrid API Reference: https://docs.sendgrid.com/api-reference/mail-send
