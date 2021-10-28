package com.codeforge.codeforgeDemo.global;

import com.codeforge.codeforgeDemo.controller.ReleaseController;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ReleaseController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception){
        if(exception instanceof ParameterValidationException) {
            logger.error("ParameterValidationException occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse();
            response.setStatus("ERROR");
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            logger.error("MethodArgumentTypeMismatchException occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse();
            response.setStatus("ERROR");
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else {
            logger.error("Exception occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse();
            response.setStatus("ERROR");
            response.setDescription(exception.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    /*@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        LOGGER.error("HttpRequestMethodNotSupportedException occured. Message: {}", exception.getMessage());

        List<String> errors = List.of(exception.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .body(new ResponseError(errors));

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseError> handleResourceNotFoundException(ResourceNotFoundException exception){
        LOGGER.error("ResourceNotFoundException occured. Message: {}", exception.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseError> handleNumberParsingError(MethodArgumentTypeMismatchException exception){
        LOGGER.error("Invalid value recieved for parameter '{}'. Value received '{}'. Full exception message: {}",
                exception.getParameter().getParameterName(),
                exception.getValue(),
                exception.getMessage());

        String message = MessageFormat.format("Invalid value recieved for parameter {0}", exception.getParameter().getParameterName());

        List<String> errors = List.of(message);
        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        List<String> errors = exception.getAllErrors()
                .stream()
                .map(error -> {
                    LOGGER.error("Validation failed. Message {}", error.getDefaultMessage());
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ResponseError> handleApplicationException(ApplicationException exception){
        LOGGER.error("ApplicationException occured. Message: {}", exception.getMessage());

        List<String> errors = List.of(exception.getMessage());

        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseError> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        List<String> errors = List.of("Invalid JSON. Please check request body and validate it for errors");

        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseError> handleBindException(BindException exception){

        List<String> errors = exception.getAllErrors()
                .stream()
                .map(error -> {
                    LOGGER.error("Validation failed. Message {}", error.getDefaultMessage());
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseError> handleConstraintViolationException(ConstraintViolationException exception){

        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(error -> {
                    LOGGER.error("Validation failed. Message {}", error.getMessage());
                    return error.getMessage();
                })
                .collect(Collectors.toList());

        return ResponseEntity.badRequest()
                .body(new ResponseError(errors));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ResponseError> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        LOGGER.error("HttpMediaTypeNotSupportedException occured. Message {}", exception.getMessage());

        List<String> errors = List.of(exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                .body(new ResponseError(errors));
    }*/
}
