package com.canvasdiary.canvasdiaryprototype.diary;

import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.canvas.CanvasConvertor;
import com.canvasdiary.canvasdiaryprototype.diary.dto.DiaryCreateRequest;
import com.canvasdiary.canvasdiaryprototype.diary.dto.DiaryCreateResponse;
import com.canvasdiary.canvasdiaryprototype.diary.dto.DiaryCreateViewResponse;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.emotion.EmotionExtractor;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.Translator;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.TranslatorProcessingData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;
    private final Translator translator;

    @SneakyThrows
    public DiaryCreateViewResponse formCreateDiary(DiaryCreateRequest request) {
        String translatedDescription =
                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
//        String emotion = emotionExtractor.extractEmotion(new EmotionExtractProcessingData(request.description()));
        var emotion = emotionExtractor.extractEmotionAsync(new EmotionExtractProcessingData(translatedDescription));
        List<String> canvasImageUrl =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertProcessingData(translatedDescription, request.emotion(), request.style()));
        log.info("사진 URL: {}", canvasImageUrl);
        return new DiaryCreateViewResponse(
                request.description(),
                request.emotion().name(),
                emotion.get(),
                canvasImageUrl
        );
    }

    @SneakyThrows
    public DiaryCreateResponse createDiary(DiaryCreateRequest request) {
        String translatedDescription =
                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
        var emotion = emotionExtractor.extractEmotionAsync(new EmotionExtractProcessingData(translatedDescription));
        List<String> canvasImageUrl =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertProcessingData(translatedDescription, request.emotion(), request.style()));
        log.info("사진 URL: {}", canvasImageUrl);
        return new DiaryCreateResponse(canvasImageUrl);
    }

}
