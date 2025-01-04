package com.example.duolingo.repository;

import com.example.duolingo.domain.entities.Language;
import com.example.duolingo.domain.entities.Lesson;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

  List<Lesson> findAllByLanguage(Language language);
}