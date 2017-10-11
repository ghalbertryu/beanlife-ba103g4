<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>
<%@ page import="com.store.model.*"%>


<html>
  <head>
    <meta charset="UTF-8">
    <title>後端積分管理</title>
    <link rel="stylesheet prefetch" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/style.css">
    
 
    
    <%--date picker專用css --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/bootstrap-datetimepicker.min.css" />
    
  <style type="text/css">
    *{
      position: relative;
    }
    .mem_pic{
      width: 150px;
      height: 120px;
    }
    td{
      vertical-align: middle;
     
     padding: 10px;
    }
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{

  vertical-align: middle;
  text-align: center;
}

 .card .right .right_middle .middle .btn {
        float: right;
        margin-top: -50px;
        padding: 10px 20px; }
        
 
 
  
  
  
  .update{
  display: inline-block;
  }
  
  .my_mem_pt,.my_total_pt{
  
  display: none;
  }
  .mem_pt, .mem_total_pt{
  margin-right: 10px;
  width: 30%;
  }
  
  </style>
<%
MemService memSvc=new MemService();
List<MemVO> list=  memSvc.getAll();
pageContext.setAttribute("list",list);
%>
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
    <div class="container card">
      <div class="row">
        <div class="col-xs-2 left"><a class="h3 title" href="#action" aria-expanded="false" aria-controls="action" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-futbol-o"></div><a class="h3" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp"> 活動審核</a></a><a class="h3 title" href="#mem" aria-expanded="false" aria-controls="mem" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-address-card-o"></div><span class="h3">會員管理</span>
            <ul class="collapse" id="mem"><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a></ul></a><a class="h3 title" href="#gift" aria-expanded="false" aria-controls="gift" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-gift"> </div><span class="h3">平台業務管理</span>
            <ul class="collapse" id="gift"><a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a><a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a></ul></a></div>
        <div class="right col-xs-10">
          <div class="col-xs-12 right_top"><img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
            <h2>你好</h2><a class="fa fa-bell dropdown-toggle" href="#" data-toggle="dropdown"></a>
            <ul class="dropdown-menu">
             
              <li><a  href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
              <li><a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
            </ul>
          </div>
          <div class="col-xs-12 right_middle">
            <div class="col-xs-12  middle">
              <h2 class="check">積分管理</h2>
               
            </div>
          </div>
          <div class="col-xs-12 right_bottom">
          <%@ include file="mem_pt_page1.file" %> 
            <table class="table table-striped">
              <tr>
                <th>會員帳號</th>
                <th>會員姓名</th>
                <th>會員手機</th>
                <th>會員地址</th>
                <th>會員圖片</th>
                <th>會員總積分</th>
                <th>會員可用積分</th>
                <th>會員等級</th>
                <th>管理</th>
              </tr>
              <c:forEach var="mem_vo"  items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
              <tr>
                <td  valign="middle">${mem_vo.mem_ac }</td>
                <td>${mem_vo.mem_lname }${mem_vo.mem_fname } </td>
                <td>${mem_vo.mem_phone }</td>
                <td>${mem_vo.mem_add }</td>
                <td><img class="mem_pic" src="<%=request.getContextPath()%>/MemImg.do?mem_ac=${mem_vo.mem_ac}"></td>
                
                
                <td><div class="now_total_pt">${mem_vo.mem_total_pt }</div>
                <div  class="my_total_pt">
                 <Form  METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet"  class="update">
                <input type="number"  name="mem_total_pt" class="mem_total_pt"  placeholder="${mem_vo.mem_total_pt }"><button class="btn btn-success"  type="submit">修改</button>
                <input type="hidden" name="whichPage" value="<%=whichPage %>">
                     <input type="hidden"  name="mem_pt.jsp"  value="<%=request.getServletPath() %>"> 
                       <input type="hidden" name="action" value="modify_total_pt">
                       <input type="hidden" name="mem_ac" value="${mem_vo.mem_ac }">
                </Form>
                </div>
                </td>
                
                
                
                <td><div class="now_pt">${mem_vo.mem_pt }</div>
                <div  class="my_mem_pt">
                 <Form  METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet"  class="update">
                <input type="number"  name="mem_pt" class="mem_pt"  placeholder="${mem_vo.mem_pt }"><button class="btn btn-info"  type="submit">修改</button>
                <input type="hidden" name="whichPage" value="<%=whichPage %>">
                     <input type="hidden"  name="mem.jsp"  value="<%=request.getServletPath() %>"> 
                       <input type="hidden" name="action" value="modify_pt">
                       <input type="hidden" name="mem_ac" value="${mem_vo.mem_ac }">
                </Form>
                </div>
                </td>
                 <td>${mem_vo.grade_no }</td>
                <td>
                
                 
                   
                  <button class="btn btn-success  update_mem_total_pt" type="button">修改總積分</button>
                   
                       
                
                  
                  <button class="btn btn-info  update_mem_pt" type="button">修改可用積分</button>
                   
                  
                </td>
              </tr>
                </c:forEach>
            </table>
          
          </div>
          <%@ include file="mem_pt_page2.file" %> 
           
        </div>
      </div>
     
    </div>
    
    
  
    
    
    
    
    
    
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
   
  

    
    <script>
    
   $(".update_mem_pt").click(function(){
	   $(this).parent().prev().prev().children().css("display","none");
	   $(this).parent().prev().prev().children().next().css("display","block");
	  
	   
	   
   })
   
    $(".update_mem_total_pt").click(function(){
	   $(this).parent().prev().prev().prev().children().css("display","none");
	   $(this).parent().prev().prev().prev().children().next().css("display","block");
	  
	   
	   
   })
   
   
   if(${not empty errorMsgs}){
	   sweetAlert("Oops!", <c:forEach var="message" items="${errorMsgs} ">
			"${message}"
		</c:forEach>, "error");
   }
    	
    	
    	   
    
    
 
    </script>
     
    
  </body>
</html>