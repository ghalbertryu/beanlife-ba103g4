<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.ord.model.*"%>
<%@ page import="com.ord_list.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>

<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService"/>
<jsp:useBean id="ordSvc" scope="page" class="com.ord.model.OrdService"/>
<%

	String mem_ac = (String) session.getAttribute("mem_ac");
	StoreService storeSvc = new StoreService();
	
	
	StoreVO storeVO=storeSvc.getOneByMem(mem_ac);
	pageContext.setAttribute("storeVO", storeVO); 
	String store_no = storeVO.getStore_no();
	pageContext.setAttribute("store_no", store_no); 
	request.setAttribute("storeVO",storeVO);
	
	OrdVO ordVO=(OrdVO) request.getAttribute("ordVO");
%>
<c:set var="ord_listVOs" value="${ordSvc.getOrd_listByOrd(ordVO.ord_no)}"/>


<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-10 col-sm-offset-1">
			<h3 class="bold">訂單明細</h3>


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
	
	<div class="product col-sm-2 pad0">
	<div class="table-responsive">  
	<table class="store" >
	<tr><td align="center"><h4>${storeVO.store_name}</h5></td></tr>
	<tr><td align="center"><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" width='150'></td></tr>
	<tr><td align="center"><h5><a class="showStore" name="${storeVO.store_no}" href='#modal-inner' data-toggle="modal" >預覽商場</a></h5></td></tr>
	<tr><td align="center"><h5><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></h5></td></tr>
	</table>
	</div>
	</div>
	
	
	<div class="product col-sm-10 ">
			<div class="table-responsive">       
			<table class="table-bordered table addpro ord_list">
				<tr>
					<th>商品名稱</th>
					<th>數量</th>
					<th>單價</th>
					<th>總金額</th>
				</tr>
				
				<c:forEach var="ord_listVO" items="${ord_listVOs}" >
					<tr>
						<td>${prodSvc.getOneProd(ord_listVO.prod_no).prod_name}</td>
						<td>${ord_listVO.amont}</td>
						<td>${prodSvc.getOneProd(ord_listVO.prod_no).prod_price}</td>
						<td>NT$${ord_listVO.amont*prodSvc.getOneProd(ord_listVO.prod_no).prod_price}</td>
					</tr>
				</c:forEach>
			</table>
			</div >
				<div class="">
				運費：NT$${ordVO.send_fee}<br>
				結帳總金額：NT$${ordVO.total_pay}
				<br><br>
				</div>
	
	
					<table class="table-bordered customer">
						<caption class="customerca"><B>買家資訊</B></caption>
						<tr><td>收貨人</td><td>${ordVO.ord_name}</td><td>買家帳號</td><td>${ordVO.mem_ac}</td></tr>
						<tr><td>收貨人電話</td><td>${ordVO.ord_phone}</td><td>收貨人住址</td><td>${ordVO.ord_add}</td></tr>
					</table>

		<br>
					<div >
						付款資訊：
				<c:if test="${fn:startsWith(ordVO.pay_info, 'B')}"> 
 						${ordVO.pay_info}
				</c:if> 
 
				<c:if test="${fn:startsWith(ordVO.pay_info, 'C')}"> 
 						${ordVO.pay_info}
				</c:if>
					</div>
	
			
		<fmt:formatDate value="${ordVO.ord_date}" var="ord_date"  
                  pattern="yyyy-MM-dd hh:mm:ss"/> 
		<fmt:formatDate value="${ordVO.pay_date}" var="pay_date"  
                 pattern="yyyy-MM-dd hh:mm:ss"/> 
        <fmt:formatDate value="${ordVO.pay_chk_date}" var="pay_chk_date"  
                 pattern="yyyy-MM-dd hh:mm:ss"/> 
        <fmt:formatDate value="${ordVO.send_date}" var="send_date"  
                pattern="yyyy-MM-dd hh:mm:ss" /> 
	
		
		<br><br>
				
					
						<table class="bar table-bordered">
							<tr><td class="bar1" >訂單成立</td><td  class="w1" >${ord_date}</td></tr>
							<tr><td class="bar1">付款時間</td><td  class="w1">${pay_date}</td></tr>
							<tr><td class="bar1">確認付款時間</td><td  class="w1">${pay_chk_date}</td></tr>
							<tr><td class="bar1">出貨時間</td><td  class="w1">${send_date}</td></tr>
						</table>
						<br>
						訂單目前狀態：${ordVO.ord_stat}<br>
						<p ${empty ordVO.send_id ? "hidden" : ""}>出貨物流編號：${ordVO.send_id.equals("") ? "" : ordVO.send_id}<br></p>
						<FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/ord/Ord_manag.do">
										<input type=${ordVO.ord_stat.equals("已確認付款") ? "text" : "hidden" } name="send_id" value="${ordVO.send_id}" >
										<input type=${ordVO.ord_stat.equals("未付款")||ordVO.ord_stat.equals("已出貨") ? "hidden" : "submit"} value="${ordVO.ord_stat.equals("已付款") ? "確認已付款" : '發出出貨通知'}" class="btn-info">
										<input type="hidden" name="ord_no" value="${ordVO.ord_no}">
										<input type="hidden" name="ord_stat" value="${ordVO.ord_stat}">
										<input type="hidden" name="action" value="update_stat">
										<input type="hidden" name="ordmem_ac" value="${ordVO.mem_ac}">
										<span>${ordVO.ord_stat.equals("已確認付款") ? "請輸入物流編號並發出出貨通知" : ''}</span>
										
							</FORM>
						<a href="<%=request.getContextPath()%>/FrontEnd/ord_mag/listAllorder_bystore.jsp" role="button"><span class="btn btn-info pull-right" >返回上頁</span></a>
					</div>
				
			
</div>
</div>
</div>
<style>
	
	.verticnav{
			list-style: none;
			font-size: 25px;
	}
	caption{
			text-align:center;
	}
	.w1{
       	 width:300px;
       	 text-align: center;
       	 padding-bottom: 2px;
       	 text-indent : -2em ;
       }
    .bar1{
       	 width:180px;
       	 padding-bottom: 2px;
       	 text-indent : 0em ;
       }
	.bar{
		text-indent: -7em

		}
	.customer{
		width:700px;
		
	}
	.ord_list{
			width:700px;
	}
	.customerca{
			text-align:left;
	}
</style>


<jsp:include page="/FrontEnd/include/footer.jsp"/>