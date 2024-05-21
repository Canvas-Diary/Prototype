package com.canvasdiary.canvasdiaryprototype.diary.domain;

import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(length = 1000)
    private String content;
    private Emotion emotion;
    private String imageUrl;
    private LocalDate date;

    private Diary(String content, Emotion emotion, String imageUrl) {
        this.content = content;
        this.emotion = emotion;
        this.imageUrl = imageUrl;
        this.date = LocalDate.now();
    }

    public static Diary of(String content, Emotion emotion, String imageUrl) {
        return new Diary(content, emotion, imageUrl);
    }

}
