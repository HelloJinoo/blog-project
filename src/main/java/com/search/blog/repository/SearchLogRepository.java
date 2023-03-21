package com.search.blog.repository;

import com.search.blog.dto.PopularBlog;
import com.search.blog.entity.SearchLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {


    List<SearchLog> findTop10ByOrderByCountDesc();

    Optional<SearchLog> findByQuery(String query);



}
