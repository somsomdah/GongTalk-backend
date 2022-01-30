package com.dasom.gongtalk.domain.keyword;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String content;

    @NotNull
    private boolean isDefault;

    public Keyword(String content){
        this.content = content;
        this.isDefault = false;
    }

}
