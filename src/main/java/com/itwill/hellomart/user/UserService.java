package com.itwill.hellomart.user;

import java.util.List;

public class UserService {
	private UserDao userDao;
	
	public UserService() throws Exception {
		userDao = new UserDao();
	}
	
	/*
	 * 회원가입
	 */
	public int create(User user) throws Exception {
		if(userDao.existedUser(user.getUserId())) {
			//아이디 중복
			return -1;
		}else {
			//아이디 중복 X -> 회원가입
			int insertRowCount = userDao.create(user);
			return insertRowCount;
			// 1 => 회원가입
		}
	}
	
	/*
	 * 로그인
	 */
	public int login(String userId, String password) throws Exception {
		int result = -1;
		
		User user = userDao.findUser(userId);
		if(user == null) {
			//아이디 존재 X
			result = 0;
		}else {
			//아이디 존재
			if(user.isMatchPassword(password)) {
				//패스워드 일치
				result = 2;
			}else {
				//패스워드 불일치
				result = 1;
			}
		}
		return result; 
	}
	
	/*
	 * 회원정보수정
	 */
	public int update(User user) throws Exception {
		return userDao.update(user);
	}
	
	/*
	 * 회원탈퇴
	 */
	public int remove(String userId) throws Exception {
		return userDao.remove(userId);
	}
	
	/*
	 * 회원정보 상세보기
	 */
	public User findUser(String userId) throws Exception {
		return userDao.findUser(userId);
	}
	
	/*
	 * 회원정보 전체 출력
	 */
	public List<User> findAllUser() throws Exception {
		List<User> findAllUser = userDao.findUserList();
		return findAllUser;
	}
	
	/*
	 * 아이디 중복체크
	 */
	public boolean isDuplicateId(String userId) throws Exception {
		boolean isExist = userDao.existedUser(userId);
		if(isExist) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 아이디 중복체크용 조회리스트
	 */
	public List findAllUserId() throws Exception {
		List findAllUserId = userDao.findUserIdList();
		return findAllUserId();
	}
	
	
	
	
	
	

}
