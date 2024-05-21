package com.canvasdiary.canvasdiaryprototype.diary.domain.reposiroty;

import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import com.canvasdiary.canvasdiaryprototype.diary.domain.emotion.Emotion;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.canvasdiary.canvasdiaryprototype.diary.domain.QDiary.*;


@Repository
@RequiredArgsConstructor
public class CustomDiaryRepositoryImpl implements CustomDiaryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Diary> findBySearchParam(LocalDate from, LocalDate to, String content, Emotion emotion) {
        return query.select(diary)
                .from(diary)
                .where(
                        betweenDate(from, to),
                        containsContent(content),
                        eqEmotion(emotion)
                ).fetch();
    }

    private BooleanExpression betweenDate(LocalDate from, LocalDate to) {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            return null;
        }
        return diary.date.between(from, to);
    }

    private BooleanExpression containsContent(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        return diary.content.contains(content);
    }

    private BooleanExpression eqEmotion(Emotion emotion) {
        if (Objects.isNull(emotion)) {
            return null;
        }
        return diary.emotion.eq(emotion);
    }

}
