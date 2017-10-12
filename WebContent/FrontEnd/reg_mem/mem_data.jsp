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


	String mem_set = memVO.getMem_set();
	String[] set = mem_set.split(",");
	System.out.println(set.length);
 	pageContext.setAttribute("coun",set[0]);


%>

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
		ACTION="<%=request.getContextPath()%>/mem/mem.do" enctype="multipart/form-data">
		<table border="1">
			<caption>
				您好${memVO.mem_ac}，這是您的資料</caption>
			
			<tr>
				<td>會員姓氏:</td>
				<td><input type="TEXT" name="mem_lname" size="45"
					value="${memVO.mem_lname}" /></td>
			</tr>
			<tr>
				<td>會員名字:</td>
				<td><input type="TEXT" name="mem_fname" size="45" 
					value="${memVO.mem_fname}" /></td>
			</tr>
			<tr>
				<td>會員Email:</td>
				<td><input type="TEXT"  name="mem_email" size="45"
					 value="${memVO.mem_email}" /></td>
			</tr>
			<tr>
				<td>會員地址:</td>
				<td><input type="TEXT"  name="mem_add" size="45"
					 value="${memVO.mem_add}" /></td>
			</tr>
			<tr>
				<td>會員圖片:</td>
				<td><output id="mylist1"><img src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${memVO.mem_ac}" width='100'></output></td>
				<td><input type="file"  name="mem_pic" size="45" id="mem_pic"
					 value="上傳" /></td>
			</tr>
			
			<tr>
				<td>搜尋偏好設定:國家</td>
				<td>
					<select name="mem_set1">
						<c:forEach var="country"  items="${countrys}">
							<option value="${country}" ${(coun==country)?"selected": ""}>${country}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<td>搜尋偏好設定:處理法</td>
				<td>
					<input type="radio" name="mem_set2" value="日曬" <%=(set[1].equals("日曬")?"checked":"") %>>日曬<br>
					<input type="radio" name="mem_set2" value="半水洗" <%=(set[1].equals("半水洗")?"checked":"") %>>半水洗<br>
					<input type="radio" name="mem_set2" value="水洗" <%=(set[1].equals("水洗")?"checked":"") %>>水洗<br>
					<input type="radio" name="mem_set2" value="蜜處理" <%=(set[1].equals("蜜處理")?"checked":"") %>>蜜處理<br>
				</td>
			</tr>
			<tr>
				<td>搜尋偏好設定:烘焙度</td>
				<td>
					<select name="mem_set3">
						<option value="0" <%=(set[2].equals("0")?"selected":"") %>>極淺焙</option>
						<option value="1" <%=(set[2].equals("1")?"selected":"") %>>淺焙</option>
						<option value="2" <%=(set[2].equals("2")?"selected":"") %>>中焙</option>
						<option value="3" <%=(set[2].equals("3")?"selected":"") %>>中深焙</option>
						<option value="4" <%=(set[2].equals("4")?"selected":"") %>>城市烘焙</option>
						<option value="5" <%=(set[2].equals("5")?"selected":"") %>>深焙</option>
						<option value="6" <%=(set[2].equals("6")?"selected":"") %>>法式烘焙</option>
						<option value="7" <%=(set[2].equals("7")?"selected":"") %>>重焙</option>
					</select>
				</td>
			</tr>
		</table>
		<input type="submit" value="確定送出"> 
		<input type="hidden" value="${memVO.mem_ac}" name="mem_ac">
		<input type="hidden" name="action" value="update_data">
	</FORM>
	
	
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