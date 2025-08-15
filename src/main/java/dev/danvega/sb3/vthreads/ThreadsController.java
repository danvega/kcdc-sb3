package dev.danvega.sb3.vthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadsController {
    
    private static final Logger log = LoggerFactory.getLogger(ThreadsController.class);
    
    @GetMapping("/vthreads")
    public String vthreads() {
        log.info("Thread: {}", Thread.currentThread());
        return "Hello, Virtual Threads";
    }
}
