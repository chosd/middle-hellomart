<%@page import="com.itwill.hellomart.user.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="login_check.jspf" %>     
<%
if(request.getMethod().equals("GET")){
	response.sendRedirect("hellomart_main.jsp");
	return;
}
try{
	String password=request.getParameter("password");
	String name=request.getParameter("name");
	String email=request.getParameter("email");
	User user=new User(sUserId,password,name,email);
	UserService userService=new UserService();
	int updateRowCount=userService.update(user);
	
	response.sendRedirect("user_view.jsp");
}catch(Exception e){
	e.printStackTrace();
	response.sendRedirect("user_error.jsp");
}
%>






