package com.codeforge.codeforgeDemo.mapper;

import com.codeforge.codeforgeDemo.model.dto.Release;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReleaseMapper {

    public int insertRelease(Release release);
    public int updateRelease(Release release);
    public List<Release> getAllReleases(String status, String name, Date releaseDate, Integer rows);
    public Release getReleaseById(int id);
    public int deleteReleaseById(int id);
}
