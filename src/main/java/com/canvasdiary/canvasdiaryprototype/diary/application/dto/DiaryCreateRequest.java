package com.canvasdiary.canvasdiaryprototype.diary.application.dto;

import com.canvasdiary.canvasdiaryprototype.diary.domain.canvas.Style;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;

public record DiaryCreateRequest(
        String description,
        Emotion emotion,
        Style style
) {
}
