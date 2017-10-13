<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>
<%



%>
<jsp:include page="/FrontEnd/include/head.jsp"/>



	
	
<div  class="content container mgt-depn-nav">
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
	
	<FORM METHOD="post"
		ACTION="<%=request.getContextPath()%>/mem/mem.do">
		<table border="1">
			<caption>
				請填寫會員資料，以註冊會員</caption>
			<tr>
				<td>會員帳號:</td>
				<td><input type="TEXT" name="mem_ac" size="45" class="ac"
					value="${memVO.mem_ac}" /></td>
			</tr>
			<tr>
				<td>會員密碼:</td>
				<td><input type="password" name="mem_pwd" size="45" class="pd"
					value="${memVO.mem_pwd}" /></td>
			</tr>
			
			<tr>
				<td>會員手機:</td>
				<td><input type="TEXT"  name="mem_phone" size="45" class="phone"
					 value="${memVO.mem_phone}" /><input type="button" class="auth" value="驗證手機"> </td>
			</tr>
			<tr>
				<td>請輸入手機驗證碼:</td>
				<td><input type="text" name="code" size="45" 
					value="" /></td>
			</tr>
		</table>
		
		<input type="submit" value="確定申請會員"> 
		<input type="hidden" name="action" value="Application">
	</FORM>
</div>
<form METHOD="post" class="phoneform"
		ACTION="<%=request.getContextPath()%>/mem/mem.do">
		<input type="hidden"  name="authphone" id ="authphone" value="${memVO.mem_phone}"> 
		<input type="hidden" name="action" value="authphone">
		<input type="hidden"  name="mem_ac" id ="ac1" value="${memVO.mem_ac}"> 
		<input type="hidden"  name="mem_pwd" id ="pd1" value="${memVO.mem_pwd}"> 
</form>


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

	

</script>


<jsp:include page="/FrontEnd/include/footer.jsp"/>