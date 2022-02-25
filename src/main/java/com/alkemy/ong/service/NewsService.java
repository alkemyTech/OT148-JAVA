package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.model.NewsModel;
import javassist.NotFoundException;

import java.util.Optional;

public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public NewsDTO getById(Long id) throws NotFoundException {
        Optional<NewsModel> modelOptional = newsRepository.findById(id);
        if(modelOptional.isPresent()){
            NewsModel newsModel= modelOptional.get();
            return NewsMapper.mapDomainToDTO(NewsMapper.mapModelToDomain(newsModel));
        }else{
            throw new NotFoundException(String.format("News with ID: %s not found", id));
        }
    }
}
