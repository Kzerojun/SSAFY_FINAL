package com.enjoytrip.user.model;

import com.enjoytrip.user.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {

    private int userNo;
    private String userEmail;
    private String userPwd; // 비밀번호
    private String userName;
    private String createdAt;
    private Role role; // 사용자 권한

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한(Role)을 반환. 예: ROLE_USER
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return userPwd; // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return userEmail; // 사용자명을 이메일로 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부. true로 설정하여 만료되지 않음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부. true로 설정하여 잠기지 않음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호) 만료 여부. true로 설정하여 만료되지 않음
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부. true로 설정하여 활성화
    }

    @Override
    public String toString() {
        return "UserDto [userNo=" + userNo + ", userEmail=" + userEmail + ", userPwd=" + userPwd +
                ", userName=" + userName + ", createdAt=" + createdAt + ", role=" + role + "]";
    }
}
