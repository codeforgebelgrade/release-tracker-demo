package com.codeforge.codeforgeDemo.controller;

import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.mapper.ReleaseMapper;
import com.codeforge.codeforgeDemo.model.api.ApiResponse;
import com.codeforge.codeforgeDemo.model.dto.Release;
import com.codeforge.codeforgeDemo.validation.CreateReleaseValidator;
import com.codeforge.codeforgeDemo.validation.SelectReleaseValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    private static final Logger logger = LogManager.getLogger(ReleaseController.class);

    @Autowired
    private ReleaseMapper mapper;

    @RequestMapping(method = RequestMethod.GET)
    public ApiResponse getReleases(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false, name = "release_date") String releaseDate,
                                   @RequestParam(required = false) Integer rows) throws ParseException, ParameterValidationException {
        SelectReleaseValidator.validate(status, releaseDate, rows);
        Date parsedDate = null;
        if(!StringUtils.isEmpty(releaseDate)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = dateFormat.parse(releaseDate);
        }
        ApiResponse response = new ApiResponse();
        response.setEntity(mapper.getAllReleases(status, name, parsedDate, rows));
        return response;
    }

    @RequestMapping(value = "/{releaseId}", method = RequestMethod.GET, produces={"application/json"})
    public ApiResponse getSingleRelease(@PathVariable int releaseId) {
        ApiResponse response = new ApiResponse();
        response.setEntity(mapper.getReleaseById(releaseId));
        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse insertRelease(@RequestBody Release release) throws ParameterValidationException {
        CreateReleaseValidator.validate(release);
        var result = mapper.insertRelease(release);
        ApiResponse response = new ApiResponse();
        response.setStatus("SUCCESS");
        return response;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ApiResponse updateRelease(@RequestBody Release release) throws ParameterValidationException {
        CreateReleaseValidator.validate(release);
        mapper.updateRelease(release);
        ApiResponse response = new ApiResponse();
        response.setStatus("SUCCESS");
        return response;
    }

    @RequestMapping(value = "/{releaseId}", method = RequestMethod.DELETE, produces={"application/json"})
    public ApiResponse removeRelease(@PathVariable int releaseId) {
        mapper.deleteReleaseById(releaseId);
        ApiResponse response = new ApiResponse();
        response.setStatus("SUCCESS");
        return response;
    }

}
