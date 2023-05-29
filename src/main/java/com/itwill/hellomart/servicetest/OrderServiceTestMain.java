package com.itwill.hellomart.servicetest;

import com.itwill.hellomart.order.OrderService;

public class OrderServiceTestMain {

	public static void main(String[] args) throws Exception{
		
		//상품 직접 주문
		OrderService orderService = new OrderService();
		//System.out.println("상품에서 직접 주문 : " + orderService.createDirectOrder("guard1", 1, 2));
		//System.out.println("장바구니에서 전체 주문 : " + orderService.createCartOrder("guard2"));
		//System.out.println("로그인 회원 주문 목록 : " + orderService.findOrderByUserId("guard2"));
		//System.out.println("주문+주문아이템 [상세보기]:" + orderService.orderWithOrderItem(20));
		//System.out.println("주문 + 주문 아이템 목록 :" orderService.findOrderWithOrderItemByUserId("guard2"));
		//System.out.println("로그인 회원의 전체 주문 삭제 : " + orderService.deleteBysUserId("guard2"));
		//System.out.println("주문 1건 삭제 : " + orderService.deleteByOrderNo(16));
		//System.out.println("배송 전 삭제 :" + orderService.deleteByOrderStatus(29));

	}

}
