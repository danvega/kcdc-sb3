package dev.danvega.sb3.vthreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlowController {

    private static final Logger log = LoggerFactory.getLogger(SlowController.class);

    @GetMapping("/slow")
    public String slowService() throws InterruptedException {
        log.info("Slow service endpoint hit, current thread: {}", Thread.currentThread());
        Thread.sleep(1000); // Simulate a 1-second delay
        return "Slow service response";
    }
}
