package dev.danvega.sb3.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Value("${app.message}")
    private String message;

    @GetMapping("/app/message")
    public String message() {
        return message;
    }

}
