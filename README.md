# Client Service - User Information Endpoint

This document provides information about the `/me` endpoint in the **Client Service**, which returns details about the currently authenticated and authorized user. The endpoint fetches the user's data from the authorization server, and it will return a response with user-related information like user ID, client ID, issuer, and roles.

## Endpoint: `GET /client/me`

### Description

The `/me` endpoint provides details about the currently authenticated and authorized user by fetching relevant information from the OAuth2 authentication token. This token is provided by the authorization server.

### Request

**Method**: `GET`  
**URL**: `/client/me`  
**Authentication Required**: Yes (OAuth2 Authentication)

The request should include the OAuth2 token as part of the authentication process (e.g., as a Bearer token in the Authorization header).

### Response

The response will include a JSON object with the following fields:

- `sub`: The subject (user ID/username) of the authenticated user (fetched from the token's principal).
- `client-id`: The client ID from the token’s attributes (aud).
- `issuer`: The issuer from the token’s attributes (iss).
- `roles`: A list of roles assigned to the authenticated user (mapped from token authorities).

#### Successful Response (200 OK)

**Example Response**:
```json
{
  "sub": "user123",
  "client-id": "my-client-id",
  "issuer": "authorization-server",
  "roles": [""OIDC_USER","SCOPE_openid","SCOPE_profile""]
}
