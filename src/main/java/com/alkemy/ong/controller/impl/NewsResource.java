package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.NewsController;
import com.alkemy.ong.domain.News;
import com.alkemy.ong.dto.NewsCreationDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsListDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.util.ContextUtils;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import static com.alkemy.ong.mapper.NewsMapper.mapDomainToDTO;

@Api(value = "NewsResource", tags = {"News"})
@RestController
public class NewsResource implements NewsController {

    private final NewsService newsService;

    public NewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public NewsListDTO getAll(Integer page) {
        Page<News> news = newsService.getAll(page);
        NewsListDTO response = new NewsListDTO(page, news, ContextUtils.currentContextPath());
        return response;
    }

    @Override
    public NewsDTO getById(Long id) throws NewsNotFoundException {
        return mapDomainToDTO(newsService.getById(id));
    }

    @Override
    public NewsDTO createNews(NewsCreationDTO newsCreationDTO) {
        News news = newsService.createNews((NewsMapper.mapCreationDTOToDomain(newsCreationDTO)));
        NewsDTO result = mapDomainToDTO(news);
        return result;
    }

    @Override
    public NewsDTO updateNews(Long id, NewsUpdateDTO newsUpdateDTO) throws NewsNotFoundException {
        News news = NewsMapper.mapUpdateDTOToDomain(newsUpdateDTO);
        NewsDTO newsUpdated = mapDomainToDTO(newsService.updateNews(id, news));
        return newsUpdated;
    }

    @Override
    public void deleteNews(Long id) throws NewsNotFoundException {
        newsService.deleteNews(id);
    }
}
