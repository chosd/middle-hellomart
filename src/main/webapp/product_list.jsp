<%@page import="com.itwill.hellomart.product.ProductListPageMakerDto"%>
<%@page import="com.itwill.hellomart.product.ProductPageService"%>
<%@page import="com.itwill.hellomart.product.Categories"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.itwill.hellomart.product.Product"%>
<%@page import="java.util.List"%>
<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%

	//ProductService productService = new ProductService();
	String ct_noStr = request.getParameter("ct_no");
	//String sort_option = request.getParameter("sort_option");
	
	/***************검색어 서치**************/
	String keyword=null;
	if(keyword==null || keyword.equals("null")){
		keyword = request.getParameter("keyword");
	}
	/*******************************************/
	
/* 	List<Product> productList = new ArrayList<Product>();
	if(ct_noStr == null){
		if(keyword != null){
			productList = productService.searchByName(keyword);
		}else{
			productList =  productService.findAll();
		}
	}else{
		if(keyword != null){
			productList = productService.searchByName(keyword);
		}else{
			productList = productService.findByCategoryNumber(Integer.parseInt(ct_noStr));
		}
	} */
	
	/*********상품페이징 테스트*************/
	String productPageno=request.getParameter("productPageno");
	if(productPageno==null||productPageno.equals("")){
		productPageno="1";
	}	
	//게시물조회
	/* ProductListPageMakerDto productListPage
		=ProductPageService.getInstance().findProductList(Integer.parseInt(productPageno)); */
	
	ProductListPageMakerDto productListPage= ProductPageService.getInstance().findProductList(Integer.parseInt(productPageno));
	if(ct_noStr == null){
		if(keyword != null){
			productListPage 
				=ProductPageService.getInstance().findProductListByName(Integer.parseInt(productPageno),keyword);
		}else{
			productListPage 
				=ProductPageService.getInstance().findProductList(Integer.parseInt(productPageno));
		}
	}
/* 	else{
		if(keyword != null){
			productList = productService.searchByName(keyword);
		}else{
			productList = productService.findByCategoryNumber(Integer.parseInt(ct_noStr));
		}
	} */
	/**********************************/
