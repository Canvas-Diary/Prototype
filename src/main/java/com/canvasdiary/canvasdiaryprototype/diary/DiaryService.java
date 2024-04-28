package com.canvasdiary.canvasdiaryprototype.diary;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.Translator;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.TranslatorProcessingData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {
    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;
    private final Translator translator;

    @SneakyThrows
    public DiaryCreateResponse createDiary(DiaryCreateRequest request) {
        String translatedDescription =
                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
//        String emotion = emotionExtractor.extractEmotion(new EmotionExtractProcessingData(request.description()));
        var emotion = emotionExtractor.extractEmotionAsync(new EmotionExtractProcessingData(translatedDescription));
        String canvasImageUrl =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertProcessingData(translatedDescription, request.emotion()));
        log.info("사진 URL: {}", canvasImageUrl);
        return new DiaryCreateResponse(
                request.description(),
                request.emotion(),
                emotion.get(),
                canvasImageUrl
        );
    }

}
