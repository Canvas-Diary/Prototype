package com.canvasdiary.canvasdiaryprototype.diary.domain.service;

import com.canvasdiary.canvasdiaryprototype.diary.domain.reposiroty.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryRemover {
    private final DiaryRepository diaryRepository;


    public void removeDiary(Long diaryId) {
        diaryRepository.findById(diaryId)
                .ifPresent(diaryRepository::delete);
    }
}
