package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ErrorDTO;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getById(@PathVariable Long id) throws NewsNotFoundException {
        return ResponseEntity.ok(NewsMapper.mapDomainToDTO(newsService.getById(id)));
    }

    @ExceptionHandler(NewsNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNewsNotFoundExceptions(NewsNotFoundException ex){
        ErrorDTO newsNotFound =
                ErrorDTO.builder()
                        .code(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage()).build();
        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);

    }
}
