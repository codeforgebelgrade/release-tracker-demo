package com.codeforge.codeforgeDemo.model;

import org.apache.commons.lang3.StringUtils;

public enum ReleaseStatus {

    CREATED("Created"),
    IN_DEVELOPMENT("In Development"),
    ON_DEV("On Dev"),
    QA_DONE_ON_DEV("QA Done on DEV"),
    ON_STAGING("On staging"),
    QA_DONE_ON_STAGING("QA done on STAGING"),
    ON_PROD("On PROD"),
    DONE("Done");

    private String name;

    ReleaseStatus(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ReleaseStatus getReleaseStatusByName(String status){
        ReleaseStatus result = null;
        if(!StringUtils.isEmpty(status)) {
            for (ReleaseStatus releaseStatus : values()) {
                if (releaseStatus.getName().equals(status.trim())) {
                    result = releaseStatus;
                    break;
                }
            }
        }
        return result;
    }
}
