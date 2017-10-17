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
	ProdVO prodVO = (ProdVO) request.getAttribute("prodVO");
%>
<style>
.te{
width:340px
}
</style>

<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-10 col-sm-offset-1">
			<h3 class="bold">新增商品</h3>
	
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
	
		<button class="new  btn-xs btn-default">P1</button><button class="new2  btn-xs btn-default">P2</button>
			<FORM METHOD="POST" ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do" name="form1"	enctype="multipart/form-data">
		<div class="product col-sm-9">
			<div class="table-responsive">       
				<table class="table addpro">
					
				
				<tr>
					<th class="col-sm-5">商品名稱*</th>
					<td><input type="text" name="prod_name" class="form-control prod_name te"
						value="${prodVO.prod_name}"size="35"></td>
				</tr>
				<tr>
					<td>豆種</td>
					<td><input type="text" name="bean_type" class="form-control bean_type te"
						value="${prodVO.bean_type}" size="35"></td>
				</tr>
				<tr>
					<td>生豆等級*</td>
					<td><input type="radio" name="bean_grade" value="G1" ${(prodVO.bean_grade==("G1")) ? "checked" : ""} class="bean_grade">G1 
						<input type="radio" name="bean_grade" value="G2" ${(prodVO.bean_grade==("G2")) ? "checked" : ""} class="bean_grade">G2 
						<input type="radio" name="bean_grade" value="G3" ${(prodVO.bean_grade==("G3")) ? "checked" : ""} class="bean_grade">G3 
						<input type="radio" name="bean_grade" value="G4" ${(prodVO.bean_grade==("G4")) ? "checked" : ""} class="bean_grade">G4 
						<input type="radio" name="bean_grade" value="G5" ${(prodVO.bean_grade==("G5")) ? "checked" : ""} class="bean_grade">G5</td>
				</tr>
				<tr>
					<td>生產國*</td>
					<td><input type="text" name="bean_contry" class="form-control bean_contry te"
						value="${prodVO.bean_contry}"></td>
				</tr>
				<tr>
					<td>地區</td>
					<td><input type="text" name="bean_region" class="form-control bean_region te"
						value="${prodVO.bean_region}"></td>
				</tr>
				<tr>
					<td>農場</td>
					<td><input type="text" name="bean_farm" class="form-control bean_farm te"
						value="${prodVO.bean_farm}"></td>
				</tr>
				<tr>
					<td>生產者</td>
					<td><input type="text" name="bean_farmer" class="form-control bean_farmer te"
						value="${prodVO.bean_farmer}"></td>
				</tr>
				<tr>
					<td>海拔</td>
					<td><input type="number" name="bean_el" class="form-control bean_el te"
						value="${prodVO.bean_el}"></td>
				</tr>
				<tr>
					<td>處理法*</td>
					<td><select size="1" name="proc"  class="form-control" >
							<option value="日曬" ${(prodVO.proc==("日曬")) ? "SELECTED" : ""} class="proc">日曬</option>
							<option value="半水洗" ${(prodVO.proc==("半水洗")) ? "SELECTED" : ""} class="proc">半水洗</option>
							<option value="水洗" ${(prodVO.proc==("水洗")) ? "SELECTED" : ""} class="proc">水洗</option>
							<option value="蜜處理" ${(prodVO.proc==("蜜處理")) ? "SELECTED" : ""} class="proc">蜜處理</option>
					</select></td>
				</tr>

				<tr>
					<td>烘焙度*</td>
					<td><select size="1" name="roast" class="form-control">
							<option value="0" ${(prodVO.roast==("0")) ? "SELECTED" : ""} class="roast">極淺焙</option>
							<option value="1" ${(prodVO.roast==("1")) ? "SELECTED" : ""} class="roast">淺焙</option>
							<option value="2" ${(prodVO.roast==("2")) ? "SELECTED" : ""} class="roast">中焙</option>
							<option value="3" ${(prodVO.roast==("3")) ? "SELECTED" : ""} class="roast">中深焙</option>
							<option value="4" ${(prodVO.roast==("4")) ? "SELECTED" : ""} class="roast">城市烘焙</option>
							<option value="5" ${(prodVO.roast==("5")) ? "SELECTED" : ""} class="roast">深焙</option>
							<option value="6" ${(prodVO.roast==("6")) ? "SELECTED" : ""} class="roast">法式烘焙</option>
							<option value="7" ${(prodVO.roast==("7")) ? "SELECTED" : ""} class="roast">重焙</option>
					</select></td>
				</tr>
				<tr>
					<td>風味-酸度*</td>
					<td><input type="radio" name="bean_attr_acid" value="1" ${(prodVO.bean_attr_acid==1) ? "checked" : ""} class="bean_attr_acid">1 
					<input type="radio" name="bean_attr_acid" value="2" ${(prodVO.bean_attr_acid==2) ? "checked" : ""} class="bean_attr_acid">2 
					<input type="radio" name="bean_attr_acid" value="3" ${(prodVO.bean_attr_acid==3) ? "checked" : ""} class="bean_attr_acid">3 
					<input type="radio" name="bean_attr_acid" value="4" ${(prodVO.bean_attr_acid==4) ? "checked" : ""} class="bean_attr_acid">4 
					<input type="radio" name="bean_attr_acid" value="5" ${(prodVO.bean_attr_acid==5) ? "checked" : ""} class="bean_attr_acid">5
					</td>
				</tr>
				<tr>
					<td>風味-香氣*</td>
					<td><input type="radio" name="bean_attr_aroma" value="1" ${(prodVO.bean_attr_aroma==1) ? "checked" : ""} class="bean_attr_aroma">1 
					<input type="radio" name="bean_attr_aroma" value="2" ${(prodVO.bean_attr_aroma==2) ? "checked" : ""}  class="bean_attr_aroma">2 
					<input type="radio" name="bean_attr_aroma" value="3"${(prodVO.bean_attr_aroma==3) ? "checked" : ""}  class="bean_attr_aroma">3 
					<input type="radio" name="bean_attr_aroma" value="4" ${(prodVO.bean_attr_aroma==4) ? "checked" : ""}  class="bean_attr_aroma">4 
					<input type="radio" name="bean_attr_aroma" value="5" ${(prodVO.bean_attr_aroma==5) ? "checked" : ""}  class="bean_attr_aroma">5
					</td>
				</tr>
				<tr>
					<td>風味-醇度*</td>
					<td><input type="radio" name="bean_attr_body" value="1" ${(prodVO.bean_attr_body==1) ? "checked" : ""} class="bean_attr_body">1 
					<input type="radio" name="bean_attr_body" value="2" ${(prodVO.bean_attr_body==2) ? "checked" : ""} class="bean_attr_body">2 
					<input type="radio" name="bean_attr_body" value="3"	${(prodVO.bean_attr_body==3) ? "checked" : ""} class="bean_attr_body">3 
					<input type="radio" name="bean_attr_body" value="4" ${(prodVO.bean_attr_body==4) ? "checked" : ""} class="bean_attr_body">4 
					<input type="radio" name="bean_attr_body" value="5" ${(prodVO.bean_attr_body==5) ? "checked" : ""} class="bean_attr_body">5
					</td>
				</tr>
				<tr>
					<td>風味-餘味*</td>
					<td><input type="radio" name="bean_attr_after" value="1" ${(prodVO.bean_attr_after==1) ? "checked" : ""} class="bean_attr_after">1 
						<input type="radio" name="bean_attr_after" value="2" ${(prodVO.bean_attr_after==2) ? "checked" : ""} class="bean_attr_after">2 
						<input type="radio" name="bean_attr_after" value="3" ${(prodVO.bean_attr_after==3) ? "checked" : ""} class="bean_attr_after">3 
						<input type="radio" name="bean_attr_after" value="4" ${(prodVO.bean_attr_after==4) ? "checked" : ""} class="bean_attr_after">4 
						<input type="radio" name="bean_attr_after" value="5" ${(prodVO.bean_attr_after==5) ? "checked" : ""} class="bean_attr_after">5
						</td>
				</tr>
				<tr>
					<td>風味-平衡度*</td>
					<td><input type="radio" name="bean_attr_bal" value="1" ${(prodVO.bean_attr_bal==1) ? "checked" : ""} class="bean_attr_bal">1 
						<input type="radio" name="bean_attr_bal" value="2" ${(prodVO.bean_attr_bal==2) ? "checked" : ""} class="bean_attr_bal">2 
						<input type="radio" name="bean_attr_bal" value="3" ${(prodVO.bean_attr_bal==3) ? "checked" : ""} class="bean_attr_bal">3 
						<input type="radio" name="bean_attr_bal" value="4" ${(prodVO.bean_attr_bal==4) ? "checked" : ""} class="bean_attr_bal">4 
						<input type="radio" name="bean_attr_bal" value="5" ${(prodVO.bean_attr_bal==5) ? "checked" : ""} class="bean_attr_bal">5
						</td>
				</tr>
				<tr>
					<td>香味</td>
					<td><input type="text" name="bean_aroma" class="form-control bean_aroma te"
						value="${prodVO.bean_aroma}" size="35"></td>
				</tr>
				<tr>
					<td>標價 $NT*</td>
					<td><input type="number" name="prod_price" min="0" class="form-control prod_price te"
						value="${prodVO.prod_price}"></td>
				</tr>
				<tr>
					<td>重量 lb(小數後1位)*</td>
					<td><input type="number" name="prod_wt" value="${prodVO.prod_wt}" step="0.5" min="0" class="form-control prod_wt te"></td>
				</tr>
				<tr>
					<td>運費*</td>
					<td><input type="number" name="send_fee" min="0" class="form-control send_fee te"
						value="${prodVO.send_fee}"></td>
				</tr>
				<tr>
					<td>供應數量*</td>
					<td><input type="number" name="prod_sup"value="${prodVO.prod_sup}" min="0" class="form-control prod_sup te"></td>
				</tr>
				<tr>
					<td>商品描述*</td>
					<td><textarea rows="4" name="prod_cont" class="form-control prod_cont te">${prodVO.prod_cont}</textarea>
					</td>
				</tr>
				<tr>
					<td>商品圖片-1*</td>
					<td><output id="pic1">
					
					<%
					String prod_pic1="";
					if(prodVO!=null){
					byte[] data=prodVO.getProd_pic1();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					prod_pic1 = sb.toString();
					}else{
					prod_pic1="";
					}
					%>
					<img src="<%=prod_pic1 %>" width="150px">
					</output>
					<input class="form-control-file" type="file" name="prod_pic1" id="propic1"></td>
				</tr>
				<tr>
					<td>商品圖片-2</td>
					<td><output id="pic2">
					<%
					String prod_pic2="";
					if(prodVO!=null){
					byte[] data=prodVO.getProd_pic2();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					prod_pic2 = sb.toString();
					}else{
						prod_pic2="";
					}
					%>
					<img src="<%=prod_pic2 %>" width="150px">
					
					</output><input class="form-control-file" type="file" name="prod_pic2" id="propic2"></td>
				</tr>
				<tr>
					<td>商品圖片-3</td>
					<td><output id="pic3">
					<%
					String prod_pic3="";
					if(prodVO!=null){
					byte[] data=prodVO.getProd_pic3();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					prod_pic3 = sb.toString();
					}else{
						prod_pic3="";
					}
					%>
					<img src="<%=prod_pic3 %>" width="150px">
					
					</output><input class="form-control-file" type="file" name="prod_pic3" id="propic3"></td>
				</tr>
				<tr>
					<td>上架狀態${prodVO.prod_stat }</td>
					<td>
						<input type="radio" name="prod_stat" value="下架" ${(prodVO.prod_stat==("下架")) ? "checked" : ""} class="prod_stat">下架 
						<input type="radio" name="prod_stat" value="上架" ${(prodVO.prod_stat==("上架")) ? "checked" : ""} class="prod_stat">上架 
					</td>
				</tr>
				　　　
				　　　
			 </table>
			 <input type="submit" value="送出新增" class="btn btn-primary pull-right">
				<input type ="button" onclick="history.back()" value="取消" class="btn btn-danger pull-right mgr10"></input>　
			 </div>
			 </div>
				<input type="hidden" name="action" value="insert">
			 	<input type="hidden" name="prod_no" value="${prodvo.prod_no}">
				<input type="hidden" name="store_no" value="${storeVO.store_no}">
			</FORM>
				
		</div>
	</div>
