package com.search.blog.service;


import com.search.blog.common.ApiResponse;
import com.search.blog.dto.req.SearchReqDto;
import com.search.blog.dto.res.SearchResDto;
import com.search.blog.entity.SearchLog;
import com.search.blog.repository.SearchLogRepository;
import com.search.blog.searchsource.ISearchSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class BlogSerivce {

    private final ISearchSource kakaoSearch;
    private final SearchLogRepository searchLogRepository;


    public synchronized ResponseEntity<?> findBlog(SearchReqDto searchReqDto){
        ResponseEntity<?> response = kakaoSearch.serachCall(searchReqDto);
        processSeachLog(searchReqDto.getQuery());
        log.info("=> Response [{}] " , response);
        return response;
    }


    public ResponseEntity<?> findPopularWords() {
        return new ResponseEntity<>( ApiResponse.success(searchLogRepository.findTop10ByOrderByCountDesc()), HttpStatus.OK);
    }

    private void processSeachLog(String query){
        //현재는 DB에서 조회 및 수정이 진행
        //개선 : 캐시를 사용한 조회 및 수정으로 속도 개선 및 DB 동기화 필요
        Optional<SearchLog> searchLog = searchLogRepository.findByQuery(query);
        if(searchLog.isPresent()){
            searchLog.get().plusCount();
        } else{
            searchLogRepository.save(SearchLog.builder().query(query).count(1L).build());
        }
    }
}
