<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.ord.model.*"%>
<%@ page import="com.ord_list.model.*"%>
<%@ page import="com.review.model.*"%>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="ordSvc" scope="page" class="com.ord.model.OrdService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="ordVOs" value="${ordSvc.getOrdByMem_ac(mem_ac)}" scope="page"/>

<jsp:useBean id="ordVOsList" scope="page" class="java.util.ArrayList"/>
<jsp:useBean id="ordVOs1" scope="page" class="java.util.LinkedHashSet"/>
<jsp:useBean id="ordVOs2" scope="page" class="java.util.LinkedHashSet"/>
<jsp:useBean id="ordVOs3" scope="page" class="java.util.LinkedHashSet"/>
<jsp:useBean id="ordVOs4" scope="page" class="java.util.LinkedHashSet"/>


<c:forEach var="ordVO" items="${ordVOs}">
	<c:if test="${ordVO.ord_stat=='未付款'}">
		<c:set var="notShow" value="${ordVOs1.add(ordVO)}"/>
	</c:if>
	<c:if test="${ordVO.ord_stat=='已付款'||ordVO.ord_stat=='已確認付款'}">
		<c:set var="notShow" value="${ordVOs2.add(ordVO)}"/>

	</c:if>
	<c:if test="${ordVO.ord_stat=='已出貨'}">
		<c:set var="notShow" value="${ordVOs3.add(ordVO)}"/>
	</c:if>
	<c:if test="${ordVO.ord_stat=='已取消'}">
		<c:set var="notShow" value="${ordVOs4.add(ordVO)}"/>
	</c:if>
</c:forEach>
<c:set var="notShow" value="${ordVOsList.add(ordVOs1)}"/>
<c:set var="notShow" value="${ordVOsList.add(ordVOs2)}"/>
<c:set var="notShow" value="${ordVOsList.add(ordVOs3)}"/>
<c:set var="notShow" value="${ordVOsList.add(ordVOs4)}"/>


 <!--  --------------------------------------------------------------結帳跳窗---------------------------------------------------------------->
<div class="modal fade" id="modal-pay">
        <div class="modal-dialog modal-md">
            <div class="modal-content" id="modalP">

    			<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">刷卡轉帳</h4>
				</div>

            	<div class="container-folid pad15">
            		<div class="row">
            			<div class="col-xs-12 col-sm-10 col-sm-offset-1">

            				<div class="container-fluid">
								<div class="row">
									<div class="col-xs-12 col-sm-12">
										<div class="pull-left">
											訂單編號：<span id="ordNo">O10000000001</span><br>
											<small><span id="ordDate">2017-09-30</span></small>
										</div>
										<div class="pull-right">
											<span >全部金額：<h4 class="inline-b bold text-danger">$<span id="ordPay">500</span></h4></span>
											

										</div>
									
									</div>
								</div>
							</div>
							<form method="post" action="<%=request.getContextPath()%>/ord/ord.do">
							<div>
								<h4 class="bold text-info">匯款資訊</h4>
								<div class="well" id="payInfo">
								</div>

								<h4 class="bold text-info">銀行轉帳</h4>
								<div class="input-group mgt10">
									<div class="input-group-addon">
										匯款帳戶末5碼
									</div>
									<input type="text" maxlength="5" name="bankAc" id="bankAc" class="form-control payWay atm textNumOnly">
								</div>


								<h4 class="bold text-info">信用卡付款</h4>
								<div class="input-group">
									<div class="input-group-addon">
										卡號
									</div>
									<div class="form-control padt3 card">
										<input type="text" maxlength="4" name="crdNo1" id="crdNo1" class="w20p payWay card textNumOnly"> -
										<input type="text" maxlength="4" name="crdNo2" id="crdNo2" class="w20p payWay card textNumOnly"> -
										<input type="text" maxlength="4" name="crdNo3" id="crdNo3" class="w20p payWay card textNumOnly"> -
										<input type="text" maxlength="4" name="crdNo4" id="crdNo4" class="w20p payWay card textNumOnly">
									</div>
								</div>
								<div class="input-group">
									<div class="input-group-addon">
										有效期限
									</div>
									<input type="month" name="crdVal" id="crdVal" class="form-control payWay card">
								</div>
								<div class="input-group">
									<div class="input-group-addon">
										檢核碼
									</div>
									<input type="text" maxlength="3" name="crdChk" id="crdChk" class="form-control payWay card textNumOnly">
								</div>
								<div class="pull-right">
									<input type="hidden" name="action" value="payOrd">
									<input type="hidden" name="ord_no" value="">
									<input type="hidden" name="mem_ac" value="${mem_ac}">
									<input type="submit" value="確認付款" class="form-control pull-right btn btn-primary btn-ms mgt10">
								</div>
							</div>
							</form>

            			</div>
            		</div>
            	</div>
		
                
            </div>
        </div>
    </div>

