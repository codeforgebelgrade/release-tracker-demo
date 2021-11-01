package com.codeforge.codeforgeDemo.global;

import com.codeforge.codeforgeDemo.global.exception.EntityNotFoundException;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import com.mindscapehq.raygun4java.core.RaygunClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);

    @Value("${raygun.clientId}")
    private String raygunClientId;

    @Value("${raygun.enabled}")
    private boolean raygunEnabled;

    private RaygunClient raygunClient = null;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception){
        if(exception instanceof ParameterValidationException) {
            logger.error("ParameterValidationException occurred. Message: {}", exception.getMessage());
            if(raygunEnabled) {
                this.logRaygunEvent(exception);
            }
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else if (exception instanceof EntityNotFoundException) {
            logger.error("EntityNotFoundException occurred. Message: {}", exception.getMessage());
            if(raygunEnabled) {
                this.logRaygunEvent(exception);
            }
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.notFound().build();

        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            logger.error("MethodArgumentTypeMismatchException occurred. Message: {}", exception.getMessage());
            if(raygunEnabled) {
                this.logRaygunEvent(exception);
            }
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.badRequest().body(response);

        } else {
            logger.error("Exception occurred. Message: {}", exception.getMessage());
            if(raygunEnabled) {
                this.logRaygunEvent(exception);
            }
            exception.printStackTrace();
            ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_ERROR);
            response.setDescription(exception.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    private void logRaygunEvent(Exception ex) {

        if(raygunClient == null) {
            raygunClient = new RaygunClient(raygunClientId);
        }
        raygunClient.send(ex);
    }
}
