package com.search.blog.searchsource;

import com.search.blog.dto.req.SearchReqDto;
import com.search.blog.exception.ExternalRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class NaverSearchTest {

    private ISearchSource naverSource;

    @BeforeEach
    void beforeEach(){
        this.naverSource = new NaverSearch(new RestTemplate());
    }

    @Test
    @DisplayName("Naver 검색API 요청 테스트")
    void serachCall_success() {
        SearchReqDto requestData = SearchReqDto.builder()
                .query("테스트")
                .sort("accuracy")
                .size(10)
                .page(10).build();
        ResponseEntity<?> response = naverSource.serachCall(requestData);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Naver 검색API 요청 테스트_ExternalRequestException")
    void serachCall_ExternalRequestException() {
        SearchReqDto requestData = SearchReqDto.builder()
                .query("테스트")
                .sort("accuracy")
                .size(10000)
                .page(10000).build();
        assertThrows(ExternalRequestException.class, ()-> naverSource.serachCall(requestData));
    }

}