<!--  --------------------------------------------------------------跳窗結束---------------------------------------------------------------->
<!--  --------------------------------------------------------------取消跳窗---------------------------------------------------------------->
<div class="modal fade" id="modal-del">
        <div class="modal-dialog modal-md">
            <div class="modal-content" id="modalD">


            	<div class="container-folid pad15">
            		<div class="row">
            			<div class="col-xs-12 col-sm-10 col-sm-offset-1">

            				<div class="container-fluid">
								<div class="row">
									<div class="col-xs-10 col-xs-offset-1 col-sm-10 col-sm-offset-1 well">
										<div class="pull-left">
											訂單編號：<span id="ordNoD">O10000000001</span><br>
											<small><span id="ordDateD">2017-09-30</span></small>
										</div>
										<div class="pull-right">
											<span >全部金額：<h4 class="inline-b bold text-danger">$<span id="ordPayD">500</span></h4></span>

										</div>
										
									</div>
									<div class="col-xs-12 col-sm-12">
										<h3>確定取消此筆訂單？</h3>
									</div>
								</div>
							</div>
							
							<form method="post" action="<%=request.getContextPath()%>/ord/ord.do">
							<div>
								<div class="pull-right">
									<input type="hidden" name="action" value="delOrd">
									<input type="hidden" name="ord_no" value="">
									<button type="button" class="btn btn-ms btn-default mgt10 inline-b" data-dismiss="modal" aria-hidden="true">取消</button>
									<input type="submit" value="確定" class=" btn btn-ms btn-default mgt10 inline-b">
								</div>
							</div>
							
							
							</form>

            			</div>
            		</div>
            	</div>
		
                
            </div>
        </div>
    </div>

<!--  --------------------------------------------------------------跳窗結束---------------------------------------------------------------->

 <!--  --------------------------------------------------------------評論跳窗---------------------------------------------------------------->
<div class="modal fade" id="modal-rev">
        <div class="modal-dialog modal-sm">
            <div class="modal-content" id="modalR">

    			<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="prodNameR">商品名稱</h4>
				</div>

            	<div class="container-folid pad15">
            		<div class="row">
            			<div class="col-xs-12 col-sm-10 col-sm-offset-1">

							<form method="post" action="<%=request.getContextPath()%>/review/review.do">
							<div>
								<h4 class="bold text-info">沖泡方式</h4>
								<div class="input-group">
									<div class="input-group-addon">
										重量
									</div>
									<input type="text" maxlength="3" name="useG" id="useG" class="form-control payWay atm textNumOnly" required>
									<div class="input-group-addon bg-white">g</div>
								</div>
								<div class="input-group">
									<div class="input-group-addon">
										水量
									</div>
									<input type="text" maxlength="3" name="useMl" id="useMl" class="form-control payWay atm textNumOnly" required>
									<div class="input-group-addon bg-white">ml</div>
								</div>
								<div class="input-group">
									<div class="input-group-addon">
										水溫
									</div>
									<input type="text" maxlength="3" name="useC" id="usesC" class="form-control payWay atm textNumOnly" required>
									<div class="input-group-addon bg-white">°C</div>
								</div>
								<div class="input-group">
									<div class="input-group-addon">
										時間
									</div>
									<input type="text" maxlength="3" name="useS" id="useS" class="form-control payWay atm textNumOnly" required>
									<div class="input-group-addon bg-white">sec</div>
								</div>

								<h4 class="bold text-info">評價</h4>
								<div class="pull-right">
									<span class="glyphicon glyphicon-star tx-gray revStar" aria-hidden="true"></span>
									<span class="glyphicon glyphicon-star tx-gray revStar" aria-hidden="true"></span>
									<span class="glyphicon glyphicon-star tx-gray revStar" aria-hidden="true"></span>
									<span class="glyphicon glyphicon-star tx-gray revStar" aria-hidden="true"></span>
									<span class="glyphicon glyphicon-star tx-gray revStar" aria-hidden="true"></span>
								</div>
								<input type="hidden" name="prod_score" required>
					

								<div class="form-group">
								  <label for="rev_cont">評論:</label>
								  <textarea class="form-control" rows="5" id="rev_cont"  name="rev_cont" required></textarea>
								</div>

								<div class="pull-right">
									<input type="hidden" name="action" value="addRev">
									<input type="hidden" id="ordNoR" name="ord_no" value="">
									<input type="hidden" id="prodNoR" name="prod_no" value="">
									<input type="submit" value="給評" class="form-control pull-right btn btn-primary btn-ms mgt10">
								</div>
							</div>
							</form>

            			</div>
            		</div>
            	</div>

            </div>
        </div>
    </div>

