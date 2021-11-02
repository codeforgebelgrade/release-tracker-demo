package com.codeforge.codeforgeDemo.controller;

import com.codeforge.codeforgeDemo.config.TracerConfig;
import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.EntityNotFoundException;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import com.codeforge.codeforgeDemo.model.dto.Release;
import com.codeforge.codeforgeDemo.service.ReleaseService;
import com.codeforge.codeforgeDemo.validation.ReleaseValidator;
import com.codeforge.codeforgeDemo.validation.SelectReleaseValidator;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.commons.lang3.StringUtils;
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

    private ReleaseService releaseService;
    private Tracer tracer = TracerConfig.initTracer("codeforgeDemo");

    @GetMapping(produces={"application/json"})
    public ResponseEntity<ApiResponse> findReleases(@RequestParam(required = false) String status,
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
        Span serviceSpan = tracer.buildSpan("service").asChildOf(baseSpan).start();
        List<Release> resultList = releaseService.findReleases(status, name, parsedDate, rows);
        serviceSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        response.setEntity(resultList);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{releaseId}", produces={"application/json"})
    public ResponseEntity<ApiResponse> getSingleRelease(@PathVariable int releaseId) throws EntityNotFoundException {
        Span baseSpan = tracer.buildSpan("get-single-release").start();
        Span serviceSpan = tracer.buildSpan("service").asChildOf(baseSpan).start();
        Release release = releaseService.findReleaseById(releaseId);
        if(release == null) {
            throw new EntityNotFoundException("Release not found");
        }
        serviceSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        response.setEntity(release);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(produces={"application/json"})
    public ResponseEntity<ApiResponse> insertRelease(@RequestBody Release release) throws ParameterValidationException {
        Span baseSpan = tracer.buildSpan("update-release").start();
        Span validatorSpan = tracer.buildSpan("validator").asChildOf(baseSpan).start();
        ReleaseValidator.validate(release, true);
        validatorSpan.finish();
        Span serviceSpan = tracer.buildSpan("service").asChildOf(baseSpan).start();
        releaseService.saveRelease(release);
        serviceSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{releaseId}", produces={"application/json"})
    public ResponseEntity<ApiResponse> updateRelease(@PathVariable int releaseId, @RequestBody Release release)
            throws ParameterValidationException, EntityNotFoundException {

        Span baseSpan = tracer.buildSpan("update-release").start();
        Span mapperSearchSpan = tracer.buildSpan("service-search").asChildOf(baseSpan).start();
        Release existingRelease = releaseService.findReleaseById(releaseId);
        mapperSearchSpan.finish();
        if(existingRelease == null) {
            throw new EntityNotFoundException("Release you are trying to update does not exist.");
        }

        Span validatorSpan = tracer.buildSpan("validator").asChildOf(baseSpan).start();
        ReleaseValidator.validate(release, false);
        validatorSpan.finish();
        Span serivceSpan = tracer.buildSpan("service").asChildOf(baseSpan).start();
        releaseService.updateReleaseInformation(releaseId, release);
        serivceSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        baseSpan.finish();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{releaseId}", produces={"application/json"})
    public ResponseEntity<ApiResponse> removeRelease(@PathVariable int releaseId) {
        Span baseSpan = tracer.buildSpan("delete-release").start();
        Span serviceSpan = tracer.buildSpan("service").asChildOf(baseSpan).start();
        releaseService.removeRelease(releaseId);
        serviceSpan.finish();
        ApiResponse response = new ApiResponse(GlobalConstants.API_RESULT_SUCCESS);
        baseSpan.finish();
        return ResponseEntity.noContent().build();
    }

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

}
