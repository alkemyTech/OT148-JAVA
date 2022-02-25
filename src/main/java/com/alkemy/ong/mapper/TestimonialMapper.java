package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.repository.model.TestimonialModel;

public class TestimonialMapper {
    public static Testimonial mapModelToDomain(TestimonialModel testimonialModel) {
        Testimonial testimonial = Testimonial.builder()
                .name(testimonialModel.getName())
                .image(testimonialModel.getImage())
                .content(testimonialModel.getContent())
                .creationDate(testimonialModel.getCreationDate())
                .build();
        return testimonial;
    }

    public static TestimonialModel mapDomainToModel(Testimonial testimonial) {
        TestimonialModel testimonialModel = TestimonialModel.builder()
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .creationDate(testimonial.getCreationDate())
                .build();
        return testimonialModel;
    }
}
