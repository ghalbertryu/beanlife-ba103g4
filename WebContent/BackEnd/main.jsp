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
    <title>後端首頁進入頁面</title>
    <link rel="stylesheet prefetch" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <!-- <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>-->+
   
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/main.css">
    
    <%
    ActService actSvc=new ActService();
     List<ActVO>act_vo_list= actSvc.getAll();
    int act_count=0;
    for(int i=0;i<act_vo_list.size();i++){
    	if(act_vo_list.get(i).getAct_stat().equals("待審核")){
    		act_count++;
    	}
    }
    
    pageContext.setAttribute("act_count",act_count);
    
    Convert_giftService convert_giftSvc=new Convert_giftService();
    List<Convert_giftVO> convert_gift_vo_list= convert_giftSvc.getAll();
    int convert_gift_count=0;
    for(int i=0;i<convert_gift_vo_list.size();i++){
    	if(convert_gift_vo_list.get(i).getApply_stat().equals("待出貨")){
    		convert_gift_count++;
    	}
    }
    
    pageContext.setAttribute("convert_gift_count",convert_gift_count);
    
    StoreService storeSvc=new StoreService();
    List<StoreVO>store_vo_list= storeSvc.getAll();
   int store_count=0;
   for(int i=0;i<store_vo_list.size();i++){
   	if(store_vo_list.get(i).getStore_stat().equals("待審中")){
   		store_count++;
   	}
   }
   
   pageContext.setAttribute("store_count",store_count);
    
    %>
    
    
    
  </head>
  <body>
    <div class="container_fluid titlebar"><a class="form-inline titlebarForm" href="<%=request.getContextPath()%>/BackEnd/main.jsp"><img class="icon" src="<%=request.getContextPath()%>/BackEnd/res/images/BeanLifeLogo2.png" alt="">
        <h1>Bean-Life</h1></a></div>
    <!-- .left是左邊導覽列的部分-->
    <div class="container card">
      <div class="row">
        <div class="col-xs-2 left"><a class="h3 title" href="#action" aria-expanded="false" aria-controls="action" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-futbol-o"></div><a class="h3" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp"> 活動審核</a></a><a class="h3 title" href="#check" aria-expanded="false" aria-controls="check" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-check-circle"></div><span class="h3">檢舉管理</span>
            <ul class="collapse" id="check"><a>評論檢舉</a><a>商品檢舉</a><a>討論區檢舉</a></ul></a><a class="h3 title" href="#mem" aria-expanded="false" aria-controls="mem" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-address-card-o"></div><span class="h3">會員管理</span>
            <ul class="collapse" id="mem"><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a></ul></a><a class="h3 title" href="#admin" aria-expanded="false" aria-controls="admin" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-user-o"> </div><span class="h3">管理員管理</span>
            <ul class="collapse" id="admin"><a>管理帳戶授權</a><a>帳戶管理</a></ul></a><a class="h3 title" href="#gift" aria-expanded="false" aria-controls="gift" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-gift"> </div><span class="h3">平台業務管理</span>
            <ul class="collapse" id="gift"><a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a><a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a></ul></a></div>
        <div class="right col-xs-10">
          <div class="col-xs-12 right_top"><img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
            <h2>你好</h2><a class="fa fa-bell dropdown-toggle" href="#" data-toggle="dropdown"></a>
            <ul class="dropdown-menu">
              <li><a>10項檢舉未處理</a></li>
              <li><a href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
            </ul>
          </div>
          <div class="col-xs-12 right_middle">
            <div class="col-xs-12">
              <h2 class="check">待處理</h2>
            </div>
          </div>
          <div class="col-xs-12 right_bottom">
            <div class="col-xs-3">
              <div class="report"><a class="fa fa-check-circle" href=""></a>
                <h2> <a href="">10項檢舉未處理</a></h2>
              </div>
            </div>
            <div class="col-xs-3">
              <div class="report"><a class="fa fa-futbol-o" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp"></a>
                <h2><a href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count}項活動未審核</a></h2>
              </div>
            </div>
            <div class="col-xs-3">
              <div class="report"><a class="fa fa-address-card-o" href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp"></a>
                <h2><a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></h2>
              </div>
            </div>
            <div class="col-xs-3">
              <div class="report"><a class="fa fa-gift" href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp" ></a>
                <h2><a href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp" >${convert_gift_count }項兌換贈品申請</a></h2>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  </body>
</html>