<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gift_data.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>
<%@ page import="com.store.model.*"%>
<html>
<head>
  <meta charset="UTF-8">
  <title>後端首頁登入頁面</title>
  <link rel="Shortcut Icon" type="image/x-icon" href="/BA103G4/FrontEnd/res/img/logo/BeanLifeIco2.ico" />
  
  
  <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>

      <link rel="stylesheet" href="<%=request.getContextPath() %>/BackEnd/res/css/index.css">
<style>
.markup {
  position: absolute;
  width: 100%;
  height: 100%;
  background-image: url("<%=request.getContextPath()%>/BackEnd/res/images/index_background.png");
  opacity: 0.5;
  background-size: cover;
  background-color: transparent;
}


</style>
  
  
  
  
</head>

<body>
  
<div class="background">
  <div class="markup"> </div>
  <div class="login_range">
    <div class="title"><img class="icon" src="<%=request.getContextPath()%>/BackEnd/res/images/BeanLifeLogo2.png" alt=""/>
      <h2>Bean-Life </h2>
    </div>
    <h1>後台管理系統</h1>
    <Form  METHOD="post" ACTION="<%=request.getContextPath() %>/mem_management/mem_managementServlet"  class="update">
    <h4 class="inputdata">帳號:</h4>
    <input class="account" type="text"     name="mgr_no"    /><br/>
    <h4 class="inputdata"   >密碼:</h4>
    <input class="password" type="password"  name="mgr_pwd"   /><br/><br/>
    
    <button class="btn btn-danger  cancel_all"  type="button">清除 </button>
    <button class="btn btn-primary"  type="submit">登入</button>
    <input type="hidden"  name="action" value="mgr_login" >
    <input type="hidden"  name="index.jsp" value="<%=request.getServletPath() %>" >
    </Form>
  </div>
</div>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
 <script>
    
 window.onload=function(){
	 if(${not empty errorMsgs}){
	    	sweetAlert("Oops!", <c:forEach var="message" items="${errorMsgs} ">
			"${message}"
			</c:forEach>, "error");
	    	
	    	
	    }
	 
	 
 };
 
    
    
    $(".cancel_all").click(function(){
    
    	$(".account").val("");
    	$(".password").val("");
    
    
    	
    })
    
    
    </script>
    
    
    

</body>
</html>
