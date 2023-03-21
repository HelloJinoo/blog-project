package com.search.blog.searchsource.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchBlogReqForNaver{

    private String query;
    private String sort;

    private int display;
    private int start;
}
