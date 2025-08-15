package dev.danvega.sb3.logging;

import dev.danvega.sb3.clients.Post;
import dev.danvega.sb3.clients.PostController;
import dev.danvega.sb3.clients.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts/sl")
public class PostWithLoggingController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostRepository repository;

    public PostWithLoggingController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public List<Post> findAll() {
        String requestId = UUID.randomUUID().toString();

        log.atInfo().setMessage("Retrieving all posts")
                .addKeyValue(requestId, requestId)
                .addKeyValue("endpoint", "/api/posts/")
                .addKeyValue("method", "GET")
                .log();

        List<Post> posts = repository.findAll();

        MDC.put("requestId", requestId);
        MDC.put("postCount", posts.size() + "");
        log.info("Posts retrieved successfully");
        return posts;
    }
}

