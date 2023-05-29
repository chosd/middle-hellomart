<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf" %>     
<%
	Integer boardno = null;
	Integer pageno = null;
	Integer p_no = null;
	boardno = Integer.valueOf(request.getParameter("boardno"));
	pageno = Integer.valueOf(request.getParameter("pageno"));
	p_no = Integer.valueOf(request.getParameter("p_no"));
	//댓글의 대상글번호를 읽어서 변수에 저장
	if(request.getParameter("boardno")==null){
		response.sendRedirect("board_list.jsp");
		return;
	}
	Board board=BoardService.getInstance().findBoard(boardno);
	if(board==null){
		response.sendRedirect("board_list.jsp");
		return;
	}

%>    	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="css/styles.css" type="text/css">
<link rel=stylesheet href="css/board.css" type="text/css">
 
<script type="text/javascript">
	function boardReplayCreate() {
		if (f.title.value == "") {
			alert("제목을 입력하십시요.");
			f.title.focus();
			return false;
		}

		if (f.content.value == "") {
			alert("내용을 입력하십시요.");
			f.content.focus();
			return false;
		}
		f.action = "board_reply_write_action.jsp";
		f.method='POST';
		f.submit();
	}

	function boardList() {
		f.action = "board_list.jsp";
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

								<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>게시판 -
										게시판 답글 쓰기</b></td>
							</tr>
						</table> <br> <!-- write Form  -->
						<form name="f" method="post">
							<input type="hidden" name="p_no" value="<%=board.getP_no()%>">
							<input type="hidden" name="userId" value="<%=sUserId%>">
							<input type="hidden" name="pageno"  value="<%=pageno%>" />
	        				<input type="hidden" name="boardno" value="<%=board.getBoardno()%>"/>

							<table border="0" cellpadding="0" cellspacing="1" width="590"
								bgcolor="BBBBBB">
								<tr>
									<td width=100 align=center bgcolor="E6ECDE" height="22">제목</td>
									<td width=490 bgcolor="ffffff" style="padding-left: 10px" align="left">
										<input value="[RE]<%=board.getTitle()%>"  type="text" style="width: 150"
										name="title">
									</td>
								</tr>
								<tr>
									<td width=100 align=center bgcolor="E6ECDE" height="22">작성자</td>
									<td width=490 bgcolor="ffffff" style="padding-left: 10px" align="left">
										<input value="<%=sUserId%>" readonly="readonly" type="text" style="width: 150"
										name="writer">
									</td>
								</tr>
								<tr>
									<td width=100 align=center bgcolor="E6ECDE" height="22">내용</td>
									<td width=490 bgcolor="ffffff" style="padding-left: 10px" align="left">
										<textarea name="content" class="textarea" style="width: 350px" rows="14">>><%=board.getContent().replace("\n",">>").trim()%></textarea>
									</td>
								</tr>


							</table>
						</form> <br>

						<table width=590 border=0 cellpadding=0 cellspacing=0>
							<tr>
								<td align=center><input type="button" value="게시판 답글 쓰기"
									onClick="boardReplayCreate()"> &nbsp; <input
									type="button" value="게시판 목록" onClick="boardList()"></td>
							</tr>
						</table></td>
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