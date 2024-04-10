package com.canvasdiary.canvasdiaryprototype.diary.emotion;

import java.util.concurrent.CompletableFuture;

public interface EmotionExtractor {
    String extractEmotion(EmotionExtractProcessingData data);


    CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data);
}
