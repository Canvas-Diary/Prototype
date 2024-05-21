package com.canvasdiary.canvasdiaryprototype.diary.application.dto;

import java.util.List;

public record DiaryCreateResponse(
        List<String> canvasImageUrl
) {
}
