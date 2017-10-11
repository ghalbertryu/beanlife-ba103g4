<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>

<%-- 此頁採用 JSTL 與 EL 取值 --%>

<%
	
// 	String store_stat1= (String) request.getAttribute("store_stat1");
	String store_stat1= (String) request.getParameter("store_stat1");
	StoreService storeSvc = new StoreService();
	List<StoreVO> list;
	if(store_stat1==null){
		
		list = storeSvc.getAll();
	} else {
		list=storeSvc.getstatstr(store_stat1);
	}
	
		pageContext.setAttribute("list", list);
%>


<%

List<StoreVO>store_vo_list= storeSvc.getAll();
int store_count=0;
for(int i=0;i<store_vo_list.size();i++){
	if(store_vo_list.get(i).getStore_stat().equals("待審中")){
		store_count++;
	}
}

pageContext.setAttribute("store_count",store_count);




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
    
    
    %>



<html>
<head>
<title>廠商店家授權</title>
<link rel="stylesheet prefetch"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/store.css">

<style>
 .card .right .right_top img {
        width: 70px;
        height: 70px;
        border-radius: 50%; }
        
    .sortable{
    width: 100%;
    }    
     
     .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
text-align: center;
  vertical-align: middle;
}
     th {
    text-align: center;
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
						<a href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a>
						<a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a>
					</ul>
				</a>
			</div>
			<div class="right col-xs-10">
				<div class="col-xs-12 right_top">
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
					<table class="store_tittle">
						<tr>
							<td><h3>店家資料-listALLStore.jsp</h3></td>
							<td width="100"></td>
							<td>依審核狀態選取：</td>
							<td></td>
							<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/store/store.do">
										<select size="1" name=store_stat1>
											<option value="%%">請選擇</option>
											<option value="待審中" ${(param.store_stat1=='待審中')? 'selected':''}>待審中</option>
　											<option value="審核通過" ${(param.store_stat1=='審核通過')? 'selected':''}>審核通過</option>
　											<option value="審核不通過" ${(param.store_stat1=='審核不通過')? 'selected':''}>審核不通過</option>
										</select>
											<input type="submit" value="送出"> 
											<input	type="hidden" name="action" value="selstat">
											<input	type="hidden" name="store_stat1" value="selstat">
								</FORM>	
							</td>
							<td>目前查詢狀態：${(param.store_stat1==null||param.store_stat1=='%%')? '查詢全部':param.store_stat1}</td>
						</tr>
					</table>
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
					<table class="sortable table-bordered table-responsive">
						<tr>
							<th>廠商編號</th>
							<th>會員帳號</th>
							<th>統一編號</th>
							<th>廠商名稱</th>
							<th>證件照</th>
							<th>店家照1</th>
							<th>店家照2</th>
							<th>店家照3</th>
							<th>審核狀態</th>
							<th></th>
							<th>審核日期</th>
						</tr>
						<%@ include file="page1.file"%>

						<c:forEach var="storeVO" items="${list}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
							<tr align='center' valign='middle' >
								<td>${storeVO.store_no}</td>
								<td>${storeVO.mem_ac}</td>
								<td>${storeVO.tax_id_no}</td>
								<td>${storeVO.store_name}</td>							
								<td><img width="100px" src="<%=request.getContextPath()%>/store/StoreImg.do?store_no=${storeVO.store_no}&index=0"></td>
								<td><img width="100px" src="<%=request.getContextPath()%>/store/StoreImg.do?store_no=${storeVO.store_no}&index=1" ></td>
								<td><img width="100px" src="<%=request.getContextPath()%>/store/StoreImg.do?store_no=${storeVO.store_no}&index=2" ></td>
								<td><img width="100px" src="<%=request.getContextPath()%>/store/StoreImg.do?store_no=${storeVO.store_no}&index=3" ></td>
								<td class="my_store_stat"  ${(storeVO.store_no==param.store_no) ? 'bgcolor=#CCCCFF':''}>${storeVO.store_stat}</td>
								<td>
									<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/store/store.do">
							 			<input type="submit" value="審核修改狀態" class="btn btn-info"> 
							
										<input type="hidden" name="store_no" value="${storeVO.store_no}">
										<input type="hidden" name="store_stat_cont" value="${storeVO.store_stat_cont}">
										<input type="hidden" name="whichPage"	value="<%=whichPage%>">  
										<input type="hidden" name="action" value="getOne_For_Update">
									 	<input	type="hidden" name="store_stat1" value="${(param.store_stat1==null)?"%%":param.store_stat1 }"> 
	<%--								<input	type="hidden" name="store_stat1"  class="my_store_stat1" value="${param.store_stat1}"> --%>
									</FORM>
								</td>
								<td>${storeVO.store_stat_cdate}</td>
							</tr>
						</c:forEach>



					</table>


					<%@ include file="page2.file"%>
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
	
	<script>
	<%--
	for(var i=0;i<$(".my_store_stat1").length;i++){
		var text=$(".my_store_stat").eq(i).text();
		$(".my_store_stat1").eq(i).val(text);	
	}
	--%>
	</script>
	
</body>
</html>