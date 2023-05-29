<%@page import="com.itwill.hellomart.product.ProductService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(request.getMethod().equalsIgnoreCase("GET")){
	response.sendRedirect("product_list.jsp");
	return;
}
String keyword = request.getParameter("keyword");

response.sendRedirect("product_list.jsp?keyword="+keyword);
%>
