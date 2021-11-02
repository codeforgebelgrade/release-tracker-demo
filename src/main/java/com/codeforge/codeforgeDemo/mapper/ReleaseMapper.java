package com.codeforge.codeforgeDemo.mapper;

import com.codeforge.codeforgeDemo.model.entity.ReleaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReleaseMapper {

    int insertRelease(ReleaseEntity release);
    int updateRelease(ReleaseEntity release);
    List<ReleaseEntity> getAllReleases(String status, String name, Date releaseDate, Integer rows);
    ReleaseEntity getReleaseById(int id);
    int deleteReleaseById(int id);
}
