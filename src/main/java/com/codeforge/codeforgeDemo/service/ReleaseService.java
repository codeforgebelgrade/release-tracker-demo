package com.codeforge.codeforgeDemo.service;

import com.codeforge.codeforgeDemo.global.exception.ParameterValidationException;
import com.codeforge.codeforgeDemo.mapper.ReleaseMapper;
import com.codeforge.codeforgeDemo.model.dto.Release;
import com.codeforge.codeforgeDemo.model.entity.ReleaseEntity;
import com.codeforge.codeforgeDemo.model.entity.ReleaseEntityTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReleaseService {

    private ReleaseMapper mapper;

    private static final Logger logger = LogManager.getLogger(ReleaseService.class);

    @Transactional
    public int saveRelease(Release release) throws ParameterValidationException {
        logger.info("Persisting release data...");
        return mapper.insertRelease(ReleaseEntityTransformer.transformReleaseToEntityModel(release));
    }

    @Transactional
    public int updateReleaseInformation(Integer releaseId, Release release) throws ParameterValidationException {
        release.setId(releaseId);
        logger.info("Updating release information...");
        return mapper.updateRelease(ReleaseEntityTransformer.transformReleaseToEntityModel(release));
    }

    @Transactional
    public int removeRelease(int releaseId) {
        logger.info("Deleting release...");
        return mapper.deleteReleaseById(releaseId);
    }

    @Transactional
    public Release findReleaseById(int releaseId) {
        logger.info("Retrieving release data...");
        ReleaseEntity resultEntity = mapper.getReleaseById(releaseId);
        return ReleaseEntityTransformer.transformEntityModelToRelease(resultEntity);
    }

    @Transactional
    public List<Release> findReleases(String status, String name, Date releaseDate, Integer rows) {
        List<Release> resultList = new ArrayList<>();
        logger.info("Retrieving release data...");
        for (ReleaseEntity entity : mapper.getAllReleases(status, name, releaseDate, rows)) {
            resultList.add(ReleaseEntityTransformer.transformEntityModelToRelease(entity));
        }
        return  resultList;
    }

    public ReleaseService(ReleaseMapper mapper) {
        this.mapper = mapper;
    }
}
