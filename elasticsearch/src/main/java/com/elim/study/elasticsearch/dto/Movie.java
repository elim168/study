package com.elim.study.elasticsearch.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Movie {

    private Long id;
    private String title;
    private String director;
    private Integer year;
    private Date createTime;

}
