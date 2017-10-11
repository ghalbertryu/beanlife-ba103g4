
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act.model.*"%>

  
<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
  <%
session.removeAttribute("act_vo");

List<ActVO> my_act_list=null;
System.out.println(session.getAttribute("add_date_query")!=null);
if(session.getAttribute("add_date_query")!=null){
	my_act_list=(List<ActVO>) session.getAttribute("add_date_query");
	
}else{
ActService actSvc=new ActService();
my_act_list=  actSvc.getAll();


}
List<ActVO> list=new ArrayList();
for(int i=0;i<my_act_list.size();i++){
	String act_stat=my_act_list.get(i).getAct_stat();  //1008未整合程式碼，26~35行超過時限未成團狀態改為已流團
	
	ActService actSvc=new ActService();   
	
	long today=new Date().getTime();  
	long dl_date=my_act_list.get(i).getDl_date().getTime();  
	if((!act_stat.equals("已成團")) && (today>dl_date) ){   
		my_act_list.get(i).setAct_stat("已流團");  
		actSvc.updateAct(my_act_list.get(i));  
	}                                                                                                   //1008未整合程式碼，26~35行超過時限未成團狀態改為已流團
	
	
	
	if(act_stat.equals("可報名")|| act_stat.equals("已成團")){
		list.add(my_act_list.get(i));
	}
}



pageContext.setAttribute("list",list);

ActService myActSvc=new ActService();
List<ActVO> mylist=myActSvc.getAll();
pageContext.setAttribute("mylist",list);
%>
 
 
<link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
 <%--date picker專用css --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/res/css/bootstrap-datetimepicker.min.css" />
   
<style type="text/css">




  .carousel{
    width: 100%;
    height: 100%;
    overflow: hidden;  
   
  }
.item img{
<%--    transform:scale(1.3);    --%>

  width: 100%;
  height: 300px; 
  transition: 1s;
   }
  .item img:hover{
  transform:scale(2);
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
 <%--  width: 10%; --%>
  background-color: #6F5C60;
  border-radius: 10px 0 0 10px;
  border: solid 1px black;
}

.carousel-inner>.item>a>img, .carousel-inner>.item>img, .img-responsive, .thumbnail a>img, .thumbnail>img{
height: 300px;
}

.op_date,.ed_date,.control-label ,.date_range,.form-control ,.fa-search{
display: inline-block;
}
.control-label {
flex: 1;
transform: translate(12%,-10%);

padding: 0;
}
.form_date{
flex: 2;
transform: translate(-50%,0);

}
.fa-search{
width: 30px;
transform: translate(-60%,0);
}
.input-group {
height: 30px;
}

.date{
display: inline-block;
}
.titlebarForm .form-group{
flex: 5;
}
.button{
margin-right: -50px;

}
.button button{
margin-left: 0;
}

body .titlebar  .set_action{
margin-left:30px;
padding: 10px 20px;
cursor: pointer;
color: white;
}

body    .set_action:hover{
text-decoration: none;

}
.actionbar{
right: 0%

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
 
  bottom: 30px;
  transition: 0.5s;
  cursor: pointer;
}
.bar{
overflow: hidden;
}

.titlebarForm {
  display: flex;
  background-color: #C2DCCE;
  padding: 10px 50px;
  height: 60px;
}
.titlebarForm select {
  flex: 150px;
  flex-grow: 0;
  flex-shrink: 0;
}
.titlebarForm .form-group {
  flex: 6;
  padding-left: 0%;

width: 100%;
display: flex;
 justify-content: center;
 align-items: center;

}
.titlebarForm .form-group *{

}
.titlebarForm input {
  border-radius: 5px;
}
.titlebarForm .button {
  flex: 3;
  display: flex;
  justify-content: space-around;
}
.titlebarForm .button button {
  padding: 10px 20px;
}

.titleImg {
  padding: 30px;
  width: 80%;
}
.titleImg img {
  width: 100%;
  height: auto;
}

