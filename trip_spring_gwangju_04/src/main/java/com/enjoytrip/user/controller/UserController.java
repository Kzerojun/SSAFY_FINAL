package com.enjoytrip.user.controller;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enjoytrip.global.Code;
import com.enjoytrip.global.Message;
import com.enjoytrip.global.ResponseDto;
import com.enjoytrip.user.model.dto.GetUserProfileResponseDto;
import com.enjoytrip.user.model.dto.LoginUserResponseDto;
import com.enjoytrip.user.model.dto.UserDto;
import com.enjoytrip.user.model.service.JwtService;
import com.enjoytrip.user.model.service.UserService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	       
    private final UserService userService;
    //private final JwtService jwtService;

    @GetMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@RequestBody UserDto userDto) throws SQLException {
    	
    	UserDto user = this.userService.loginUser(userDto);
    	
    	// 로그인 성공 시
    	LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto(
    			Code.SUCCESS,
    			Message.SUCCESS,
    			user.getUserEmail(),
    			user.getUserName()
    			// TODO : 토큰 발급
    	);
    	
    	return ResponseEntity.ok(loginUserResponseDto);
    			
    	// TODO : 로그인 실패 시 
    }
    
    @GetMapping("/logout")
    public ResponseEntity<ResponseDto> logout() {
    	
    	// TODO : 토큰 제거
    	
    	ResponseDto responseDto = new ResponseDto(
    			Code.SUCCESS, 
    			Message.SUCCESS
    	);
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @PostMapping("/regist")
    public ResponseEntity<ResponseDto> regist(@RequestBody UserDto userDto) throws SQLException {
    	log.info(userDto.toString());
    	
    	this.userService.registerUser(userDto);
    	
    	ResponseDto responseDto = new ResponseDto(
    			Code.SUCCESS, 
    			Message.SUCCESS
    	);
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/delete/{userNo}")
    public ResponseEntity<ResponseDto> delete(@PathVariable("userNo") int userNo) 
    		throws SQLException {
    	
    	UserDto userDto = new UserDto();
    	userDto.setUserNo(userNo);
    	
    	this.userService.deleteUser(userDto);
    	
    	ResponseDto responseDto = new ResponseDto(
    			Code.SUCCESS, 
    			Message.SUCCESS
    	);
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @PatchMapping("/update/{userNo}")
    public ResponseEntity<ResponseDto> update(
    		@PathVariable("userNo") int userNo, @RequestBody UserDto userDto) throws SQLException {
    	
    	this.userService.updateUser(userDto);
    	
    	ResponseDto responseDto = new ResponseDto(
    			Code.SUCCESS, 
    			Message.SUCCESS
    	);
    	
    	return ResponseEntity.ok(responseDto);
    }
    
    @GetMapping("/profile/{userNo}")
    public ResponseEntity<ResponseDto> profile(@PathVariable("userNo") int userNo) 
    		throws SQLException {
    	
    	UserDto userDto = this.userService.getUserProfile(userNo);
    	
    	GetUserProfileResponseDto getUserProfileResponseDto = new GetUserProfileResponseDto(
    			Code.SUCCESS, 
    			Message.SUCCESS, 
    			userDto
    	);
    		
    	return ResponseEntity.ok(getUserProfileResponseDto);
    }
}