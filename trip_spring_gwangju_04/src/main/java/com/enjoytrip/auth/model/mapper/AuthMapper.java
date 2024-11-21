package com.enjoytrip.auth.model.mapper;

import com.enjoytrip.auth.dto.request.SignUpRequest;
import com.enjoytrip.auth.dto.request.DeactivateRequest;
import com.enjoytrip.auth.dto.request.LoginRequest;
import com.enjoytrip.user.model.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface AuthMapper {

    /**
     * 회원가입
     *
     * @param request 회원가입 요청 데이터
     */
    void signUp(SignUpRequest request);

    /**
     * 사용자 비활성화
     *
     * @param request 사용자 비활성화 요청 데이터
     */
    void deactivateUser(DeactivateRequest request);

    /**
     * 로그인
     *
     * @param request 로그인 요청 데이터
     * @return 사용자 정보
     */
    UserDto loginUser(LoginRequest request);

    /**
     * 이메일로 사용자 정보 조회
     *
     * @param email 이메일
     * @return 사용자 정보
     */
    Optional<UserDto> getUserByEmail(String email);
}
