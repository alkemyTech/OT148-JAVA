package com.alkemy.ong.service;

import com.alkemy.ong.domain.Activity;
import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.repository.model.ActivityModel;
import com.alkemy.ong.repository.model.TestimonialModel;

import javax.transaction.Transactional;

public class TestimonialService {

    private final TestimonialRepository testimonialRepository;

    public TestimonialService(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Transactional
    public Testimonial createTestimonial(Testimonial testimonial) {
        TestimonialModel testimonialModel = testimonialRepository.save(TestimonialMapper.mapDomainToModel(testimonial));
        Testimonial testimonialSaved = TestimonialMapper.mapModelToDomain(testimonialModel);
        return testimonialSaved;
    }
}
