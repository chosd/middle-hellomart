package com.itwill.hellomart.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.itwill.hellomart.address.Address;
import com.itwill.hellomart.common.DataSourceFactory;
import com.itwill.hellomart.product.Product;

public class OrderDao {

	private DataSource dataSource;


	public OrderDao() throws Exception {

		//dataSource = DataSourceFactory.getDataSource();
		Properties properties = new Properties();
		properties.load(DataSourceFactory.class.getResourceAsStream("/jdbc.properties"));
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(properties.getProperty("driverClassName"));
		basicDataSource.setUrl(properties.getProperty("url"));
		basicDataSource.setUsername(properties.getProperty("username"));
		basicDataSource.setPassword(properties.getProperty("password"));
		dataSource = basicDataSource;
	}

	//주문 생성
	public int insert (Order order) throws Exception {

		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		int rowCount1 = 0;
		int rowCount2 = 0;

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			/*"insert into orderes(o_no,o_date,o_status,o_option,o_price,userid,addr_no) "
																+ "values(orders_o_no_seq.nextval, sysdate, ?, ?, ?, ?, ?)"; */
			//status --> 
			pstmt1 = con.prepareStatement(OrderSQL.ORDER_INSERT);
			pstmt1.setString(1, order.getO_option());
			pstmt1.setInt(2, order.getO_price());
			pstmt1.setString(3, order.getUserId());
			pstmt1.setInt(4, order.getAddress().getAddr_no());
			rowCount1 = pstmt1.executeUpdate();
			//"insert into order_item (oi_no,oi_qty,o_no,p_no) "values (order_item_oi_no_SEQ.nextval, ?, orders_o_no_SEQ.currva,?)";
			pstmt2 = con.prepareStatement(OrderSQL.ORDERITEM_INSERT);
			for(OrderItem orderItem : order.getOrderItemList()) {
				pstmt2.setInt(1, orderItem.getOi_qty());
				pstmt2.setInt(2, orderItem.getProduct().getP_no());
				rowCount2 = pstmt2.executeUpdate();
			}
			//commit
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
			throw e;

		} finally {
			if(pstmt1!=null || pstmt2!=null) {
				pstmt1.close();
				pstmt2.close();
			}
			if (con!=null) {
				con.close();
			}
		}
		return rowCount1*rowCount2;
	}
	// 주문 전체 삭제 
	public int deleteByUserId(String sUserId) throws Exception {

		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(OrderSQL.ORDER_DELETE_BY_USERID);
			pstmt.setString(1, sUserId);
			rowCount = pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null) {
				con.close();
			}
		}
		return rowCount;
	}

	//주문 1건 삭제
	public int deleteByOrderNo(int o_no) throws Exception{
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
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(con!=null)
				con.close();
		}
		return rowCount;
	}
	
	//T만 삭제
	public int deleteByOrderStatus(int o_no) throws Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		//삭제확인
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(OrderSQL.ORDER_DELETE_BY_O_STATUS);
			pstmt.setInt(1, o_no);
			
			rowCount = pstmt.executeUpdate();
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
	
	// 특정 회원의 *주문 목록* 보기
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
				/*o_no, o_date,o_status,o_option, o_price, userid */
				orderList.add(new Order(rs.getInt("o_no"), 
										rs.getDate("o_date"), 
										rs.getString("o_status"),
										rs.getString("o_option"), 
										rs.getInt("o_price"),
										rs.getString("userid")));
				
			}
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return orderList;
	}
	

	//특정 회원의 주문과 주문 상품 보기
	public List<Order> findOrderWithOrderItemByUserId(String sUserId) throws Exception {

		List<Order> orderList = new ArrayList<Order>();
		Connection con = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		
		try {
			con = dataSource.getConnection();
			pstmt1 = con.prepareStatement(OrderSQL.ORDER_SELECT_BY_USERID);
			pstmt1.setString(1, sUserId);
			rs1 = pstmt1.executeQuery();
			
			while (rs1.next()) {
							orderList.add(new Order(rs1.getInt("o_no"), 
													rs1.getDate("o_date"), 
													rs1.getString("o_status"), 
													rs1.getString("o_option"),
													rs1.getInt("o_price"), 
													rs1.getString("userid")));
			}
			
			pstmt2 = con.prepareStatement(OrderSQL.ORDER_SELECT_WITH_ORDERITEM_BY_O_TEST);
			for (int i = 0; i < orderList.size(); i++) {
				Order tempOrder = orderList.get(i);
				
				pstmt2.setInt(1, tempOrder.getO_no());
				rs2 = pstmt2.executeQuery();
				Order orderWithOrderItem = null;
				
				if (rs2.next()) {
					orderWithOrderItem = new Order(	rs2.getInt("o_no"), 
													rs2.getDate("o_date"), 
													rs2.getString("o_status"), 
													rs2.getString("o_option"),
													rs2.getInt("o_price"), 
													rs2.getString("userid"));
					do {
						orderWithOrderItem.getOrderItemList().add(new OrderItem(rs2.getInt("oi_no"), 
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

	// 주문번호로 주문 상세보기 
	public Order findByOrderNo(int o_no) throws Exception {

		Order order = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = dataSource.getConnection();
			/*
			 * select * from orders o join order_item oi on o.o_no=oi.o_no join product p on
			 * oi.p_no=p.p_no where o.userid=? and o.o_no = ?
			 */
			pstmt = con.prepareStatement(OrderSQL.ORDER_SELECT_WITH_ORDERITEM_BY_O_NO_ADDRESS);
			pstmt.setInt(1, o_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				/*o_no, o_date,o_status,o_option, o_price, userid */
				order = new Order(rs.getInt("o_no"),
						rs.getDate("o_date"),
						rs.getString("o_status"),
						rs.getString("o_option"),
						rs.getInt("o_price"),
						rs.getString("userid"),
									new Address(rs.getInt("addr_no"),
											rs.getString("userid"),
											rs.getString("loc")));

				do {
					order.getOrderItemList()
										.add(new OrderItem(rs.getInt("oi_no"), 
														   rs.getInt("oi_qty"), 
														   rs.getInt("o_no"),
							/*p_no, p_name, p_price,p_image,p_desc,ct_no*/
													new Product(rs.getInt("p_no"),
															rs.getString("p_name"),
															rs.getInt("p_price"),
															rs.getString("p_image"),
															rs.getString("p_desc"),
															rs.getInt("ct_no"))));
				} while (rs.next());
			}
		} finally {

			if(rs!=null) {
				rs.close();
			}
			if(pstmt!=null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return order;
	}
}
