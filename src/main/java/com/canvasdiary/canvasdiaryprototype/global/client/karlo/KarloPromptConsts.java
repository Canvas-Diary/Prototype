package com.canvasdiary.canvasdiaryprototype.global.client.karlo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KarloPromptConsts {
    public static final String KARLO_IMAGE_GENERATE_PROMPT = """
           masterpiece, high quality, only object, 
           """;

    public static final String KARLO_IMAGE_GENERATE_NEGATIVE_PROMPT = """
            people, blood
            """;
}
