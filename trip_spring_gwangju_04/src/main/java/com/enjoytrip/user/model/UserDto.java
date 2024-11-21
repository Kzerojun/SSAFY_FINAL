package com.enjoytrip.user.model;

import com.enjoytrip.user.constants.Role;
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
    private Role role;

    @Override
    public String toString() {
        return "UserDto [userNo=" + userNo + ", userEmail=" + userEmail + ", userPwd=" + userPwd + ", userName=" + userName
                + ", createdAt=" + createdAt + "]";
    }
}