<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.prod.model.*"%>
<%-- 此頁採用 JSTL 與 EL 取值 --%>

<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"/>

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="memVO" value="${memSvc.getOneMem(mem_ac)}"/>

<%
MemVO memVO=(MemVO)pageContext.getAttribute("memVO");
%>

<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-8 col-sm-offset-2">
			<h3 class="bold">修改個人資料</h3>
			
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
		ACTION="<%=request.getContextPath()%>/mem/mem.do" enctype="multipart/form-data">
	<div class="table-responsive">          
  		<table class="table">
			<caption>
				您好${memVO.mem_ac}，這是您的資料</caption>
			
			<tr >
				<td>姓氏</td>
				<td colspan="2"><input  class="form-control" type="TEXT" name="mem_lname" size="45"
					value="${memVO.mem_lname}" /></td>
			</tr>
			<tr>
				<td>名字</td>
				<td colspan="2"><input  class="form-control" type="TEXT" name="mem_fname" size="45" 
					value="${memVO.mem_fname}" /></td>
			</tr>
			<tr>
				<td>Email</td>
				<td colspan="2"><input  class="form-control"  type="TEXT"  name="mem_email" size="45"
					 value="${memVO.mem_email}" /></td>
			</tr>
			<tr>
				<td>地址</td>
				<td colspan="2"><input  class="form-control" type="TEXT"  name="mem_add" size="45"
					 value="${memVO.mem_add}" /></td>
			</tr>
			<tr>
				<td>頭像</td>
				<td><output id="mylist1"><img src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${memVO.mem_ac}" width='100'></output></td>
				<td><input  class="form-control-file"  type="file"  name="mem_pic" size="45" id="mem_pic"
					 value="上傳" /></td>
			</tr>
			
			
		</table>
	</div>
		<input class="form-control  pull-right btn btn-primary"  type="submit" value="確定"> 
		<input type="hidden" value="${memVO.mem_ac}" name="mem_ac">
		<input type="hidden" name="action" value="update_data">
		<input type="hidden" name="mem_set1" value="">
		<input type="hidden" name="mem_set2" value="">
		<input type="hidden" name="mem_set3" value="">
	</FORM>
	
	
		</div>
	</div>
</div>
<script>
function handleFileSelect1(evt) {
		$("#mylist1").empty();

		var files = evt.target.files; // FileList object

		// Loop through the FileList and render image files as thumbnails.
		for (var i = 0, f; f = files[i]; i++) {

			// Only process image files.
			if (!f.type.match('image.*')) {
				continue;
			}

			var reader = new FileReader();

			// Closure to capture the file information.
			reader.onload = (function(theFile) {
				return function(e) {
					// Render thumbnail.
					var span = document.createElement('span');
					span.innerHTML = [ '<img class="thumb" src="',
							e.target.result, '" title="', escape(theFile.name),
							'"/>' ].join('');
					document.getElementById('mylist1').insertBefore(span, null);
					$(".thumb").width(150);

				};
			})(f);

			// Read in the image file as a data URL.
			reader.readAsDataURL(f);
		}
	}
	document.getElementById('mem_pic').addEventListener('change',
			handleFileSelect1, false);
</script>
<jsp:include page="/FrontEnd/include/footer.jsp"/>