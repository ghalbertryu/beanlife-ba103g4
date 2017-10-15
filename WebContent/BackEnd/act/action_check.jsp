<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>
<%@ page import="com.store.model.*"%>

<html>
  <head>
    <meta charset="UTF-8">
    <title>後端活動審核</title>
    <link rel="Shortcut Icon" type="image/x-icon" href="/BA103G4/FrontEnd/res/img/logo/BeanLifeIco2.ico" />
    
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
    .ad_img{
      width: 150px;
      height: 120px;
    }
    td{
      vertical-align: middle;
     
     padding: 10px;
    }
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
text-align: center;
  vertical-align: middle;
}
.prod_no{
text-decoration: none;
cursor: pointer;
}
.prod_no:hover{
text-decoration: none;
}
 .card .right .right_middle .middle .btn {
        float: right;
        margin-top: -50px;
        padding: 10px 20px; }
        
  .date_picker{
  margin-left: -20px;

  padding: 20px;
  }
    .date_picker  *{
  
  }
  .control-label {
  width: 100px;
  float: left;
  padding-left: 0px;
  }
  #prod_no_selector{
  margin-right: 30px;
  }
  .delete{
  display: inline-block;
  }
  #prod_name{
  display: inline-block;

  }
  .prod_name_update{
   display: inline-block;
 
  }
  .update{
  display: inline-block;
  }
  .action_pic{
  width:120px;
  height: 80px;
  left: 50%;
  transform: translate(-50%,0%);，
  }
  
  .action_success_form,.action_fail{
  display: inline-block;
  }
  .log_out{
    padding: 10px 20px;
    margin-right: 10px;
 	margin-top: 25px;
 	margin-bottom: 25px;
    
    }
  </style>
<%
ActService actSvc=new ActService();
List<ActVO> mylist=  actSvc.getAll();
List<ActVO> list=new ArrayList();
for(int i=0;i<mylist.size();i++){
	if(mylist.get(i).getAct_stat().equals("待審核")){
		list.add(mylist.get(i));
	}
	
	
}



pageContext.setAttribute("list",list);
%>

<%
    
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
            <ul class="collapse" id="mem"><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a><a href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a></ul></a><a class="h3 title" href="#gift" aria-expanded="false" aria-controls="gift" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-gift"> </div><span class="h3">平台業務管理</span>
            <ul class="collapse" id="gift"><a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a><a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a></ul></a></div>
        <div class="right col-xs-10">
          <div class="col-xs-12 right_top">
          
          <Form  METHOD="post" ACTION="<%=request.getContextPath() %>/mem_management/mem_managementServlet" >
          <button class="btn btn-danger log_out">登出</button>
          <input type="hidden" name="action" value="log_out">
          </Form>
          
          <img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
            <h2>你好</h2><a class="fa fa-bell dropdown-toggle" href="#" data-toggle="dropdown"></a>
            <ul class="dropdown-menu">
              
              <li><a href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
            </ul>
          </div>
          <div class="col-xs-12 right_middle">
            <div class="col-xs-12  middle">
              <h2 class="check">活動審核</h2>
             
            </div>
          </div>
          <div class="col-xs-12 right_bottom">
            <%@ include file="act_page1.file" %> 
          <table class="table table-striped">
          <tr>
            <th>活動編號</th>
            <th>活動名稱</th>
            <th>主辦會員</th>
            <th>活動價格</th>
            <th>活動狀態</th>
            <th>活動照片</th>
            <th>活動審核</th>
          </tr>
          <c:forEach var="act_vo"  items="${list}"  begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
     
          <tr>
            <td><a class="link_act" href="<%=request.getContextPath() %>/act_management/act_managementServlet?action=goto_act_detail&act_no=${act_vo.act_no }&act.jsp=<%=request.getServletPath() %>" target="_blank">${act_vo.act_no }</a></td>
            <td>${act_vo.act_name }</td>
            <td>${act_vo.mem_ac }</td>
            <td>NT$${act_vo.act_fee }</td>
            <td>${act_vo.act_stat }</td>
            <td><img class="img-responsive  action_pic" src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=1" > </td>
            <td>  
           
              <a class="btn btn-danger  action_fail" href='#modal-id' data-toggle="modal">退回</a>
         
              <form   class="action_success_form"  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
                <input type="hidden" name=act_no   class="act_no"  value="${act_vo.act_no }" >
               <input type="hidden" name=action  value="action_check_pass" >
              
                <input type="hidden" name=action_check.jsp  value="<%=request.getServletPath() %>" >
              <button class="btn btn-success">通過</button>
              </form>
            </td>
          </tr>
          	
            </c:forEach>
        </table>
          
          </div>
           <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
       <%@ include file="act_page2.file" %> 
           
        </div>
      </div>
     
    </div>
    
    
    
    <div class="modal fade" id="modal-id">
			<div class="modal-dialog">
			<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">審核不通過原因</h4>
					</div>
					<div class="modal-body">
					<c:if test="${not empty errorMsgs}">
	<font color='red'  >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
						<textarea name="re_cont" class="form-control" rows="3"></textarea>
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						
						<button type="submit" class="btn btn-primary">Save changes</button>
						<input type="hidden"  name="action"  value="action_fail">
						<input type="hidden"  name="action_check.jsp"  value="<%=request.getServletPath()%>">
						<input type="hidden"  class="act_no_submit" name="act_no" value="">
					</div>
				</div>
				</form>
			</div>
		</div>
    
    
    
    
    
   
  
    
    
    
    
    
    
    
   
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <%--date picker專用js --%> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/BackEnd/res/js/bootstrap-datetimepicker.js"></script> 
 <script type="text/javascript"  src="<%=request.getContextPath()%>/BackEnd/res/js/bootstrap-datetimepicker.fr.js"></script>
  

  <script>
  
  $(".action_fail").click(function(){
var act_no=	$(this).next().children().val();
	  $(".act_no_submit").val(act_no);
	  
	  
	  
  })
  
  if(${not empty openModal}){
		 $("#modal-id").modal({show: true});
		}
  
  
  </script>
    		
    	   
    
    

     
    
  </body>
</html>