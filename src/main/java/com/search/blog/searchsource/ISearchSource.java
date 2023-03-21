package com.search.blog.searchsource;

import com.search.blog.dto.req.SearchReqDto;
import org.springframework.http.ResponseEntity;


public interface ISearchSource {

    //검색API 이용
    ResponseEntity<?> serachCall(SearchReqDto searchReqDto);



}
