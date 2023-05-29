<%@page import="com.itwill.hellomart.product.Product"%>
<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.BoardListPageMakerDto"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	int p_no=Integer.parseInt(request.getParameter("p_no"));
	String pageno=request.getParameter("pageno");
	if(pageno==null||pageno.equals("")){
		pageno="1";
	}	
	
	//게시물조회
	BoardListPageMakerDto boardListPage 
		=BoardService.getInstance().findBoardList(Integer.parseInt(pageno),p_no);
	//프로덕트
	ProductService productService=new ProductService();
	Product product=productService.findByPrimartKey(p_no);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/board.css" type="text/css">
<script type="text/javascript">
	function boardCreate() {
		location.href = "board_write.jsp?p_no="+<%=p_no%>;
	}
</script>
<style>
	/* 상품상세 footer 가리기 */
	#footer>p { display: none; } 
</style>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>

				<jsp:include page="product_detail.jsp?p_no=<%=p_no %>" />
				<table border=0 cellpadding=0 cellspacing=0 class="BOARD">
					<tr>
						<td>
							<table border=0 cellpadding=0
								cellspacing=0>
								<tr>
									<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp; <b>[<%=product.getP_name() %>] 후기게시판</b>
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
										<td width=280 align=center bgcolor="E6ECDE">제목</td>
										<td width=120 align=center bgcolor="E6ECDE">글쓴이</td>
										<td width=120 align=center bgcolor="E6ECDE">글쓴날</td>
										<td width=70 align=center bgcolor="E6ECDE">본횟수</td>
									</tr>
									<%
										for (Board board : boardListPage.itemList) {
									%>
									<tr>
										<td width=280 bgcolor="ffffff" style="padding-left: 10px" align="left">
										<a href='board_view.jsp?boardno=<%=board.getBoardno()%>&pageno=<%=boardListPage.pageMaker.getCurPage()%>&p_no=<%=p_no%>'>
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
									<!-- 
									<tr>
										<td width=280 bgcolor="ffffff" style="padding-left: 10"><a
											href='board_view.jsp?boardno=532&pageno=6'>게시판타이틀514</a></td>
										<td width=120 align=center bgcolor="ffffff">김경호514</td>
										<td width=120 bgcolor="ffffff" style="padding-left: 10">2014-12-23
										</td>
										<td width=70 align=center bgcolor="ffffff">0</td>
									</tr>
									 -->
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
											<a href="./board_list.jsp?pageno=<%=boardListPage.pageMaker.getPrevGroupStartPage()%>&p_no=<%=p_no%>">◀◀</a>&nbsp;
										 <%}%>
										
										 <%if(boardListPage.pageMaker.getPrevPage()>0) {%>    
											<a href="./board_list.jsp?pageno=<%=boardListPage.pageMaker.getPrevPage()%>&p_no=<%=p_no%>">◀</a>&nbsp;&nbsp;
										 <%}%>
										
										<%
											for (int i = boardListPage.pageMaker.getBlockBegin(); i <= boardListPage.pageMaker.getBlockEnd(); i++) {
										 	if (boardListPage.pageMaker.getCurPage() == i) {
										%>
										 <font color='red'><strong><%=i%></strong></font>&nbsp;
										<%} else {%>
										<a href="./board_list.jsp?pageno=<%=i%>&p_no=<%=p_no%>"><strong><%=i%></strong></a>&nbsp;
										<%
										   }
										  }%>
										  
										  
										 <%if(boardListPage.pageMaker.getNextPage()<=boardListPage.pageMaker.getTotPage()){%>
										  <a href="./board_list.jsp?pageno=<%=boardListPage.pageMaker.getNextPage()%>&p_no=<%=p_no%>">▶</a>
										 <%}%>
										 <%if(boardListPage.pageMaker.getTotBlock() > boardListPage.pageMaker.getCurBlock()){%>
										  <a href="./board_list.jsp?pageno=<%=boardListPage.pageMaker.getNextGroupStartPage()%>&p_no=<%=p_no%>">▶▶&nbsp;</a>
										 <%}%>
										
									</td>
								</tr>
							</table> <!-- button -->
							<table border="0" cellpadding="0" cellspacing="1" width="590">
								<tr>
									<td align="right"><input type="button" value="게시물 생성"
											onclick="boardCreate();" /></td>
								</tr>
							</table></td>
					</tr>
				</table>
				
			<div id="foot" style="width: 700px; margin: auto; margin-top: 10px;">
				<!-- include_common_bottom.jsp start-->
				<jsp:include page="include_common_bottom.jsp" />
				<!-- include_common_bottom.jsp end-->
			</div>
	<!--container end-->
</body>
</html>