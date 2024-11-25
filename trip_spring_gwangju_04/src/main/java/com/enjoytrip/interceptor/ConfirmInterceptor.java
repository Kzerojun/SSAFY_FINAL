package com.enjoytrip.interceptor;

import com.enjoytrip.user.model.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ConfirmInterceptor implements HandlerInterceptor { 

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userinfo");
		if(userDto == null) {
			System.out.println("CALL!! : Interceptor");
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
	
		return true;
	}
	
}