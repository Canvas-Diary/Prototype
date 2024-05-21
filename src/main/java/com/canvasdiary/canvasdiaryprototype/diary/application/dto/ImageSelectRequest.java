package com.canvasdiary.canvasdiaryprototype.diary.application.dto;

import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;

public record ImageSelectRequest(
        String imageUrl,
        String content,
        Emotion emotion
) {
}
