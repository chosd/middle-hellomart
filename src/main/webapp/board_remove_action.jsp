<%@page import="com.itwill.hellomart.board.BoardException"%>
<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf" %>
<%
	Integer boardno = null;
	Integer pageno = null;
	Integer p_no = null;
	boolean result = true;
	String msg = "";
	//게시글 작성자와 로그인한 유저가 같아야 삭제가능
	String userId=request.getParameter("userId");
	pageno = Integer.valueOf(request.getParameter("pageno"));
	p_no = Integer.valueOf(request.getParameter("p_no"));
	if(!sUserId.equals(userId)){
		result=false;
		msg = "자신의 게시글만 지울수 있습니다.";
	}
	else{
		try {
			boardno = Integer.valueOf(request.getParameter("boardno"));
		} catch (Exception ex) {
		}
		
		/**************************************/
		if (boardno == null) {
			result = false;
			msg = "삭제실패";
		} else {
			try {
				BoardService.getInstance().remove(boardno);
				result = true;
				msg = "삭제성공";
			} catch (BoardException e) {
				result = false;
				msg = "삭제실패:" + e.getMessage();
			}
		}
	}
%>
<script type="text/javascript">

<%if (result) {%>
	alert('<%=msg%>');
	location.href='board_list.jsp?pageno=<%=pageno%>&p_no=<%=p_no%>';
<%} else {%>
	alert('<%=msg%>');
	//history.back();
	location.href='board_list.jsp?pageno=<%=pageno%>&p_no=<%=p_no%>';
<%}%>
	
</script>




