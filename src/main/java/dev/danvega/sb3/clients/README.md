# Clients


## Rest Client

review the jdbc template first and show the old way of doing this 

```java
@Component
public class PostClient {

    private final RestClient restClient;

    public PostClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
    }

    public List<Post> findAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

}
```

JDBC API

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

review the jdbc template post repository 

then show the PostRepository 

