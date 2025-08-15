# Structured Logging

- Structured Logging
    - New feature in Spring Boot 3.4
    - Configuration and implementation examples
    - Use cases and benefits
    - `PostWithLoggingController`
    - /log/MyCustomLogger & /log/MyCustomJsonLogger

## Structured Logging in Spring Boot 3.4

Spring Boot 3.4 introduced built-in support for structured logging, which enhances the way applications 
generate and process logs. This section explains what structured logging is, how to implement it, 
and when you might want to use it.

### What is Structured Logging?

Structured logging is an approach to logging where log entries are formatted as structured 
data (typically key-value pairs or JSON objects) rather than plain text strings. 
This makes logs more machine-readable and easier to parse, search, and analyze.

Traditional logging:
```
INFO: User with ID 123 logged in from IP 192.168.1.1
```

Structured logging:
```
{"level":"INFO", "message":"User logged in", "userId":123, "ipAddress":"192.168.1.1"}
```


```properties
# Structured Logging Configuration (ecs,logstash,gelf)
logging.structured.format.console=ecs
logging.structured.format.file=ecs
# logging.structured.format.file=dev.danvega.blog.log.MyCustomJsonLogger
logging.file.name=logs/blog.log
logging.level.dev.danvega.blog=INFO
spring.main.log-structured-arguments=true
```