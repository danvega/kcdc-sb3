# Software Bill of Materials (SBOM)

A Software Bill of Materials (SBOM) functions as an ingredient list for your application. 
It documents every library, framework, and dependency your code uses along with their version numbers. 
Think of it as an inventory of all building blocks in your software. For compliance purposes, 
SBOMs matter because security vulnerabilities frequently appear in popular libraries. With an SBOM, 
you can quickly identify if your application uses affected components and update them promptly. 
Most development tools generate SBOMs automatically, eliminating the need for manual maintenance.


```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<plugin>
    <groupId>org.cyclonedx</groupId>
    <artifactId>cyclonedx-maven-plugin</artifactId>
</plugin>
```

```
management.endpoints.web.exposure.include=health,sbom
```

run `mvn clean package` > target/classes/META-INF/sbom/application.cdx.json

List of SBOMs:

```
curl http://localhost:8080/actuator/sbom
# → {"ids":["application"]}
```

Content of the application SBOM:

```
curl http://localhost:8080/actuator/sbom/application
```

This returns a CycloneDX JSON containing all dependencies, hashes, licenses, versions, and metadata