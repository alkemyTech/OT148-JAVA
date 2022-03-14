package com.alkemy.ong.service;

import com.alkemy.ong.domain.Testimonial;
import com.alkemy.ong.dto.PagesDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.exception.ParamNotFoundException;
import com.alkemy.ong.exception.TestimonialNotFoundException;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.repository.model.TestimonialModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestimonialService {

    private final TestimonialRepository testimonialRepository;

    private static final int PAGE_SIZE = 10;

    private ModelMapper mapper = new ModelMapper();

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
            throw new TestimonialNotFoundException(String.format("Testimonial with ID: %s not found", id));
        }
    }

    @Transactional
    public Testimonial updateTestimonial(Long id, Testimonial testimonial) throws TestimonialNotFoundException {
        Optional<TestimonialModel> optionalTestimonialModel = testimonialRepository.findById(id);
        if (optionalTestimonialModel.isEmpty()) {
            throw new ActivityNotFoundException(String.format("Activity with ID: " + id + " not found"));
        }
        TestimonialModel testimonialModel = optionalTestimonialModel.get();
        testimonialModel.setName(testimonial.getName());
        testimonialModel.setImage(testimonial.getImage());
        testimonialModel.setContent(testimonial.getContent());
        testimonialRepository.save(testimonialModel);
        return TestimonialMapper.mapModelToDomain(testimonialModel);
    }

    @Transactional
    public PagesDTO<TestimonialDTO> getPages(Integer page) {

        if (page < 0)
            throw new ParamNotFoundException("Page number can't be less than 0");

        Pageable pageRequest = PageRequest.of(page, 10);
        Page<TestimonialModel> testimonials = testimonialRepository.findAll(pageRequest);
        return responsePage(testimonials);
    }

    private PagesDTO<TestimonialDTO> responsePage(Page<TestimonialModel> page) {

        if (page.isEmpty())
            throw new ParamNotFoundException("The requested page doesn't exist");

        Page<TestimonialDTO> response = new PageImpl<>(
                ModelListToDTO(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize()),
                page.getTotalElements()
        );
        return new PagesDTO<>(response, "localhost:8080/testimonials?page=");
    }

    public List<TestimonialDTO> ModelListToDTO(List<TestimonialModel> entities) {
        return entities.stream().map(testimonialModel ->
                        mapper.map(testimonialModel, TestimonialDTO.class))
                .collect(Collectors.toList());
    }

}
