package com.itwill.hellomart.address;

import java.util.List;

public class AddressService {
	private AddressDao addressDao;
	
	public AddressService () throws Exception{
		addressDao=new AddressDao();
	}
	public int addressInsert(Address address) throws Exception{
		return addressDao.addressInsert(address);
	}
	public int addressUpdate(Address address) throws Exception{
		return addressDao.addressUpdate(address);
	}
	public int addressDelete(int addr_no) throws Exception{
		return addressDao.addressDelete(addr_no);
	}
	public List<Address> addressfindAll(String userId) throws Exception{
		return addressDao.addressfindAll(userId);
	}
	public int addressfindNo() throws Exception {
		return addressDao.addressfindNo();
	}
}
