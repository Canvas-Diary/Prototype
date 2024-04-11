package com.canvasdiary.canvasdiaryprototype.global.client.karlo;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
public class KarloCanvasConvertor implements CanvasConvertor {

    private final String KAKAO_KEY_API;
    private final WebClient webClient = WebClient.create("https://api.kakaobrain.com/v2/inference/karlo/t2i");

    public KarloCanvasConvertor(@Value("${kakao.key.api}") String key) {
        this.KAKAO_KEY_API = key;
    }

    @Override
    public String convertDiaryToCanvas(CanvasConvertProcessingData data) {
        return Objects.requireNonNull(webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "KakaoAK " + KAKAO_KEY_API)
                        .bodyValue(new KarloRequest(
                                "v2.1",
                                KarloPromptConsts.KARLO_IMAGE_GENERATE_PROMPT + " " + data.getDiaryDescription() + " " + data.getEmotion(),
                                KarloPromptConsts.KARLO_IMAGE_GENERATE_NEGATIVE_PROMPT,
                                1024L,
                                1024L
                        )).retrieve()
                        .bodyToMono(KarloResponse.class)
                        .block())
                .getImages().get(0).image;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class KarloRequest{
        private String version;
        private String prompt;
        private String negativePrompt;
        private Long height;
        private Long width;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KarloResponse{
        private String id;
        private String modelVersion;
        private List<KarloImageResponse> images;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class KarloImageResponse{
        private String id;
        private String image;
    }

}


/**
 * {
 *     "version": "v2.1",
 *     "prompt": "A photo of a cute tiny monster on the beach, daylight",
 *     "height": 1024,
 *     "width": 1024
 * }
 *
 *
 * {
 *     "id": "e7699c32d6e06cef",
 *     "model_version": "v2.1.1.prod",
 *     "images": [
 *         {
 *             "id": "a2FybG8tdHJpdG9uLXYyLjEuMS04NGZkOWNkNTQ1LXZsNmdj-1c6904ac-dcd2-424a-b8ae-544a5966ebbe",
 *             "image": "https://mk.kakaocdn.net/dna/karlo/image/2024-04-09/18/1c6904ac-dcd2-424a-b8ae-544a5966ebbe.webp?credential=smxRqiqUEJBVgohptvfXS5JoYeFv4Xxa&expires=1712654159&signature=Y1ZLmkKvLUckTP5Jcb0Zwxdu1c4%3D",
 *             "seed": 1991768070,
 *             "nsfw_content_detected": null,
 *             "nsfw_score": null
 *         }
 *     ]
 * }
 */