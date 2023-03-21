package com.search.blog.config;

import com.search.blog.searchsource.ISearchSource;
import com.search.blog.searchsource.KaKaoSearch;
import com.search.blog.searchsource.NaverSearch;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SeachConfig {



    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }


}
