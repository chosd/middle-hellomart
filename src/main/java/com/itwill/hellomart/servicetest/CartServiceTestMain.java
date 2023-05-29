package com.itwill.hellomart.servicetest;

import java.util.List;

import com.itwill.hellomart.cart.Cart;
import com.itwill.hellomart.cart.CartService;

public class CartServiceTestMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		CartService cartService = new CartService();

//		System.out.println(cartService.addCart("guard3", 5, 1));

//		System.out.println(cartService.updateCart(13, 2));

//		System.out.println(cartService.getCartItemByCartNo(20));

//		System.out.println(cartService.deleteCartItemByCartNo(20));

//		System.out.println(cartService.deleteCartItemByUserId("guard1"));

		List<Cart> cartList = cartService.getCartListByUserId("guard2");
		for (Cart cart : cartList) {
			System.out.println(cart);
		}

	}

}