package com.enjoytrip.user.controller;

import com.enjoytrip.user.model.dto.LoginUserResponseDto;
import com.enjoytrip.user.model.dto.PatchProfileImageResponse;
import com.enjoytrip.user.model.service.UserService;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping("/info")
	public ResponseEntity<LoginUserResponseDto> getUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated");
		}

		return ResponseEntity.ok(LoginUserResponseDto.success(authentication.getName()));
	}

	@PatchMapping("/profile")
	public ResponseEntity<PatchProfileImageResponse> updateProfileImage(String profileImage)
			throws SQLException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("User is not authenticated");
		}

		userService.updateProfileImage(authentication.getName(), profileImage);

		return ResponseEntity.ok(PatchProfileImageResponse.success());
	}

}
