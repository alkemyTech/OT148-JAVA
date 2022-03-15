package com.alkemy.ong.controller.impl;

import com.alkemy.ong.controller.NewsController;
import com.alkemy.ong.domain.News;
import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import static com.alkemy.ong.mapper.NewsMapper.mapDomainToDTO;
import com.alkemy.ong.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsResource implements NewsController {

    private final NewsService newsService;

    public NewsResource(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public NewsDTO getById(Long id) throws NewsNotFoundException {
        return mapDomainToDTO(newsService.getById(id));
    }

    @Override
    public NewsDTO updateNews(Long id, NewsUpdateDTO newsUpdateDTO) throws NewsNotFoundException {
        News news = NewsMapper.mapUpdateDTOToDomain(newsUpdateDTO);
        NewsDTO newsUpdated = mapDomainToDTO(newsService.updateNews(id, news));
        return newsUpdated;
    }

    @Override
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = newsService.createNews((NewsMapper.mapDTOToDomain(newsDTO)));
        NewsDTO result = mapDomainToDTO(news);
        return result;
    }

    @Override
    public void deleteNews(Long id) throws NewsNotFoundException {
        newsService.deleteNews(id);
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
