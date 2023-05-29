package com.itwill.hellomart.cart;

import com.itwill.hellomart.product.Product;

public class Cart {

	private int cart_no;
	private int cart_qty;
	private String userid;
	private Product product;

	public Cart() {

	}

	public Cart(int cart_no, int cart_qty, String userid, Product product) {
		super();
		this.cart_no = cart_no;
		this.cart_qty = cart_qty;
		this.userid = userid;
		this.product = product;
	}

	public int getCart_no() {
		return cart_no;
	}

	public int getCart_qty() {
		return cart_qty;
	}

	public String getUserid() {
		return userid;
	}

	public Product getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return "Cart [cart_no=" + cart_no + ", cart_qty=" + cart_qty + ", userid=" + userid + ", product=" + product
				+ "]";
	}

}
