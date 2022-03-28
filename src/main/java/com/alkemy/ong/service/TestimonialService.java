package com.alkemy.ong.service;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.exception.OngRequestException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.repository.model.TestimonialModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestimonialService {

    private final TestimonialRepository testimonialRepository;

    private static final int PAGE_SIZE = 10;

    public TestimonialService(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Transactional
    public Testimonial createTestimonial(Testimonial testimonial) {
        TestimonialModel testimonialModel = testimonialRepository.save(TestimonialMapper.mapDomainToModel(testimonial));
        Testimonial testimonialSaved = TestimonialMapper.mapModelToDomain(testimonialModel);
        return testimonialSaved;
    }

    public void deleteTestimonial(Long id) throws OngRequestException {
        Optional<TestimonialModel> testimonialOptional = testimonialRepository.findById(id);
        if (!testimonialOptional.isEmpty()) {
            TestimonialModel testimonialModel = testimonialOptional.get();
            testimonialRepository.delete(testimonialModel);
        } else {
            throw new OngRequestException("Testimonial not found", "not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Testimonial updateTestimonial(Long id, Testimonial testimonial) throws OngRequestException {
        Optional<TestimonialModel> optionalTestimonialModel = testimonialRepository.findById(id);
        if (optionalTestimonialModel.isEmpty()) {
            throw new OngRequestException("Testimonial not found", "not.found", HttpStatus.NOT_FOUND);
        }
        TestimonialModel testimonialModel = optionalTestimonialModel.get();
        testimonialModel.setName(testimonial.getName());
        testimonialModel.setImage(testimonial.getImage());
        testimonialModel.setContent(testimonial.getContent());
        testimonialRepository.save(testimonialModel);
        return TestimonialMapper.mapModelToDomain(testimonialModel);
    }

    @Transactional
    public Page<Testimonial> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        var paginatedTestimonials = testimonialRepository.findAll(pageable);
        var testimonials = paginatedTestimonials.getContent().stream().map(TestimonialMapper::mapModelToDomain).collect(Collectors.toList());
        return new PageImpl<>(testimonials, pageable, paginatedTestimonials.getTotalElements());
    }
}
