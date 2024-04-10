package com.canvasdiary.canvasdiaryprototype.global.client.deepl;

import com.canvasdiary.canvasdiaryprototype.global.util.translate.Translator;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.TranslatorProcessingData;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class DeepLTranslator implements Translator {

    private final String DEEPL_API_KEY;
    private final WebClient webClient = WebClient.create("https://api-free.deepl.com/v2/translate");

    public DeepLTranslator(@Value("${deepl.key.api}") String key) {
        this.DEEPL_API_KEY = key;
    }

    @Override
    public String translateKoreanToEnglish(TranslatorProcessingData data) {
        return Objects.requireNonNull(webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "DeepL-Auth-Key " + DEEPL_API_KEY)
                        .bodyValue(new DeepLRequest(
                                List.of(data.getContent()),
                                "KO",
                                "EN-US"
                        )).retrieve()
                        .bodyToMono(DeepLResponse.class)
                        .block())
                .getTranslations().get(0).getText();
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class DeepLRequest {
        private List<String> text;
        private String sourceLang;
        private String targetLang;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class DeepLResponse {
        private List<Translation> translations;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class Translation {
        private String detectedSourceLanguage;
        private String text;
    }

}
