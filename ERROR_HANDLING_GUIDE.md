# Error Handling Guide

## Backend Global Exception Handler

The application uses multiple layers of exception handling:

1. **ErrorController** (`@RestControllerAdvice`) - Catches application-level exceptions
2. **CustomAccessDeniedHandler** - Handles Spring Security authorization failures (403)
3. **CustomAuthenticationEntryPoint** - Handles Spring Security authentication failures (401)

### Exception Types Handled

1. **UserAlreadyExistsException** (409 Conflict)
   - Thrown when trying to register with an existing username
   - Example: "Username already exists"

2. **BadCredentialsException** (401 Unauthorized)
   - Thrown when login credentials are invalid
   - Example: "Invalid email or password"

3. **UsernameNotFoundException** (404 Not Found)
   - Thrown when a username cannot be found
   - Example: "Username not found"

4. **AccessDeniedException** (403 Forbidden)
   - Thrown when user lacks permissions
   - Example: "Access denied. Insufficient permissions."

5. **IllegalArgumentException** (400 Bad Request)
   - Thrown for invalid input parameters
   - Example: "Invalid argument provided"

6. **RuntimeException** (Smart Detection)
   - **404 Not Found**: If message contains "not found" or "does not exist"
     - Example: "Product not found", "Category not found with id: xxx"
   - **400 Bad Request**: If message contains "insufficient", "invalid", or "negative"
     - Example: "Insufficient stock. Available: 5", "Adjustment would result in negative stock"
   - **500 Internal Server Error**: For other runtime exceptions

7. **Generic Exception** (500 Internal Server Error)
   - Catches any unexpected exceptions
   - Returns: "An unexpected error occurred. Please try again later."

### Error Response Format

All errors return a JSON response with the following structure:

```json
{
  "message": "User-friendly error message",
  "status": 404,
  "error": "Not Found",
  "timestamp": "2025-11-01T22:24:00.123456"
}
```

## Frontend Error Handling

### API Client (`frontend/src/lib/api.ts`)

The API client automatically:
1. Checks if the response is not OK (`!response.ok`)
2. Extracts the error JSON from the response
3. Throws an Error with the `message` field
4. Falls back to "An error occurred" if parsing fails

```typescript
if (!response.ok) {
  const error = await response
    .json()
    .catch(() => ({ message: "An error occurred" }));
  throw new Error(error.message || `HTTP ${response.status}`);
}
```

### Component Error Handling

All components follow this pattern:

```typescript
try {
  await apiClient.someMethod();
  toast({ title: "Success message" });
} catch (error: any) {
  toast({
    title: "Error",
    description: error.message,
    variant: "destructive",
  });
}
```

The toast notification automatically displays the error message from the backend.

## Testing Error Handling

### Test Cases

1. **User Already Exists**
   - Try to register with an existing username
   - Expected: Red toast with "Username already exists"

2. **Invalid Login**
   - Enter wrong email/password
   - Expected: Red toast with "Invalid email or password"

3. **Delete Non-Existent Resource**
   - Try to delete a product that doesn't exist
   - Expected: Red toast with "Product not found"

4. **Insufficient Stock**
   - Try to remove more stock than available
   - Expected: Red toast with "Insufficient stock. Available: X"

5. **Access Denied (Role-Based)**
   - Try to access admin-only endpoint as regular user (e.g., /api/users as non-admin)
   - Expected: Red toast with "Access denied. You don't have permission to access this resource."

6. **Unauthorized Access**
   - Try to access protected endpoint without valid token
   - Expected: Red toast with "Authentication required. Please login to access this resource."

6. **Negative Stock Adjustment**
   - Try to adjust stock to a negative value
   - Expected: Red toast with "Adjustment would result in negative stock"

## Adding New Custom Exceptions

1. Create exception class in `backend/src/main/java/com/adv_java/inventory_management/exception/`:

```java
public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
```

2. Add handler in `ErrorController.java`:

```java
@ExceptionHandler(ProductOutOfStockException.class)
public ResponseEntity<Map<String, Object>> handleProductOutOfStock(
        ProductOutOfStockException ex, WebRequest request) {
    log.error("Product out of stock: {}", ex.getMessage());
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
}
```

3. Throw in service layer:

```java
if (product.getQuantityInStock() == 0) {
    throw new ProductOutOfStockException("Product is out of stock");
}
```

4. Frontend automatically handles it - no changes needed!

## Best Practices

1. **Always use descriptive error messages** - they're shown directly to users
2. **Log errors with context** - helps debugging in production
3. **Use appropriate HTTP status codes** - helps frontend handle errors correctly
4. **Never expose sensitive information** - sanitize error messages
5. **Catch errors in all async operations** - prevents unhandled promise rejections
6. **Use toast notifications consistently** - provides uniform UX
