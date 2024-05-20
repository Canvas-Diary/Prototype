package com.canvasdiary.canvasdiaryprototype.diary;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.Style;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.Emotion;

public record DiaryCreateRequest(
        String description,
        Emotion emotion,
        Style style
) {
}
