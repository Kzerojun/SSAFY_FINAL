package com.enjoytrip.file.model.dto;


import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import lombok.Getter;

@Getter
public class FileUploadResponse extends ResponseDto {

    private String filePath;

    private FileUploadResponse(Code code, Message message, String fileUrl) {
        super(code, message);
        this.filePath = fileUrl;
    }

    public static FileUploadResponse success(String filePath) {
        return new FileUploadResponse(Code.SUCCESS, Message.SUCCESS, filePath);
    }
}
