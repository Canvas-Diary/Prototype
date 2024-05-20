package com.canvasdiary.canvasdiaryprototype.global.client.gemini;

import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@Primary
public class GeminiEmbeddingEmotionExtractor implements EmotionExtractor {

    private final WebClient webClient;

    public GeminiEmbeddingEmotionExtractor(@Value("${google.key.gemini}") String key) {
        webClient = WebClient.create(
                "https://generativelanguage.googleapis.com/v1beta/models/embedding-001:embedContent?key=" + key);
    }

    @Override
    public String extractEmotion(EmotionExtractProcessingData data) {
        log.info("Gemini executing embedding emotion extract");
        List<Double> embedding = getEmbedding(data.getDiaryDescription());
        GeminiEmotionEmbedding emotion = getNearestEmotion(embedding);
        return emotion.name().toLowerCase();
    }

    @Override
    public CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data) {
        return CompletableFuture.supplyAsync(() -> extractEmotion(data));
    }

    private List<Double> getEmbedding(String text) {
        return Objects.requireNonNull(webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new GeminiRequest(
                        "models/embedding-001",
                        new RequestContent(List.of(new Part(text))),
                        "classification"
                )).retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(GeminiResponse.class)
                .block())
                .embedding.values;
    }

    private GeminiEmotionEmbedding getNearestEmotion(List<Double> embedding) {
        TreeMap<Double, GeminiEmotionEmbedding> map = new TreeMap<>();
        GeminiEmotionEmbedding[] emotions = GeminiEmotionEmbedding.values();
        
        for (GeminiEmotionEmbedding emotion : emotions) {
            map.put(cosineSimilarity(embedding, emotion.getEmbedding()), emotion);
        }

        log.info("emotion={}", map);

        return map.firstEntry().getValue();
    }

    private Double cosineSimilarity(List<Double> l1, double[] l2) {
        double dot = 0;
        double sum1 = 0;
        double sum2 = 0;
        for  (int i = 0; i < l1.size(); i++) {
            dot += l1.get(i) * l2[i];
            sum1 += l1.get(i) * l1.get(i);
            sum2 += l2[i] * l2[i];
        }
        return 1.0 - dot / (Math.sqrt(sum1) * Math.sqrt(sum2));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class GeminiRequest {
        private String model;
        private RequestContent content;
        private String taskType;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class RequestContent {
        private List<Part> parts;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class Part {
        private String text;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class GeminiResponse {
        private Embedding embedding;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class Embedding {
        private List<Double> values;
    }

}
