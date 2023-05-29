package com.itwill.hellomart.order.김준;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


import com.itwill.hellomart.common.DataSourceFactory;
import com.itwill.hellomart.product.Product;

public class OrderDao {
	
	private DataSource dataSource;
	
	public OrderDao() throws Exception {
		
		dataSource = DataSourceFactory.getDataSource();
	}
	
	//전체 삭제
	public int deleteAll(String userId) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement("OrderSQL.ORDER_DELETE_ALL");
			pstmt.setString(1, userId);
			rowCount = pstmt.executeUpdate();
			con.commit();
			
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
			throw e;
			
		} finally {
			if (con != null) {
				con.close();
			}
		}
		
		return rowCount;
	}
	
	//1개 삭제
	public int deleteByOrderNo(int o_no) throws Exception {
			
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
			
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(OrderSQL.ORDER_DELETE_BY_O_NO);
			pstmt.setInt(1, o_no);
			rowCount = pstmt.executeUpdate();
			con.commit();
			
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
			throw e;
			
		} finally {
			if (con != null) {
				con.close();
			}
		}
		
		return rowCount;
	}
	
	//주문
	public int insert(Order order) throws Exception {

		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt1 = con.prepareStatement(OrderSQL.ORDER_INSERT);
			pstmt1.setString(1, order.getO_status());
			pstmt1.setString(2, order.getO_option());
			pstmt1.setInt(3, order.getO_price());
			pstmt1.setString(4, order.getUserid());
			pstmt1.executeUpdate();

			pstmt2 = con.prepareStatement(OrderSQL.ORDERITEM_INSERT);
			for (OrderItem orderItem : order.getOrderItemList()) {
				pstmt2.setInt(1, orderItem.getOi_qty());
				pstmt2.setInt(2, orderItem.getProduct().getP_no());
				pstmt2.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;
		} finally {
			if (con != null)
				con.close();
		}
		return 0;
	}
	
	//주문 전체 조회
	public List<Order> findOrderByUserId(String sUserId) throws Exception {
		ArrayList<Order> orderList = new ArrayList<Order>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(OrderSQL.ORDER_SELECT_BY_USERID);
			pstmt.setString(1, sUserId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				orderList.add(new Order(rs.getInt("o_no"), rs.getDate("o_date"), 
										rs.getString("o_status"), rs.getString("o_option"),
										rs.getInt("o_price"), rs.getString("userid")));
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return orderList;
	}
	
	//주문+주문아이템 보기

	public List<Order> findOrderWithOrderItemByUserId(String userId) throws Exception {
		
		List<Order> orderList = new ArrayList<Order>();
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try {
			con = dataSource.getConnection();
			pstmt1 = con.prepareStatement(OrderSQL.ORDER_SELECT_BY_USERID);
			pstmt1.setString(1, userId);
			rs1 = pstmt1.executeQuery();
			
			while (rs1.next()) {
				orderList.add(new Order(rs1.getInt("o_no"), 
										rs1.getDate("o_date"), 
										rs1.getString("o_status"), 
										rs1.getString("o_option"),
										rs1.getInt("o_price"), 
										rs1.getString("userid")));
							}
			
			pstmt2 = con.prepareStatement(OrderSQL.ORDER_SELECT_WITH_ORDERITEM_BY_O_NO);
			for (int i = 0; i < orderList.size(); i++) {
				Order tempOrder = orderList.get(i);
				
				pstmt2.setInt(1, tempOrder.getO_no());
				rs2 = pstmt2.executeQuery();
				Order orderWithOrderItem = null;
				
				if (rs2.next()) {
					orderWithOrderItem = new Order(	rs2.getInt("o_no"), rs2.getDate("o_date"), 
													rs2.getString("o_status"), rs2.getString("o_option"),
													rs2.getInt("o_price"), rs2.getString("userid"));
					do {
						orderWithOrderItem.getOrderItemList()
												.add(new OrderItem(rs2.getInt("oi_no"), 
																   rs2.getInt("oi_qty"), 
																   rs2.getInt("o_no"),
																	new Product(rs2.getInt("p_no"), 
																				rs2.getString("p_name"), 
																				rs2.getInt("p_price"),
																				rs2.getString("p_image"),
																				rs2.getString("p_desc"), 
																				rs2.getInt("ct_no"))));
					} while (rs2.next());
				}
				orderList.set(i, orderWithOrderItem);
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return orderList;
	}
	
	//주문 상태 T만 삭제
	public Order deleteT(int o_no) throws Exception {
		Order order = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int rowCount = 0;
		
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(OrderSQL.ORDER_SELECT_BY_O_NO);
			pstmt.setInt(1, o_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				order = new Order(rs.getInt("o_no"), rs.getDate("o_date"), 
										rs.getString("o_status"), rs.getString("o_option"),
										rs.getInt("o_price"), rs.getString("userid"));
				
				if(order.getO_status().equalsIgnoreCase("T         ")) {
				rowCount = deleteByOrderNo(o_no);
				}
			}
			
		}	finally {
			if (con != null) {
				con.close();
			}
		}
		return order;
	}
	
	//T만 삭제
	public int deleteByOrderStatus(String o_status) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		//삭제확인
		int rowCount = 0;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(OrderSQL.ORDER_DELETE_BY_0_STATUS);
			pstmt.setString(1, o_status);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return rowCount;
	}
	
	
	
}	