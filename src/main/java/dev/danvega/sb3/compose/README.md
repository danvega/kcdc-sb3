# Docker Compose SUpport

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-docker-compose</artifactId>
  <scope>runtime</scope>
  <optional>true</optional>
</dependency>
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <scope>runtime</scope>
</dependency>
```

compose.yaml

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=kcdc'
      - 'POSTGRES_PASSWORD=admin'
      - 'POSTGRES_USER=admin'
    ports:
      - '5432:5432'
```

Connect to database