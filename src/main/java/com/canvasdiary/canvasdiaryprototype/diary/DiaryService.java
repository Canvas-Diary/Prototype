package com.canvasdiary.canvasdiaryprototype.diary;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;


    public DiaryCreateResponse createDiary(DiaryCreateRequest request){
        String emotion = emotionExtractor.extractEmotion(null);
        String canvasImageUrl = canvasConvertor.convertDiaryToCanvas(null);
        return new DiaryCreateResponse(
                request.description(),
                request.emotion(),
                emotion,
                canvasImageUrl
        );
    }
}
