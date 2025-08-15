package dev.danvega.sb3.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TodoRestTemplateClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public TodoRestTemplateClient(RestTemplateBuilder builder, @Value("${jsonplaceholder.base-url}") String baseUrl) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
    }

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

    public Todo getTodoById(Integer id) {
        String url = baseUrl + "/todos/{id}";
        return restTemplate.getForObject(url, Todo.class, id);
    }

    public Todo createTodo(Todo todo) {
        String url = baseUrl + "/todos";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Todo> request = new HttpEntity<>(todo, headers);
        return restTemplate.postForObject(url, request, Todo.class);
    }

    public Todo updateTodo(Integer id, Todo todo) {
        String url = baseUrl + "/todos/{id}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Todo> request = new HttpEntity<>(todo, headers);
        ResponseEntity<Todo> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                request,
                Todo.class,
                id
        );
        return response.getBody();
    }

    public void deleteTodo(Integer id) {
        String url = baseUrl + "/todos/{id}";
        restTemplate.delete(url, id);
    }
}