package com.search.blog.searchsource;

import com.search.blog.dto.req.SearchReqDto;
import com.search.blog.dto.res.SearchResDto;
import com.search.blog.exception.ExternalRequestException;
import com.search.blog.searchsource.dto.req.SearchBlogReqForKaKao;
import com.search.blog.searchsource.dto.res.SearchBlogResForKaKao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component("kakaoSearch")
@Slf4j
@AllArgsConstructor
public class KaKaoSearch  implements ISearchSource{

    private final String REST_API_KEY = "KakaoAK 1d13b2b98238cb4bff9e87a66b7a51c4";
    private final String URL = "https://dapi.kakao.com/v2/search/blog";

    private final RestTemplate restTemplate;
    private final ISearchSource naverSearch;


    @Override
    public ResponseEntity<?> serachCall(SearchReqDto searchReqDto) {
        String getUrl =  createGetRequestUrl(createRequestParam(searchReqDto));
        /*블로그 검색 API 요청 - kakao*/
        MultiValueMap<String, String> headers = createHeaders();
        HttpEntity httpEntity = new HttpEntity(null, headers );
        try {
            ResponseEntity<SearchBlogResForKaKao> response = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, SearchBlogResForKaKao.class, new HashMap<>());
            log.info("[ SERVER -> KAKAO RESPONSE = {}]", response);
            SearchBlogResForKaKao kakaoResponse = response.getBody();
            List<SearchResDto.Item> items = kakaoResponse.getDocuments().stream().map(i -> SearchResDto.Item.builder()
                    .blogname(i.getBlogname())
                    .contents(i.getContents())
                    .title(i.getTitle())
                    .url(i.getUrl())
                    .datetime(i.getDatetime())
                    .build()
            ).collect(Collectors.toList());

            SearchResDto searchResDto = SearchResDto.builder()
                    .total_count(kakaoResponse.getMeta().getTotal_count())
                    .is_end(kakaoResponse.getMeta().is_end())
                    .items(items)
                    .build();
            return new ResponseEntity(searchResDto,response.getStatusCode());
        }catch (HttpServerErrorException e){
            //throw new ExternalServerException(e.getMessage(), e);
            return naverSearch.serachCall(searchReqDto);
        }catch (HttpClientErrorException e){
            throw new ExternalRequestException(e.getResponseBodyAsString(), e);
        }
    }

    private String createGetRequestUrl(SearchBlogReqForKaKao requestParam) {

        String getUrl = URL;
        StringBuffer sb = new StringBuffer();
        sb.append(getUrl)
                .append("?")
                .append("query=").append(requestParam.getQuery())
                .append("&sort=").append(requestParam.getSort())
                .append("&page=").append(requestParam.getPage())
                .append("&size=").append(requestParam.getSize());
        return sb.toString();
    }

    private MultiValueMap<String, String> createHeaders(){
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("Authorization", List.of(REST_API_KEY));
        return  headers;
    }

    private SearchBlogReqForKaKao createRequestParam(SearchReqDto searchReqDto) {
        return SearchBlogReqForKaKao.builder()
                .query(searchReqDto.getQuery())
                .sort(searchReqDto.getSort())
                .page(searchReqDto.getPage())
                .size(searchReqDto.getSize())
                .build();
    }


}
