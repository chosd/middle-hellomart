package com.itwill.hellomart.servicetest;

import com.itwill.hellomart.user.User;
import com.itwill.hellomart.user.UserService;

public class UserServiceTestMain {

	public static void main(String[] args) throws Exception {
		UserService userService = new UserService();
		
		/*
		 * 회원가입
		 */
		System.out.println(">>회원가입 : " + userService.create(new User("guard5", "5555", "김경호5", "guard5@korea.com")));
		
		/*
		 * 로그인
		 */
		System.out.println(">>로그인 : " + userService.login("guard1", "1111"));
		
		/*
		 * 회원정보수정
		 */
		System.out.println(">>회원정보수정 : " + userService.update(new User("guard3", "3333", "김경호3", "guard3@nate.com")));
		
		/*
		 * 회원탈퇴
		 */
		System.out.println(">>회원탈퇴 : " + userService.remove("guard5"));
		
		/*
		 * 회원정보보기
		 */
		System.out.println(">>회원정보보기 : " + userService.findUser("guard2"));
		
		/*
		 * 회원정보 전체 출력
		 */
		System.out.println(">>회원전체정보 : " + userService.findAllUser());
		
		/*
		 * 아이디 중복 체크
		 */
		System.out.println(">>아이디 중복 체크 : " + userService.isDuplicateId("guard1"));

	}

}
