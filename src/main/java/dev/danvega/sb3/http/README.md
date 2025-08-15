# HTTP Clients in Spring Boot 3: RestTemplate vs HTTP Interfaces

This package demonstrates the evolution of HTTP client development in Spring Boot 3, showcasing the traditional verbose approach with RestTemplate versus the modern, declarative HTTP Interfaces feature.

## Overview

This example compares two approaches for consuming the JSONPlaceholder API (https://jsonplaceholder.typicode.com):

1. **Traditional Approach**: `TodoRestTemplateClient` - Using RestTemplate with verbose boilerplate code
2. **Modern Approach**: `TodoClient` - Using HTTP Interfaces with minimal declarative code

## The Traditional Way: RestTemplate

### TodoRestTemplateClient.java

The traditional approach requires significant boilerplate code for each HTTP operation:

```java
@Component
public class TodoRestTemplateClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    // Constructor with RestTemplateBuilder and @Value injection
    public TodoRestTemplateClient(RestTemplateBuilder builder, @Value("${jsonplaceholder.base-url}") String baseUrl) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
    }

    // Verbose GET request for all todos
    public List<Todo> getAllTodos() {
        String url = baseUrl + "/todos";
        ResponseEntity<List<Todo>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Todo>>() {}
        );
        return response.getBody();
    }

    // More boilerplate for POST, PUT, DELETE...
}
```

**What's verbose about this approach?**
- Manual URL construction for each endpoint
- Explicit HTTP method specification
- Headers must be manually set for POST/PUT operations
- Complex `ParameterizedTypeReference` for generic collections
- Repetitive error handling and response extraction
- **70 lines of code** for 5 simple CRUD operations

## The Modern Way: HTTP Interfaces

### TodoClient.java

Spring Boot 3 introduces HTTP Interfaces, allowing you to define HTTP clients declaratively:

```java
@HttpExchange
public interface TodoClient {

    @GetExchange("/todos")
    List<Todo> getAllTodos();

    @GetExchange("/todos/{id}")
    Todo getTodoById(@PathVariable Integer id);

    @PostExchange("/todos")
    Todo createTodo(@RequestBody Todo todo);

    @PutExchange("/todos/{id}")
    Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo);

    @DeleteExchange("/todos/{id}")
    void deleteTodo(@PathVariable Integer id);
}
```

**What makes this approach better?**
- **Just 30 lines of code** for the same functionality
- No implementation required - Spring generates it automatically
- Familiar Spring MVC annotations (`@PathVariable`, `@RequestBody`)
- Clean, readable interface that focuses on the contract
- Automatic serialization/deserialization
- Built-in error handling

## Configuration: HttpConfig.java

To use HTTP Interfaces, you need minimal configuration:

```java
@Configuration
public class HttpConfig {

    @Bean
    public RestClient restClient(@Value("${jsonplaceholder.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public TodoClient todoClient(RestClient restClient) {
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(TodoClient.class);
    }
}
```

This configuration:
1. Creates a `RestClient` bean with the base URL
2. Creates a proxy implementation of `TodoClient` using `HttpServiceProxyFactory`
3. Spring automatically implements all the interface methods

## Data Model: Todo.java

Both approaches use the same simple record:

```java
public record Todo(
    Integer userId,
    Integer id,
    String title,
    Boolean completed
) {}
```

## Usage: TodoController.java

The controller demonstrates how clean the HTTP Interfaces approach is:

```java
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoClient todoClient;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoClient.getAllTodos();  // Clean, simple call
    }
    
    // More endpoints...
}
```

## Running the Example

1. **Start the application**:
   ```bash
   mvn spring-boot:run
   ```

2. **Test the endpoints**:
   ```bash
   # Get all todos
   curl http://localhost:8080/api/todos

   # Get a specific todo
   curl http://localhost:8080/api/todos/1

   # Create a new todo
   curl -X POST http://localhost:8080/api/todos \
     -H "Content-Type: application/json" \
     -d '{"userId":1,"title":"New Todo","completed":false}'

   # Update a todo
   curl -X PUT http://localhost:8080/api/todos/1 \
     -H "Content-Type: application/json" \
     -d '{"userId":1,"id":1,"title":"Updated Todo","completed":true}'

   # Delete a todo
   curl -X DELETE http://localhost:8080/api/todos/1
   ```

## Key Benefits of HTTP Interfaces

### 1. **Less Code, More Clarity**
- RestTemplate approach: **70 lines** of boilerplate
- HTTP Interfaces approach: **30 lines** of clean interface

### 2. **Familiar Annotations**
Uses the same annotations you know from Spring MVC:
- `@GetExchange` instead of `@GetMapping`
- `@PostExchange` instead of `@PostMapping`
- Same `@PathVariable`, `@RequestBody`, etc.

### 3. **Automatic Implementation**
Spring generates the implementation automatically - no need to write HTTP client code.

### 4. **Type Safety**
Compile-time checking ensures your HTTP client interface matches your expectations.

### 5. **Testing Made Easy**
Easy to mock the interface for unit testing vs. mocking RestTemplate behavior.

## Configuration Requirements

Add this to your `application.properties`:
```properties
jsonplaceholder.base-url=https://jsonplaceholder.typicode.com
```

## When to Use Each Approach

### Use HTTP Interfaces When:
- You want clean, declarative HTTP clients
- You prefer type-safe interfaces
- You want less boilerplate code
- You're building new applications

### Stick with RestTemplate When:
- You need complex custom request/response handling
- You're working with legacy applications
- You need very specific HTTP client behavior

## Conclusion

HTTP Interfaces in Spring Boot 3 represent a significant improvement in developer experience for HTTP client development. They reduce boilerplate, improve readability, and make HTTP client development feel natural for Spring developers.

The example shows a **57% reduction in code** (from 70 to 30 lines) while maintaining the same functionality and improving readability. This is the future of HTTP client development in Spring Boot!