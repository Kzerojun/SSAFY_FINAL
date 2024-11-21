package com.enjoytrip.user.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.enjoytrip.user.model.dto.UserDto;

@Mapper
public interface UserMapper {
	
	void registerUser(UserDto userDto) throws SQLException;
	
	UserDto loginUser(UserDto userDto) throws SQLException;
	
	void logoutUser();
	
	void deleteUser(UserDto userDto) throws SQLException;
	
	UserDto getUserProfile(int userNo) throws SQLException;

	UserDto getUserByEmail(String email) throws SQLException;

}
