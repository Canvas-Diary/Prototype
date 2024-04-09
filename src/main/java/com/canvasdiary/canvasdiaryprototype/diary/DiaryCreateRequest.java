package com.canvasdiary.canvasdiaryprototype.diary;

public record DiaryCreateRequest(
        String description,
        String emotion
) {
}
