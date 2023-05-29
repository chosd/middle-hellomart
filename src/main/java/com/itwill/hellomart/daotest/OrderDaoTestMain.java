package com.itwill.hellomart.daotest;

import java.util.ArrayList;
import java.util.List;

import com.itwill.hellomart.address.Address;
import com.itwill.hellomart.cart.Cart;
import com.itwill.hellomart.cart.CartDao;
import com.itwill.hellomart.order.Order;
import com.itwill.hellomart.order.OrderDao;
import com.itwill.hellomart.order.OrderItem;
import com.itwill.hellomart.product.Product;
import com.itwill.hellomart.product.ProductDao;

public class OrderDaoTestMain {

	public static void main(String[] args) throws Exception {
		
		OrderDao orderDao = new OrderDao();
//		System.out.println(orderDao.deleteByOrderStatus(6));
		
		//System.out.println(orderDao.deleteByUserId("guard1"));
		
		//System.out.println(orderDao.findOrderByUserId("guard2"));
		
		//배송 전 (삭제 )
		//System.out.println(orderDao.deleteByOrderStatus(18, "T         "));
	
		
		/*
		//상품에서 주문
		int p_qty = 1;
		int p_no = 1;
		ProductDao productDao = new ProductDao();
		Product product = productDao.findByPrimaryKey(p_no);
		System.out.println("product : " + product);
		
		ArrayList<OrderItem> newOrderItemList = new ArrayList<OrderItem>();
		Order newOrder = new Order(0,null,"배송","앞앞앞앞",950550,"guard2",new Address(1,null,null));
		newOrderItemList.add(new OrderItem(0,p_qty,p_no,product));
		newOrder.setOrderItemList(newOrderItemList);
		System.out.println("newOrder : " + newOrder);
		System.out.println(orderDao.insert(newOrder));
		
		//System.out.println(orderDao.findOrderByUserId("guard2"));
		
	
		*/
		/*cart에서 주문
		int o_tot_price = 0;
		String sUserId = "guard2";
		CartDao cartDao = new CartDao();
		List<Cart> cartList = new ArrayList<Cart>();
		System.out.println(cartList = cartDao.findByUserId(sUserId));
		newOrderItemList = new ArrayList<OrderItem>();
		for(Cart cart : cartList) {
			OrderItem orderItem = new OrderItem(0,cart.getCart_qty(),0,cart.getProduct());
			newOrderItemList.add(orderItem);
			o_tot_price+=orderItem.getOi_qty()*orderItem.getProduct().getP_price();
		}
		newOrder = new Order(0,null,null,"옵션",o_tot_price,sUserId, new Address(1,null,null));
		newOrder.setOrderItemList(newOrderItemList);
		System.out.println(orderDao.insert(newOrder));
		System.out.println(cartDao.deleteByUserId(sUserId));
		*/
		//System.out.println(orderDao.findOrderWithOrderItemByUserId("guard2"));
		
		//주문 번호로 삭제
		//System.out.println(orderDao.deleteByOrderNo(1));
		//주문 전체 삭제
//		System.out.println(orderDao.deleteByUserId("guard1"));
//		System.out.println(orderDao.findOrderByUserId("guard1"));
 
		System.out.println(orderDao.findOrderWithOrderItemByUserId("guard1"));
		
	}	
}
