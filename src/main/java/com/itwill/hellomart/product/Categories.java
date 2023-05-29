package com.itwill.hellomart.product;

public class Categories {
	private int ct_no;
	private String ct_name;
	
	public Categories() {
		// TODO Auto-generated constructor stub
	}

	public Categories(int ct_no, String ct_name,Product product) {
		super();
		this.ct_no = ct_no;
		this.ct_name = ct_name;
		
	}

	public int getCt_no() {
		return ct_no;
	}

	public void setCt_no(int ct_no) {
		this.ct_no = ct_no;
	}

	public String getCt_name() {
		return ct_name;
	}

	public void setCt_name(String ct_name) {
		this.ct_name = ct_name;
	}


	@Override
	public String toString() {
		return "Categories [ct_no=" + ct_no + ", ct_name=" + ct_name + ", product=" +  "]";
	}
	

	
	
}