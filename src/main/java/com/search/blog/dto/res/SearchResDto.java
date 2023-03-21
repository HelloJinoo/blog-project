package com.search.blog.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResDto {

    private int total_count;

    @JsonProperty(value = "is_end")
    private boolean is_end;
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class Item {
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private Date datetime;
    }
}
