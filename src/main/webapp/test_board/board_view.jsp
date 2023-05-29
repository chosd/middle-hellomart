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
	
	Board board=BoardService.getInstance().findBoard(boardno);
	if(boardno==null){
		//목록으로이동
		response.sendRedirect("board_list.jsp?pageno="+pageno+"&p_no="+p_no);
		return;
	}
	
	//읽은회수증가
	BoardService.getInstance().updateHitCount(boardno);
	
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="../css/styles.css" type="text/css">
<link rel=stylesheet href="../css/board.css" type="text/css">
 
<script language="JavaScript">
	function boardCreate() {
		fw.action = "board_write.jsp?p_no="+<%=board.getP_no()%>;
		fw.method = "POST";
		fw.submit();
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
		f.action = "board_list.jsp?pageno="+<%=pageno%>+"&p_no="+<%=board.getP_no()%>;
		f.submit();
	}
</script>
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
						<td><br />
							<table style="padding-left: 10px" border=0 cellpadding=0
								cellspacing=0>
								<tr>
									<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp; <b> 게시물
											내용보기 </b>
									</td>
								</tr>
							</table> <br> <!-- view Form  -->

							<form name="f" method="post">
								<input type="hidden" name="p_no" value="<%=board.getP_no()%>">
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
										<form name="fw" method="post">
											<input type="hidden" name="p_no" value="<%=board.getP_no()%>">
											<input type="hidden" name="userId" value="<%=board.getUserId()%>">
											<input type="button" value="글쓰기" onClick="boardCreate()"> &nbsp;
										</form> <br> 
										<input type="button" value="답글쓰기" onClick="boardReplyCreate()"> &nbsp; 
										<input type="button" value="수정" onClick="boardUpdate()"> &nbsp; 
										<input type="button" value="삭제" onClick="boardRemove()"> &nbsp; 
										<input type="button" value="리스트" onClick="boardList()"></td>
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