<!--  --------------------------------------------------------------跳窗結束---------------------------------------------------------------->





		<div class="container cart-tab-block content">
			<div class="row">
				<div class="col-xs-12 col-sm-8 col-sm-offset-2">
					<h3 class="bold">購買清單</h3>

						<div role="tabpanel">
						    <!-- 標籤面板：標籤區 -->
						    <ul class="nav nav-tabs" role="tablist">
						        <li role="presentation" class="w25p bold text-center ${(param.status==1)?'active':''}">
						            <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">待付款</a>
						        </li>
						        <li role="presentation"  class="w25p bold text-center ${(param.status==2)?'active':''}">
						            <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">待出貨</a>
						        </li>
						        <li role="presentation" class="w25p bold text-center ${(param.status==3)?'active':''}">
						            <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">已出貨</a>
						        </li>
						        <li role="presentation" class="w25p bold text-center ${(param.status==4)?'active':''}">
						            <a href="#tab4" aria-controls="tab4" role="tab" data-toggle="tab">已取消</a>
						        </li>
						    </ul>
						    
							<!-- 標籤面板：內容區 -->
						    <div class="tab-content">
						    
						    <c:forEach items="${ordVOsList}" varStatus="count">
						   
						        <div role="tabpanel" class="tab-pane ${(count.count==param.status)?'active':''}" id="tab${count.count}">
 									<!--//////////////////////////////////////// -->
 									<c:forEach var="ordVO" items="${ordVOsList.get(count.index)}">
									<c:set var="storeVO" value="${storeSvc.getOneStore(prodSvc.getOneProd(ordSvc.getOrd_listByOrd(ordVO.ord_no).toArray()[0].prod_no).store_no)}" scope="page"/>
									
									<div class="container-fluid padt10 mgt20">
										<div class="row">
											<div class="col-xs-4 col-sm-6 bold pull-left">
												<h4><a class="${storeVO.store_no}" name="${storeVO.store_no}" href='#modal-inner' data-toggle="modal">
													${storeVO.store_name}
												</a></h4>
											</div>
											<div class="col-xs-8 col-sm-6 pull-right text-right">
												<small><fmt:formatDate value="${ordVO.ord_date}" pattern="yyyy-MM-dd HH:mm"/></small><br>
												<small>訂單編號：${ordVO.ord_no}</small>
											</div>
										</div>
									</div>
									<div class="container-fluid ">
									<table class="table table-hover table-striped table-rwd">


										<c:set var="totalAmount" value="0"/>
										<c:forEach var="ord_listVO" items="${ordSvc.getOrd_listByOrd(ordVO.ord_no)}">
											<c:set var="prodVO" value="${prodSvc.getOneProd(ord_listVO.prod_no)}"/>
											
										<!-- ////////////////////////////// -->
										<tbody>
											<tr>
												<td  data-th="商品">
													<div class="container-fluid">
										                <div class="row zidx0">
										                
										                <a class="${prodVO.prod_no}" name="${prodVO.prod_no}" href='#modal-inner' data-toggle="modal">
										                  <div class="col-xs-3 col-sm-2 vam-div60 pad0">
										                    <img class="img-responsive mg-auto vam-img rd5 " src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
										                  </div>
										                  
										                    <div class="col-xs-9 col-sm-8">
										                      <p class="inline-b bold">${prodVO.prod_name}</p>
										                      <div>
										                        <p class="inline-b bold text-info">${prodVO.bean_contry}　${prodVO.proc}　${prodVO.roast}　${prodVO.prod_wt}lb/包</p>
										                      </div>
										                    </div>
										                 </a>
										                 	<div class="col-xs-10 col-xs-offset-1 col-sm-1 col-sm-offset-0">
										                 		<c:if test="${ordVO.ord_stat=='已出貨'&&reviewSvc.getByOrdProd(ordVO.ord_no,prodVO.prod_no)==null}">
										                 		<span class="btn btn-xs btn-warning inline-b pull-right" id="rev${ordVO.ord_no}${prodVO.prod_no}" >給評</span>
										                 		</c:if>
										                 	</div>
										                 
										                </div>
										            </div>
				
												</td>
												<td data-th="數量">
													x${ord_listVO.amont}
												</td>
												<td data-th="小計">
													NT$${prodVO.prod_price*ord_listVO.amont}
												</td>
											</tr>
										</tbody>
										<c:set var="totalAmount" value="${totalAmount+ord_listVO.amont}"/>
				
				
