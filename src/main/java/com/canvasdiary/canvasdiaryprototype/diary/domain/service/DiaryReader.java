package com.canvasdiary.canvasdiaryprototype.diary.domain.service;

import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import com.canvasdiary.canvasdiaryprototype.diary.domain.reposiroty.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryReader {
    private final DiaryRepository diaryRepository;


    public List<Diary> readDiaryByDateRangeAndContentAndEmotion(
            LocalDate from, LocalDate to, String content, Emotion emotion
    ){

    }
}
