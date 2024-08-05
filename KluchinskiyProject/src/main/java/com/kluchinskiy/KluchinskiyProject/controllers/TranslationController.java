package com.kluchinskiy.KluchinskiyProject.controllers;

import com.kluchinskiy.KluchinskiyProject.models.TranslationRequest;
import com.kluchinskiy.KluchinskiyProject.services.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public String greet() {
        return "Hello world!!";
    }

    @PostMapping("/translate")
    public String translate(@RequestBody TranslationRequest request,
                            HttpServletRequest httpServletRequest
    ) throws ExecutionException, InterruptedException {

        String ipAddress = httpServletRequest.getRemoteAddr();
        String inputString = request.getInputString();
        String sourceLang = request.getSourceLang();
        String targetLang = request.getTargetLang();

        String translatedString = translationService.translate(inputString, sourceLang, targetLang);

        // Сохраняем данные в базу данных
        jdbcTemplate.update(
                "INSERT INTO translations (ip_address, input_string, translated_string) VALUES (?, ?, ?)",
                ipAddress, inputString, translatedString
        );

        return translatedString;
    }
}