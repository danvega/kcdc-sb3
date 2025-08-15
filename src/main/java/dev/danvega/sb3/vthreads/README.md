# Virtual Threads

Virtual Threads were introduced in JDK 21 as a solution to the challenges of asynchronous programming. These lightweight threads do not block platform threads, making them highly efficient. In fact, you can spawn millions of Virtual Threads without needing to worry about thread pooling.

Enabling Virtual Threads in your Spring Boot application couldn't be easier. Open the application.properties file and set the following property.

```
spring.threads.virtual.enabled=true
```

```java
@GetMapping("")
List<Post> findAll() {
    log.info("Thread: {}", Thread.currentThread());
    return postRepository.findAll();
}
```
Run the application and inspect the logs to see the Virtual Threads in action.

```
2024-02-13T16:12:28.117-05:00  INFO 11869 --- [omcat-handler-0] dev.danvega.sb3.post.PostController      : Thread: VirtualThread[#76,tomcat-handler-0]/runnable@ForkJoinPool-1-worker-1
```

```java
@Bean
CommandLineRunner commandLineRunner() {
    return args -> {

        // ❌ By default, this will use the platform thread
        System.out.println("CommandLineRunner thread: " + Thread.currentThread());

        // ✅ Explicitly use a virtual thread
        Thread.startVirtualThread(() -> {
            System.out.println("Explicit virtual thread in CommandLineRunner: " + Thread.currentThread());
        }).join();

    };
}
```


- Virtual Threads
    - SlowController (22 seconds before / 2 after)
    - Enable properties:
        - `spring.threads.virtual.enabled=true`
        - `server.tomcat.threads.max=5`
        - ab -n 100 -c 100 http://localhost:8080/slow