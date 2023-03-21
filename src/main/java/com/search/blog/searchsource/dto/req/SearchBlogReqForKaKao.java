package com.search.blog.searchsource.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBlogReqForKaKao {

    private String query;
    private String sort;
    private int page;
    private int size;
}
