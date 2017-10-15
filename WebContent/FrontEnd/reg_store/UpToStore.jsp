<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>



<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<%StoreVO storeVO = (StoreVO) request.getAttribute("storeVO"); %>
<style>
textarea {
  resize : none;
  width:340px
}


</style>


<div class="content container mgt-depn-nav">
	
	
<div class="col-sm-2">
	
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
<div class="container">
<div class="col-sm-10">
	<h3>會員申請店家 </h3>
	<button class="new">紐</button>
	<FORM METHOD="POST"
		ACTION="<%=request.getContextPath()%>/store/ToStore.do" name="form1" enctype="multipart/form-data">
		<table class="regstore">
			<caption>
				您好：${mem_ac}</caption>
			<tr>
				<td>店家名稱:</td>
				<td><input type="TEXT" name="store_name" size="45" class="store_name"
					value="" /></td>
			</tr>
			<tr>
				<td>統一編號:</td>
				<td><input type="TEXT" name="tax_id_no" size="45" class="tax_id_no"
					value="" /></td>
			</tr>
			<tr>
				<td>公司電話:</td>
				<td><input type="TEXT" name="store_phone" size="45" pattern="0\d{1,2}-?(\d{1,4})-?(\d{1,4})" placeholder="EX:02-123-456" class="store_phone"
					value="" /></td>
			</tr>
			<tr>
				<td>店家住址:</td>
				<td><input type="TEXT" id="address" name="store_add" size="45" 
					class="add" value="" /></td>
			</tr>
			<tr>
				<td>店家介紹:</td>
				<td><textarea rows="4" cols="50" name="store_cont" class="store_cont"
						placeholder="EX:媽媽嘴曾經發生命案糾咪"></textarea></td>
			</tr>
			<tr>
				<td>匯款資訊：</td>
				<td><textarea rows="4" cols="50" name="store_atm_info" class="store_atm_info"
						placeholder="EX:匯款銀行：彰化銀行 蘆洲分行 
戶名：陳建儒 
銀行代碼：009  
銀行帳號：9832-51-326845-00"></textarea></td>
			</tr>

			<tr>
				<td>免運費金額:</td>
				<td><input type="number" name="store_free_ship" size="45" min="0" step="1" class="store_free_ship"
					value="" /></td>
			</tr>
	
			<tr>
				<td>證件照:</td>
				<td><input type="file" name="win_id_pic" id="idpic1"></td>
				<td><output id="mylist1">
				<%
				String win_id_pic="";
				if(storeVO!=null){
					byte[] data=storeVO.getWin_id_pic();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					win_id_pic = sb.toString();
				}else{
					win_id_pic="";
				}
				%>
				<img src="<%=win_id_pic %>" width="150px">
				</output></td>
			</tr>
			<tr>
				<td>店家照1:</td>
				<td><input type="file" name="store_pic1" id="stpic1" ></td>
				<td><output id="mylist2">
				<%
				String store_pic1="";
				if(storeVO!=null){
					byte[] data=storeVO.getStore_pic1();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					store_pic1 = sb.toString();
				}else{
					store_pic1="";
				}
				%>
				<img src="<%=store_pic1 %>" width="150px">
				</output></td>
			</tr>
			<tr>
				<td>店家照2:</td>
				<td><input type="file" name="store_pic2" id="stpic2"></td>
				<td><output id="mylist3">
				<%
				String store_pic2="";
				if(storeVO!=null){
					byte[] data=storeVO.getStore_pic2();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					store_pic2 = sb.toString();
				}else{
					store_pic2="";
				}
				%>
				<img src="<%=store_pic2 %>" width="150px">
				</output></td>
			</tr>
			<tr>
				<td>店家照3:</td>
				<td><input type="file" name="store_pic3" id="stpic3"></td>
				<td><output id="mylist4">
				<%
				String store_pic3="";
				if(storeVO!=null){
					byte[] data=storeVO.getStore_pic3();
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
					store_pic3 = sb.toString();
				}else{
					store_pic3="";
				}
				%>
				<img src="<%=store_pic3 %>" width="150px">
				</output></td>
			</tr>
			
		</table>
		<br> <input type="hidden" name="action" value="insert"> 
		<input	type="hidden" name="mem_ac" value="${mem_ac}"> 
		<input	type="hidden" name="store_add_lat" id="lat" size="45" value="${storeVO.store_add_lat}" class="store_add_lat"/>
		<input	type="hidden" name="store_add_lon" id="lng" size="45" value="${storeVO.store_add_lon}" class="store_add_lon"/> 
		<input type ="button" onclick="history.back()" value="取消" class="btn-info"></input>　　　　　<input	type="submit" value="送出新增"  class="btn-info"/>
	</FORM>

