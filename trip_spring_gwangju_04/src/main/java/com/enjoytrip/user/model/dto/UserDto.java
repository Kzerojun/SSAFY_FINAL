package com.enjoytrip.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private int userNo;
    private String userEmail;
    private String userPwd;
    private String userName;
    private String createdAt;

    @Override
    public String toString() {
        return "UserDto [userNo=" + userNo + ", userEmail=" + userEmail + ", userPwd=" + userPwd + ", userName=" + userName
                + ", createdAt=" + createdAt + "]";
    }
}