.actionbar {
  padding: 0 120px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
 .actionbar .label {
  height: 30px;
  font-size: 20px;
  font-weight: 100;
  display: inline-block;
  color: black;
  border: solid 1px black;
  background-color: #C2DCCE;
  margin-right: 50px;
  margin-left: 100px;
}
.actionbar .time {
  transform: translate(-10%, 0);
}
.actionbar .quene {
  cursor: pointer;
  width: 100px;
  height: 40px;
}

.card {
  width: 70%;
  height: 290px;
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

.card .detail .dataLocation {
  width: 100%;
  display: flex;
  justify-content: center;
}

.card .detail .info:hover {
  color: #eee;
  background-color: #80BD01;
 font-weight: 900;
}

.fa-search {
  font-size: 20px;
  position: relative;
  left: 2%;
  top: 5%;
  background-color: #eee;
  padding: 5px;
  cursor: pointer;
}
.fa-search:hover {
  background-color: #80BD01;
  color: #eee;
}

.detail  *{
position: relative;
}

.my_area{
 background-color: #C8B6A1;
 margin-top: 57px;
}

.for_act_page1,.for_act_page2{
display: flex;
justify-content: center;


}
.for_act_page1 *,.for_act_page2 *{
font-size: 20px;
font-weight: 600;

}

</style>


  <div class="my_area  content">

  
<div class="container_fluid titlebar">
  <form class="form-inline titlebarForm"  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
  	<input type="hidden" name="action" value="add_date_query"> 
  	<input type="hidden" name="act.jsp"  value="<%=request.getServletPath() %>">
 
    <select class="location"  name="act_add">
      
      <option  value="">全顯示</option>
     <%
     Set<String> set=new LinkedHashSet();
     for(int i=0;i<mylist.size();i++){
    	String text= mylist.get(i).getAct_add();
    	 int index1=text.indexOf("縣");
    	 int index2=text.indexOf("市");
    	if(index1>0){
    		set.add(text.substring(0,index1+1));
    	}else if(index2>0){
    		set.add(text.substring(0,index2+1));
    	}else{
    		set.add(text.substring(0,2));
    	}
 
     }
     for(String act_add: set ){
     %>
      <option value=<%= act_add%>><%= act_add%></option>
    <% } %>
    </select>
    
    <div class="form-group">
    <div class="form-group">
                <label for="dtp_input1" class="col-md-2 control-label">開始時間</label>
                <div class="input-group date form_datetime col-md-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:00" data-link-field="dtp_input1">
                    <input class="form-control  test  myop_date" size="16" type="text"  name="act_op_date"    value=""  ">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" /><br/>
          
                <label for="dtp_input1" class="col-md-2 control-label">結束時間</label>
                <div class="input-group date form_datetime col-md-5" data-date="" data-date-format="yyyy-mm-dd hh:ii:00" data-link-field="dtp_input1">
                    <input class="form-control  test  myed_date" size="16" type="text"  name="act_ed_date"    value="" >
                    <div class="input-group-addon "><span class="glyphicon glyphicon-remove"></span></div>
					<span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
				<input type="hidden" id="dtp_input1" value="" /><br/>
            </div>  
			
                
           
  <span class="fa fa-search"></span>
    </div>
    <button   class="btn-success  set_action"  type="button">發起活動</button>
    <div class="button">
    
      <button class="btn-primary  act_tag"  type="button">達人教學</button>
      <button class="btn-primary  act_tag"  type="button">咖啡課程</button>
      <button class="btn-primary  act_tag"  type="button">咖啡店活動</button>
      <button class="btn-primary   act_tag"  type="button">新手教學</button>
    </div>
  </form>
  <form  class="action_tag_button"  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
  <input type="hidden"  name="action"  value="search_for_actTag">
 <input type="hidden" name="act.jsp" value="<%=request.getServletPath() %>"> 
  </form>
  
  
   <form  class="goto_start_act"  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
  <input type="hidden"  name="action"  value="goto_start_act.jsp">

  </form>
  
  
  
</div>
<%-- 
<div class="container titleImg"><img src="https://macicafedenver.com/wp-content/uploads/2014/10/coffee_slide.jpg" alt=""/></div>
--%>
<div class="actionbar">
  <h3 class="">${(act_tag==null)? "搜尋結果":act_tag }</h3>
  <h3 class="time"><span class="add_display">${act_add}</span><span  class="op_display"></span><span class="into">${(empty act_op_date)?"":"~" }</span></span><span   class="ed_display"></span></h3>
   <form  class="sort_form"   method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
  <select class="quene" name="sort">
    <option  value="">排序方式</option>
    <option value="act_op_date">依時間</option>
    <option value="act_add">依地點</option>
  </select>
  <input type="hidden"  name="act.jsp" value="<%=request.getServletPath() %>">
  <input type="hidden"  name="action" value="sort">
  </form>
  
</div>

<div class="for_act_page1">
  <%@ include file="act_page1.file" %> 
  </div>
 <c:forEach var="act_vo"  items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">

<div class="container card  ">
  <div class="row">
    <div class="col-sm-6 img">
<div id="${act_vo.act_no }" class="carousel slide" data-ride="carousel">
        <!-- 幻燈片小圓點區 -->
        <ol class="carousel-indicators">
            <li data-target="#carousel-id" data-slide-to="0" class=""></li>
            <li data-target="#carousel-id" data-slide-to="1" class=""></li>
            <li data-target="#carousel-id" data-slide-to="2" class="active"></li>
        </ol>
        <!-- 幻燈片主圖區 -->
        <div class="carousel-inner">
            <div class="item">
                <img src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=1" alt="">
                <div class="container">
                    <div class="carousel-caption">
                        
                    </div>
                </div>
            </div>
            <div class="item">
                <img src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=2" alt="">
                <div class="container">
                    <div class="carousel-caption">
                       
                    </div>
                </div>
            </div>
            <div class="item active">
                 <img src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=3" alt="">
                <div class="container">
                    <div class="carousel-caption">
                       
                    </div>
                </div>
            </div>
        </div>
        <!-- 上下頁控制區 -->
        <a class="left carousel-control" href="#${act_vo.act_no }" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
        <a class="right carousel-control" href="#${act_vo.act_no }" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
    </div>

    </div>
    <div class="col-sm-6 detail">
      <h2 class="actionTitle">${act_vo.act_name}</h2>
      <p class="page">${act_vo.act_cont }</p>
     
      <h4>售價: <span>NT$ ${act_vo.act_fee }元 </span><span>招募剩餘<span class="remain_date"></span>天</span></h4>
      <input type="hidden" class="dl_date"  value="${act_vo.dl_date }">
      <div class="bar">
        <div class="joinNumber"  style="width: calc(${act_vo.mem_count }% *  100 /  ${act_vo.min_mem })"></div>
      </div>
      <h3>已招募<span  class="mem_cont">${act_vo.mem_count }</span><span>/</span><span  class="max_mem">${act_vo.min_mem }</span><span>人</span></h3>
      <p class="dataLocation"><span class="act_op_date">${act_vo.act_op_date }<span>舉辦</span> </span><span class="act_add">${act_vo.act_add }</span></p>
      <form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
      <a class="info">詳細資訊</a>
      <input type="hidden" name=action  value="goto_act_detail" >
      <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      <input type="hidden"  name="act.jsp" value="<%=request.getServletPath() %>">
    </form>
    </div>
  </div>
</div>
  </c:forEach>
<div class="for_act_page2">
  <%@ include file="act_page2.file" %> 
  </div>
</div>


 <%--date picker專用js --%> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.js"></script> 
 <script type="text/javascript"  src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap-datetimepicker.fr.js"></script>
<script>

$(".set_action").click(function(){

	if(${mem_ac==null}){
		 $('#modal-login').modal("show");
		 return false;
	}
	
	$(".goto_start_act").submit();
	
})




$(".act_tag").click(function(){
$(".action_tag_button").append("<input type='hidden' name=act_tag value="+$(this).text()+">");
	
$(".action_tag_button").submit();
	
	
	
})





function changeTimeFormate(time){
	
	
	 var new_time=new Date(time.substring(0,4),time.substring(5,7)-1,time.substring(8,10),time.substring(11,13),time.substring(14,16),time.substring(17,19))
	 console.log(new_time);
    var year=new_time.getFullYear();
	 var month=new_time.getMonth()+1;
	 var day=new_time.getDate();
	 var hour=new_time.getHours();
	 if(hour<10){
		 hour="0"+hour;
	 }
	 
	 var minute=new_time.getMinutes();
	 console.log("minute串接前="+minute);
	 if(minute<10){
		 minute="0"+minute;
	 }
	 console.log("minute串接後="+minute);
	 
	 var number_week=new_time.getDay();
	 console.log("number_week= "+number_week);
	 var week;
	 switch(number_week){
	 case 0:
		 week="日";
		 break;
	 case 1:
		 week="一";
		 break;
	 case 2:
		 week="二";
		 break;
	 case 3:
		 week="三";
		 break;
	 case 4:
		 week="四";
		 break;
	 case 5:
		 week="五";
		 break;
	 case 6:
		 week="六";
		 break;
	 }
	 
	 
	 var show_time=year+"-"+month+"-"+day+"("+week+")  "+ hour+":"+minute;
	 
	 console.log("得到的show_time= "+show_time);
	 return show_time;
	 
}
console.log("act_op_date=${act_op_date}");


if(${(!empty act_op_date)}){
	$(".op_display").text(changeTimeFormate("${act_op_date}"));
	
}

if(${(!empty act_ed_date)}){
	$(".ed_display").text(changeTimeFormate("${act_ed_date}"));
	
}



<%-- 活動描述截取50字元顯示 --%>
for(var i=0;i<$(".page").length;i++){
	if($(".page").eq(i).text().length>50){
	
	
var text=	$(".page").eq(i).text().substring(0,50)+"....";
$(".page").eq(i).text(text);
}
}

<%-- 計算截止日扣掉今天還有幾天 --%>
for(var i=0;i<$(".dl_date").length;i++){
	var today=new Date();
	var dl_date_text=$(".dl_date").eq(i).val();
	var dl_date=Date.parse(dl_date_text);
	 var remain_date=Math.floor((dl_date-today)/1000/60/60/24);
	 
	 if(remain_date>0){
	$(".remain_date").eq(i).text(remain_date);
	 }else{
		 $(".remain_date").eq(i).text("0");
	 }
}
<%--裁切地址--%>
for(var i=0;i<$(".act_add").length;i++){
	var index1=$(".act_add").eq(i).text().indexOf("縣");
	var index2=$(".act_add").eq(i).text().indexOf("市");
	if(index2<0){
		var text=$(".act_add").eq(i).text().substring(0,index1+1);
		$(".act_add").eq(i).text(text);
	}else{
	var text=$(".act_add").eq(i).text().substring(0,index2+1);
	$(".act_add").eq(i).text(text);
	}
	
	
	
}

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


$(".fa-search").click(function(){
$(".titlebarForm ").submit();
})

$(".quene").change(function(){
	
	$(".sort_form").submit();
	
	
})


$(".info").click(function(){
$(this).parent().submit();
	
	
})


	<%--裁切日期 去除時分秒--%>
for(var i=0;i<$(".act_op_date").length;i++){
	var op_date=$(".act_op_date").eq(i).text().substring(0,10);
	$(".act_op_date").eq(i).text(op_date);
}
	
<%-- 修正日曆會選取到秒數 強制將秒數設成0 --%>
$(".myop_date").change(function(){
var myop_date=$(".myop_date").val();
new_time=  myop_date.substring(0,17)+"00";
$(".myop_date").val(new_time);
})

$(".myed_date").change(function(){
var myed_date=$(".myed_date").val();
new_time=  myed_date.substring(0,17)+"00";
$(".myed_date").val(new_time);
})

</script>
  

<jsp:include page="/FrontEnd/include/footer.jsp"/>