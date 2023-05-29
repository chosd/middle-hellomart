package com.itwill.hellomart.order;

public class OrderSQL {
	
	public final static String ORDER_INSERT = "insert into orders(o_no,o_date,o_status,o_option,o_price,userid,addr_no) values(orders_o_no_seq.nextval, sysdate,default,?,?,?,?)";
	
	public final static String ORDERITEM_INSERT = "insert into order_item (oi_no,oi_qty,o_no,p_no) values (order_item_oi_no_SEQ.nextval, ?, orders_o_no_SEQ.currval,?)";
	
	public final static String ORDER_DELETE_BY_USERID = "delete from orders where userid = ? and o_status = 'T'";
	
	public static final String ORDER_DELETE_BY_O_NO = "delete from orders where o_no = ?";
	
	public final static String ORDER_SELECT_BY_USERID = "select * from orders where userid = ? order by o_no desc";
	//주문 + 주문 아이템보기
	public final static String ORDER_SELECT_WITH_ORDERITEM_BY_O_TEST 
			= "select * from orders o join order_item oi on o.o_no=oi.o_no  join  product p on oi.p_no=p.p_no where  o.o_no = ? order by o.o_no desc";
	//특정 회원의 주문 목록 (상품 + 주소)
	public final static String ORDER_SELECT_WITH_ORDERITEM_BY_O_NO_ADDRESS
	= "select * from orders o join order_item oi on o.o_no = oi.o_no join product p on oi.p_no = p.p_no join address a on o.addr_no = a.addr_no where o.o_no = ?";

	public final static String ORDER_DELETE_BY_O_STATUS = "delete from orders where o_no = ? and o_status = 'T'";	
}
