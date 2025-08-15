package dev.danvega.sb3.clients;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostClient postClient;

    public PostController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("")
    public List<Post> findAll() {
        return postClient.findAll();
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Integer id) {
        return postClient.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postClient.create(post);
    }

    @PutMapping("/{id}")
    public Post update(@PathVariable Integer id, @RequestBody Post post) {
        return postClient.update(id, post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        postClient.delete(id);
    }

}
