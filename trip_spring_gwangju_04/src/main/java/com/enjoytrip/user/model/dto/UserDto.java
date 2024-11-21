package com.enjoytrip.user.model.dto;

import com.enjoytrip.user.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
	
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.toString()));
    }

    @Override
    public String getPassword() {
        return userPwd;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }
}