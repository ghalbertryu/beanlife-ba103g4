<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.ord.model.*"%>
<%@ page import="com.ord_list.model.*"%>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="ordSvc" scope="page" class="com.ord.model.OrdService" />

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="ordVO" value="${ordVO}" scope="page"/>
<c:set var="ord_listVOs" value="${ord_listVOs}" scope="page"/>





		<div class="container cart-tab-block content">
			<div class="row">
				<div class="col-xs-12 col-sm-8 col-sm-offset-2">
					<h3 class="bold">結帳</h3>
					<form method="post" action="<%=request.getContextPath()%>/ord/ord.do">
					<table class="table table-hover table-striped table-rwd">


						<thead>
							<tr class="tr-only-hide">
								<th>商品</th>
								<th class="w50">單價</th>
								<th class="w50">數量</th>
								<th class="w90">總計</th>
							</tr>
						</thead>
						
						<c:set var="count" value="0"/>
						<c:set var="totalAmount" value="0"/>
						<c:forEach var="ord_listVO" items="${ord_listVOs}">
						<c:set var="count" value="${count+1}"/>
						<c:set var="prodVO" value="${prodSvc.getOneProd(ord_listVO.prod_no)}"/>
						<c:set var="storeVO" value="${storeSvc.getOneStore(prodVO.store_no)}" scope="page"/>
						<!-- ////////////////////////////// -->
						<tbody>
							<tr>
								<td  data-th="商品">
									<div class="container-floid">
						                <div class="row zidx0">
						                
						                <a id="${prodVO.prod_no}" href='#modal-inner' data-toggle="modal">
						                  <div class="col-xs-3  col-sm-2 vam-div60 pad0">
						                    <img class="img-responsive mg-auto vam-img rd5 " src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
						                  </div>
						                  
						                    <div class="col-xs-9 col-sm-10">
						                      <p class="inline-b bold">${prodVO.prod_name}</p>
						                      <div>
						                        <p class="inline-b bold text-info">${prodVO.bean_contry}　${prodVO.proc}　${prodVO.roast}　${prodVO.prod_wt}lb/包</p>
						                      </div>
						                    </div>
						                 </a>
						                 
						                </div>
						            </div>

								</td>
								<td data-th="單價">
									NT$${prodVO.prod_price}
								</td>
								<td data-th="數量">
									${ord_listVO.amont}
									 <input type="hidden" name="prod_no${count}" value="${prodVO.prod_no}">
			                         <input type="hidden" name="amount${count}" value="${ord_listVO.amont}">
								</td>
								<td data-th="總計">			
									NT$<span>${prodVO.prod_price*ord_listVO.amont}</span>
								</td>
							</tr>
						</tbody>
						<c:set var="totalAmount" value="${totalAmount+ord_listVO.amont}"/>

<script> 
//Prod View
var $modalX = $("#modalX");
var $btn = $("#${prodVO.prod_no}").click(function(){
		var prodNo =  $("#${prodVO.prod_no}").attr("id");
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
</script> 						
						
						</c:forEach>
						
					</table>


					<div class="container-floid">
						<div class="row">
							<div class="col-xs-12 col-sm-12">
								<span class="pull-left padt5 padl5 ">共${totalAmount}件商品</span>
								<span class="pull-right mgr20 ">運費：$${ordVO.send_fee}</span><br>
								<span class="pull-right mgr20 ">訂單金額 ：<h4 class="inline-b text-danger">$${ordVO.total_pay}</h4></span>
							
							</div>
						</div>
					</div>

					<div class="container-floid">
						<div class="row">
							
							<div class="col-xs-12 col-sm-7">
								<h4 class="bold text-info">填寫收件資訊</h4>
								<div class="input-group mgt10">
									<div class="input-group-addon">
										收件人姓名
									</div>
									<input type="text" maxlength="10" name="ord_name" class="form-control"
									data-toggle="tooltip" data-placement="bottom" title="${errorMsgs.get('errName')}" value="${ordVO.ord_name }">
								</div>


								<div class="input-group ">
									<div class="input-group-addon">
										收件人地址
									</div>
									<input type="text" maxlength="50" name="ord_add" class="form-control"
									data-toggle="tooltip" data-placement="bottom" title="${errorMsgs.get('errAdd')}" value="${ordVO.ord_add }">
								</div>

								<div class="input-group">
									<div class="input-group-addon">
										收件人手機
									</div>
									<input type="number" maxlength="15" name="ord_phone" class="form-control"
									data-toggle="tooltip" data-placement="bottom" title="${errorMsgs.get('errPhone')}" value="${ordVO.ord_phone}">
								</div>
								
							</div>					
							
							<div class="col-xs-12 col-sm-1 col-sm-offset-11">
								<input type="hidden" name="store_no" value="${storeVO.store_no}">
								<input type="hidden" name="mem_ac" value="${mem_ac}">
								<input type="hidden" name="count" value="${count}">
								<input type="hidden" name="action" value="newOrd">
								<input type="submit" class="btn btn-primary pull-right mgt20" name="submit" value="下訂單">
							</div>
							

						</div>
					</div>
					</form>
								</div>
					        </div>
					    </div>

<script>
	$(function () {
		if(${errorMsgs.size()}!=0){
			$("[data-toggle='tooltip']").tooltip('show'); 
		}
	});
</script>
<jsp:include page="/FrontEnd/include/footer.jsp"/>