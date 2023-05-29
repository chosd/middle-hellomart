<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf" %>     
<%
	String p_noStr=request.getParameter("p_no");
	int p_no = Integer.parseInt(p_noStr);
 	if(request.getMethod().equalsIgnoreCase("GET")){
		response.sendRedirect("board_list.jsp?p_no="+p_no);
		return;
	}
	 
	Board board=new Board();
	board.setTitle(request.getParameter("title"));
	board.setUserId(sUserId);
	board.setContent(request.getParameter("content"));
	
	BoardService.getInstance().create(board,sUserId,p_no);
	response.sendRedirect("board_list.jsp?p_no="+p_no);
%>
