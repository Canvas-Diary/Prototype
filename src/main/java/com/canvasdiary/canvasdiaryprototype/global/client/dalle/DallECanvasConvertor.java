package com.canvasdiary.canvasdiaryprototype.global.client.dalle;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
public class DallECanvasConvertor implements CanvasConvertor {

    private static final String HEIGHT = "1024";
    private static final String WIDTH = "1024";
    private static final int samples = 3;

    private final String DALLE_REST_API_KEY;
    private final WebClient webClient = WebClient.create("https://api.openai.com/v1/images/generations");

    public DallECanvasConvertor(@Value("${dalle.key.api}") String key) {
        this.DALLE_REST_API_KEY = key;
    }

    @Override
    public List<String> convertDiaryToCanvas(CanvasConvertProcessingData data) {
        return Objects.requireNonNull(webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + DALLE_REST_API_KEY)
                        .bodyValue(new DallECanvasConvertor.DallERequest(
                                "dall-e-3",
                                DallEPromptConsts.DALLE_IMAGE_GENERATE_PROMPT + data.style() + ", " + data.emotion() + ", " + data.diaryDescription(),
                                1,
                                "1024x1024"
                        )).retrieve()
                        .bodyToMono(DallECanvasConvertor.DallEResponse.class)
                        .block())
                .data.stream()
                .map(r -> r.url)
                .toList();
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class DallERequest{
        private String model;
        private String prompt;
        private int n;
        private String size;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class DallEResponse{
        private String created;
        private List<DallECanvasConvertor.DallEImageResponse> data;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class DallEImageResponse{
        private String url;
        private String revisedPrompt;
    }

}
