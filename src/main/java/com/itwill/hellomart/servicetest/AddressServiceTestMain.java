package com.itwill.hellomart.servicetest;

import com.itwill.hellomart.address.Address;
import com.itwill.hellomart.address.AddressService;

public class AddressServiceTestMain {

	public static void main(String[] args) throws Exception {
		AddressService addressService = new AddressService();
		
		//addressService.addressInsert(new Address(0,"guard1","경기도 용인시 선덕원"));
		//addressService.addressUpdate(new Address(4,"guard1","서울이 성독구 도봉동"));
		//addressService.addressDelete(4);
		//System.out.println(addressService.addressfindAll("guard2"));
	}

}
