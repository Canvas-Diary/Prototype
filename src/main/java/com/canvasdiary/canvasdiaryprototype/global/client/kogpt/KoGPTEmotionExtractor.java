package com.canvasdiary.canvasdiaryprototype.global.client.kogpt;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KoGPTEmotionExtractor implements EmotionExtractor {

    private final String KAKAO_KEY_API;

    private final WebClient webClient = WebClient.create("https://api.kakaobrain.com/v1/inference/kogpt/generation");

    public KoGPTEmotionExtractor(@Value("${kakao.key.api}") String key) {
        this.KAKAO_KEY_API = key;
    }

    @Override
    public String extractEmotion(EmotionExtractProcessingData data) {
        log.info("KoGPT executing emotion extract");
        return Objects.requireNonNull(webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "KakaoAK" + KAKAO_KEY_API)
                        .bodyValue(new KoGPTRequest(
                                KoGPTPromptConsts.EMOTION_EXTRACT_PROMPT + data.getDiaryDescription(),
                                120
                        )).retrieve()
                        .onStatus(
                                HttpStatus.BAD_REQUEST::equals,
                                response -> response.bodyToMono(String.class).map(Exception::new)
                        )
                        .bodyToMono(KoGPTResponse.class)

                        .block())
                .generations.get(0).text;
    }

    @Override
    public CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data) {
        return CompletableFuture.supplyAsync(() -> extractEmotion(data));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class KoGPTRequest {
        private String prompt;
        private Integer maxTokens;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class KoGPTResponse{
        private String id;
        private List<KoGPTGenerations> generations;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class KoGPTGenerations{
        private String text;
    }
}
