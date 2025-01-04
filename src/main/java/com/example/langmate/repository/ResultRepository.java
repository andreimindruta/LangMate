package com.example.langmate.repository;

import com.example.langmate.domain.entities.Language;
import com.example.langmate.domain.entities.Result;
import com.example.langmate.domain.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>{


  List<Result> findAllByLanguageAndUserOrderByTimestamp(Language language, User user);


}
