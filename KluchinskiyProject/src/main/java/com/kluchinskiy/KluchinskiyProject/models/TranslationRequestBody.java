package com.kluchinskiy.KluchinskiyProject.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

 public class TranslationRequestBody {
     @JsonProperty("sourceLanguageCode")
    private String sourceLanguageCode;
     @JsonProperty("targetLanguageCode")
    private String targetLanguageCode;
     @JsonProperty("format")
    private String format;
     @JsonProperty("texts")
    private String[] texts;
     @JsonProperty("folderId")
    private String folderId;
     @JsonProperty("speller")
    private Boolean speller;

    public TranslationRequestBody(String sourceLanguageCode, String targetLanguageCode, String format, String[] texts, String folderId, Boolean speller) {
        this.sourceLanguageCode = sourceLanguageCode;
        this.targetLanguageCode = targetLanguageCode;
        this.format = format;
        this.texts = texts;
        this.folderId = folderId;
        this.speller = speller;
    }
}