<script> 
//Prod View
var $modalX = $("#modalX");
var $btnPordX = $(".${prodVO.prod_no}").click(function(){
		var prodNo =  $(".${prodVO.prod_no}").attr("name");
		var urlstr = '<%=request.getContextPath()%>/FrontEnd/prod/prodPage.jsp?prodNo='+prodNo;
		$.ajax({
			url : urlstr,
			type : 'GET',
			dataType: "html",
			async: false,
			success : function(result) {
				while($modalX.children().length > 0){
					$modalX.empty();
				}
				
				$modalX.html(result);
			},
			error : function(xhr) {
				alert('Ajax request 發生錯誤');
			}
		});
		
	});
	
//show Review Modal
<c:if test="${ordVO.ord_stat=='已出貨'}">
var $btnRev = $("#rev${ordVO.ord_no}${prodVO.prod_no}").click(function(){
     var $ord_no = "${ordVO.ord_no}";
     var $prod_no = "${prodVO.prod_no}";

     $('#prodNameR').text("${prodVO.prod_name}");
     $('#ordNoR').val($ord_no);
     $('#prodNoR').val($prod_no);

		$('#modal-rev').modal('show');
		return false;
 });
</c:if>
</script>

										</c:forEach><%-- ord_listVO --%>

									</table>
									</div>
									<div class="container-fluid cus-order-row">
										<a role="button" data-toggle="collapse" href="#sendInfo${ordVO.ord_no}" aria-expanded="false" aria-controls="#sendInfo${ordVO.ord_no}">
										<div class="row">
											<div class="col-xs-12 col-sm-12">
												<span class="pull-left padt5 ">共${totalAmount}件商品</span>
												<span class="pull-right mgr10">運費：$${ordVO.send_fee}</span> <br>
												
												<span class="pull-right mgr10">
													<c:if test="${ordVO.ord_stat.equals('已確認付款')}">
														<span class="btn btn-xs btn-success inline-b">已確認付款</span>
													</c:if>
													訂單金額：<h4 class="inline-b bold text-danger">$${ordVO.total_pay}</h4>
												</span>
											
											</div>
											<c:if test="${ordVO.ord_stat=='未付款'}">
											<div class="col-xs-12 col-sm-12">
												<span id="pay${ordVO.ord_no}"class="btn btn-ms btn-primary pull-right inline-b mgr10">付款</span>
												<span id="del${ordVO.ord_no}" class="btn btn-ms btn-danger pull-right inline-b mgr10">取消訂單</span>
											</div>
											</c:if>
										</div>
										</a>
										<div class="collapse" id="sendInfo${ordVO.ord_no}">
											<div class="inline-b  mgr40">
												收件人：${ordVO.ord_name}<br>
												地址：${ordVO.ord_add}<br>
												手機：${ordVO.ord_phone}
											</div>
											<div class="inline-b mgr40">
												<c:if test="${ordVO.pay_date!=null}">
												付款時間：<fmt:formatDate value="${ordVO.pay_date}" pattern="yyyy-MM-dd HH:mm"/><br>
												</c:if>
												<c:if test="${ordVO.pay_chk_date!=null}">
												確認付款：<fmt:formatDate value="${ordVO.pay_chk_date}" pattern="yyyy-MM-dd HH:mm"/>
												</c:if>
											</div>
											<div class="inline-b">
												<c:if test="${ordVO.send_date!=null}">
												出貨時間：<fmt:formatDate value="${ordVO.send_date}" pattern="yyyy-MM-dd HH:mm"/><br>
												物流編號：${ordVO.send_id}
												</c:if>
											</div>
										</div>
									</div>
									