</div>
</div>
</div>














<!-- --------------------------------------------------------------------------------------------------------------- -->

<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCLrfGty4UTt0tx-W-iI8kvMI07T7vgapc">
</script>
<script>

	$(function() {
		function getLatLngByAddr($address) {
			var geocoder = new google.maps.Geocoder(); //定義一個Geocoder物件
			geocoder.geocode({
				address : $address
			}, //設定地址的字串
			function(results, status) { //callback function
				if (status == google.maps.GeocoderStatus.OK) { //判斷狀態
					$lat = results[0].geometry.location.lat();
					var lat = $lat;
					var lat1 = lat.toFixed(11);
					$("#lat").val(lat1);
					$lng = results[0].geometry.location.lng();
					var lng = $lng;
					var lng1 = lng.toFixed(11);
					$("#lng").val(lng1);

				}
			});
		}
		$("#address").focusout(function() { //地址查詢經緯度
			$address = $("#address").val();
			getLatLngByAddr($address);

		});
	});

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
	document.getElementById('idpic1').addEventListener('change',
			handleFileSelect1, false);

	//=======================================================================================

	function handleFileSelect2(evt) {
		$("#mylist2").empty();

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
					document.getElementById('mylist2').insertBefore(span, null);
					$(".thumb").width(150);

				};
			})(f);

			// Read in the image file as a data URL.
			reader.readAsDataURL(f);
		}
	}
	document.getElementById('stpic1').addEventListener('change',
			handleFileSelect2, false);

	//=======================================================================================

	function handleFileSelect3(evt) {
		$("#mylist3").empty();

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
					document.getElementById('mylist3').insertBefore(span, null);
					$(".thumb").width(150);

				};
			})(f);

			// Read in the image file as a data URL.
			reader.readAsDataURL(f);
		}
	}
	document.getElementById('stpic2').addEventListener('change',
			handleFileSelect3, false);

	//=======================================================================================

	function handleFileSelect4(evt) {
		$("#mylist4").empty();

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
					document.getElementById('mylist4').insertBefore(span, null);
					$(".thumb").width(150);

				};
			})(f);

			// Read in the image file as a data URL.
			reader.readAsDataURL(f);
		}
	}
	document.getElementById('stpic3').addEventListener('change',
			handleFileSelect4, false);

	$(".new").click(function(){
		$(".store_name").val("爸爸嘴咖啡");
		$(".tax_id_no").val("42630352");
		$(".store_phone").val("02-2297-6825")
		$(".add").val("新北市新莊區明安西路205號");
		$(".store_cont").val("爸爸嘴是一群愛喝咖啡的同好們合作的一間咖啡工作室，將好咖啡帶入生活中，讓每個愛好者與爸爸嘴共享 CAFE IS LIFE!");
		$(".store_atm_info").val("匯款銀行：彰化銀行 蘆洲分行\n戶名：陳建儒\n銀行代碼：009\n銀行帳號：9832-51-326845-00");
		$(".store_free_ship").val("100");
		$(".store_add_lat").val("25.01501500000");
		$(".store_add_lon").val("121.42613890000");
	})
</script>

<jsp:include page="/FrontEnd/include/footer.jsp"/>