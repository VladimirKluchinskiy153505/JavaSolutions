package com.kluchinskiy.KluchinskiyProject.services;

import com.kluchinskiy.KluchinskiyProject.models.TranslationRequestBody;
import com.kluchinskiy.KluchinskiyProject.models.TranslationResponseBody;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class TranslationService {

    private static final int poolCount = 10;
    private static final int threadDelay = 500;
    private static final String apiUrl = "https://translate.api.cloud.yandex.net/translate/v2/translate";
    private static final String apiKey = "AQVNxycxNRBKxq_sXqSwXwTdaIRZELLWhA2vuzGU";
    private static final String folderId = "b1gp0q07n8u2bdlchca0";
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;

    public TranslationService() {

        this.restTemplate = new RestTemplate();
        this.executorService = Executors.newFixedThreadPool(poolCount);
    }
    public String translate(String inputString, String sourceLang, String targetLang)
            throws InterruptedException, ExecutionException {

        String[] words = inputString.split(" ");
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (String word : words) {
                Future<String> future = executorService.submit(() -> {
                    try {
                        Thread.sleep(threadDelay);
                        return translateWord(word, sourceLang, targetLang);
                    } catch (Exception e) {
                        throw e;
                    }

                });
                futures.add(future);
            }

            StringBuilder translatedStringBuilder = new StringBuilder();
            for (Future<String> future : futures) {
                translatedStringBuilder.append(future.get()).append(" ");
            }
            return "Success " + HttpStatus.OK.value() + ": " + translatedStringBuilder.toString().trim();

        }catch(Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    private String translateWord(String word, String sourceLang, String targetLang) {

        TranslationRequestBody requestBody = new TranslationRequestBody(
                sourceLang, targetLang, "PLAIN_TEXT", new String[]{word}, folderId, false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Api-Key " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TranslationRequestBody> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<TranslationResponseBody> response = restTemplate.exchange(
                 apiUrl, HttpMethod.POST, requestEntity, TranslationResponseBody.class
        );
        return response.getBody().getTranslations().get(0).getText();
    }
//    private String handleHttpClientError(HttpClientErrorException e) {
//        int statusCode = e.getStatusCode().value();
//        String errorMessage = e.getMessage();
//        return "http " + statusCode + " " + errorMessage;
//    }
}
