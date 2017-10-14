<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.fo_act.model.*"%>
<%@ page import="com.act_pair.model.*"%>



<%--
String mem_ac=(String) session.getAttribute("mem_ac");
if(mem_ac==null){
	mem_ac="mamabeak";
}
pageContext.setAttribute("mem_ac",mem_ac);

--%>
<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>

<%



ActService actSvc=new ActService();
List<ActVO> act_list= actSvc.getAll();
pageContext.setAttribute("act_list",act_list);

Fo_actService fo_actSvc=new Fo_actService();
List<Fo_actVO> fo_act_list=fo_actSvc.getAll();
pageContext.setAttribute("fo_act_list",fo_act_list);

Act_pairService act_pairSvc=new Act_pairService();
List<Act_pairVO> act_pair_list=act_pairSvc.getAll();
pageContext.setAttribute("act_pair_list",act_pair_list);


%>

<link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
<style type="text/css">

.myAction{
	font-weight: bold;
	margin-bottom: 30px;
}


.myJoin>tbody>tr>td{
	vertical-align: middle;
	

	text-align: center;
}
.myJoin>tbody>tr>th{
	text-align: center;
width: 6%;
}
.myJoin>tbody>tr>.large{
	width: 8%;
}

.myHeld>tbody>tr>td{
	vertical-align: middle;
	

	text-align: center;
}
.myHeld>tbody>tr>th{
	text-align: center;
width: calc(100% / 11);
}
.myCollection>tbody>tr>td{
	vertical-align: middle;
	

	text-align: center;
}
.myCollection>tbody>tr>th{
	text-align: center;
width: 10%;
}
.myCollection>tbody>tr>.large{
	width: 10%;
}
.goto_detail,.display_act_pair{
cursor: pointer;
}

.goto_detail{
color: #4da6ff;
font-weight: 900;
}

.confirm_modify_act .modify_act{
display: none;
}
.my_act_all{
width: 110%;

}

.my_area{
margin-top: 57px;
}

.host_mem_count{
color: #4da6ff;
font-weight: 900;
}

