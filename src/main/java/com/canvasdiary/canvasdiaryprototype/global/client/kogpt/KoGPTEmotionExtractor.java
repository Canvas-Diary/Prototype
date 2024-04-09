package com.canvasdiary.canvasdiaryprototype.global.client.kogpt;

import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import org.springframework.stereotype.Component;

@Component
public class KoGPTEmotionExtractor implements EmotionExtractor {

    @Override
    public String extractEmotion(EmotionExtractProcessingData data) {
        return "";
    }
}
