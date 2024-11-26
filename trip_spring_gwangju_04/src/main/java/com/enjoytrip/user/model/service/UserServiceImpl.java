package com.enjoytrip.user.model.service;

import com.enjoytrip.user.model.dto.UserDto;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoytrip.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private final UserMapper userMapper;
	
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public void registerUser(UserDto userDto) throws SQLException {
		this.userMapper.registerUser(userDto);
	}
	
	@Override
	public UserDto loginUser(UserDto userDto) throws SQLException {
		return this.userMapper.loginUser(userDto);
	}

	@Override
	public void logoutUser() {
		this.userMapper.logoutUser();
	}

	@Override
	public void deleteUser(UserDto userDto) throws SQLException {
		this.userMapper.deleteUser(userDto);
	}

	@Override
	public void updateUser(UserDto userDto) throws SQLException {
		this.userMapper.deleteUser(userDto);
	}

	@Override
	public UserDto getUserProfile(int userNo) throws SQLException {
		return this.userMapper.getUserProfile(userNo);
	}

	@Override
	public void updateProfileImage(String userEmail, String profileImage) throws SQLException {
			userMapper.updateProfileImage(userEmail, profileImage);
	}

//	@Override
//	public List<UserDto> searchListAll() {
//		return UserDaoImpl.getUserDao().searchListAll();
//	}
//
//	@Override
//	public List<UserDto> searchUserByName(String name) {
//		return UserDaoImpl.getUserDao().searchUserByName(name);
//	}
//
//	@Override
//	public UserDto searchUserById(String id) {
//		return UserDaoImpl.getUserDao().searchUserByEmail(id);
//	}
//
//	@Override
//	public UserDto searchUserByNo(int no) {
//		return UserDaoImpl.getUserDao().searchUserByNo(no);
//	}
//
//	@Override
//	public void modifyUserId(String id, String pwd, String modifyId) {
//		UserDaoImpl.getUserDao().modifyUserEmail(id, pwd, modifyId);
//		
//	}
//
//	@Override
//	public void modifyUserPwd(String id, String pwd, String modifyPwd) {
//		UserDaoImpl.getUserDao().modifyUserPwd(id, pwd, modifyPwd);
//		
//	}
//
//	@Override
//	public void modifyUserName(String id, String pwd, String modifyName) {
//		UserDaoImpl.getUserDao().modifyUserName(id, pwd, modifyName);
//		
//	}
//
//	@Override
//	public void deleteUserById(String id) {
//		UserDaoImpl.getUserDao().deleteUserByEmail(id);
//		
//	}
//	
//	@Override
//	public void deleteUserByName(String name) {
//		UserDaoImpl.getUserDao().deleteUserByName(name);
//		
//	}
//
//	@Override
//	public String findPwd(String id, String name) {
//		return UserDaoImpl.getUserDao().findPwd(id, name);
//	}
//	
//    @Override
//    public int idCheck(String email) {
//        return userDao.idCheck(email);
//    }

}