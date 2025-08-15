# Spring Boot 3 AOT Resource Hints Example

This package demonstrates the official Spring Framework example for AOT (Ahead-of-Time) runtime hints, specifically focusing on resource loading that requires explicit hints for GraalVM native image generation.

## The Problem

When building Spring Boot applications as GraalVM native images, resources that are loaded dynamically at runtime need to be explicitly registered with the native image compiler. While Spring Boot 3's AOT engine automatically detects many resource patterns, certain resource loading scenarios still require manual hints.

## How This Example Works

This example follows the official Spring Framework documentation pattern for a `SpellCheckService` that loads dictionary files from the classpath.

### Code Structure
- **`SpellCheckService`** - Service that loads dictionary files from `dicts/` directory
- **`SpellCheckController`** - REST endpoint to test dictionary loading
- **Dictionary files** - Located in `src/main/resources/dicts/` (en.txt, es.txt, fr.txt)

### The Resource Loading Pattern

In `SpellCheckService.java`, this code loads resources dynamically:

```java
public String loadDictionary(Locale locale) {
    ClassPathResource resource = new ClassPathResource("dicts/" + locale.getLanguage() + ".txt");
    // ... load and return content
}
```

**Why hints might be needed:**
- The resource path includes a dynamic component (`locale.getLanguage()`)
- The `dicts/` directory is not in a standard Spring Boot location
- GraalVM may not automatically detect all resource patterns

## Running the Example

### 1. Test with Regular JVM (Always Works)
```bash
mvn spring-boot:run
```

Test the endpoints:
```bash
curl http://localhost:8080/api/spellcheck/dictionary/en
# Should return English dictionary content

curl http://localhost:8080/api/spellcheck/dictionary/es  
# Should return Spanish dictionary content

curl http://localhost:8080/api/spellcheck/dictionary/fr
# Should return French dictionary content
```

### 2. Test Native Image Without Runtime Hints

Make sure line 14 in `SpellCheckService.java` is commented out:
```java
//@ImportRuntimeHints(SpellCheckService.SpellCheckServiceRuntimeHints.class)
```

Build native image:
```bash
mvn -Pnative native:compile
```

Run the native executable and test the endpoints. Depending on Spring Boot's automatic resource detection, this might work or fail with resource not found errors.

### 3. Test Native Image With Runtime Hints (Guaranteed to Work)

Uncomment line 14 in `SpellCheckService.java`:
```java
@ImportRuntimeHints(SpellCheckService.SpellCheckServiceRuntimeHints.class)
```

Build native image again:
```bash
mvn -Pnative native:compile
```

Run the native executable and test the endpoints - they should work reliably.

## The Solution: Resource Hints

The `SpellCheckServiceRuntimeHints` class registers the necessary resource metadata:

```java
static class SpellCheckServiceRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("dicts/*");
    }
}
```

This tells GraalVM:
1. Include all files matching the pattern `dicts/*` in the native image
2. Make them available for runtime resource loading via `ClassPathResource`

## Key Concepts

### Resource Patterns
The `registerPattern("dicts/*")` method uses glob patterns:
- `dicts/*` - All files directly in the dicts directory
- `dicts/**` - All files in dicts directory and subdirectories
- `dicts/*.txt` - Only .txt files in dicts directory

### When Runtime Hints Are Needed
Resource hints are typically needed when:
- Loading resources from non-standard directories
- Using dynamic resource paths built at runtime
- Working with third-party libraries that load resources
- Loading configuration files or data files

### Spring Boot's Automatic Detection
Spring Boot 3's AOT engine automatically handles:
- Resources in standard locations (`static/`, `templates/`, `application.properties`)
- Resources accessed via `@Value("classpath:...")` 
- Common Spring resource patterns

## Key Takeaways

1. **Follow the official pattern** - This example uses the exact code from Spring Framework documentation
2. **Resource hints ensure reliability** - Even if automatic detection works, explicit hints guarantee behavior
3. **Simple glob patterns work** - `dicts/*` is sufficient for this use case
4. **Test your native images** - Resource loading behavior can differ between JVM and native execution
5. **Documentation is your friend** - The Spring Framework documentation provides proven patterns

## Extending the Example

To add more dictionary languages, simply:
1. Create new `.txt` files in `src/main/resources/dicts/`
2. No code changes needed - the existing hint pattern `dicts/*` covers all files
3. Test with: `curl http://localhost:8080/api/spellcheck/dictionary/{language}`

The beauty of this approach is that the resource hint pattern covers all current and future dictionary files automatically!