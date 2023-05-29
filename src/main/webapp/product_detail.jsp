<%@page import="java.text.DecimalFormat"%>
<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.BoardListPageMakerDto"%>
<%@page import="com.itwill.hellomart.product.Product"%>
<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	/********게시판*********/
	String pageno = "1";
	if (pageno == null || pageno.equals("")) {
		pageno="1";
	}
	pageno= request.getParameter("pageno");
	/*********************/
	String p_noStr = request.getParameter("p_no");
	if (p_noStr == null || p_noStr.equals("")) {
		response.sendRedirect("product_list.jsp");
		return;
	}
	boolean isLogin = false;
	if (session.getAttribute("sUserId") != null) {
		isLogin = true;
	}
	
	ProductService productService = new ProductService();
	Product product = productService.findByPrimartKey(Integer.parseInt(p_noStr));
	if (product == null) {
		out.println("<script>");
		out.println("alert('매진된상품입니다.');");
		out.println("location.href='product_list.jsp';");
		out.println("</script>");
		return;
	}
	
	BoardListPageMakerDto boardListPage 
		=BoardService.getInstance().findBoardList(Integer.parseInt("1"),Integer.parseInt(p_noStr));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>쇼핑몰</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/shop.css" type="text/css">
<style type="text/css" media="screen">
</style>
<script type="text/javascript">
	function add_cart_popup_window() {
		if (<%=!isLogin%>) {
			alert('로그인 하세요');
			location.href = 'user_login_form.jsp';
		} else {

			var left = Math.ceil((window.screen.width) / 5);
			var top = Math.ceil((window.screen.height) / 3);
			console.log(left);
			console.log(top);
			var cartWin = window
					.open(
							"about:blank",
							"cartForm",
							"width=260,height=130,top="
									+ top
									+ ",left="
									+ 0
									+ ",location=no, directories=no, status=no, menubar=no, scrollbars=no,copyhistory=no");
			document.add_cart_form.action = 'cart_add_action_popup_window.jsp';
			document.add_cart_form.target = 'cartForm';
			document.add_cart_form.method = 'POST';
			document.add_cart_form.submit();
		}
	}

	function order_create_form() {
		if (<%=!isLogin%>) {
			alert('로그인 하세요');
			location.href = 'user_login_form.jsp';
		} else {
			document.product_detail_form.method = 'POST';
			document.product_detail_form.action = 'order_create_form.jsp';
			document.product_detail_form.submit();
		}
	}
	function boardList() {
		product_detail_form.action = "board_list.jsp?pageno="+<%=1%>+"&p_no="+<%=Integer.parseInt(p_noStr)%>;
		product_detail_form.submit();
	}
	function productList() {
		location.href = 'product_list.jsp';
	}
	
</script>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>
	<form name="product_detail_form" style="margin:0">
		<input type="hidden" name="p_no" value="<%=product.getP_no()%>">
		<input type="hidden" name="p_qty" value="1"> <input
			type="hidden" name="buyType" value="direct">
	</form>
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
									<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>상품상세보기</b></td>
								</tr>
							</table> <!-- 
							<form name="f" method="post">
							-->
							<br>
							<table border=0 width=80% height=376
								align=center>
								<tr valign=bottom>
									<td colspan=2 align=left class=t1><font size=4
										color=black><b><%=product.getP_name()%></b></font></td>
								</tr>
								<tr width=100%>
									<td colspan=3 height=5><hr color=#556b2f></td>
								</tr>
								<tr width=100%>
									<td width=30% align=center><img border=0
										src='image/<%=product.getP_image()%>' width=150 height=150></td>
									<td width=40% class=t1 align=center>
										<font color=#0000FF><%=product.getP_desc()%></font><br><br>
										<font color=#FF0000>금액 : <%=new DecimalFormat("#,###").format(product.getP_price())%>원</font>
									</td>
									<td width=30% height=150 align=center class=t1>
										<form name="add_cart_form" method="post"
											action="cart_add_action.jsp">
											수량 :
											<!-- 
											 <input type=text name="cart_qty" value=1 size=4 class=TXTFLD>  
											-->
											<select name="cart_qty">
												<option value="1">1
													<option
													value="2">2
												<option value="3">3
												<option value="4">4
												<option value="5">5
												<option value="6">6
												<option value="7">7 
												<option value="8">8
												<option value="9">9
												<option value="10">10
											</select> <br><br> 
											<input type="button" value="장바구니" onClick="location.href='javascript:add_cart_popup_window(this.parentElement)';"> &nbsp;
											<input type="button" value="바로주문" id="directOrder" onClick="order_create_form();">
											<input type="hidden" name=p_no value="<%=product.getP_no()%>">
										</form>
									</td>
								</tr>
								<tr>
									<td colSpan=3 height=21><hr color=#556b2f></td>
								</tr>
							</table> <!-- 
							</form>
							-->

							<table border="0" cellpadding="0" cellspacing="1">
								<tr>
									<td align=center>
									<input type="button" value="상품후기(<%=boardListPage.pageMaker.getTotCount()%>건)" 
										onClick="boardList();"> &nbsp; 
									<input type="button" value="상품리스트" onClick="productList();"></td>
								</tr>
							</table></td>
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