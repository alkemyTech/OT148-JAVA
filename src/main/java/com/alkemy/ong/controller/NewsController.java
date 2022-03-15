package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.NewsUpdateDTO;
import com.alkemy.ong.exception.NewsNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface NewsController {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    NewsDTO getById(@PathVariable Long id) throws NewsNotFoundException;

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    NewsDTO updateNews(@PathVariable Long id,
                       @RequestBody NewsUpdateDTO newsUpdateDTO) throws NewsNotFoundException;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    NewsDTO createNews(@Valid @RequestBody NewsDTO newsDTO);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteNews(@PathVariable Long id) throws NewsNotFoundException;

}
