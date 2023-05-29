package com.itwill.hellomart.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.itwill.hellomart.common.DataSourceFactory;

public class ProductDao {
	
	 
	private DataSource dataSource;
	public ProductDao() throws Exception{
	
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
	public int insert(Product product) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount =0;
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(ProductSQL.PRODUCT_INSERT);
			pstmt.setString(1,product.getP_name());
			pstmt.setInt(2,product.getP_price());
			pstmt.setString(3,product.getP_image());
			pstmt.setString(4,product.getP_desc());
			pstmt.setInt(5,product.getCt_no());
			rowCount = pstmt.executeUpdate();
			return rowCount;
		}finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
		
	}
	public int update(Product product) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount =0;
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(ProductSQL.PRODUCT_UPDATE);
			pstmt.setString(1,product.getP_name());
			pstmt.setInt(2,product.getP_price());
			pstmt.setString(3,product.getP_image());
			pstmt.setString(4,product.getP_desc());
			pstmt.setInt(5,product.getCt_no());
			pstmt.setInt(6,product.getP_no());
			rowCount = pstmt.executeUpdate();
			return rowCount;
		}finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
	}
	public int delete(int p_no)throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement pstmt = con.prepareStatement(ProductSQL.PRODUCT_DELETE);
		int deleteRowCount = 0;
		
		try {
		pstmt.setInt(1, p_no);
		deleteRowCount = pstmt.executeUpdate();
		return deleteRowCount;
		} finally {
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
	}
	/*
	 * selectByPK : 상품번호로 검색
	 */
	public Product findByPrimaryKey(int p_no) throws Exception{
		Product product = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	try {
		con=dataSource.getConnection();
		pstmt=con.prepareStatement(ProductSQL.PRODUCT_SELECT_BY_NO);
		pstmt.setInt(1, p_no);
		rs=pstmt.executeQuery();
		if(rs.next()) {
			product=new Product(
						rs.getInt("p_no"),
						rs.getString("p_name"),
						rs.getInt("p_price"),
						rs.getString("p_image"),
						rs.getString("p_desc"),
						rs.getInt("ct_no"));
		}
	} finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
		return product;
	}
	/*
	 * selectAll : 상품전체검색
	 */
	public List<Product> findAll() throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList=new ArrayList<Product>();
		
	try {
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(ProductSQL.PRODUCT_SELECT_ALL);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			productList.add(new Product(
					rs.getInt("p_no"),
					rs.getString("p_name"),
					rs.getInt("p_price"),
					rs.getString("p_image"),
					rs.getString("p_desc"),
					rs.getInt("ct_no")));
		}
	} finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
		return productList;
	}
	
	/*
	 * selectAll : 상품전체검색
	 */
	public HashSet<Product> findAll2() throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HashSet<Product> productSet=new HashSet<Product>();
		
	try {
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(ProductSQL.PRODUCT_SELECT_ALL);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			productSet.add(new Product(
					rs.getInt("p_no"),
					rs.getString("p_name"),
					rs.getInt("p_price"),
					rs.getString("p_image"),
					rs.getString("p_desc"),
					rs.getInt("ct_no")));
		}
	} finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
		return productSet;
	}
	/*
	 * selectAll : 상품전체검색 페이징
	 */
	public List<Product> findAllPaing(int start, int last) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList=new ArrayList<Product>();
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(ProductSQL.PRODUCT_SELECT_ALL_FOR_PAGING);
			pstmt.setInt(1, start);
			pstmt.setInt(2, last);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				productList.add(new Product(
						rs.getInt("p_no"),
						rs.getString("p_name"),
						rs.getInt("p_price"),
						rs.getString("p_image"),
						rs.getString("p_desc"),
						rs.getInt("ct_no")));
			}
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
		return productList;
	}
	/*
	 * selectAll : 상품전체검색 키워드검색후 페이징
	 */
	public List<Product> findAllPaingByName(int start, int last, String p_name) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList=new ArrayList<Product>();
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(ProductSQL.PRODUCT_SELECT_FOR_PAGING_SEARCH_BY_NAME);
			pstmt.setString(1, p_name);
			pstmt.setInt(2, start);
			pstmt.setInt(3, last);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				productList.add(new Product(
						rs.getInt("p_no"),
						rs.getString("p_name"),
						rs.getInt("p_price"),
						rs.getString("p_image"),
						rs.getString("p_desc"),
						rs.getInt("ct_no")));
			}
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
		return productList;
	}
	public int getProductCount() throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM product";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
	public int getProductCountByName(String p_name) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM product where p_name like '%'||?||'%'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_name);
			rs = pstmt.executeQuery();
			if (rs.next())
				count = rs.getInt(1);

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (con != null)
					con.close();
			} catch (Exception ex) {
			}
		}
		return count;
	}
	/*
	 * 이름으로 찾기
	 */
	public Product findByName(String p_name)throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Product product =null;
	try {
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(ProductSQL.PRODUCT_SELECT_BY_NAME);
		pstmt.setString(1, p_name);
		rs= pstmt.executeQuery();
		if(rs.next()) {
			product=new Product(
						rs.getInt("p_no"),
						rs.getString("p_name"),
						rs.getInt("p_price"),
						rs.getString("p_image"),
						rs.getString("p_desc"),
						rs.getInt("ct_no"));
		}
	} finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
		return product;
	}
	public List<Product> searchByName(String p_name) throws Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> productList=new ArrayList<Product>();
	try{	
		con = dataSource.getConnection();
		pstmt = con.prepareStatement(ProductSQL.PRODUCT_SEARCH_BY_NAME);
		pstmt.setString(1, p_name);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			productList.add(new Product(
					rs.getInt("p_no"),
					rs.getString("p_name"),
					rs.getInt("p_price"),
					rs.getString("p_image"),
					rs.getString("p_desc"),
					rs.getInt("ct_no")));
		}
	} finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
		return productList;
	}
	public List<Product> findByCategoryNumber(int ct_no) throws Exception{
		
		List<Product> categoryList=new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		con=dataSource.getConnection();
		pstmt=con.prepareStatement(ProductSQL.PRODUCT_SELECT_BY_CNO);
		pstmt.setInt(1, ct_no);
		rs=pstmt.executeQuery();
		while(rs.next()) {
				Product product = new Product(
											  rs.getInt("p_no"),
											  rs.getString("p_name"),
											  rs.getInt("p_price"),
											  rs.getString("p_image"),
											  rs.getString("p_desc"),
											  rs.getInt("ct_no")); 
							categoryList.add(product);
		}
		}finally {
		if(rs != null) {
			rs.close();
		}
		if(pstmt != null) {
			pstmt.close();
		}
		if(con != null) {
			con.close();
		}
	}
	
	return categoryList;
}
	public List<Product> priceAsc() throws Exception{
			List<Product> priceList = new ArrayList<Product>();
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
			con=dataSource.getConnection();
			pstmt=con.prepareStatement(ProductSQL.PRODUCT_PRICE_ASC);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Product product = new Product(
												 rs.getInt("p_no"),
												  rs.getString("p_name"),
												  rs.getInt("p_price"),
												  rs.getString("p_image"),
												  rs.getString("p_desc"),
												  rs.getInt("ct_no"));
					priceList.add(product);
			}
			}finally {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}
			return priceList;
	}
	public List<Product> priceDesc() throws Exception{
		List<Product> priceList = new ArrayList<Product>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		con=dataSource.getConnection();
		pstmt=con.prepareStatement(ProductSQL.PRODUCT_PRICE_DESC);
		rs=pstmt.executeQuery();
		while(rs.next()) {
			Product product = new Product(
											 rs.getInt("p_no"),
											  rs.getString("p_name"),
											  rs.getInt("p_price"),
											  rs.getString("p_image"),
											  rs.getString("p_desc"),
											  rs.getInt("ct_no"));
				priceList.add(product);
		}
		}finally {
			if(rs != null) {
				rs.close();
			}
			if(pstmt != null) {
				pstmt.close();
			}
			if(con != null) {
				con.close();
			}
		}
		return priceList;
}
	
	
	
	
	
	
}