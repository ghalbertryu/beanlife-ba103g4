<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>

<jsp:include page="/FrontEnd/include/head.jsp"/>


<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-6 col-sm-offset-3">
			<h3 class="bold">會員註冊</h3>

	

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

	<button class="new btn-xs btn-default">oh</button>

	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/mem/mem.do">
	<div class="table-responsive">          
  		<table class="table">
			<caption>
				請填寫會員資料，以註冊會員</caption>
			<tr>
				<td>會員帳號:</td>
				<td><input  class="form-control ac" type="TEXT" name="mem_ac" size="45"
					value="${memVO.mem_ac}" /></td>
			</tr>
			<tr>
				<td>會員密碼:</td>
				<td><input  class="form-control pd" type="password" name="mem_pwd" size="45"
					value="${memVO.mem_pwd}" /></td>
			</tr>
			
			<tr>
				<td>會員手機:</td>
				<td>
					<input  class="form-control phone" type="TEXT"  name="mem_phone" size="45"
					 value="${memVO.mem_phone}" />
					 <input class="form-control auth btn-success" type="button" value="驗證手機">
				 </td>
			</tr>
			<tr>
				<td>請輸入手機驗證碼:</td>
				<td><input  class="form-control" type="text" name="code" size="45" 
					value="" /></td>
			</tr>
		</table>
	</div>
		<input  class="btn btn-primary pull-right" type="submit" value="確定申請會員"> 
		<input type="hidden" name="action" value="Application">
	</FORM>


<form METHOD="post" class="phoneform"
		ACTION="<%=request.getContextPath()%>/mem/mem.do">
		<input type="hidden"  name="authphone" id ="authphone" value="${memVO.mem_phone}"> 
		<input type="hidden" name="action" value="authphone">
		<input type="hidden"  name="mem_ac" id ="ac1" value="${memVO.mem_ac}"> 
		<input type="hidden"  name="mem_pwd" id ="pd1" value="${memVO.mem_pwd}"> 
</form>
			
		</div>
	</div>
</div>

<script>
$(".phone").change(function(){
	
	$("#authphone").val($(".phone").val());
	
})
$(".ac").change(function(){
	
	$("#ac1").val($(".ac").val());
	
})
$(".pd").change(function(){
	
	$("#pd1").val($(".pd").val());
	
})


$(".auth").click(function(){
	$(".phoneform").submit();
})
$(".new").click(function(){
	$(".ac").val("ohyes103");
	$(".pd").val("zxc123");
	$(".phone").val("0903567887");
	$("#ac1").val("ohyes103");
	$("#pd1").val("zxc123");
	$("#authphone").val("0903567887");
})


</script>


<jsp:include page="/FrontEnd/include/footer.jsp"/>