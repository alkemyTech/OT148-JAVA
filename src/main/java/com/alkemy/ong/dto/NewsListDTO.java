package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsListDTO {
    private List<NewsDTO> news;
    private String nextPage;
    private String previousPage;
}
