<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@page import="com.itwill.hellomart.product.Product"%>
<%@page import="com.itwill.hellomart.board.BoardListPageMakerDto"%>
<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf"%>
<%!public String getTitleString(Board board) {
		StringBuilder title = new StringBuilder(256);
		String t = board.getTitle();
		if (t.length() > 30) {
			//t = t.substring(0,15);
			//t = t+"...";
			t = String.format("%s...", t.substring(0, 30));
		}
		//답글공백삽입
		
		for (int i = 0; i < board.getDepth(); i++) {
			title.append("&nbsp;&nbsp;");
		}
		
		if (board.getDepth() > 0) {
			title.append("<img border='0' src='image/re.gif'/>");
		}
		
		title.append(t.replace(" ", "&nbsp;"));
		
		return title.toString();
	}%>

<%
	String pageno=request.getParameter("pageno");
	if(pageno==null||pageno.equals("")){
		pageno="1";
	}	
	
	//게시물조회
	BoardListPageMakerDto boardListPage 
		=BoardService.getInstance().findBoardListByUser(Integer.parseInt(pageno), sUserId);
	
	ProductService productService=new ProductService();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>유저작성글 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/board.css" type="text/css">

</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>
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
									<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp; <b>작성글 조회</b>
									</td>
								</tr>
								<tr bgcolor="#FFFFFF">
									<td height="20" class="t1" align="right" valign="bottom">♠
										총 <font color="#FF0000"><%=boardListPage.pageMaker.getTotCount()%></font>
										건 | 현재페이지( <font color="#FF0000"><%=boardListPage.pageMaker.getCurPage()%></font>
										/ <font color="#0000FF"><%=boardListPage.pageMaker.getTotPage()%></font>
										)
									</td>
								</tr>
							</table> <!-- list -->
							<form name="f" method="post" action="">
								<table border="0" cellpadding="0" cellspacing="1" width="590"
									bgcolor="BBBBBB">

									<tr>
										<td width=350 align=center bgcolor="E6ECDE">상품이름</td>
										<td width=280 align=center bgcolor="E6ECDE">제목</td>
										<td width=120 align=center bgcolor="E6ECDE">글쓴이</td>
										<td width=120 align=center bgcolor="E6ECDE">글쓴날</td>
										<td width=70 align=center bgcolor="E6ECDE">본횟수</td>
									</tr>
									<%
										for (Board board : boardListPage.itemList) {
											System.out.println(board);
											Product product=productService.findByPrimartKey(board.getP_no());
											
									%>
									<tr>
										<td width=350 align=center bgcolor="ffffff"><%=product.getP_name()%></td>
										<td width=280 bgcolor="ffffff" style="padding-left: 10px" align="left">
										<a href='board_view.jsp?boardno=<%=board.getBoardno()%>&pageno=<%=boardListPage.pageMaker.getCurPage()%>&p_no=<%=board.getP_no()%>'>
										<%=this.getTitleString(board)%>
										</a>
										</td>
										<td width=120 align=center bgcolor="ffffff"><%=board.getUserId()%>
										</td>
										<td width=120 bgcolor="ffffff" style="padding-left: 10px" align="left">
											<%=board.getRegdate().toString().substring(0, 10)%>
										</td>
										<td width=70 align=center bgcolor="ffffff" align="left"><%=board.getReadcount()%>
										</td>
									</tr>
									<%
										}
									%>
								</table>
								<!-- /list -->
							</form>
							<table border="0" cellpadding="0" cellspacing="1" width="590">
								<tr>
									<td align="center">
							     		 <%if(boardListPage.pageMaker.getCurBlock() > 1) {%>    
											<a href="./user_board_list.jsp?pageno=<%=boardListPage.pageMaker.getPrevGroupStartPage()%>">◀◀</a>&nbsp;
										 <%}%>
										
										 <%if(boardListPage.pageMaker.getPrevPage()>0) {%>    
											<a href="./user_board_list.jsp?pageno=<%=boardListPage.pageMaker.getPrevPage()%>">◀</a>&nbsp;&nbsp;
										 <%}%>
										
										<%
											for (int i = boardListPage.pageMaker.getBlockBegin(); i <= boardListPage.pageMaker.getBlockEnd(); i++) {
										 	if (boardListPage.pageMaker.getCurPage() == i) {
										%>
										 <font color='red'><strong><%=i%></strong></font>&nbsp;
										<%} else {%>
										<a href="./user_board_list.jsp?pageno=<%=i%>"><strong><%=i%></strong></a>&nbsp;
										<%
										   }
										  }%>
										  
										  
										 <%if(boardListPage.pageMaker.getNextPage()<=boardListPage.pageMaker.getTotPage()){%>
										  <a href="./user_board_list.jsp?pageno=<%=boardListPage.pageMaker.getNextPage()%>">▶</a>
										 <%}%>
										 <%if(boardListPage.pageMaker.getTotBlock() > boardListPage.pageMaker.getCurBlock()){%>
										  <a href="./user_board_list.jsp?pageno=<%=boardListPage.pageMaker.getNextGroupStartPage()%>">▶▶&nbsp;</a>
										 <%}%>
										
									</td>
								</tr>
							</table> <!-- button -->
					</tr>
				</table>
				</div>
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