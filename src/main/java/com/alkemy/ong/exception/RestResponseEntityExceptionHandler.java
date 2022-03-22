package com.alkemy.ong.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class ExceptionHandler extends ResponseEntityExceptionHandler {

    public ExceptionHandler() {
        super();
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final Map<String, String> bodyOfResponse = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            bodyOfResponse.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(ex, bodyOfResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    public ResponseEntity<ApiErrorDTO> handleUserNotFoundExceptions(OngRequestException ex) {
        ApiErrorDTO dtoNotFound =
                ApiErrorDTO.builder()
                        .code(ex.getCause().getLocalizedMessage())
                        .message(ex.getMessage()).build();
        return ResponseEntity.ok(dtoNotFound);
    }

//    @ExceptionHandler(DuplicateEmailException.class)
//    public ResponseEntity<ApiErrorDTO> handleDuplicateEmailExceptions(DuplicateEmailException ex) {
//        ApiErrorDTO emailDuplicate =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.BAD_REQUEST)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(emailDuplicate, HttpStatus.BAD_REQUEST);
//
//    }
//
//    @ExceptionHandler(InvalidPasswordException.class)
//    public ResponseEntity<Object> handleInvalidPasswordException(final InvalidPasswordException ex, final WebRequest request) {
//        final ApiErrorDTO bodyOfResponse =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.BAD_REQUEST)
//                        .message(ex.getMessage()).build();
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
//    }
//
//    @ExceptionHandler(WrongValuesException.class)
//    public ResponseEntity<ApiErrorDTO> handleWrongValuesException(WrongValuesException ex) {
//        ApiErrorDTO wrongValues = ApiErrorDTO.builder()
//                .code(HttpStatus.UNAUTHORIZED)
//                .message(ex.getMessage()).build();
//        return new ResponseEntity(wrongValues, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(OrganizationNotFoundException.class)
//    public ResponseEntity<Object> handleOrganizationNotFoundException(final InvalidPasswordException ex, final WebRequest request) {
//        final ApiErrorDTO bodyOfResponse =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
//    }
//
//    @ExceptionHandler(CategoryNotFoundException.class)
//    public ResponseEntity<ApiErrorDTO> handleCategoryNotFoundExceptions(CategoryNotFoundException ex) {
//        ApiErrorDTO categoryNotFound = ApiErrorDTO.builder()
//                .code(HttpStatus.NOT_FOUND)
//                .message(ex.getMessage()).build();
//        return new ResponseEntity(categoryNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NewsNotFoundException.class)
//    private ResponseEntity<ApiErrorDTO> handleNewsNotFound(NewsNotFoundException ex) {
//        ApiErrorDTO newsNotFound =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(CommentNotFoundException.class)
//    private ResponseEntity<ApiErrorDTO> handleNewsNotFound(CommentNotFoundException ex) {
//        ApiErrorDTO commentNotFound =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(commentNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(OperationNotPermittedException.class)
//    private ResponseEntity<ApiErrorDTO> handleOpNotPermittedException(OperationNotPermittedException ex) {
//        ApiErrorDTO badRequest =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.FORBIDDEN)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(badRequest, HttpStatus.FORBIDDEN);
//    }
//
//    @ExceptionHandler(MemberNotFoundException.class)
//    public ResponseEntity<ApiErrorDTO> handleMemberNotFoundExceptions(MemberNotFoundException ex) {
//        ApiErrorDTO memberNotFound = ApiErrorDTO.builder()
//                .code(HttpStatus.NOT_FOUND)
//                .message(ex.getMessage()).build();
//        return new ResponseEntity(memberNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NewsNotFoundException.class)
//    public ResponseEntity<ApiErrorDTO> handleNewsNotFoundExceptions(NewsNotFoundException ex) {
//        ApiErrorDTO newsNotFound =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(newsNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(OrganizationNotFoundException.class)
//    private ResponseEntity<ApiErrorDTO> handleOrganizationNotFound(OrganizationNotFoundException ex) {
//        ApiErrorDTO organizationNotFound =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(organizationNotFound, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(SlideNotFoundException.class)
//    public ResponseEntity<ApiErrorDTO> handleUserNotFoundExceptions(SlideNotFoundException ex) {
//        ApiErrorDTO slideNotFound =
//                ApiErrorDTO.builder()
//                        .code(HttpStatus.NOT_FOUND)
//                        .message(ex.getMessage()).build();
//        return new ResponseEntity(slideNotFound, HttpStatus.NOT_FOUND);
//    }
}