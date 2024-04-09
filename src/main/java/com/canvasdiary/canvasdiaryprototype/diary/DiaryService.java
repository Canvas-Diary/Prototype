package com.canvasdiary.canvasdiaryprototype.diary;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;


    public DiaryCreateResponse createDiary(DiaryCreateRequest request){
        String emotion = emotionExtractor.extractEmotion(new EmotionExtractProcessingData(request.description()));
        String canvasImageUrl =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertProcessingData(request.description()+ request.emotion()));
        return new DiaryCreateResponse(
                request.description(),
                request.emotion(),
                emotion,
                canvasImageUrl
        );

    }
}
