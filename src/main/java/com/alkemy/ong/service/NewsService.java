package com.alkemy.ong.service;

import com.alkemy.ong.domain.News;
import com.alkemy.ong.exception.NewsNotFoundException;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.model.NewsModel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class NewsService {

    private final NewsRepository newsRepository;

    private static final Integer pageSize = 10;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News getById(Long id) throws NewsNotFoundException {
        Optional<NewsModel> modelOptional = newsRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            NewsModel newsModel = modelOptional.get();
            return NewsMapper.mapModelToDomain(newsModel);
        } else {
            throw new NewsNotFoundException(String.format("News with ID: %s not found", id));
        }
    }

    public News createNews(News news) {
        NewsModel newsModel = newsRepository.save(NewsMapper.mapDomainToModel(news));
        News newsSaved = NewsMapper.mapModelToDomain(newsModel);
        return newsSaved;
    }

    public News updateNews(Long id, News news) throws NewsNotFoundException {
        if (newsRepository.existsById(id)) {
            NewsModel newsModel = newsRepository.findById(Long.valueOf(id)).get();
            newsModel.setId(news.getId());
            newsModel.setName(news.getName());
            newsModel.setContent(news.getContent());
            newsModel.setImage(news.getImage());
            newsModel.setCategoryModel(CategoryMapper.mapDomainToModel(news.getCategory()));
            newsModel.setCreationDate(news.getCreationDate());
            NewsModel newsSaved = newsRepository.save(newsModel);
            return NewsMapper.mapModelToDomain(newsSaved);
        } else {
            throw new NewsNotFoundException(String.format("News with ID: %s not found", id));
        }
    }

    public void deleteNews(Long id) throws NewsNotFoundException {
        Optional<NewsModel> modelOptional = newsRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            NewsModel newsModel = modelOptional.get();
            newsRepository.delete(newsModel);
        } else {
            throw new NewsNotFoundException(String.format("News with ID: %s not found", id));
        }
    }

    @Transactional
    public Page<News> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<NewsModel> paginatedNews = newsRepository.findAll(pageable);
        List<News> news = paginatedNews.getContent().stream().map(NewsMapper::mapModelToDomain).collect(Collectors.toList());
        return new PageImpl<>(news, pageable, paginatedNews.getTotalElements());
    }
}
