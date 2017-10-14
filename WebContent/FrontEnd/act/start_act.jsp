
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act.model.*"%>

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
  

<link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
      <%--date picker專用css --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/res/css/bootstrap-datetimepicker.min.css" />
      
<style>




.myrow {
  border: solid 1px black;
  padding: 50px;
  border-radius: 50px;
}

.first {
  color: white;
  background-color: #6F5C60;
}

.circles {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
  width: 100%;
}
.circles .circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: solid 1px black;
}
.circles .circle h2 {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -110%);
}
.circles .line {
  width: 50px;
  border: solid 5px black;
}

.title {
  text-align: center;
}

.left {
  text-align: center;
}
.left .time h2 {
  display: flex;
  justify-content: space-evenly;
}
.left .time input {
  width: 150px;
}
.left .introduce textarea {
  width: 400px;
  height: 250px;
  left: 50%;
  transform: translate(-50%, 0);
}

.right {
  text-align: left;
}
.right .host textarea {
  width: 400px;
  height: 250px;
  left: 50%;
  transform: translate(-50%, 0);
}

h2 {
  display: inline-block;
  font-size: 25px;
}

.next {
  height: 100px;
}
.next button {
  position: absolute;
  right: 20%;
  top: 30%;
}







.left input , .right  .long{
width: 60%;
}
.dollor{
font-size: 24px;
}
.number-spinner{
width: 50%;

}
.minJoin, .maxJoin{

display: flex;
justify-content: left;
align-items: center;
}
.deadline{
font-size: 24px;
width: 40%;
}
.endTime{
display: flex;
justify-content: left;
}

.input-group{
width: 60%;
}
.form_datetime,.deadline{
margin-top: 20px;
transform: translate(-8%,0);
}
.form_datetime{
transform: translate(-15%,0);
}
.myerror{
position: relative;
left: 45%;
transform: translate(-100%,0);
}

.payWay_info{
display: none;
}


.my_area *{
position: relative;
}
.my_area{
 background-color: #eee8e1;
 margin-top: 57px;
}

.fa{
margin-right: 10px;
}


.left .actionLabel .act_tag{
width: 10px;
}

.magical_button{
left: 50%;
transform: translate(-50%,0%);
}


</style>
  


<div class="my_area content">
  
<div class="circles">
  <div class="circle first">
    <h2>1</h2>
  </div>
  <div class="line"></div>
  <div class="circle">
    <h2>2</h2>
  </div>
  <div class="line"></div>
  <div class="circle"> 
    <h2>3</h2>
  </div>
</div>
<h1 class="title"><span class="fa fa-pencil-square-o"></span>填寫活動資訊</h1><button class="btn btn-default magical_button">神奇小按鈕</button>
<c:if test="${not empty errorMsgs}">
	<font color='red'  class="myerror" >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
<form method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
<input type="hidden"  name="act_add_lat"   class="act_add_lat">
<input type="hidden"  name="act_add_lon"   class="act_add_lon">
<input type="hidden"  name="mem_ac"   value="${mem_ac}">


<input type="hidden"  name="action"  value="start_act_to_pg2">
<input type="hidden" name="start_act.jsp"  value=<%=request.getServletPath() %> >
<div class="container">
  <div class="info">
    <div class="row  myrow">
      <div class="col-md-6 left">
      
        <div class="actionName">
          <h2>活動名稱:</h2>
          <input type="text" class="act_name"   name="act_name"  value="${(act_vo==null)?"":act_vo.act_name }"/>
        </div>
        
        <div class="address">
          <h2>活動地點:</h2>
          <input type="text"   class="act_add"  name="act_add"  value="${(act_vo==null)?"":act_vo.act_add }"/>
        </div>
         <div class="price"  >
          <h2>活動費用: NT</h2>
          <input type="text"  class="act_fee" name="act_fee" value="${(act_vo==null)?"":act_vo.act_fee }"/>
          <span  class="dollor">元</span>
        </div>
        <div class="time">
          
        </div>
     <%-- 
        <div class="actionLabel">
          <h2>活動標籤:</h2>
          <input type="text"  name="act_tag" value="${(act_vo==null)?"":act_vo.act_tag }"/>
        </div>
        --%>   
        <div class="actionLabel">
          <h2>活動標籤:</h2>
          <input type="radio"   class="act_tag" name="act_tag"   value="達人教學"/><span>達人教學</span>
          <input type="radio"  class="act_tag" name="act_tag"  value="咖啡課程"/><span>咖啡課程</span>
          <input type="radio"  class="act_tag"  name="act_tag"  value="咖啡店活動"/><span>咖啡店活動</span>
          <input type="radio"  class="act_tag"  name="act_tag"  value="新手教學"/><span>新手教學</span>
          
        </div>
        
        
      </div>
      <div class="col-md-6 right">
       
        <div class="minJoin">
          <h2  class="mimPeople">最低參加人數:</h2>
          <div class="input-group number-spinner">

				<span class="input-group-btn">
					<button  type="button" class="btn btn-default " data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text"  name="min_mem" class="form-control text-center min_mem" value="${(act_vo==null)?"0":act_vo.min_mem }">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default add" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>
        </div>
        <div class="maxJoin">
         <h2  class="maxPeople">最高參加人數:</h2>
          <div class="input-group number-spinner">

				<span class="input-group-btn">
					<button  type="button" class="btn btn-default " data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text"  name="max_mem" class="form-control text-center max_mem" value="${(act_vo==null)?"0":act_vo.max_mem }">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default add" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>
        </div>
        <div class="endTime form-group">
          
           <span class="col-md-2 control-label  deadline">報名截止日期:</span>
                <div class="input-group date form_datetime " data-date="" data-date-format="yyyy-mm-dd hh:ii:00" data-link-field="dtp_input1">
                    <input class="form-control  test dl_date" size="16" type="text"  name="dl_date"    value="${(act_vo==null)?"":dl_date }">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				
        </div>
        <div class="payWay">
          <h2>繳費方式:</h2>
          <input type="radio"   class="payWay" name="pay_way"   value="不須繳費"/><span>不須繳費</span>
          <input type="radio"  class="payWay" name="pay_way"  value="ATM"/><span>ATM</span>
          <input type="radio"  class="payWay"  name="pay_way"  value="信用卡"/><span>信用卡</span>
          <input type="radio"  class="payWay"  name="pay_way"  value="現場繳費"/><span>現場繳費</span>
          <div class="payWay_info">
          <h3>繳費資訊:</h3>
          <textarea class="form-control  introduct" rows="2" cols="20" wrap="hard" placeholder="匯款銀行：彰化銀行 蘆洲分行 戶名：陳建儒 銀行代碼：009 銀行帳號：9832-51-326845-00"  name="act_atm_info">${(act_vo==null)?"":((act_vo.act_atm_info==null)?"":act_vo.act_atm_info)}</textarea>
          </div>
        </div>
        
      </div>
    </div>
  </div>
