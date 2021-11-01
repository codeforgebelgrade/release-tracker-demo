package com.codeforge.codeforgeDemo.global;

import com.codeforge.codeforgeDemo.global.exception.EntityNotFoundException;
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

    private static final Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception){
        if(exception instanceof ParameterValidationException) {
            logger.error("ParameterValidationException occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else if (exception instanceof EntityNotFoundException) {
            logger.error("EntityNotFoundException occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.notFound().build();

        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            logger.error("MethodArgumentTypeMismatchException occured. Message: {}", exception.getMessage());
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else {
            logger.error("Exception occured. Message: {}", exception.getMessage());
            exception.printStackTrace();
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
