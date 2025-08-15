package dev.danvega.sb3.aot;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

@Component
//@ImportRuntimeHints(SpellCheckService.SpellCheckServiceRuntimeHints.class)
public class SpellCheckService {

    public String loadDictionary(Locale locale) {
        try {
            ClassPathResource resource = new ClassPathResource("dicts/" + locale.getLanguage() + ".txt");
            
            if (!resource.exists()) {
                return "Dictionary not found for language: " + locale.getLanguage();
            }
            
            try (InputStream inputStream = resource.getInputStream();
                 Scanner scanner = new Scanner(inputStream)) {
                
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                
                return "Dictionary loaded successfully for " + locale.getLanguage() + ":\n" + content.toString();
            }
            
        } catch (IOException e) {
            return "Error loading dictionary: " + e.getMessage();
        }
    }

    static class SpellCheckServiceRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("dicts/*");
        }
    }
}