<%@page import="com.itwill.hellomart.cart.CartService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="login_check.jspf" %>    
<%
    if(request.getMethod().equalsIgnoreCase("GET")){
    		response.sendRedirect("product_list.jsp");
    		return;
    	}
    	CartService cartService=new CartService();
    	cartService.deleteCartItemByUserId(sUserId);
    	response.sendRedirect("cart_view_select_update_qty.jsp");
    %>