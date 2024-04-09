package com.canvasdiary.canvasdiaryprototype.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping("/api/diaries")
    public DiaryCreateResponse postDiary(@RequestBody DiaryCreateRequest request){
        return diaryService.createDiary(request);
    }
}