.fa{
margin-right: 10px;
}
.small_title{
font-size: 18px;
font-weight: 800;

}
</style>

		<div class="container  content  my_area">
			<div class="row">
				<div class="col-md-12  my_act_all">
					<h1 class="myAction"><span class="fa fa-futbol-o"></span>個人活動
					<input type="hidden" value=${mem_ac }>
					</h1>
					<div role="tabpanel">
					    <!-- 標籤面板：標籤區 -->
					    <ul class="nav nav-tabs" role="tablist">
					        <li role="presentation" class="active">
					            <a href="#tab1"  class="small_title"  aria-controls="tab1" role="tab" data-toggle="tab"><span class="fa fa-credit-card-alt"></span>我參加的活動</a>
					        </li>
					        <li role="presentation">
					            <a href="#tab2"   class="small_title"  aria-controls="tab2" role="tab" data-toggle="tab"><span class="fa fa-address-book"></span>我舉辦的活動</a>
					        </li>
					        <li role="presentation">
					            <a href="#tab3"  class="small_title"  aria-controls="tab3" role="tab" data-toggle="tab"><span class="fa fa-list"></span>我收藏的活動</a>
					        </li>
					    </ul>
					
					    <!-- 標籤面板：內容區 -->
					    <div class="tab-content">
					        <div role="tabpanel" class="tab-pane active" id="tab1">
					     <table class="table table-striped myJoin">
					     	<tr>
					     		<th class=large>活動編號</th>
					     		<th>主辦會員</th>
					     		<th class=large>活動名稱</th>
					     		<th>參加人數</th>
					     		<th class=large>活動開始時間</th>
					     		<th class=large>活動結束時間</th>
					     		<th class=large>活動地點</th>
					     		
					     		<th>活動費用</th>
					     		<th class=large>活動照片</th>
					     		<th>活動狀態</th>
					     		<th>繳費狀態</th>
					     		<th>編輯</th>
					     		<th>通知已繳費</th>
					     	</tr>
					     	
					     	
					     		<c:forEach var="act_pair_vo" items="${act_pair_list}"  >
					     		<c:if test="${act_pair_vo.mem_ac==mem_ac}">
					     		<c:forEach var="act_vo" items="${act_list}"  >
					     		<c:if test="${act_pair_vo.act_no == act_vo.act_no}">
					     	<tr>
					     		<td>
					     		<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<a class="goto_detail"  title="查看活動詳情">${act_vo.act_no }</a>
					     		 <input type="hidden" name=action  value="goto_act_detail" >
     							 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      							<input type="hidden"  name="act.jsp" value="<%=request.getServletPath() %>"><%--為了重複使用Act_management的goto_act_detail區塊，沿用act.jsp名稱 --%>
					     		
					     		
					     		</form>
					     		</td>
					     		<td>${act_vo.mem_ac }</td>
					     		<td>${act_vo.act_name }</td>
					     		<td>${act_vo.mem_count }</td>
					     		<td>${act_vo.act_op_date }</td>
					     		<td>${act_vo.act_ed_date }</td>
					     		<td>${act_vo.act_add }</td>
					     		
					     		<td>${act_vo.act_fee }</td>
					     		<td><img class="img-responsive" src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=1" ></td>
					     		<td class="join_stat">${act_vo.act_stat }</td>
					     		<td>${act_pair_vo.pay_state }</td>
					     		<td>
					     		<div class="full_notice"></div>
					     		<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<button class="btn btn-danger  cancel_join_thisAction"><span class="fa fa-frown-o"></span>取消報名</button>
					     		 <input type="hidden" name=action  value="cancel_join" >
     							 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      							<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
      							<input type="hidden"  name="mem_ac"  value="${mem_ac }">
					     		</form>
					     		</td>
					     		<td>
					     		<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<button class="btn btn-success"><span class="fa fa-envelope"></span>通知已繳費</button>
					     		<input type="hidden" name=action  value="notice_pay" >
					     		 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      							<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
      							<input type="hidden"  name="mem_ac"  value="${mem_ac }">
      							<input type="hidden"  name="pay_state"  value="${act_pair_vo.pay_state}">
      									</form>
					     		</td>
					     	</tr>
					     	</c:if>
					     	</c:forEach>
					     	</c:if>
					     	</c:forEach>

					     </table>
					
					
					
					
					
					
					
					        </div>
					        <div role="tabpanel" class="tab-pane" id="tab2"><table class="table table-striped myHeld">
					     	<tr>
					     		<th>活動編號</th>
					     		<th>活動名稱</th>
					     		<th>最低參加人數</th>
					     		<th>最高參加人數</th>
					     		<th>目前參加人數</th>
					     		<th>開始時間</th>
					     		<th>結束時間</th>
					     		<th>活動狀態</th>
					     		<th>審核不通過原因</th>
					     		<th>更改審核狀態日期</th>
								<th class="edit">編輯</th>
								
					     	</tr>
					     	<c:forEach var="act_vo" items="${act_list}"  >
					     	<c:if test="${act_vo.mem_ac==mem_ac}">
					     	<tr>
					     		<td>
					     		<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<a class="goto_detail"  title="查看活動詳情">${act_vo.act_no }</a>
					     		 <input type="hidden" name=action  value="goto_act_detail" >
     							 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      							<input type="hidden"  name="act.jsp" value="<%=request.getServletPath() %>"><%--為了重複使用Act_management的goto_act_detail區塊，沿用act.jsp名稱 --%>
					     		
					     		
					     		</form>
					     		</td>
					     		<td>${act_vo.act_name }</td>
					     		<td class="host_min_mem">${act_vo.min_mem }</td>
					     		<td>${act_vo.max_mem }</td>
					     		<td>
					     		<form  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet" >
					     		<a class="display_act_pair host_mem_count"  title="查看已參加會員">${act_vo.mem_count }</a>
					     		<c:forEach var="act_pair_vo" items="${act_pair_list}"  >
					     		<c:if test="${act_vo.act_no==act_pair_vo.act_no}">
					     		<input type="hidden"  name="act_no" value="${act_pair_vo.act_no}">
					     		<input type="hidden"  name="mem_ac" value="${act_pair_vo.mem_ac}">
					     		
					     		
					     		</c:if>
					     		</c:forEach>
					     		
					     		<input type="hidden"  name="action" value="display_act_pair">
					     		<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
					     		</form>
					     		</td>
					     		<td>${act_vo.act_op_date }</td>
					     		<td>${act_vo.act_ed_date }</td>
					     		<td  class="act_stat">${act_vo.act_stat }</td>
					     		<td>${(act_vo.re_cont==null)?"無":act_vo.re_cont }</td>
					     		<td>${act_vo.review_ed_date }</td>
					     		<td >
					     		<div class="cancel_action"></div>
					     		<form  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet" >
					     		<button class="btn btn-danger  dismiss_act"><span class="fa fa-frown-o"></span>取消活動</button>
					     		<input type="hidden"  name="action" value="delete_act">
					     		<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
					     		<input type="hidden"  name="act_no" value="${act_vo.act_no}">
					     		
					     		<c:forEach var="act_pair_vo" items="${act_pair_list}"  >
					     		<c:if test="${act_vo.act_no==act_pair_vo.act_no}">
					     	
					     		<input type="hidden"  name="mem_ac" value="${act_pair_vo.mem_ac}">
					     		
					     		
					     		</c:if>
					     		</c:forEach>
					     		</form>
					     		<form  class="confirm_modify_act" method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet" >
					     		<button class="btn btn-success modify_act"><span class="fa fa-repeat"></span>修正活動</button>
					     		<input type="hidden"  name="action" value="modify_act">
					     		<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
					     		<input type="hidden"  name="act_no" value="${act_vo.act_no}">
					     		</form>
					     		</td>
					     		
					     	</tr>
					     	</c:if>
