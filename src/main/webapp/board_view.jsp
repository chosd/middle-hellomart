<%@page import="java.util.List"%>
<%@page import="com.itwill.hellomart.product.Product"%>
<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	/* if(request.getMethod().equals("GET")){
		response.sendRedirect("board_list.jsp?p_no=1");
	} */
	Integer boardno=null;
	int pageno=1;
	int p_no=1;
	try{
		boardno=Integer.parseInt(request.getParameter("boardno"));
		pageno=Integer.parseInt(request.getParameter("pageno"));
		p_no=Integer.parseInt(request.getParameter("p_no"));
	}catch(Exception e){
		
	}
	if(boardno==null){
		//목록으로이동
		response.sendRedirect("board_list.jsp?pageno="+pageno+"&p_no="+p_no);
		return;
	}
	Board board=BoardService.getInstance().findBoard(boardno);
	if(board==null){
		response.sendRedirect("board_list.jsp?pageno="+pageno+"&p_no="+p_no);
		return;
	}
	//읽은회수증가
	BoardService.getInstance().updateHitCount(boardno);
	
	ProductService productService=new ProductService();
	Product product= productService.findByPrimartKey(board.getP_no());
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/board.css" type="text/css">
 
<script language="JavaScript">
	function boardCreate() {
		f.action = "board_write.jsp";
		f.method = "POST";
		f.submit();
	}
	function boardReplyCreate() {
		document.f.action = "board_reply_write.jsp";
		document.f.method='POST';
		document.f.submit();
	}
	function boardUpdate() {
		document.f.action = "board_modify.jsp";
		document.f.submit();
	}
	function boardRemove() {
		if (confirm("정말 삭제하시겠습니까?")) {
			document.f.action = "board_remove_action.jsp";
			document.f.submit();
		}
	}
	function boardList() {
		f.action = "board_list.jsp?pageno="+<%=pageno%>+"&p_no="+<%=p_no%>;
		f.submit();
	}
</script>
<style>
	/* 상품상세 footer 가리기 */
	#footer>p { display: none; } 
</style>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0
	marginwidth=0 marginheight=0>
	<!-- container start-->
	<jsp:include page="product_detail.jsp?p_no=<%=p_no %>" />
		<table border=0 cellpadding=0 cellspacing=0 class="BOARD">
			<tr>
				<td>
					<table border=0 cellpadding=0
						cellspacing=0>
						<tr>
							<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp; <b>[<%=product.getP_name() %>] 후기 게시물
									내용보기 </b>
							</td>
						</tr>
					</table> <br> <!-- view Form  -->

					<form name="f" method="post">
						<input type="hidden" name="p_no" value="<%=board.getP_no()%>">
						<input type="hidden" name="userId" value="<%=board.getUserId()%>">
						<input type="hidden" name="boardno" value="<%=board.getBoardno()%>">
						<input type="hidden" name="pageno" value="<%=pageno%>">
						<table border="0" cellpadding="0" cellspacing="1" width="590"
							bgcolor="BBBBBB">
							<tr>
								<td width=100 align=center bgcolor="E6ECDE" height="22">글쓴이</td>
								<td width=490 bgcolor="ffffff" style="padding-left: 10px"
									align="left"><%=board.getUserId()%></td>
							</tr>

							<tr>
								<td width=100 align=center bgcolor="E6ECDE" height="22">제목</td>
								<td width=490 bgcolor="ffffff" style="padding-left: 10px"
									align="left"><%=board.getTitle()%></td>
							</tr>
							<tr>
								<td width=100 align=center bgcolor="E6ECDE" height="22">내용</td>
								<td width=490 bgcolor="ffffff" height="180px"
									style="padding-left: 10px" align="left"><%=board.getContent().replace("\n","<br/>")%><br />

								</td>
							</tr>

						</table>

					</form> <br>
					<table width=590 border=0 cellpadding=0 cellspacing=0>
						<tr>
							<td align=center>
								<input type="button" value="글쓰기" onClick="boardCreate()"> &nbsp;
								<input type="button" value="답글쓰기" onClick="boardReplyCreate()"> &nbsp; 
								<input type="button" value="수정" onClick="boardUpdate()"> &nbsp; 
								<input type="button" value="삭제" onClick="boardRemove()"> &nbsp; 
								<input type="button" value="리스트" onClick="boardList()"></td>
						</tr>
					</table></td>
					<%
						Board nextBoard=null;
						Board prevBoard=null;
						List<Board> BoardList =BoardService.getInstance().findBoardList(p_no);
						for (int i=0;i<BoardList.size();i++) {
							if((int)boardno==BoardList.get(i).getBoardno()){
								if(i-1>=0)
									nextBoard = BoardList.get(i-1);
								if(i+1<BoardList.size()) 
									prevBoard = BoardList.get(i+1);
								break;
							}
						}
					
					%>
				</table>
				<table border=0 cellpadding=0 cellspacing=0 class="BOARD">
					<tr>
					<%if(prevBoard!=null){ %>
							<td bgcolor="ffffff" style="padding-left: 10">
								<a href="board_view.jsp?pageno=<%=pageno%>&p_no=<%=p_no%>&boardno=<%=prevBoard.getBoardno()%>">
								[이전글]<%=prevBoard.getTitle() %></a>
							</td>
						
					<%} %>
					<%if(nextBoard!=null){ %>
						
							<td bgcolor="ffffff" style="padding-left: 10">
								<a href="board_view.jsp?pageno=<%=pageno%>&p_no=<%=p_no%>&boardno=<%=nextBoard.getBoardno()%>">
								[다음글]<%=nextBoard.getTitle() %></a>
							</td>
					<%} %>
					</tr>
				</table>

	<!--container end-->
			<div id="foot" style="width: 700px; margin: auto; margin-top: 10px;">
				<!-- include_common_bottom.jsp start-->
				<jsp:include page="include_common_bottom.jsp" />
				<!-- include_common_bottom.jsp end-->
			</div>
</body>
</html>