package com.canvasdiary.canvasdiaryprototype.diary.application.dto;

import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;

import java.time.LocalDate;
import java.util.List;

public record DiaryReadResponse(
        List<DiaryResponse> diaries
) {

    public static DiaryReadResponse from(List<Diary> diaries) {
        return new DiaryReadResponse(
                diaries.stream()
                        .map(DiaryResponse::from)
                        .toList()
        );
    }

}

record DiaryResponse(
        String content,
        String imageUrl,
        Emotion emotion,
        LocalDate date
) {

    public static DiaryResponse from(Diary diary) {
        return new DiaryResponse(diary.getContent(), diary.getImageUrl(), diary.getEmotion(), diary.getDate());
    }

}