<script>

//show Store
var $modalX = $("#modalX");
var $btnStoreX = $(".${storeVO.store_no}").click(function(){
		var storeNo =  $(".${storeVO.store_no}").attr("name");
		var urlstr = '<%=request.getContextPath()%>/FrontEnd/store/storePage.jsp?storeNo='+ storeNo;
		$.ajax({
			url : urlstr,
			type : 'GET',
			dataType: "html",
			async: false,
			success : function(result) {
				while($modalX.children().length > 0){
					$modalX.empty();
				}
				$modalX.html(result);
			},
			error : function(xhr) {
				alert('Ajax request 發生錯誤');
			}
		});
	});
	

<c:if test="${ordVO.ord_stat=='未付款'}">
//show PayInfo
var $btnPayInfo = $("#pay${ordVO.ord_no}").click(function(){
       var $action = "getPayInfo";
       var $ord_no = "${ordVO.ord_no}";
       var $store_no = "${storeVO.store_no}";
       var $mem_ac = "${mem_ac}";
       $.ajax({
           url : "<%=request.getContextPath()%>/ord/ordAjax.do",
           type : 'post',
           contentType: "application/json",
           data: JSON.stringify({action:$action, ord_no: $ord_no,store_no:$store_no, mem_ac: $mem_ac}),
           dataType: "JSON",
           async: false,
           success : function(jdata) {  	
           	if(jdata.err!=null){
           		console.log(jdata.err);
           	} else {
           		$('#ordDate').text(jdata.ord_date);
                $('#ordNo').text(jdata.ord_no);
                $('#ordPay').text(jdata.total_pay);
                $('#payInfo').html(jdata.store_atm_info.replace(/\r\n|\n/g, "<br>"));
                $('#modal-pay :hidden [name="ord_no"]').val(jdata.ord_no);
           	}	
           },
           error : function(xhr) {
           	console.log(xhr);
            console.log('查詢訂單Info失敗');
           }
       });
		$('#modal-pay').modal('show');
		return false;
   });
   
//show Del
var $btnDel = $("#del${ordVO.ord_no}").click(function(){
       var $ord_no = "${ordVO.ord_no}";
       var $total_pay = "${ordVO.total_pay}";
       var $ord_date = "${ordVO.ord_date}";
       
       $('#ordNoD').text($ord_no);
       $('#ordDateD').text($ord_date);
       $('#ordPayD').text($total_pay);
       $('#modal-del :hidden [name="ord_no"]').val($ord_no);
       
		$('#modal-del').modal('show');
		return false;
   });
</c:if>   


</script>									

									</c:forEach><%-- ordVO --%>
		
								</div>
							</c:forEach><%-- ordVOsList 1 2 3 4--%>
					
						    </div>
						</div>
			
					</div>
		        </div>
		    </div>
		    




<jsp:include page="/FrontEnd/include/footer.jsp"/>