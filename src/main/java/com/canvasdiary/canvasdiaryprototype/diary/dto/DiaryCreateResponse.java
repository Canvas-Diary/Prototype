package com.canvasdiary.canvasdiaryprototype.diary.dto;

import java.util.List;

public record DiaryCreateResponse(
        List<String> canvasImageUrl
) {
}
