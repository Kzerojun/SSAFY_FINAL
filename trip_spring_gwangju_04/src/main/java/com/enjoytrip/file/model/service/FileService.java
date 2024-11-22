package com.enjoytrip.file.model.service;

import com.enjoytrip.file.model.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService{

    FileUploadResponse uploadFile(MultipartFile file);

}
