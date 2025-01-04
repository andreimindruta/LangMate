package com.example.duolingo.repository;

import com.example.duolingo.domain.entities.Language;
import com.example.duolingo.domain.entities.Result;
import com.example.duolingo.domain.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>{


  List<Result> findAllByLanguageAndUserOrderByTimestamp(Language language, User user);


}
