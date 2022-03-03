package com.alkemy.ong.controller;

import com.alkemy.ong.domain.Contact;
import com.alkemy.ong.dto.ContactDTO;
import static com.alkemy.ong.mapper.ContactMapper.mapDomainToDto;
import static com.alkemy.ong.mapper.ContactMapper.mapDtoToDomain;
import com.alkemy.ong.service.ContactService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactDTO> storeContact(@Valid @RequestBody ContactDTO contactDTO) {
        Contact contact = mapDtoToDomain(contactDTO);
        ContactDTO contactDTOSave = mapDomainToDto(contactService.addContact(contact));
        return ResponseEntity.ok(contactDTOSave);
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
