package com.canvasdiary.canvasdiaryprototype.diary.presentation;

import com.canvasdiary.canvasdiaryprototype.diary.application.DiaryService;
import com.canvasdiary.canvasdiaryprototype.diary.application.dto.DiaryCreateRequest;
import com.canvasdiary.canvasdiaryprototype.diary.application.dto.DiaryCreateResponse;
import com.canvasdiary.canvasdiaryprototype.diary.application.dto.DiaryReadResponse;
import com.canvasdiary.canvasdiaryprototype.diary.application.dto.ImageSelectRequest;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(DiaryApi.BASE_URL)
    public DiaryCreateResponse createImages(@RequestBody DiaryCreateRequest request) {
        return diaryService.createImages(request);
    }

    @PostMapping()
    public void selectImages(@RequestBody ImageSelectRequest request) {
        diaryService.saveDiary(request);
    }

    @GetMapping(DiaryApi.BASE_URL)
    public DiaryReadResponse readDiaries(@RequestParam(required = false) LocalDate from,
                                             @RequestParam(required = false) LocalDate to,
                                             @RequestParam(required = false) String content,
                                             @RequestParam(required = false) Emotion emotion) {
        return diaryService.readDiaries(from, to, content, emotion);
    }

    @DeleteMapping(DiaryApi.SPECIFIC_DIARY)
    public void deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
    }

}
