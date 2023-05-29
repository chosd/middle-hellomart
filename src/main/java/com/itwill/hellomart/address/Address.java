package com.itwill.hellomart.address;

public class Address {
	private int addr_no;
	private String userid;
	private String loc;
	
	public Address() {
		// TODO Auto-generated constructor stub
	}

	public Address(int addr_no, String userid, String loc) {
		super();
		this.addr_no = addr_no;
		this.userid = userid;
		this.loc = loc;
	}
	public String getUserid() {
		return userid;
	}

	public int getAddr_no() {
		return addr_no;
	}

	public void setAddr_no(int addr_no) {
		this.addr_no = addr_no;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@Override
	public String toString() {
		return "Address [addr_no=" + addr_no + ", userid=" + userid + ", loc=" + loc + "]";
	}
	
}
