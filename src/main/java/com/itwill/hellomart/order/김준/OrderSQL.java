package com.itwill.hellomart.order.김준;

public class OrderSQL {
	public final static String ORDER_DELETE_ALL = "delete from orders where userid = ?";
	public final static String ORDER_DELETE_BY_O_NO = "delete from orders where o_no = ?";
	public final static String ORDER_INSERT = "insert into orders(o_no, o_date, o_status, o_option, o_price, userid) values (orders_o_no_SEQ.nextval, sysdate, ?, ?, ?, ?)";
	public final static String ORDERITEM_INSERT = "insert into order_item(oi_no, oi_qty, o_no, p_no) values(order_item_oi_no_SEQ.nextval, ?, orders_o_no_SEQ.currval, ?)";
	public final static String ORDER_SELECT_BY_USERID = "select * from orders where userid = ?";
	public final static String ORDER_SELECT_BY_O_NO = "select * from orders where o_no = ?";
	public final static String ORDER_SELECT_WITH_ORDERITEM_BY_O_NO = "select * from orders o join order_item oi on o.o_no=oi.o_no  join  product p on oi.p_no=p.p_no where  o.o_no = ?";
	public final static String ORDER_DELETE_BY_0_STATUS = "delete from orders where o_status = ?";
	
}