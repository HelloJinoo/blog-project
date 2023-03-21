package com.search.blog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SearchLog {

    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private Long id;
    private String query;
    private Long count;

    @Builder
    public SearchLog(String query, Long count) {
        this.query = query;
        this.count = count;
    }

    public void plusCount(){
        this.setCount(this.count+1);
    }

    private void setCount(Long count) {
        this.count = count;
    }
}