</div>
</div>
<script src="<%=request.getContextPath()%>/FrontEnd/res/js/sorttable.js"></script>
<script>
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
				$(".thumb").width(150);

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
				$(".thumb").width(150);

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
				$(".thumb").width(150);
				
			};
		})(f);

		// Read in the image file as a data URL.
		reader.readAsDataURL(f);
	}
}
document.getElementById('propic3').addEventListener('change',
		handleFileSelect3, false);

$(".new").click(function(){
	$(".prod_name").val("古吉Guji 凱勇山 咖啡豆");
	$(".bean_type").val("哥倫比亞原生種 Ethiopian");
	$(".bean_grade").eq(4).attr("checked",true);
	$(".bean_contry").val("哥倫比亞");
	$(".bean_region").val("烏拉科");
	$(".bean_farm").val("亞達農場");
	$(".bean_farmer").val("阿古力小農家");
	$(".bean_el").val("1250");
	$(".proc").eq(2).attr("selected",true);
	$(".roast").eq(3).attr("selected",true);
	$(".bean_attr_acid").eq(4).attr("checked",true);
	$(".bean_attr_aroma").eq(3).attr("checked",true);
	$(".bean_attr_body").eq(2).attr("checked",true);
	$(".bean_attr_after").eq(4).attr("checked",true);
	$(".bean_attr_bal").eq(4).attr("checked",true);
	$(".bean_aroma").val("水果香味");
	$(".prod_price").val("399");
	$(".prod_wt").val("1");
	$(".send_fee").val("120");
	$(".prod_sup").val("99");
	$(".prod_cont").val("哥倫比亞是世界上的咖啡生產大國，愛喝咖啡的你一定知道，主要的咖啡方式都是以水洗處理方式，因為位於南美多樣化的生態，也是咖啡豆適合生長的位置，而這次的 Supremo 咖啡豆單品則是哥倫比亞咖啡豆中等級最高級 17 目以上的咖啡豆，品嘗之餘也感謝這個世界給我們一個這麼優良的環境讓咖啡豆如此美味！");
	$(".prod_stat").eq(1).attr("checked",true);
})

