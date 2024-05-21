package com.canvasdiary.canvasdiaryprototype.diary.domain.reposiroty;

import com.canvasdiary.canvasdiaryprototype.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long>, CustomDiaryRepository {
    List<Diary> findByDate(LocalDate date);
}
