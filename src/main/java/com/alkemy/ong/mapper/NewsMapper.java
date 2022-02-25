package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.News;
import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.repository.model.NewsModel;

public class NewsMapper {

    public static News mapModelToDomain(NewsModel newsModel){
        News news = News.builder()
                .id(newsModel.getId())
                .name(newsModel.getName())
                .content(newsModel.getContent())
                .image(newsModel.getImage())
                .category(CategoryMapper.mapModelToDomain(newsModel.getCategoryModel()))
                .creationDate(newsModel.getCreationDate())
                .build();
        return news;
    }

    public static NewsModel mapDomainToModel(News news){
        NewsModel newsModel = NewsModel.builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .categoryModel(CategoryMapper.mapDomainToModel(news.getCategory()))
                .creationDate(news.getCreationDate())
                .build();
        return newsModel;
    }

    public static News mapDTOToDomain(NewsDTO dto){
        News news = News.builder()
                .id(dto.getId())
                .name(dto.getName())
                .content(dto.getContent())
                .image(dto.getImage())
                .category(CategoryMapper.mapDTOToDomain(dto.getCategory()))
                .creationDate(dto.getCreationDate())
                .build();
        return news;
    }

    public static NewsDTO mapDomainToDTO(News news){
        NewsDTO newsDTO = NewsDTO.builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .category(CategoryMapper.mapDomainToDTO(news.getCategory()))
                .creationDate(news.getCreationDate())
                .build();
        return newsDTO;
    }
}