$(".new2").click(function(){
	$(".prod_name").val("卡杜艾 AA 巴拉圭 咖啡豆");
	$(".bean_type").val("巴拉圭原生種 Ethiopian");
	$(".bean_grade").eq(4).attr("checked",true);
	$(".bean_contry").val("台灣");
	$(".bean_region").val("大溪");
	$(".bean_farm").val("新埔農場");
	$(".bean_farmer").val("尾小寶");
	$(".bean_el").val("800");
	$(".proc").eq(1).attr("selected",true);
	$(".roast").eq(6).attr("selected",true);
	$(".bean_attr_acid").eq(4).attr("checked",true);
	$(".bean_attr_aroma").eq(2).attr("checked",true);
	$(".bean_attr_body").eq(4).attr("checked",true);
	$(".bean_attr_after").eq(1).attr("checked",true);
	$(".bean_attr_bal").eq(2).attr("checked",true);
	$(".bean_aroma").val("鳳梨香味");
	$(".prod_price").val("499");
	$(".prod_wt").val("1");
	$(".send_fee").val("120");
	$(".prod_sup").val("99");
	$(".prod_cont").val("以往有人提到巴黎，就會想到時尚、名牌及咖啡，但在地狹人稠的台灣，以驚人的"+
			"速度成長咖啡店，在星巴克進駐台灣之後，更是大規模的開始開店，現在台灣的咖啡館密度已經超"+
			"越巴黎。也被殖民過的台灣，也曾經種植咖啡，但一直不是台灣主力生產的作物，直到近五年在"+
			"各種資金以及科學化的做法，讓我們的咖啡也漸漸在世界上嶄露頭角，但因為台灣的種植面積不高且人工昂貴，價格普遍較高，較多的資深玩家才會飲用。");
	$(".prod_stat").eq(1).attr("checked",true);
})

</script>

<jsp:include page="/FrontEnd/include/footer.jsp"/>