%>
<%
boolean isLogin = false;
if (session.getAttribute("sUserId") != null) {
	isLogin = true;
}
%>
<!DOCTYPE html>
<html>
<head>
<title>쇼핑몰</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/shop.css" type="text/css">
<script type="text/javascript">
function add_cart_popup_window(f){
	if (<%=!isLogin%>) {
		alert('로그인 하세요');
		location.href = 'user_login_form.jsp';
	} else {
		var left = Math.ceil(( window.screen.width)/5);
		var top = Math.ceil(( window.screen.height)/3); 
		console.log(left);
		console.log(top);
		var cartWin = window.open("about:blank","cartForm","width=260,height=130,top="+top+",left="+left+",location=no, directories=no, status=no, menubar=no, scrollbars=no,copyhistory=no");
		f.action = 'cart_add_action_popup_window.jsp';
		f.target = 'cartForm';
		f.method = 'POST';
		f.submit();
	}
}
function searchByKeyword() {
	document.ff.action ='product_search_action.jsp';
	document.ff.method='POST';
	document.ff.submit();
}
</script> 
<style type="text/css" media="screen">
</style>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>
	<!-- container start-->
	<div id="container">
		<!-- header start -->
		<div id="header">
			<!-- include_common_top.jsp start-->
			<jsp:include page="include_common_top.jsp" />
			<!-- include_common_top.jsp end-->
		</div>
		<!-- header end -->
		<!-- navigation start-->
		<div id="navigation">
			<!-- include_common_left.jsp start-->
			<jsp:include page="include_common_left.jsp" />
			<!-- include_common_left.jsp end-->
		</div>
		<!-- navigation end-->
		<!-- wrapper start -->
		<div id="wrapper">
			<!-- content start -->

			<!-- include_content.jsp start-->
			<div id="content">
				<table border=0 cellpadding=0 cellspacing=0>
					<tr>
						<td>
							<table border=0 cellpadding=0
								cellspacing=0>
								<tr>
									<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>상품리스트</b></td>
								</tr>
							</table>

							<div id="f">
								<table width="100%" align="center" border="0" cellpadding="10"
									cellspacing="1" bgcolor="BBBBBB">
									<%
									int product_size=productListPage.itemList.size();
									int product_column_size=3;
									int product_line_count = 1;
									
									
									//for (int i=0;i<productList.size();i++) {
											//Product product=productList.get(i);
									/************상품페이징 테스트**************/
									for (int i=0;i<productListPage.itemList.size();i++) {
										Product product=productListPage.itemList.get(i);
									%>
									<!--상품시작 -->
									<%
									 if(i%product_column_size==0){
									%>
									<tr>
									<%} %>
										<td align="center" width="25%"  bgcolor="ffffff"><a
											href="product_detail.jsp?p_no=<%=product.getP_no()%>"><img width="150px" height="150px"
												src="image/<%=product.getP_image()%>" border="0"></a><br />	
											<br /> <b><%=product.getP_name()%></b>
											<form style="display: inline;">
												<input type="hidden" name="p_no" value="<%=product.getP_no()%>">
												<input type="hidden" name="cart_qty" value="1">
												<%--<img src='image/cart20.png' style="cursor:pointer;" onclick="add_cart_popup_window(this.parentElement);" align="top"/>--%>
											</form><br> <font
											color="#FF0000"><%=new DecimalFormat("#,##0").format(product.getP_price())%>원
										</font></td>
									<%if(i%product_column_size==3){%>
									</tr>
									<%} %>
								   <!--상품 끝 -->
 								<%}%>	
							</table>
															
							
						</div>
						<table border="0" cellpadding="0" cellspacing="1" width="590">
							<tr>
								<td align="center">
						     		 <%if(productListPage.productPageMaker.getCurBlock() > 1) {%>    
										<a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getPrevGroupStartPage()%>&keyword=<%=keyword%>">◀◀</a>&nbsp;
									 <%}%>
									
									 <%if(productListPage.productPageMaker.getPrevPage()>0) {%>
									 	<%if(keyword==null){ %>
											<a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getPrevPage()%>">◀</a>&nbsp;&nbsp;
									 	<%}else{ %>
											<a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getPrevPage()%>&keyword=<%=keyword%>">◀</a>&nbsp;&nbsp;
									 	<%} %>
									 <%}%>
									
									<%
										for (int i = productListPage.productPageMaker.getBlockBegin(); i <= productListPage.productPageMaker.getBlockEnd(); i++) {
									 	if (productListPage.productPageMaker.getCurPage() == i) {
									%>
									 <font color='red'><strong><%=i%></strong></font>&nbsp;
									<%} else {%>
										<%if(keyword==null){ %>
										<a href="./product_list.jsp?productPageno=<%=i%>"><strong><%=i%></strong></a>&nbsp;
										<%}else{ %>
										<a href="./product_list.jsp?productPageno=<%=i%>&keyword=<%=keyword%>"><strong><%=i%></strong></a>&nbsp;
										<%} %>
									<%
									   }
									  }%>
									  
									  
									 <%if(productListPage.productPageMaker.getNextPage()<=productListPage.productPageMaker.getTotPage()){%>
									 	<%if(keyword==null){ %>
										  <a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getNextPage()%>">▶</a>
									 	<%}else{ %>
										  <a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getNextPage()%>&keyword=<%=keyword%>">▶</a>
									 	<%} %>
									 <%}%>
									 <%if(productListPage.productPageMaker.getTotBlock() > productListPage.productPageMaker.getCurBlock()){%>
									  <a href="./product_list.jsp?productPageno=<%=productListPage.productPageMaker.getNextGroupStartPage()%>&keyword=<%=keyword%>">▶▶&nbsp;</a>
									 <%}%>
									
									</td>
								</tr>
							</table> 
						<br /></td>
					</tr>
				</table>
			</div>
			<!-- include_content.jsp end-->
			<!-- content end -->
		</div>
		<!--wrapper end-->
		<div id="footer">
			<!-- include_common_bottom.jsp start-->
			<jsp:include page="include_common_bottom.jsp" />
			<!-- include_common_bottom.jsp end-->
		</div>
	</div>
	<!--container end-->
</body>
</html>
