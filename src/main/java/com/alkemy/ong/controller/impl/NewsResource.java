package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.NewsController;
import com.alkemy.ong.domain.News;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsListDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.util.ContextUtils;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "NewsResource", tags = {"News"})
@RestController
public class NewsResource implements NewsController {

    private final NewsService newsService;

    public NewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public ResponseEntity<NewsListDTO> getAll(Integer page) {
        Page<News> news = newsService.getAll(page);
        NewsListDTO response = new NewsListDTO(page, news, ContextUtils.currentContextPath());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<NewsDTO> getById(Long id) throws NewsNotFoundException {
        return ResponseEntity.ok(NewsMapper.mapDomainToDTO(newsService.getById(id)));
    }

    @Override
    public ResponseEntity<NewsDTO> createNews(NewsDTO newsDTO) {
        News news = newsService.createNews((NewsMapper.mapDTOToDomain(newsDTO)));
        NewsDTO result = NewsMapper.mapDomainToDTO(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Override
    public ResponseEntity<NewsDTO> updateNews(Long id, NewsUpdateDTO newsUpdateDTO) throws NewsNotFoundException {
        News news = NewsMapper.mapUpdateDTOToDomain(newsUpdateDTO);
        NewsDTO newsUpdated = NewsMapper.mapDomainToDTO(newsService.updateNews(id, news));
        return ResponseEntity.ok().body(newsUpdated);
    }

    @Override
    public ResponseEntity<?> deleteNews(Long id) throws NewsNotFoundException {
        newsService.deleteNews(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNewsNotFoundExceptions(NewsNotFoundException ex) {
        ErrorDTO newsNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);
    }
}
