package com.enjoytrip.user.model.service;

import com.enjoytrip.user.model.dto.UserDto;
import java.sql.SQLException;

public interface UserService {

	void updateProfileImage(String userEmail, String profileImage) throws SQLException;

	void registerUser(UserDto userDto) throws SQLException;
	
	UserDto loginUser(UserDto userDto) throws SQLException;
	
	void logoutUser();
	
	void deleteUser(UserDto userDto) throws SQLException;
	
	void updateUser(UserDto userDto) throws SQLException;
	
	UserDto getUserProfile(int userNo) throws SQLException;
	
	//List<UserDto> searchListAll();
	
	//List<UserDto> searchUserByName(String name);
	
	//UserDto searchUserById(String id);
	
	//UserDto searchUserByNo(int no);
	
	//void modifyUserId(String id, String pwd, String modifyId);
	
	//void modifyUserPwd(String id, String pwd, String modifyPwd);
	
	//void modifyUserName(String id, String pwd, String modifyName);
	
	//void deleteUserById(String id);
	
	//void deleteUserByName(String name);
	
	//String findPwd(String id, String name);
	
	//int userIdCheck(String email);

}

