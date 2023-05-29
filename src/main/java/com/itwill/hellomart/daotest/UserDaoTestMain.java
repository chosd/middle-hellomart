package com.itwill.hellomart.daotest;

import com.itwill.hellomart.user.User;
import com.itwill.hellomart.user.UserDao;

public class UserDaoTestMain {

	public static void main(String[] args) throws Exception {
		UserDao userDao = new UserDao();
		
		//회원추가
		System.out.println("1. insert : " + userDao.create(new User("guard4", "4444", "김경호4", "guard4@korea.com")));
		
		//회원정보수정
		System.out.println("2. update : " + userDao.update(new User("guard3", "3333", "김경호3", "guard3@korea.com")));
		
		//회원 삭제
		System.out.println("3. remove : " + userDao.remove("guard4"));
		
		//아이디로 회원 조회
		System.out.println("4. findUser : " + userDao.findUser("guard1"));
		
		//회원목록조회
		System.out.println("5. findUserList : " + userDao.findUserList());
		
		//아이디 존재 여부
		System.out.println("6. existedUser : " + userDao.existedUser("guard1"));
		
		

	}

}
