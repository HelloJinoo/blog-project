package com.search.blog.searchsource;

import com.search.blog.dto.req.SearchReqDto;
import com.search.blog.dto.res.SearchResDto;
import com.search.blog.exception.ExternalRequestException;
import com.search.blog.exception.ExternalServerException;
import com.search.blog.searchsource.dto.req.SearchBlogReqForNaver;
import com.search.blog.searchsource.dto.res.SearchBlogResForNaver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component("naverSearch")
@AllArgsConstructor
public class NaverSearch implements ISearchSource{

    private final String CLIENT_ID = "KPjrViWGYAeIBE_W5G_b";
    private final String CLIENT_SECRET = "NdKv7GRgLk";
    private final String URL = "https://openapi.naver.com/v1/search/blog.json";

    private final RestTemplate restTemplate;


    @Override
    public ResponseEntity<?> serachCall(SearchReqDto searchReqDto) {
        String getUrl =  createGetRequestUrl(createRequestParam(searchReqDto));

        /*블로그 검색 API 요청 - naver*/
        MultiValueMap<String, String> headers = createHeaders();
        HttpEntity httpEntity = new HttpEntity(null, headers );
        try {
            ResponseEntity<SearchBlogResForNaver> response = restTemplate.exchange(getUrl, HttpMethod.GET, httpEntity, SearchBlogResForNaver.class, new HashMap<>());
            log.info("[ SERVER -> NAVER RESPONSE = {}]", response);
            // repsonse -> 실제 response로 포맷 변경
            boolean isEnd = true;
            if (response.getBody().getTotal() > searchReqDto.getPage() + searchReqDto.getSize()) {
                isEnd = false;
            }

            SearchBlogResForNaver naverResponse = response.getBody();
            List<SearchResDto.Item> items = naverResponse.getItems().stream().map(i -> SearchResDto.Item.builder()
                    .blogname(i.getBloggername())
                    .contents(i.getDescription())
                    .title(i.getTitle())
                    .url(i.getLink())
                    .datetime(i.getPostdate())
                    .build()
            ).collect(Collectors.toList());

            SearchResDto searchResDto = SearchResDto.builder()
                    .total_count(naverResponse.getTotal())
                    .is_end(isEnd)
                    .items(items)
                    .build();
            return new ResponseEntity(searchResDto,response.getStatusCode());
        }catch (HttpServerErrorException e){
            throw new ExternalServerException(e.getResponseBodyAsString(), e);
        }catch (HttpClientErrorException e){
            throw new ExternalRequestException(e.getResponseBodyAsString(), e);
        }
    }

    private String createGetRequestUrl(SearchBlogReqForNaver requestParam)  {
        String getUrl = URL;
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(getUrl)
                    .append("?")
                    .append("query=").append(URLEncoder.encode(requestParam.getQuery(),"UTF-8"))
                    .append("&sort=").append(requestParam.getSort())
                    .append("&display=").append(requestParam.getDisplay())
                    .append("&start=").append(requestParam.getStart());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private SearchBlogReqForNaver createRequestParam(SearchReqDto searchReqDto) {
        if(searchReqDto.getSort().equals("accuracy")){
            searchReqDto.setSort("sim");
        } else if (searchReqDto.getSort().equals("recency")){
            searchReqDto.setSort("date");
        }
        return SearchBlogReqForNaver.builder()
                .query(searchReqDto.getQuery())
                .sort(searchReqDto.getSort())
                .display(searchReqDto.getSize())
                .start(searchReqDto.getPage())
                .build();
    }

    private MultiValueMap<String, String> createHeaders(){
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.put("X-Naver-Client-Id", List.of(CLIENT_ID));
        headers.put("X-Naver-Client-Secret", List.of(CLIENT_SECRET));


        return  headers;
    }


}
