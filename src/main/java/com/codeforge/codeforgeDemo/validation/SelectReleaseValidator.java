package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.ReleaseStatus;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectReleaseValidator {

    public static void validate(String status, String releaseDate, Integer rows) throws ParameterValidationException {
        if(!StringUtils.isEmpty(status)) {
            if (ReleaseStatus.getReleaseStatusByName(status) == null) {
                throw new ParameterValidationException("Release status must have a valid value!");
            }
        }

        if (rows != null && rows <= 0) {
            throw new ParameterValidationException("Value for rows parameter must not be zero or negative!");
        }

        if(!StringUtils.isEmpty(releaseDate)){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parsedDate = dateFormat.parse(releaseDate);
            } catch (ParseException e) {
                throw new ParameterValidationException("Invalid value specified for releaseDate parameter!");
            }
        }
    }
}
