
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gift_data.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>

<style type="text/css">








	.buy_title{
		margin-bottom: 50px;
		font-weight: 900;
	}
	.bold{
		font-weight: 900;
	}

	.myAdd{
		width: 80%;
	}
.not_long{
	width:50%;
}

.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
 vertical-align:middle;
 height: 30px ;
}

.my_area{
margin-top: 57px;
}
body .col-md-offset-9 .cancel_getGift{
color: #eee;
font-weight: 700;

}

</style>


	
<div class="my_area content">	
		
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<h1 class="buy_title">兌換贈品</h1>
			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet" name="form1" >
			<input type="hidden"  name="apply_date"  value="${apply_date }">
			<input type="hidden"  name="gift_no"  value="${gift_data_vo.gift_no }">
			<input type="hidden"  name="mem_ac"  value="${(mem_ac==null)? "mamabeak":mem_ac}">
			<input type="hidden"  name="action"  value="buy_gift_confirm">
			<input type="hidden"  name="buy_gift.jsp"  value="<%=request.getServletPath()%>">
			
				<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'  >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
			
			
			<table class="table table-striped mytable">
			
			
				
				<tr>
					<td class="bold">贈品名稱</td>
					<td >${gift_data_vo.gift_name }</td>
				</tr>
				<tr>
					<td class="bold">兌換數量</td>
					<td >${gift_amount }
					<input type="hidden"  name="gift_amount" value="${gift_amount }">
					
					</td>
				</tr>
				
				<tr>
					<td class="bold">兌換申請日期</td>
					<td>${apply_date }</td>
				</tr>
				<tr>
					<td class="bold">收件人姓名</td>
					<td><input type="text"  name="apply_name" class="form-control not_long" value="${(convert_gift_vo==null)?"":convert_gift_vo.apply_name}"  placeholder="陳xx"></td>
				</tr>
				<tr>
					<td class="bold">收件人電話</td>
					<td><input type="number"  name="apply_phone" class="form-control not_long" value="${(convert_gift_vo==null)?"":convert_gift_vo.apply_phone}"  placeholder="09xxxxxxxx"></td>
				</tr>
			<tr>
					<td class="bold">收貨地址</td>
					<td><input type="text"  name="apply_add" class="myAdd form-control" value="${(convert_gift_vo==null)?"":convert_gift_vo.apply_add}"  placeholder="台北市xx路..."></td>
				</tr>
			
			</table>
			
			<div class="col-md-offset-9">
				<a class="btn btn-danger  cancel_getGift"  href="<%=request.getContextPath()%>/FrontEnd/gift/gift_data_frontEnd.jsp">取消兌換</a>
				<button  type="submit" class="btn btn-success">確定兌換</button>
			</div>
</FORM>
		</div>
	</div>
</div>

</div>	

<jsp:include page="/FrontEnd/include/footer.jsp"/>
		
		
	
	