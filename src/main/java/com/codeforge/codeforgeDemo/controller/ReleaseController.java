package com.codeforge.codeforgeDemo.controller;

import com.codeforge.codeforgeDemo.config.TracerConfig;
import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.EntityNotFoundException;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import com.codeforge.codeforgeDemo.model.dto.Release;
import com.codeforge.codeforgeDemo.service.ReleaseService;
import com.codeforge.codeforgeDemo.validation.CreateReleaseValidator;
import com.codeforge.codeforgeDemo.validation.SelectReleaseValidator;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private static final Logger logger = LogManager.getLogger(ReleaseController.class);

    @Autowired
    private ReleaseService releaseService;

    private Tracer tracer = TracerConfig.initTracer("codeforgeDemo");


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApiResponse> getReleases(@RequestParam(required = false) String status,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false, name = "release_date") String releaseDate,
                                      @RequestParam(required = false) Integer rows) throws ParseException, ParameterValidationException {
        Span baseSpan = tracer.buildSpan("get-releases").start();
        Span validatorSpan = tracer.buildSpan("validator").asChildOf(baseSpan).start();
        SelectReleaseValidator.validate(status, releaseDate, rows);
        validatorSpan.finish();

        Date parsedDate = null;
        if(!StringUtils.isEmpty(releaseDate)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = dateFormat.parse(releaseDate);
        }
        Span mapperSpan = tracer.buildSpan("mapper").asChildOf(baseSpan).start();
        List<Release> resultList = releaseService.findReleases(status, name, parsedDate, rows);
        mapperSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        response.setEntity(resultList);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/{releaseId}", method = RequestMethod.GET, produces={"application/json"})
    public ResponseEntity<ApiResponse> getSingleRelease(@PathVariable int releaseId) throws EntityNotFoundException {
        Release release = releaseService.findReleaseById(releaseId);
        if(release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        response.setEntity(release);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> insertRelease(@RequestBody Release release) throws ParameterValidationException {
        CreateReleaseValidator.validate(release);
        releaseService.saveRelease(release);
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/{releaseId}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse> updateRelease(@PathVariable int releaseId, @RequestBody Release release)
            throws ParameterValidationException, EntityNotFoundException {

        Span baseSpan = tracer.buildSpan("update-release").start();
        Span mapperSearchSpan = tracer.buildSpan("find-release").asChildOf(baseSpan).start();
        Release existingRelease = releaseService.findReleaseById(releaseId);
        mapperSearchSpan.finish();
        if(existingRelease == null) {
            throw new EntityNotFoundException("Release you are trying to update does not exist.");
        }

        Span validatorSpan = tracer.buildSpan("validator").asChildOf(baseSpan).start();
        CreateReleaseValidator.validate(release);
        validatorSpan.finish();
        Span mapperUpdateSpan = tracer.buildSpan("mapper-search").asChildOf(baseSpan).start();
        releaseService.updateReleaseInformation(releaseId, release);
        mapperUpdateSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/{releaseId}", method = RequestMethod.DELETE, produces={"application/json"})
    public ResponseEntity<ApiResponse> removeRelease(@PathVariable int releaseId) {
        releaseService.removeRelease(releaseId);
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        return ResponseEntity.noContent().build();
    }

}
