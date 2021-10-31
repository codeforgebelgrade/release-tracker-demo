package com.codeforge.codeforgeDemo.model.entity;

import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.model.dto.Release;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReleaseEntityTransformer {

    public static ReleaseEntity transformReleaseToEntityModel(Release release) throws ParameterValidationException {

        ReleaseEntity entity = new ReleaseEntity();
        entity.setId(release.getId());
        entity.setName(release.getReleaseName());
        entity.setDescription(release.getReleaseDescription());
        entity.setStatus(release.getReleaseStatus());
        if(!StringUtils.isEmpty(release.getReleaseDate())){
            entity.setReleaseDate(getDateFromStringPattern(release.getReleaseDate()));
        }
        return entity;
    }

    public static Release transformEntityModelToRelease(ReleaseEntity entity) {
        if(entity != null) {
            Release release = new Release();
            release.setId(entity.getId());
            release.setReleaseName(entity.getName());
            release.setReleaseStatus(entity.getStatus());
            release.setReleaseDescription(entity.getDescription());
            if (entity.getReleaseDate() != null) {
                release.setReleaseDate(DateFormatUtils.format(entity.getReleaseDate(), "yyyy-MM-dd"));
            }
            return release;
        } else {
            return null;
        }
    }

    private static Date getDateFromStringPattern(String datePattern) throws ParameterValidationException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(datePattern);
        } catch (ParseException e) {
            throw new ParameterValidationException(e.getMessage());
        }
    }
}