</c:forEach>
					     </table>






					        </div>
					        <div role="tabpanel" class="tab-pane" id="tab3"> <table class="table table-striped myCollection">
					     	<tr>
					     		<th class=large>活動編號</th>
					     		<th>主辦會員</th>
					     		<th class=large>活動名稱</th>
					     		
					     		<th class=large>活動開始時間</th>
					     		<th class=large>活動結束時間</th>
					     		<th class=large>活動地點</th>
					     		
					     		<th>活動費用</th>
					     		<th class=large>活動照片</th>
					     		<th>活動狀態</th>
					     		<th>加入收藏日期</th>
					     		<th>編輯</th>
					     	</tr>
					     	<c:forEach var="fo_act_vo" items="${fo_act_list}"  >
					     		<c:if test="${fo_act_vo.mem_ac==mem_ac}">
					     		<c:forEach var="act_vo" items="${act_list}"  >
					     		<c:if test="${fo_act_vo.act_no == act_vo.act_no}">
					     	<tr>
					     		<td>
					     		
					     		
					     		<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<a class="goto_detail"  title="查看活動詳情">${act_vo.act_no }</a>
					     		 <input type="hidden" name=action  value="goto_act_detail" >
     							 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
      							<input type="hidden"  name="act.jsp" value="<%=request.getServletPath() %>"><%--為了重複使用Act_management的goto_act_detail區塊，沿用act.jsp名稱 --%>
					     		
					     		
					     		</form>
					     		</a>
					     		
					     		</td>
					     		<td>${act_vo.mem_ac }</td>
					     		<td>${act_vo.act_name }</td>
					     		
					     		<td>${act_vo.act_op_date }</td>
					     		<td>${act_vo.act_ed_date }</td>
					     		<td>${act_vo.act_add }</td>
					     		
					     		<td>${act_vo.act_fee }</td>
					     		<td><img class="img-responsive" src="<%=request.getContextPath()%>/ActImg.do?act_no=${act_vo.act_no}&index=1"></td>
					     		<td>${act_vo.act_stat }</td>
					     		<td>${fo_act_vo.fo_act_date }</td>
					     		<td>
					     		<form  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet" >
					     		<button class="btn btn-danger"><span class="fa fa-frown-o"></span>取消收藏</button>
					     		 <input type="hidden" name=action  value="delete_fo_act" >
     							 <input type="hidden"  name="act_no"  value="${act_vo.act_no }">
     							 <input type="hidden"  name="mem_ac"  value="${mem_ac }">
      							<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
					     		</form>
					     		</td>
					     	</tr>
	</c:if>
					     	</c:forEach>
					     	</c:if>
					     	</c:forEach>
					     </table>



					        </div>
					    </div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="modal-act">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">參加會員</h4>
					</div>
					<div class="modal-body">
						<table class="table table-striped">
							<tr>
								<th>會員帳號</th>
								<th>報名時間</th>
								<th>繳費狀態</th>
								<th>報到狀態</th>
								<th>確認會員已繳費</th>
							</tr>
						
						<c:forEach var="act_pair_vo" items="${act_pair_vo_list}"  >	
							<tr class="act_pair_detail">
								<td class="show_mem_ac">${act_pair_vo.mem_ac }</td>
								<td>${act_pair_vo.apply_date }</td>
								<td>${act_pair_vo.pay_state }</td>
								<td>${act_pair_vo.chk_state }</td>
								<td>
								<form  method="post" action="<%=request.getContextPath() %>/act_management/act_managementServlet">
					     		<button class="btn btn-success" ><span class="fa fa-check-square-o"></span>確認會員已繳費</button>
					     		<input type="hidden" name=action  value="confirm_mem_pay" >
					     		 <input type="hidden"  name="act_no"  value="${act_pair_vo.act_no }">
      							<input type="hidden"  name="my_act.jsp" value="<%=request.getServletPath() %>">
      							<input type="hidden"  name="mem_ac"  value="${act_pair_vo.mem_ac }">
      									</form>
								
								
								</td>
							</tr>
							</c:forEach>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					<%-- 	<button type="button" class="btn btn-primary"><span class="fa fa-volume-control-phone"></span>開啟群組私訊</button> --%>
					</div>
				</div>
			</div>
		</div>
		
		
		
