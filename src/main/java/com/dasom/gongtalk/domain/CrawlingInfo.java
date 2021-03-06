package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Entity
public class CrawlingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="board_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Board board;

    @NotNull
    private String boardUrl;
    private String boardRowSelector;
    private String boardRowItemSelector;

    @NotNull
    private String postContentSelector;
    private String postTitleSelector;
    private String postCategorySelector;
    private String postWriterSelector;
    private String postDateSelector;
    private String postDatePattern;


    @Column(columnDefinition = "varchar(10) default 'GET'")
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    public enum HttpMethod {
        GET,
        POST;
    }


}
