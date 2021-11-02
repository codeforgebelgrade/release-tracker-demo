package com.codeforge.codeforgeDemo.validation;

import com.codeforge.codeforgeDemo.global.GlobalConstants;
import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.ReleaseStatus;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.apache.commons.lang3.StringUtils;

public class ReleaseValidator {

    public static void validate(Release release, boolean isNewRelase) throws ParameterValidationException {
        if(StringUtils.isEmpty(release.getReleaseName())){
            throw new ParameterValidationException(GlobalConstants.INVALID_RELEASE_NAME);
        }

        if(StringUtils.isEmpty(release.getReleaseDescription())){
            throw new ParameterValidationException(GlobalConstants.INVALID_RELEASE_DESCRIPTION);
        }

        if(ReleaseStatus.getReleaseStatusByName(release.getReleaseStatus()) == null){
            throw new ParameterValidationException(GlobalConstants.INVALID_RELEASE_STATUS);
        } else {
            if(isNewRelase && !release.getReleaseStatus().equals(ReleaseStatus.CREATED.getName())) {
                throw new ParameterValidationException(GlobalConstants.INVALID_INITIAL_RELEASE_STATUS);
            }
        }





    }
}
