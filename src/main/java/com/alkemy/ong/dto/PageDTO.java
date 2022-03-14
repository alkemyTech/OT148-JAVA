package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDTO<T> {
    private Integer totalPages;
    private String nextPage;
    private String previousPage;
    private List<T> list;
}
