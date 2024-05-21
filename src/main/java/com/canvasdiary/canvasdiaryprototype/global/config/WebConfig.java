package com.canvasdiary.canvasdiaryprototype.global.config;

import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(
                new Converter<String, Emotion>() {
                    @Override
                    public Emotion convert(String source) {
                        return Emotion.valueOf(source.toUpperCase());
                    }
                }
        );
    }
}
