package com.itwill.hellomart.product;

import java.util.HashSet;
import java.util.List;

public class ProductPageService {
	private static ProductPageService _instance;
	private ProductDao productDao;
	
	private ProductPageService() throws Exception {
		productDao=new ProductDao();
	}
	
	//한번만 생성되도록 자기참조 멤버변수 사용 - 싱글톤기법
	public static ProductPageService getInstance() throws Exception{
		if(_instance==null) {
			_instance=new ProductPageService();
		}
		return _instance;
	}
	public ProductListPageMakerDto findProductList(int currentPage) throws Exception{
		//1.전체상품의 갯수
		int totalRecordCount = productDao.getProductCount();
		
		//2.paging계산(PageMaker 유틸클래스)
		ProductPageMaker productPageMaker=new ProductPageMaker(totalRecordCount,currentPage);
	
		//3.게시물데이타 얻기
		List<Product> productList=
				productDao.findAllPaing(productPageMaker.getPageBegin(),
										 productPageMaker.getPageEnd());
		
		ProductListPageMakerDto pageMakerProductList=new ProductListPageMakerDto();
		pageMakerProductList.itemList=productList;
		pageMakerProductList.productPageMaker=productPageMaker;
		return pageMakerProductList;
	}
	public ProductListPageMakerDto findProductListByName(int currentPage,String p_name) throws Exception{
		//1.전체상품의 갯수
		int totalRecordCount = productDao.getProductCountByName(p_name);
		
		//2.paging계산(PageMaker 유틸클래스)
		ProductPageMaker productPageMaker=new ProductPageMaker(totalRecordCount,currentPage);
	
		//3.게시물데이타 얻기
		List<Product> productList=
				productDao.findAllPaingByName(productPageMaker.getPageBegin(),
										 productPageMaker.getPageEnd(), p_name);
		
		ProductListPageMakerDto pageMakerProductList=new ProductListPageMakerDto();
		pageMakerProductList.itemList=productList;
		pageMakerProductList.productPageMaker=productPageMaker;
		return pageMakerProductList;
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
