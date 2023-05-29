package com.itwill.hellomart.product;

import java.util.HashSet;
import java.util.List;

public class ProductService {
	private ProductDao productDao;
	
	public ProductService () throws Exception{
		productDao=new ProductDao();
	}
	public int insert(Product product) throws Exception{
		return productDao.insert(product);
	}
	public int update(Product product) throws Exception{
		return productDao.update(product);
	}
	public int delete(int p_no) throws Exception{
		return productDao.delete(p_no);
	}
	public Product findByPrimartKey(int p_no) throws Exception{
		return productDao.findByPrimaryKey(p_no);
	}
	public List<Product> findAll() throws Exception{
		return productDao.findAll();
	}
	public HashSet<Product> findAll2() throws Exception{
		return productDao.findAll2();
	}
	public Product findByName(String p_name)throws Exception{
		return productDao.findByName(p_name);
	}
	public List<Product> searchByName(String p_name)throws Exception{
		return productDao.searchByName(p_name);
	}
	public List<Product> findByCategoryNumber(int ct_no) throws Exception{
		return productDao.findByCategoryNumber(ct_no);
	}
	public List<Product> priceAsc() throws Exception{
		return productDao.priceAsc();
	}
	public List<Product> priceDesc() throws Exception{
		return productDao.priceDesc();
	}
}
