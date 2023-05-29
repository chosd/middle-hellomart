package com.itwill.hellomart.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itwill.hellomart.address.Address;

/*
이름       널?       유형            
-------- -------- ------------- 
O_NO     NOT NULL NUMBER(10)    
O_DATE            DATE          
O_STATUS          CHAR(10)      
O_OPTION          VARCHAR2(100) 
O_PRICE           NUMBER(10)    
USERID            VARCHAR2(100) 
ADDR_NO           NUMBER(10)  
 */
public class Order {
	
	private int o_no;
	private Date date;
	private String o_status;
	private String o_option;
	private int o_price;
	private String userId;
	private Address address;
	
	private List<OrderItem> orderItemList;
 	
	public Order() {
		orderItemList = new ArrayList<OrderItem>();
	}

	public Order(int o_no, Date date, String o_status, String o_option, int o_price, String userId, Address address) {
		super();
		this.o_no = o_no;
		this.date = date;
		this.o_status = o_status;
		this.o_option = o_option;
		this.o_price = o_price;
		this.userId = userId;
		this.address = address;
		orderItemList = new ArrayList<OrderItem>();
	}
	
	
	public Order(int o_no, Date date, String o_status, String o_option, int o_price, String userId) {
		super();
		this.o_no = o_no;
		this.date = date;
		this.o_status = o_status;
		this.o_option = o_option;
		this.o_price = o_price;
		this.userId = userId;
		this.orderItemList = new ArrayList<OrderItem>();
	}

	public int getO_no() {
		return o_no;
	}

	public void setO_no(int o_no) {
		this.o_no = o_no;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getO_status() {
		return o_status;
	}

	public void setO_status(String o_status) {
		this.o_status = o_status;
	}

	public String getO_option() {
		return o_option;
	}

	public void setO_option(String o_option) {
		this.o_option = o_option;
	}

	public int getO_price() {
		return o_price;
	}

	public void setO_price(int o_price) {
		this.o_price = o_price;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "Order [o_no=" + o_no + ", date=" + date + ", o_status=" + o_status + ", o_option=" + o_option
				+ ", o_price=" + o_price + ", userId=" + userId + ", address=" + address + ", orderItemList="
				+ orderItemList + "]";
	}

	
	

}