</div>

<div class="next">
  <button class="btn-success btn-lg  next_step"  type="button">下一步</button>
 
</div>
 </form>
 
</div>
<%--date picker專用js --%> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.js"></script> 
 <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.fr.js"></script>
<%--google map 地圖 --%>
 <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDZqbxURS33Q5XimlMq6it_KanwhInsh0Q">
    </script>

<script>

	
	
	


<%-- 修正日曆會選取到秒數 強制將秒數設成0 --%>
$(".test").change(function(){
var de=$(".test").val();
new_time=  de.substring(0,17)+"00";
$(".test").val(new_time);
})
<%-- 修正日期會跑出秒數以下的部分 --%>
 var dl_date= $(".test").val().substring(0,19);
 $(".test").val(dl_date);



 // 數字的+、-按鈕控制
			$(document).on('click', '.number-spinner button', function () {    
	var btn = $(this),
		oldValue = btn.closest('.number-spinner').find('input').val().trim(),
		newVal = 0;
	
	if (btn.attr('data-dir') == 'up') {
		newVal = parseInt(oldValue) + 1;
	} else {
		if (oldValue > 1) {
			newVal = parseInt(oldValue) - 1;
		} else {
			newVal = 1;
		}
	}
	btn.closest('.number-spinner').find('input').val(newVal);
});
 
			<%--Date picker--%>
			$('.form_datetime').datetimepicker({
			    //language:  'fr',
			    weekStart: 1,
			    todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				forceParse: 0,
			    showMeridian: 1
			});
 
 if(${(act_vo!=null)}){
	 for(var i=1;i<=$(".payWay").length;i++){
		 if($(".payWay").eq(i).val()==("${act_vo.pay_way}")){
			 $(".payWay").eq(i).attr("checked",true);
		 }
		 
		 
	 }
	 for(var i=0;i<=$(".act_tag").length;i++){
		 if($(".act_tag").eq(i).val()==("${act_vo.act_tag}")){
			 $(".act_tag").eq(i).attr("checked",true);
		 }
		 
		 
	 }
	 
 }
	
 <%--由地圖查經緯度--%>

 function getLatLngByAddr($address) {
 	var geocoder = new google.maps.Geocoder(); //定義一個Geocoder物件
 	geocoder.geocode({
 		address : $address
 	}, //設定地址的字串
 	function(results, status) { //callback function
 		if (status == google.maps.GeocoderStatus.OK) { //判斷狀態
 			$lat = results[0].geometry.location.lat();
 			var lat = $lat;
 			var  lat1 = lat.toFixed(11);
 			$(".act_add_lat").val(lat1);
 			$lng = results[0].geometry.location.lng();
 			var lng = $lng;
 			var  lng1 = lng.toFixed(11);
 			$(".act_add_lon").val(lng1);

 		}
 	});
 }
 function compute(){
	 getLatLngByAddr($(".act_add").val());
 }
 
 
$(".next_step").click(function(){
	compute();
	 $(this).parent().parent().submit();
})

$(".next_step").mouseenter(function(){
	compute();
	
})


<%-- --%>
 $(".act_add").change(function(){
	
	 getLatLngByAddr($(".act_add").val());
	 
	 
	 
 })
 
 $(".payWay").change(function(){
	 
	 if($("input[name='pay_way']:checked").val()=="ATM"){
		 $(".payWay_info").css("display","block");
	 }else{
		 $(".payWay_info").css("display","none");
	 }
	 
	 
 })

$(".magical_button").click(function(){
$(".act_name").val("single place coffee lab.專屬咖啡師計畫");
$(".act_add").val("台中市北區一中街167巷3號");
$(".act_fee").val(0);
$(".act_tag").eq(2).attr("checked",true);
$(".min_mem").val(10);
$(".max_mem").val(10);
$(".dl_date").val("2017-10-31 16:00:00");	
$(".payWay").eq(1).attr("checked",true);
	
	
	
})

			</script>
			
<jsp:include page="/FrontEnd/include/footer.jsp"/>
