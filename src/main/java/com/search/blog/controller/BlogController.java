package com.search.blog.controller;


import com.search.blog.dto.req.SearchReqDto;
import com.search.blog.dto.res.SearchResDto;
import com.search.blog.service.BlogSerivce;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/search")
public class BlogController {

    private final BlogSerivce blogSerivce;

    /**
     * 블로그 검색
     * 키워드를 통해 블로그 검색
     * 검색 결과 sorting(정확도순, 최신순) 기능 지원
     * @retrun : paigination 형태
     */
    @GetMapping("/blog")
    public ResponseEntity<?> searchBlog(@RequestParam(value = "query") String query ,
                                        @RequestParam(value = "sort", required = false, defaultValue = "accuracy") String sort,
                                        @PageableDefault(page = 1, size = 10) Pageable pageable){
        log.info("-------------------------- requestData ------------------------");
        log.info("---> query = {}" , query );
        log.info("---> sort = {} " , sort);
        log.info("---> page = {}" , pageable.getPageNumber());
        log.info("---> size = {} " ,pageable.getPageSize());
        log.info("--------------------------------------------------------------");
        SearchReqDto searchReqDto = SearchReqDto.builder()
                .query(query)
                .sort(sort)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();
        return blogSerivce.findBlog(searchReqDto);
    }

    @GetMapping("/popular")
    public ResponseEntity<?> searchPopularWords(){
        return blogSerivce.findPopularWords();
    }
}
