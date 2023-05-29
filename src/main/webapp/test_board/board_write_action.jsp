<%@page import="com.itwill.hellomart.board.BoardService"%>
<%@page import="com.itwill.hellomart.board.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(request.getMethod().equalsIgnoreCase("GET")){
		response.sendRedirect("board_list.jsp");
		return;
	}
	 
	Board board=new Board();
	board.setTitle(request.getParameter("title"));
	board.setUserId((request.getParameter("writer")));
	board.setContent(request.getParameter("content"));
	
	BoardService.getInstance().create(board,request.getParameter("writer"),15);
	response.sendRedirect("board_list.jsp");
%>
