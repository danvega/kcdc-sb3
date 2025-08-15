package dev.danvega.sb3.http;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoClient todoClient;

    public TodoController(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoClient.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Integer id) {
        return todoClient.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoClient.createTodo(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo todo) {
        return todoClient.updateTodo(id, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Integer id) {
        todoClient.deleteTodo(id);
    }
}