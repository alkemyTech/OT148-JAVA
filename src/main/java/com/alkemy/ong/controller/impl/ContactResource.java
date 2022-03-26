package com.alkemy.ong.controller.impl;


import com.alkemy.ong.controller.ContactController;
import com.alkemy.ong.domain.Contact;
import com.alkemy.ong.dto.ContactDTO;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.service.ContactService;
import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static com.alkemy.ong.mapper.ContactMapper.mapDomainToDto;
import static com.alkemy.ong.mapper.ContactMapper.mapDtoToDomain;

@Api(value = "ContactResource", tags = {"Contact"})
@RestController
public class ContactResource implements ContactController {

    private final ContactService contactService;

    public ContactResource(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactDTO createContact(ContactDTO contactDTO) {
        Contact contact = mapDtoToDomain(contactDTO);
        ContactDTO contactDTOSave = mapDomainToDto(contactService.addContact(contact));
        return contactDTOSave;
    }

    @Override
    public List<ContactDTO> findAll() {
        List<ContactDTO> contactDTOS = contactService.getAll()
                .stream().map(ContactMapper::mapDomainToDto)
                .collect(Collectors.toList());
        return contactDTOS;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}