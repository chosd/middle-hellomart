package com.itwill.hellomart.product;

public class ProductSQL {
	public final static String PRODUCT_SELECT_ALL=
			"select * from product";
	public final static String PRODUCT_SELECT_ALL_FOR_PAGING=
			"select * from product where p_no>=? and p_no<=?";
	public final static String PRODUCT_SELECT_FOR_PAGING_SEARCH_BY_NAME=
			"SELECT * FROM ( SELECT rownum idx, p.* FROM( SELECT p_no,p_name,p_price,p_image,p_desc,ct_no FROM product where p_name like '%'||?||'%' )p) WHERE idx >= ? AND idx <= ?";
	public final static String PRODUCT_SELECT_BY_NO=
			"select * from product where p_no=?";
	public final static String PRODUCT_INSERT=
			"insert into product values(product_p_no_SEQ.nextval,?,?,?,?,?)";
	public final static String PRODUCT_UPDATE=
			"update product set p_name = ? , p_price = ? ,p_image = ?  , p_desc = ? , ct_no = ? where p_no = ?";
	public final static String PRODUCT_DELETE =
			"delete from product where p_no = ?";
	public final static String PRODUCT_SELECT_BY_NAME=
			"select * from product where p_name=?";
	public final static String PRODUCT_SEARCH_BY_NAME=
			"select * from product where p_name like '%'||?||'%'";
	public final static String PRODUCT_SELECT_BY_CNO=
			"select * from categories c join product p on c.ct_no=p.ct_no where c.ct_no=?";
	public final static String PRODUCT_PRICE_DESC =
			"select * from product order by p_price desc";
	public final static String PRODUCT_PRICE_ASC =
			"select * from product order by p_price asc";
}
