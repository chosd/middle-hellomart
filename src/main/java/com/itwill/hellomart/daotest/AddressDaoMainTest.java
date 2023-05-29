package com.itwill.hellomart.daotest;

import com.itwill.hellomart.address.Address;
import com.itwill.hellomart.address.AddressDao;

public class AddressDaoMainTest {

	public static void main(String[] args)throws Exception {
		AddressDao addressDao = new AddressDao();
		//주소 입력
		//addressDao.addressInsert(new Address(0,"guard1","경기도 성남시 분당구 삼평동"));
		//주소 변경
		//System.out.println(addressDao.addressUpdate(new Address(1,null,"서울시 수정구 수정동")));
		//주소 삭제
		//addressDao.addressDelete(1);
		//주소 전제보기
		//System.out.println(addressDao.addressfindAll("guard2"));

	}

}
