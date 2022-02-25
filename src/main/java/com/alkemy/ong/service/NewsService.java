package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.repository.NewsRepository;

public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public NewsDTO getById(Long id){

    }
}
