package com.alkemy.ong.dto;

import com.alkemy.ong.domain.News;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.service.NewsService;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsListDTO {
    private List<NewsDTO> news;
    private String nextPage;
    private String previousPage;

    @Autowired
    public NewsService newsService;

    public NewsListDTO(Integer page, Page<News> news, String currentContextPath) {
        if (news.hasNext()) {
            this.nextPage = currentContextPath.concat(String.format("/news?page=%d", page + 1));
        }
        if (news.hasPrevious()) {
            this.previousPage = currentContextPath.concat(String.format("/news?page=%d", page - 1));
        }
        this.news = news.getContent().stream().map(NewsMapper::mapDomainToDTO).collect(Collectors.toList());
    }
}
