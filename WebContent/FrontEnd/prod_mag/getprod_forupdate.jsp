<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<%
	String mem_ac = (String) session.getAttribute("mem_ac");
	StoreService storeSvc = new StoreService();
	
	
	StoreVO storeVO=storeSvc.getOneByMem(mem_ac);
	pageContext.setAttribute("storeVO", storeVO); 
	String store_no = storeVO.getStore_no();
	pageContext.setAttribute("store_no", store_no); 
	String prod_no = request.getParameter("prod_no");
	ProdService proSvc = new ProdService();
	ProdVO prodvo = (ProdVO) request.getAttribute("prodvo");
%>
<style>
textarea {
  resize : none;
  width:340px
}
.te{
width:340px
}

</style>

<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-10 col-sm-offset-1">
			<h3 class="bold">修改商品</h3>

	<div>
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
	</div>
	
	<div class="product col-sm-3">
	<div class="table-responsive">  
	<table class="store" >
	<tr><td align="center"><h4>${storeVO.store_name}</h5></td></tr>
	<tr><td align="center"><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" width='150'></td></tr>
	<tr><td align="center"><h5><a class="showStore" name="${storeVO.store_no}" href='#modal-inner' data-toggle="modal" >預覽商場</a></h5></td></tr>
	<tr><td align="center"><h5><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></h5></td></tr>
	</table>
	</div>
	</div>
	
	<div class="shop">
		<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do" name="form1" enctype="multipart/form-data">
					
		<div class="product col-sm-9">
			<div class="table-responsive">       
				<table class="table pro_one">
				<tr>
					<td>商品名稱*</td>
					<td><input type="text" name="prod_name" class="form-control te"
						value="${prodvo.prod_name}" size="35"></td>
				</tr>
				<tr>
					<td>豆種</td>
					<td><input type="text" name="bean_type" class="form-control te"
						value="${prodvo.bean_type}" size="35"></td>
				</tr>
				<tr>
					<td>生豆等級</td>
					<td><input type="radio" name="bean_grade" value="G1"
						${prodvo.bean_grade.equals("G1") ? "checked" : ""}>G1 <input
						type="radio" name="bean_grade" value="G2"
						${prodvo.bean_grade.equals("G2") ? "checked" : ""}>G2 <input
						type="radio" name="bean_grade" value="G3"
						${prodvo.bean_grade.equals("G3") ? "checked" : ""}>G3 <input
						type="radio" name="bean_grade" value="G4"
						${prodvo.bean_grade.equals("G4") ? "checked" : ""}>G4 <input
						type="radio" name="bean_grade" value="G5"
						${prodvo.bean_grade.equals("G5") ? "checked" : ""}>G5</td>
				</tr>
				<tr>
					<td>生產國*</td>
					<td><input type="text" name="bean_contry" class="form-control te"
						value="${prodvo.bean_contry}"></td>
				</tr>
				<tr>
					<td>地區</td>
					<td><input type="text" name="bean_region" class="form-control te"
						value="${prodvo.bean_region}"></td>
				</tr>
				<tr>
					<td>農場</td>
					<td><input type="text" name="bean_farm" class="form-control te"
						value="${prodvo.bean_farm}"></td>
				</tr>
				<tr>
					<td>生產者</td>
					<td><input type="text" name="bean_farmer" class="form-control te"
						value="${prodvo.bean_farmer}"></td>
				</tr>
				<tr>
					<td>海拔</td>
					<td><input type="number" name="bean_el" class="form-control te"
						value="${prodvo.bean_el}"></td>
				</tr>
				<tr>
					<td>處理法*</td>
					<td><select size="1" name="proc">
							<option value="日曬" ${prodvo.proc.equals("日曬") ? "SELECTED" : ""}>日曬</option>
							<option value="半水洗"
								${prodvo.proc.equals("半水洗") ? "SELECTED" : ""}>半水洗</option>
							<option value="水洗" ${prodvo.proc.equals("水洗") ? "SELECTED" : ""}>水洗</option>
							<option value="蜜處理"
								${prodvo.proc.equals("蜜處理") ? "SELECTED" : ""}>蜜處理</option>
					</select></td>
				</tr>

				<tr>
					<td>烘焙度*</td>
					<td><select size="1" name="roast">
							<option value="0"
								${prodvo.roast.equals("0") ? "SELECTED" : ""}>極淺焙</option>
							<option value="1" ${prodvo.roast.equals("1") ? "SELECTED" : ""}>淺焙</option>
							<option value="2" ${prodvo.roast.equals("2") ? "SELECTED" : ""}>中焙</option>
							<option value="3"
								${prodvo.roast.equals("3") ? "SELECTED" : ""}>中深焙</option>
							<option value="4"
								${prodvo.roast.equals("4") ? "SELECTED" : ""}>城市烘焙</option>
							<option value="5" ${prodvo.roast.equals("5") ? "SELECTED" : ""}>深焙</option>
							<option value="6"
								${prodvo.roast.equals("6") ? "SELECTED" : ""}>法式烘焙</option>
							<option value="7" ${prodvo.roast.equals("7") ? "SELECTED" : ""}>重焙</option>
					</select></td>
				</tr>
				<tr>
					<td>風味-酸度</td>
					<td><input type="radio" name="bean_attr_acid" value="1"
						${prodvo.bean_attr_acid==1 ? "checked" : ""}>1 <input
						type="radio" name="bean_attr_acid" value="2"
						${prodvo.bean_attr_acid==2 ? "checked" : ""}>2 <input
						type="radio" name="bean_attr_acid" value="3"
						${prodvo.bean_attr_acid==3 ? "checked" : ""}>3 <input
						type="radio" name="bean_attr_acid" value="4"
						${prodvo.bean_attr_acid==4 ? "checked" : ""}>4 <input
						type="radio" name="bean_attr_acid" value="5"
						${prodvo.bean_attr_acid==5 ? "checked" : ""}>5</td>
				</tr>
				<tr>
					<td>風味-香氣</td>
					<td><input type="radio" name="bean_attr_aroma" value="1"
						${prodvo.bean_attr_aroma==1 ? "checked" : ""}>1 <input
						type="radio" name="bean_attr_aroma" value="2"
						${prodvo.bean_attr_aroma==2 ? "checked" : ""}>2 <input
						type="radio" name="bean_attr_aroma" value="3"
						${prodvo.bean_attr_aroma==3 ? "checked" : ""}>3 <input
						type="radio" name="bean_attr_aroma" value="4"
						${prodvo.bean_attr_aroma==4 ? "checked" : ""}>4 <input
						type="radio" name="bean_attr_aroma" value="5"
						${prodvo.bean_attr_aroma==5 ? "checked" : ""}>5</td>
				</tr>
				<tr>
					<td>風味-醇度</td>
					<td><input type="radio" name="bean_attr_body" value="1"
						${prodvo.bean_attr_body==1 ? "checked" : ""}>1 <input
						type="radio" name="bean_attr_body" value="2"
						${prodvo.bean_attr_body==2 ? "checked" : ""}>2 <input
						type="radio" name="bean_attr_body" value="3"
						${prodvo.bean_attr_body==3 ? "checked" : ""}>3 <input
						type="radio" name="bean_attr_body" value="4"
						${prodvo.bean_attr_body==4 ? "checked" : ""}>4 <input
						type="radio" name="bean_attr_body" value="5"
						${prodvo.bean_attr_body==5 ? "checked" : ""}>5</td>
				</tr>
				<tr>
					<td>風味-餘味</td>
					<td><input type="radio" name="bean_attr_after" value="1"
						${prodvo.bean_attr_after==1 ? "checked" : ""}>1 <input
						type="radio" name="bean_attr_after" value="2"
						${prodvo.bean_attr_after==2 ? "checked" : ""}>2 <input
						type="radio" name="bean_attr_after" value="3"
						${prodvo.bean_attr_after==3 ? "checked" : ""}>3 <input
						type="radio" name="bean_attr_after" value="4"
						${prodvo.bean_attr_after==4 ? "checked" : ""}>4 <input
						type="radio" name="bean_attr_after" value="5"
						${prodvo.bean_attr_after==5 ? "checked" : ""}>5</td>
				</tr>
				<tr>
					<td>風味-平衡度</td>
					<td><input type="radio" name="bean_attr_bal" value="1"
						${prodvo.bean_attr_bal==1 ? "checked" : ""}>1 <input
						type="radio" name="bean_attr_bal" value="2"
						${prodvo.bean_attr_bal==2 ? "checked" : ""}>2 <input
						type="radio" name="bean_attr_bal" value="3"
						${prodvo.bean_attr_bal==3 ? "checked" : ""}>3 <input
						type="radio" name="bean_attr_bal" value="4"
						${prodvo.bean_attr_bal==4 ? "checked" : ""}>4 <input
						type="radio" name="bean_attr_bal" value="5"
						${prodvo.bean_attr_bal==5 ? "checked" : ""}>5</td>
				</tr>
				<tr>
					<td>香味</td>
					<td><input type="text" name="bean_aroma" class="form-control te"
						value="${prodvo.bean_aroma}" size="35"></td>
				</tr>
				<tr>
					<td>標價 $NT*</td>
					<td><input type="number" name="prod_price" class="form-control te"
						value="${prodvo.prod_price}"></td>
				</tr>
				<tr>
					<td>重量 lb(小數後1位)*</td>
					<td><input type="number" name="prod_wt" step="0.5" class="form-control te"
						value="${prodvo.prod_wt}" ></td>
				</tr>
				<tr>
					<td>運費*</td>
					<td><input type="number" name="send_fee" class="form-control te"
						value="${prodvo.send_fee}"></td>
				</tr>
				<tr>
					<td>供應數量*</td>
					<td><input type="number" name="prod_sup"  class="form-control te"
						value="${prodvo.prod_sup}" ></td>
				</tr>
				<tr>
					<td>商品描述*</td>
					<td><textarea rows="4" name="prod_cont" >${prodvo.prod_cont}</textarea>
					</td>
				</tr>
				<tr>
					<td>商品圖片-1*</td>
					<td>
					<output id="pic1"><img src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodvo.prod_no}&index=1" width="150px"></output>
					<input  class="form-control-file" type="file" name="prod_pic1" id="propic1">
					</td>
				</tr>
				<tr>
					<td>商品圖片-2</td>
					<td><output id="pic2"><img src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodvo.prod_no}&index=2" width="150px"></output>
					<input  class="form-control-file" type="file" name="prod_pic2" id="propic2">
					</td>
				</tr>
				<tr>
					<td>商品圖片-3</td>
					<td><output id="pic3"><img src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodvo.prod_no}&index=3" width="150px"></output>
					<input  class="form-control-file" type="file" name="prod_pic3" id="propic3">
					</td>
				</tr>
				<tr>
					<td>上架狀態</td>
					<td>
						<input type="radio" name="prod_stat" value="下架" ${prodvo.prod_stat.equals("下架") ? "checked" : ""}>下架 
						<input type="radio" name="prod_stat" value="上架" ${prodvo.prod_stat.equals("上架") ? "checked" : ""}>上架 
					</td>
				</tr>
				<tr>
				<td>
						
						<input type="hidden" name="prod_no" value="${prodvo.prod_no}">
						<input type="hidden" name="store_no" value="${storeVO.store_no}">
						<input type="hidden" name="action" value="update_prod">
						<input type="hidden" name="whichPage" value="<%=request.getAttribute("whichPage")%>">
				</td>
				</tr>
			</table>
			<input type="submit" value="修改商品資料" class="btn btn-warning pull-right">
			<input type ="button" onclick="history.back()" value="取消" class="btn btn-danger pull-right mgr10"></input>
			</div>
			</div>
			</FORM>
		</div>
	</div>
