package com.enjoytrip.file.model.service;

import com.enjoytrip.file.model.dto.File;
import com.enjoytrip.file.model.dto.FileUploadResponse;
import com.enjoytrip.file.model.mapper.FileMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {
       try{
           String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
           String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

           Path filePath = Paths.get(uploadDir, uniqueFileName);
           Files.createDirectories(filePath.getParent());
           file.transferTo(filePath.toFile());

           String fileUrl = "/files/" + uniqueFileName; // 클라이언트가 사용할 경로

           File uploadFile = new File();
           uploadFile.setFilePath(fileUrl);
           fileMapper.insertFile(uploadFile);

           return FileUploadResponse.success(fileUrl);
       }catch(IOException e) {
           throw new RuntimeException("파일 업로드실패");
       }

    }

}
