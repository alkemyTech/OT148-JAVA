package com.alkemy.ong.mapper;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.repository.model.TestimonialModel;

public class TestimonialMapper {
    public static Testimonial mapModelToDomain(TestimonialModel testimonialModel) {
        Testimonial testimonialDomain = Testimonial.builder().
                id(testimonialModel.getId()).
                name(testimonialModel.getName()).
                image(testimonialModel.getImage()).build();
        content(testimonialModel.getContent()).build();
        return testimonialDomain;
    }

    public static TestimonialModel mapDomainToModel(Testimonial testimonialDomain) {
        TestimonialModel testimonialModel = TestimonialModel.builder().
                id(testimonialDomain.getId()).
                name(testimonialDomain.getName()).
                image(testimonialDomain.getImage()).build();
        content(testimonialDomain.getContent()).build();
        return testimonialModel;
    }
}
