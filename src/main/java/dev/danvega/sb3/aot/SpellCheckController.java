package dev.danvega.sb3.aot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/spellcheck")
public class SpellCheckController {

    private final SpellCheckService spellCheckService;

    public SpellCheckController(SpellCheckService spellCheckService) {
        this.spellCheckService = spellCheckService;
    }

    @GetMapping("/dictionary/{language}")
    public String getDictionary(@PathVariable String language) {
        Locale locale = new Locale(language);
        return spellCheckService.loadDictionary(locale);
    }
}