package com.codeforge.codeforgeDemo.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Timestamp;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String status;
    private String description;
    private String timestamp;
    private Object entity;

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String error) {
        this.description = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApiResponse(){
        Date date = new Date();
        long timeInMilliseconds = date.getTime();
        Timestamp tStamp = new Timestamp(timeInMilliseconds);
        this.timestamp = tStamp.toString();
    }
}
