package com.canvasdiary.canvasdiaryprototype.diary.domain.canvas;


import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;

public record CanvasConvertProcessingData(
        String diaryDescription,
        Emotion emotion,
        Style style
) {
}
