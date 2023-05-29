<%@page import="com.itwill.hellomart.user.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf"%>
<%
	//회원이 아니면 글 못씀
	int p_no = Integer.parseInt(request.getParameter("p_no"));
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

		f.action = "board_write_action.jsp";
		f.method="POST";
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
									후기 쓰기</b></td>
						</tr>
					</table> <br> <!-- write Form  -->
					<form name="f" method="post">
						<input type="hidden" name="p_no" value="<%=p_no%>">
						<table border="0" cellpadding="0" cellspacing="1" width="590"
							bgcolor="BBBBBB">
							<tr>
								<td width=100 align=center bgcolor="E6ECDE" height="22">제목</td>
								<td width=490 bgcolor="ffffff" style="padding-left: 10px"
									align="left"><input type="text" style="width: 150px"
									name="title"></td>
							</tr>
							<tr>
								<td width=100 align=center bgcolor="E6ECDE" height="22">작성자</td>
								<td width=490 bgcolor="ffffff" style="padding-left: 10px"
									align="left"><input type="text" readonly="readonly" style="width: 150px"
									name="userId" value="<%=sUserId %>"></td>
							</tr>
							<tr>
								<td width=100 align=center bgcolor="E6ECDE">내용</td>
								<td width=490 bgcolor="ffffff" style="padding-left: 10px"
									align="left"><textarea name="content" class="textarea"
										style="width: 350px" rows="14"></textarea></td>
							</tr>
						</table>
					</form> <br>
					<table width=590 border=0 cellpadding=0 cellspacing=0>
						<tr>
							<td align=center>
							<input type="button" value="게시판 쓰기" onClick="boardCreate()"> &nbsp; 
							<input type="button" value="게시판 목록" onClick="boardList()"></td>
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