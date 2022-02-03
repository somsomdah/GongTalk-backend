package com.dasom.gongtalk.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name="post_keyword", uniqueConstraints = @UniqueConstraint(columnNames = {"post_id","keyword_id"}))
public class PostKeyword {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    private Keyword keyword;

    public PostKeyword(Post post, Keyword keyword){
        this.post = post;
        this.keyword = keyword;
    }
}