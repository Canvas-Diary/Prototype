package com.canvasdiary.canvasdiaryprototype.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("/")
    public String diary() {
        return "home";
    }

    @PostMapping("/diaries")
    public String postDiary(@ModelAttribute DiaryCreateRequest request, RedirectAttributes redirect) {
        DiaryCreateResponse diary = diaryService.createDiary(request);
        redirect.addFlashAttribute("diary", diary);
        return "redirect:diaries";
    }

    @GetMapping("/diaries")
    public String getDiary() {
        return "diary";
    }

//    @ResponseBody
//    @PostMapping("/api/diaries")
//    public DiaryCreateResponse postDiary(@RequestBody DiaryCreateRequest request){
//        return diaryService.createDiary(request);
//    }

}