</div>
</div>
	
	<script>
	$(".pro_one img:first-child").css(
	    {"width":"70px"}
	)
	
	
function handleFileSelect1(evt) {
	$("#pic1").empty();

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
				document.getElementById('pic1').insertBefore(span, null);
				$(".thumb").width(70);

			};
		})(f);

		// Read in the image file as a data URL.
		reader.readAsDataURL(f);
	}
}
document.getElementById('propic1').addEventListener('change',
		handleFileSelect1, false);

//=======================================================================================

function handleFileSelect2(evt) {
	$("#pic2").empty();

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
				document.getElementById('pic2').insertBefore(span, null);
				$(".thumb").width(70);

			};
		})(f);

		// Read in the image file as a data URL.
		reader.readAsDataURL(f);
	}
}
document.getElementById('propic2').addEventListener('change',
		handleFileSelect2, false);

//=======================================================================================

function handleFileSelect3(evt) {
	$("#pic3").empty();

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
				document.getElementById('pic3').insertBefore(span, null);
				$(".thumb").width(70);
				
			};
		})(f);

		// Read in the image file as a data URL.
		reader.readAsDataURL(f);
	}
}
document.getElementById('propic3').addEventListener('change',
		handleFileSelect3, false);




	</script>

<jsp:include page="/FrontEnd/include/footer.jsp"/>