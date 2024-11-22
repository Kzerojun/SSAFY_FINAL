package com.enjoytrip.file.model.mapper;


import com.enjoytrip.file.model.dto.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    // 파일 경로 저장
    void insertFile(File file);

}
