package com.search.blog.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchReqDto {

    private String query;
    private String sort;

    private int page;
    private int size;
}
