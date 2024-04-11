package com.canvasdiary.canvasdiaryprototype.global.client.kogpt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KoGPTPromptConsts {

    public static final String EMOTION_EXTRACT_PROMPT = """
            일기 내용을 바탕으로 분노·두려움·행복·슬픔·관심·놀라움·혐오·수치심 중 하나로 분류한다.
            
            일기 내용:
            """;
}
