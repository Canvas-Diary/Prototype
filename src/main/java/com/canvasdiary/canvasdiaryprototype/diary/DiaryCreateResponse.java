package com.canvasdiary.canvasdiaryprototype.diary;

public record DiaryCreateResponse(
        String description,
        String emotion,
        String extractedEmotion,
        String canvasImageUrl
) {
}
