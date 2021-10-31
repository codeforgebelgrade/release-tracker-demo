package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.ReleaseStatus;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class CreateReleaseValidator {

    public static void validate(Release release) throws ParameterValidationException {
        if(StringUtils.isEmpty(release.getReleaseName())){
            throw new ParameterValidationException("Release name must not empty!");
        }

        if(StringUtils.isEmpty(release.getReleaseDescription())){
            throw new ParameterValidationException("Release description must not empty!");
        }

        if(ReleaseStatus.getReleaseStatusByName(release.getReleaseStatus()) == null){
            throw new ParameterValidationException("Release status must have a valid value!");
        }

    }
}
