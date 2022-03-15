package com.alkemy.ong.dto;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class TestimonialListDTO {
    private List<TestimonialDTO> testimonials;
    private String nextPageUrl;
    private String previousPageUrl;

    @Autowired
    TestimonialService testimonialService;

    public TestimonialListDTO(Integer page, Page<Testimonial> testimonials, String currentContextPath) {
        if (testimonials.hasPrevious()) {
            this.previousPageUrl = currentContextPath.concat(String.format("/testimonials?page=%d", page - 1));
        }
        if (testimonials.hasNext()) {
            this.nextPageUrl = currentContextPath.concat(String.format("/testimonials?page=%d", page + 1));
        }
        this.testimonials = testimonials.getContent().stream().map(TestimonialMapper::mapDomainToDTO).collect(Collectors.toList());
    }
}
