package com.canvasdiary.canvasdiaryprototype.diary.canvas;


import com.canvasdiary.canvasdiaryprototype.diary.emotion.Emotion;

public record CanvasConvertProcessingData(
        String diaryDescription,
        Emotion emotion,
        Style style
) {
}
