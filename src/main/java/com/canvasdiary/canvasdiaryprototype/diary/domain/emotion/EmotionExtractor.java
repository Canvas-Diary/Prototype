package com.canvasdiary.canvasdiaryprototype.diary.domain.emotion;

import java.util.concurrent.CompletableFuture;

public interface EmotionExtractor {
    String extractEmotion(EmotionExtractProcessingData data);


    CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data);
}
