<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf" %>  
<%
	Integer boardno = null;
	Integer pageno = null;
	Integer p_no = null;
	boolean isMatchUser = true;
	String msg = "";
	//게시글 작성자와 로그인한 유저가 같아야 삭제가능
	String userId=request.getParameter("userId");
	pageno = Integer.valueOf(request.getParameter("pageno"));
	p_no = Integer.valueOf(request.getParameter("p_no"));
	if(!sUserId.equals(userId)){
		isMatchUser=false;
		msg = "자신의 게시글만 수정할 수 있습니다.";
	}
	else{
		try {
			boardno = Integer.valueOf(request.getParameter("boardno"));
			Board board = new Board();
			board.setBoardno(boardno);
			board.setTitle(request.getParameter("title"));
			board.setContent(request.getParameter("content"));

			//2. 데이터베이스에 변경된 내용 적용
			BoardService.getInstance().update(board);
			
			response.sendRedirect(
					String.format("board_view.jsp?boardno=%d&pageno=%d&p_no=%d",
							boardno, pageno, p_no));
		} catch (Exception ex) {
		}
	}
/* //1. Board 객체를 만들고 사용자가 입력한 데이터를 저장
Board board = new Board();
board.setBoardno(
	Integer.parseInt(request.getParameter("boardno")));
board.setTitle(request.getParameter("title"));
board.setUserId(request.getParameter("writer"));
board.setContent(request.getParameter("content"));

//2. 데이터베이스에 변경된 내용 적용
BoardService.getInstance().update(board); */

%>
<script type="text/javascript">

<%if (!isMatchUser) {%>
	alert('<%=msg%>');
	location.href='board_list.jsp?pageno=<%=pageno%>&p_no=<%=p_no%>';
<%}%>
	
</script>
