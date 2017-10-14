
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



.second {
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

h2 {
  display: inline-block;
  font-size: 25px;
}

.next {
  height: 100px;
}
.next .previous {
  position: absolute;
  right: 35%;
  top: 30%;
  
}
.next .end {
  position: absolute;
  right: 25%;
  top: 30%;
}

.card {
  width: 70%;
  height: 350px;
  border: solid 1px black;
  padding: 0;
  margin-bottom: 50px;
  box-shadow: 0px 0px 35px rgba(0, 0, 0, 0.3);
  transition: 0.5s;
  margin-top: 50px;
}
.card:hover {
  box-shadow: 5px 5px 60px rgba(0, 0, 0, 0.4);
  transform: translate(-5px, -5px);
}
.card .row {
  height: 100%;
}
.card .img {
  height: 100%;
}
.card .img .actionImg {
  width: 100%;
  height: 100%;
}
.card .detail {
  text-align: center;
  height: 100%;
  background-color: #C2DCCE;
  margin-left: -15px;
}
.card .detail .actionTitle {
  font-weight: bold;
  margin-top: 30px;
}
.card .detail .page {
  padding: 10px 30px;
  width: 80%;
  text-align: left;
  display: inline-block;
}
.card .detail .bar {
  height: 20px;
  width: 300px;
  border-radius: 10px;
  border: solid 1px black;
  left: 50%;
  right: auto;
  transform: translate(-50%, 0);
}
.card .detail .bar .joinNumber {
  height: 100%;
  width: 100px;
  background-color: #6F5C60;
  border-radius: 10px 0 0 10px;
  border: solid 1px black;
}
.card .detail .dataLocation {
  width: 100%;
  display: flex;
  justify-content: center;
}
.card .detail .info {
  display: inline-block;
  position: absolute;
  right: 2%;
  padding: 10px 25px;
  text-decoration: none;
  color: #C8B6A1;
  font-weight: 900;
  background-color: #6F5C60;
  bottom: 2%;
  transition: 0.5s;
  cursor: pointer;
}
.card .detail .info:hover {
  color: #111;
  background-color: #80BD01;
}

.bottom {
  position: relative;
  left: 50%;
  transform: translate(-50%, 0);
  font-weight: bold;
}




textarea.form-control{
height: 300px;

}
.control-label {
font-size: 24px;
font-weight: 100;
}
.previous{
cursor: pointer;
}
.previous:hover{
text-decoration: none;
}
.memory{
width: 150px;
height: 100px;
}
.myerror{
position: relative;
left: 45%;


}
.my_area{
 width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #eee8e1;
margin-top: 57px;
}
.my_area *{
 position: relative;
}
 body .next .previous{
  color: #eee;
  }
  
  .fa{
margin-right: 10px;
}
  .magical_button{
left: 50%;
transform: translate(-50%,0%);
}
  
</style>


<div class="my_area">

  
<div class="circles">
  <div class="circle first">
    <h2>1</h2>
  </div>
  <div class="line"></div>
  <div class="circle second">
    <h2>2</h2>
  </div>
  <div class="line"></div>
  <div class="circle">
    <h2>3</h2>
  </div>
</div>
<% ActVO act_vo=(ActVO) session.getAttribute("act_vo");  %>
<form  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet" enctype="multipart/form-data">
<input type="hidden" name="start_act2.jsp" value="<%=request.getServletPath() %>">
<input type="hidden"  name="action" value="start_act_to_pg3">


<h1 class="title"><span class="fa fa-pencil-square-o"></span>填寫活動資訊</h1>
<button class="btn btn-default magical_button" type="button">神奇小按鈕</button>
<c:if test="${not empty errorMsgs}">
	<font color='red'  class="myerror" >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>



<div class="container">
  <div class="col-md-12">
 
            
    <div class="actionPic">
          <div class="form-group">
  				  <label for="exampleInputFile"  class="h3">活動照片1</label>
  					  <input type="file" id="myfiles"    name="act_pic1"  value="">
 					 	<output id="mylist">
 					 	
 					 <%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String act_pic1="";
  						  if(act_vo!=null){
 					 	byte[] data=act_vo.getAct_pic1();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						act_pic1 = sb.toString();
  						}else{
  							act_pic1="";
  						}
 						%>	
 					 	<img class="memory"  src="<%=act_pic1 %>">
 					 	</output>
  						</div>
  						 <div class="form-group">
  				  <label for="exampleInputFile"  class="h3">活動照片2</label>
  					  <input type="file" id="myfiles2"  name="act_pic2"  value="">
 					 	<output id="mylist2">
 					 	
 					 	<%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String act_pic2="";
  						  if(act_vo!=null){
 					 	byte[] data=act_vo.getAct_pic2();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						act_pic2 = sb.toString();
  						}else{
  							act_pic2="";
  						}
 						%>	
 					 	<img  class="memory" src="<%=act_pic2 %>">
 					 	</output>
  						</div>
  						 <div class="form-group">
  				  <label for="exampleInputFile"  class="h3">活動照片3</label>
  					  <input type="file" id="myfiles3"  name="act_pic3"  value="">
 					 	<output id="mylist3">
 					 	
 					 	<%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String act_pic3="";
  						  if(act_vo!=null){
 					 	byte[] data=act_vo.getAct_pic3();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						act_pic3 = sb.toString();
  						}else{
  							act_pic3="";
  						}
 						%>	
 					 	<img  class="memory" src="<%=act_pic3 %>">
 					 	</output>
  						</div>
  						
        </div>
    <div class="form-group">
                <label for="dtp_input1" class="col-md-2 control-label  ">活動開始時間</label>
                <div class="input-group date form_datetime col-md-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:00" data-link-field="dtp_input1">
                    <input class="form-control  test act_op_date" size="16" type="text"  name="act_op_date"    value="${(act_vo==null)?"":act_op_date }">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" /><br/>
          
                <label for="dtp_input1" class="col-md-2 control-label ">活動結束時間</label>
                <div class="input-group date form_datetime col-md-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:00" data-link-field="dtp_input1">
                    <input class="form-control  test act_ed_date" size="16" type="text"  name="act_ed_date"    value="${(act_vo==null)?"":act_ed_date }" >
                    <div class="input-group-addon "><span class="glyphicon glyphicon-remove"></span></div>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" /><br/>
            </div>  
            <div class="host">
          <h2>主辦單位簡介</h2>
          <textarea class="form-control host_cont" rows="2" cols="20" wrap="hard" placeholder="介紹一下你自己吧~"   name="org_cont">${(act_vo==null)?"":act_vo.org_cont }</textarea>
        </div>
    <div class="introduce">
          <h2>活動介紹:</h2>
          <textarea class="form-control  introduct" rows="2" cols="20" wrap="hard" placeholder="說明一下這個精彩絕倫的活動~"  name="act_cont">${(act_vo==null)?"":act_vo.act_cont }</textarea>
        </div>
    
    
  </div>
</div>

<div class="next">
  <a class="btn-success btn-lg previous"  href="<%=request.getContextPath() %>/FrontEnd/act/start_act.jsp" >上一步</a>
  <button type="submit"  class="btn-success btn-lg end"><span class="fa fa-smile-o"></span>完成</button>
</div>
</form>
 </div>

  <%--date picker專用js --%> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.js"></script> 
 <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.fr.js"></script>
 
 <script>
 for(var i=0;i<$(".memory").length;i++){
	 if($(".memory").eq(i).attr("src")=="data:image/png;base64,null"){
		 $(".memory").eq(i).css("display","none");
	 }
 }
 
 
 
 <%-- 修正日曆會選取到秒數 強制將秒數設成0 --%>
 $(".act_op_date").change(function(){
 var de=$(".act_op_date").val();
 new_time=  de.substring(0,17)+"00";
 $(".act_op_date").val(new_time);
 })
 
 <%-- 修正日期會跑出秒數以下的部分 --%>
 var act_op_date= $(".act_op_date").val().substring(0,19);
 $(".act_op_date").val(act_op_date);
 
 <%-- 修正日曆會選取到秒數 強制將秒數設成0 --%>
 $(".act_ed_date").change(function(){
 var de=$(".act_ed_date").val();
 new_time=  de.substring(0,17)+"00";
 $(".act_ed_date").val(new_time);
 })
 
 <%-- 修正日期會跑出秒數以下的部分 --%>
 var act_ed_date= $(".act_ed_date").val().substring(0,19);
 $(".act_ed_date").val(act_ed_date);
 
 
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
 
 // 上傳預覽1
	function handleFileSelect(evt) {
		$("#mylist").empty();
		
		
		
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
	          span.innerHTML = ['<img class="thumb" src="', e.target.result,
	                            '" title="', escape(theFile.name), '"/>'].join('');
	          document.getElementById('mylist').insertBefore(span, null);
	          $(".thumb").width(150).height(100);
	                        
	        };
	      })(f);

	      // Read in the image file as a data URL.
	      reader.readAsDataURL(f);
	    }
	  }
	  document.getElementById('myfiles').addEventListener('change', handleFileSelect, false);
 
 
 // 上傳預覽2
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
	          span.innerHTML = ['<img class="thumb" src="', e.target.result,
	                            '" title="', escape(theFile.name), '"/>'].join('');
	          document.getElementById('mylist2').insertBefore(span, null);
	          $(".thumb").width(150).height(100);
	                        
	        };
	      })(f);

	      // Read in the image file as a data URL.
	      reader.readAsDataURL(f);
	    }
	  }
	  document.getElementById('myfiles2').addEventListener('change', handleFileSelect2, false);
	  
	  　 // 上傳預覽3
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
		          span.innerHTML = ['<img class="thumb" src="', e.target.result,
		                            '" title="', escape(theFile.name), '"/>'].join('');
		          document.getElementById('mylist3').insertBefore(span, null);
		          $(".thumb").width(150).height(100);
		                        
		        };
		      })(f);

		      // Read in the image file as a data URL.
		      reader.readAsDataURL(f);
		    }
		  }
		  document.getElementById('myfiles3').addEventListener('change', handleFileSelect3, false);
	  
	  
		  
		  $(".magical_button").click(function(){
			$(".act_op_date").val("2017-11-31 14:00:00");
			$(".act_ed_date").val("2017-12-31 14:00:00");
			  $(".host_cont").text("single place coffee lab. \n 從最單純的地方出發，因為這個信念我們使用單一\n產區精品莊園豆，表現出豆子的特有味道，跳脫以\n往的只主打義式咖啡的框架，增加手沖和摩卡壺，\n讓大家體驗同一款咖啡豆不同的表現～ 帶領大家品\n嚐不同的咖啡風味～");
			  $(".introduct").text('活動介紹  \n single place coffee lab.想做個有趣的計畫，我們想成為大家的《專屬咖啡師》\n，方法是你購買single place的三款配方豆我們為大家沖煮，有半磅跟4/1磅可選擇；\n半磅（225g）的豆子，可沖12杯美式等於一杯只要48元，不但有品質價錢更是優\n惠，還可以擁有自己的咖啡豆，咖啡豆分三個焙度，讓大家更容易了解自己選擇的\n咖啡豆特色。 希望滿足各種不同喜好，以及市場求新求變的需求，提供了最完整的\n服務，讓你無論在辦公、放假、休閒時間，任何時刻都能夠享受一杯好咖啡！ \n \n https://docs.google.com/forms/d/e/1FAIpQLSfOd1ov_H5bdvmA7FmgDSRCkdLl-GAxLXSIKVVTEzd5Xasc4g/viewform \n \n 歡迎公司集體合訂，學生團購，家庭預訂，人人都可以享受一杯好咖啡～');
			                 
			  
			  
			  
			  
		  })
		  
		  
 </script>
 
 

<jsp:include page="/FrontEnd/include/footer.jsp"/>