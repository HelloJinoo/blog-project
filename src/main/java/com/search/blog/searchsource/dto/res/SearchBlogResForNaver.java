package com.search.blog.searchsource.dto.res;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class SearchBlogResForNaver {

    //format : Sun, 19 Mar 2023 12:48:32 +0900
    private Date lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Builder
    public SearchBlogResForNaver(Date lastBuildDate, int total, int start, int display, List<Item> items) {
        this.lastBuildDate = lastBuildDate;
        this.total = total;
        this.start = start;
        this.display = display;
        this.items = items;
    }

    public SearchBlogResForNaver() {
    }

    @Data
    @NoArgsConstructor
    public static class Item{
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;
        private Date postdate;
    }
}
