<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>

<%
	//String store_stat1= (String) request.getAttribute("store_stat1");
	StoreVO storeVO = (StoreVO) request.getAttribute("storeVO"); //EmpServlet.java (Concroller), 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	
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
<html>
<head>
<title>店家頁面修改</title>
<link rel="Shortcut Icon" type="image/x-icon" href="/BA103G4/FrontEnd/res/img/logo/BeanLifeIco2.ico" />

<link rel="stylesheet prefetch"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="../font-awesome-4.7.0/css/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res//css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/store.css">
<script src="sorttable.js"></script>
<style>
 .card .right .right_top img {
        width: 70px;
        height: 70px;
        border-radius: 50%; }
        
    .log_out{
    padding: 10px 20px;
    margin-right: 10px;
 	margin-top: 25px;
 	margin-bottom: 25px;
    
    }    
</style>


</head>
<body>
	<div class="container_fluid titlebar">
		<a class="form-inline titlebarForm" href="<%=request.getContextPath()%>/BackEnd/main.jsp"><img
			class="icon" src="<%=request.getContextPath()%>/BackEnd/res/images/BeanLifeLogo2.png">
			<h1>Bean-Life</h1></a>
	</div>
	<!-- .left是左邊導覽列的部分-->
	<!-- .left選擔部分0910修正選單內容-->
	<!-- 0910 玲當部份新增下拉選單-->
	<div class="container card">
		<div class="row">
			<div class="col-xs-2 left">
				<a class="h3 title act" href="#action" aria-expanded="false"
					aria-controls="action" data-toggle="collapse"
					style="text-decoration: none;">
					<div class="fa fa-futbol-o"></div> <a class="h3" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">
						活動審核</a>
				</a><a class="h3 title" href="#mem" aria-expanded="false"
					aria-controls="mem" data-toggle="collapse"
					style="text-decoration: none;">
					<div class="fa fa-address-card-o"></div> <span class="h3">會員管理</span>
					<ul class="collapse" id="mem">
						<a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a>
						<a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a>
						<a href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a>
					</ul>
				</a><a class="h3 title" href="#gift" aria-expanded="false"
					aria-controls="gift" data-toggle="collapse"
					style="text-decoration: none;">
					<div class="fa fa-gift"></div> <span class="h3">平台業務管理</span>
					<ul class="collapse" id="gift">
						<a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a>
						<a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a>
						<a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a>
					</ul>
				</a>
			</div>
			<div class="right col-xs-10">
				<div class="col-xs-12 right_top">
				<Form  METHOD="post" ACTION="<%=request.getContextPath() %>/mem_management/mem_managementServlet" >
          <button class="btn btn-danger log_out">登出</button>
          <input type="hidden" name="action" value="log_out">
          </Form>
<img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
					<h2>你好</h2>
					<a class="fa fa-bell dropdown-toggle" href="#"
						data-toggle="dropdown"></a>
					<ul class="dropdown-menu">
						
						<li><a  href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
						<li><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
						<li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
					</ul>
				</div>

				<div class="col-xs-12 right_bottom">
<%-- 
	<table border='1' cellpadding='5' cellspacing='0' width='400'>
		<tr  align='center' valign='middle' height='20'>
			<td>
				<h3>店家資料修改 - update_store_input.jsp</h3> <a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">回首頁</a>
			</td>
		</tr>
	</table>
--%>
	<h3>狀態修改:</h3>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/store/store.do" name="form1" enctype="multipart/form-data">
		
		<table border="0">
			<tr>
				<td>統一編號:</td>
				<td><%=storeVO.getTax_id_no()%></td>
			</tr>
			<tr>
				<td>店家名稱:</td>
				<td><%=storeVO.getStore_name()%></td>
			</tr>
			<tr>
				<td>公司電話:</td>
				<td><%=storeVO.getStore_phone()%></td>
			</tr>
			<tr>
				<td>店家住址:</td>
				<td><%=storeVO.getStore_add()%></td>
				</td>
			</tr>
			<tr>
				<td>審核狀態:</td>
				<td><select size="1" name=store_stat>
						<option value="待審中" <%=(storeVO.getStore_stat().equals("待審中"))? "SELECTED" : ""%>>待審中</option>
　						<option value="審核通過" <%=(storeVO.getStore_stat().equals("審核通過"))? "SELECTED" : ""%>>審核通過</option>
　						<option value="審核不通過" <%=(storeVO.getStore_stat().equals("審核不通過"))? "SELECTED" : ""%>>審核不通過</option>
				</select></td>
			</tr>
			
			<tr>
				<%
					java.sql.Date date_SQL = new java.sql.Date(System.currentTimeMillis());
				%>
				<td>審核日期:</td>
				<td >
				<%=storeVO.getStore_stat_cdate()%>
				</td>
			</tr>
			<tr>
				<td>證件照:</td>
				<td><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=0" width='100'>
				<td></td>
			</tr>
			<tr>
				<td>店家照1:</td>
				<td><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" width=100></td>
				<td></td>
			</tr>
			<tr>
				<td>店家照2:</td>
				<td><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=2" width=100></td>
				<td></td>
				
			</tr>
			<tr>
				<td>店家照3:</td>
				<td><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=3" width=100></td>
				<td></td>
			</tr>
			<tr>
				<td>不通過理由:</td>
				<td><textarea rows="4" cols="50" name="store_stat_cont" value="<%=storeVO.getStore_stat_cont()%>">${storeVO.store_stat_cont}</textarea>
					</td>
				<td></td>
			</tr>
			
		</table>
		<br> <input type="hidden" name="action" value="update_stat"> 
		<input
			type="hidden" name="store_no" value="<%=storeVO.getStore_no()%>">
		
		<input
			type="hidden" name="store_stat_cdate" value="<%=storeVO.getStore_stat_cdate()%>">
		<input
			type="hidden" name="tax_id_no" value="<%=storeVO.getTax_id_no()%>">
		<input
			type="hidden" name="store_add" value="<%=storeVO.getStore_add()%>">
		<input
			type="hidden" name="store_name" value="<%=storeVO.getStore_name()%>">
		<input
			type="hidden" name="store_phone" value="<%=storeVO.getStore_phone()%>">
		<input
			type="hidden" name="win_id_pic" value="<%=storeVO.getWin_id_pic()%>">
		<input
			type="hidden" name="store_pic1" value="<%=storeVO.getStore_pic1()%>">
		<input
			type="hidden" name="store_pic2" value="<%=storeVO.getStore_pic2()%>">
		<input
			type="hidden" name="store_pic3" value="<%=storeVO.getStore_pic3()%>">
		<input type="hidden" name="whichPage" value="<%=request.getAttribute("whichPage")%>">
		<input	type="hidden" name="store_stat1" value="${param.store_stat1}">
		<input type="submit" value="送出修改">
	
	</FORM>
	</div>

			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/BackEnd/res/js/index.js"></script>
	<script src="<%=request.getContextPath()%>/BackEnd/res/js/sorttable.js"></script>
</body>
</html>


