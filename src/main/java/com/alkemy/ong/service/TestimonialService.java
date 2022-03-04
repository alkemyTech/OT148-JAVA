package com.alkemy.ong.service;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.exception.CategoryNotFoundException;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.repository.model.TestimonialModel;

import javax.transaction.Transactional;
import java.util.Optional;

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

    public void deleteTestimonial(Long id) throws TestimonialNotFoundException {
        Optional<TestimonialModel> testimonialOptional = testimonialRepository.findById(id);
        if (!testimonialOptional.isEmpty()) {
            TestimonialModel testimonialModel = testimonialOptional.get();
            testimonialRepository.delete(testimonialModel);
        } else {
            throw new CategoryNotFoundException(String.format("Testimonial with ID: %s not found", id));
        }
    }
}
