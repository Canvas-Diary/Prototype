package com.canvasdiary.canvasdiaryprototype.diary.api;

import com.canvasdiary.canvasdiaryprototype.diary.DiaryService;
import com.canvasdiary.canvasdiaryprototype.diary.dto.DiaryCreateRequest;
import com.canvasdiary.canvasdiaryprototype.diary.dto.DiaryCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryCreateResponse> postDiary(@RequestBody DiaryCreateRequest request) {
        DiaryCreateResponse diary = diaryService.createDiary(request);
        return ResponseEntity.ok(diary);
    }

}
