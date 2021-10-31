package com.codeforge.codeforgeDemo.mapper;

import com.codeforge.codeforgeDemo.model.entity.ReleaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReleaseMapper {

    public int insertRelease(ReleaseEntity release);
    public int updateRelease(ReleaseEntity release);
    public List<ReleaseEntity> getAllReleases(String status, String name, Date releaseDate, Integer rows);
    public ReleaseEntity getReleaseById(int id);
    public int deleteReleaseById(int id);
}
