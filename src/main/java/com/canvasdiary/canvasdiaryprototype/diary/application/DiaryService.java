package com.canvasdiary.canvasdiaryprototype.diary.application;

import com.canvasdiary.canvasdiaryprototype.diary.application.dto.*;
import com.canvasdiary.canvasdiaryprototype.diary.domain.canvas.CanvasConvertProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.domain.canvas.CanvasConvertor;
import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import com.canvasdiary.canvasdiaryprototype.diary.domain.dto.*;
import com.canvasdiary.canvasdiaryprototype.diary.domain.service.DiaryRemover;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.EmotionExtractProcessingData;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.EmotionExtractor;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.Translator;
import com.canvasdiary.canvasdiaryprototype.global.util.translate.TranslatorProcessingData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;
    private final Translator translator;
    private final DiaryRemover diaryRemover;

    @SneakyThrows
    public DiaryCreateViewResponse formCreateDiary(DiaryCreateRequest request) {
        String translatedDescription =
                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
//        String emotion = emotionExtractor.extractEmotion(new EmotionExtractProcessingData(request.content()));
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
    public DiaryCreateResponse createImages(DiaryCreateRequest request) {
        String translatedDescription =
                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
        var emotion = emotionExtractor.extractEmotionAsync(new EmotionExtractProcessingData(translatedDescription));
        List<String> canvasImageUrl =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertProcessingData(translatedDescription, request.emotion(), request.style()));
        log.info("사진 URL: {}", canvasImageUrl);
        return new DiaryCreateResponse(canvasImageUrl);
    }

    public void saveDiary(ImageSelectRequest request) {
        // TODO - S3에 이미지 저장, DB에는 url만 저장
        Diary diary = Diary.of(request.content(), request.emotion(), request.imageUrl());

    }

    public DiaryReadResponse readDiaries(LocalDate from, LocalDate to, String content, Emotion emotion) {
        List<Diary> diaries = diaryRepository.findBySearchParam(from, to, content, emotion);
        return DiaryReadResponse.from(diaries);
    }

    public void deleteDiary(Long diaryId) {
        diaryRemover.removeDiary(diaryId);
    }

}
