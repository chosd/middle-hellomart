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
	//Board객체를 생성하고 입력된데이타를 읽어서 객체에저장
	Board board=new Board();
	/*
	원글boardno
	*/
	board.setBoardno(boardno);
	/*
	답글 데이타
	*/
	board.setTitle(request.getParameter("title"));
	board.setUserId(request.getParameter(sUserId));
	board.setContent(request.getParameter("content"));
	BoardService.getInstance().createReplay(board, sUserId);
	
	response.sendRedirect(
			String.format("board_list.jsp?pageno=%d&p_no=%d",pageno, p_no));
	
%>