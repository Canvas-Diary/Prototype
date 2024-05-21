package com.canvasdiary.canvasdiaryprototype.diary.domain.reposiroty;

import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface CustomDiaryRepository {

    public List<Diary> findBySearchParam(LocalDate from, LocalDate to, String content, Emotion emotion);

}