<!-- 		<script src="https://code.jquery.com/jquery.js"></script> -->
<!-- 		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
		<script>
		<%--開啟第二個tab--%>
		if(${not empty openTab2}){
			$('.nav-tabs a[href="#tab2"]').tab('show');
			}
		<%--開啟第三個tab--%>
		if(${not empty openTab3}){
			$('.nav-tabs a[href="#tab3"]').tab('show');
			}
		if(${not empty openModal}){
			 $("#modal-act").modal({show: true});
			}
		
		$(".display_act_pair").click(function(){
			$(this).parent().submit();
		})
		
		$(".goto_detail").click(function(){
			$(this).parent().submit();
		})
	for(var i=0;i<$(".host_mem_count").length;i++){	
		if(parseInt($(".host_mem_count").eq(i).text())>=parseInt($(".host_min_mem").eq(i).text())){
			$(".dismiss_act").eq(i).css("display","none");
			$(".cancel_action").eq(i).text("達成團標準無法取消");
		}
	}
		for(var i=0;i<$(".act_stat").length;i++){
		if($(".act_stat").eq(i).text()=="不通過審核"){
			$(".dismiss_act").eq(i).css("display","none");
			$(".modify_act").eq(i).css("display","block");
		}
		}
		
		for(var i=0;i<$(".join_stat").length;i++){
			if(($(".join_stat").eq(i).text()=="已成團") || ($(".join_stat").eq(i).text()=="已滿團")){
				$(".cancel_join_thisAction").eq(i).css("display","none");
				$(".full_notice").eq(i).text("已成團無法取消");
			}
			
			
			
		}
		
		
		</script>
		
<jsp:include page="/FrontEnd/include/footer.jsp"/>
