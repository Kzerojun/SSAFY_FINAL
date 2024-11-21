package com.enjoytrip.security.service;

import com.enjoytrip.auth.model.mapper.AuthMapper;
import com.enjoytrip.user.constants.Role;
import com.enjoytrip.user.model.dto.UserDto;
import com.enjoytrip.user.model.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MysqlUsersDetailsService implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findUserByUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while loading user: " + e.getMessage(), e);
        }
    }

    private UserDto findUserByUsername(String username) throws SQLException {
        UserDto user = authMapper.getUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        log.debug("Found user: {}", user); // userPwd가 null이 아닌지 확인
        return user;
    }
}
