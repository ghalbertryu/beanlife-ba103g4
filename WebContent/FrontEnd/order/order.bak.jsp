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
<c:set var="ordVO" value="${ordSvc.getOrdByOrdno(param.ord_no)}" scope="page"/>
<c:set var="ord_listVOs" value="${ordSvc.getOrd_listByOrd(param.ord_no)}" scope="page"/>





		<div class="container cart-tab-block content">
			<div class="row">
				<div class="col-xs-12 col-sm-8 col-sm-offset-2">
					<h3 class="bold">付款填寫結帳單</h3>

					<table class="table table-hover table-striped table-rwd">


						<thead>
							<tr class="tr-only-hide">
								<th>商品</th>
								<th class="w50">單價</th>
								<th class="w50">數量</th>
								<th class="w90">總計</th>
							</tr>
						</thead>
						
						<c:set var="totalAmount" value="0"/>
						<c:forEach var="ord_listVO" items="${ord_listVOs}">
						<c:set var="prodVO" value="${prodSvc.getOneProd(ord_listVO.prod_no)}"/>
						<c:set var="storeVO" value="${storeSvc.getOneStore(prodVO.store_no)}" scope="page"/>
						<!-- ////////////////////////////// -->
						<tbody>
							<tr>
								<td  data-th="商品">
									<div class="container-floid">
						                <div class="row zidx0">
						                
						                <a id="${prodVO.prod_no}" href='#modal-id' data-toggle="modal">
						                  <div class="col-xs-10 col-xs-offset-1 col-sm-2 col-sm-offset-0 vam-div60">
						                    <img class="img-responsive mg-auto vam-img rd5 " src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
						                  </div>
						                  
						                    <div class="col-xs-10 col-xs-offset-1 col-sm-10 col-sm-offset-0">
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
								<span class="pull-right mgr20 ">總金額共 <h4 class="inline-b text-danger">$${ordVO.total_pay}</h4></span>
								<span class="pull-right mgr20 ">運費：$${ordVO.send_fee} <br><small>滿$${storeVO.store_free_ship}免運費</small></span>
							</div>
						</div>
					</div>

					<div class="container-floid mgt20">
						<div class="row">
							<div class="col-xs-12 col-sm-5 col-sm-push-7">
								<h4 class="bold text-info">匯款資訊</h4>
								<div class="well">
									
									匯款銀行：彰化銀行 蘆洲分行<br>
									戶名：陳建儒<br>
									銀行代碼：009 <br>
									銀行帳號：9832-51-326845-00
								</div>

							</div>

							<div class="col-xs-12 col-sm-7 col-sm-pull-5">
								<h4 class="bold text-info">填寫收件資訊</h4>

									<form>
										<div class="input-group mgt10">
											<div class="input-group-addon">
												收件人姓名
											</div>
											<input type="text" name="mem_name" id="" class="form-control">
										</div>


										<div class="input-group ">
											<div class="input-group-addon">
												收件人地址
											</div>
											<input type="text" name="mem_add" id="" class="form-control">
										</div>

										<div class="input-group">
											<div class="input-group-addon">
												收件人手機
											</div>
											<input type="text" name="mem_phone" id="" class="form-control">
										</div>

									
										<h4 class="bold text-info">信用卡付款</h4>
										<div class="input-group">
											<div class="input-group-addon">
												卡號
											</div>
											<div class="form-control padt3 card">
												<input type="text" maxlength="4" name="crdNo1" id="crdNo1" class="w20p payWay card"> -
												<input type="text" maxlength="4" name="crdNo2" id="crdNo2" class="w20p payWay card"> -
												<input type="text" maxlength="4" name="crdNo3" id="crdNo3" class="w20p payWay card"> -
												<input type="text" maxlength="4" name="crdNo4" id="crdNo4" class="w20p payWay card">
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
											<input type="text" maxlength="3" name="crdChk" id="crdChk" class="form-control payWay card">
										</div>


										<h4 class="bold text-info">銀行轉帳</h4>
										<div class="input-group mgt10">
											<div class="input-group-addon">
												匯款帳戶末5碼
											</div>
											<input type="text" maxlength="5" name="bankAc" id="bankAc" class="form-control payWay atm">
										</div>
										<input type="submit" class="btn btn-info pull-right mgt10" name="submit" value="確認">

									</form>
							</div>

						</div>
					</div>











									
								</div>
					        </div>
					    </div>


<script>
// bank/credit
$(document).ready(function(){
	$('.payWay').blur(function(){
		if($(this).attr('id')=='bankAc'){
			//bank
			if($('#bankAc').val()!=''){
				$('.card').attr('readonly', true);
			} else {
				$('.card').attr('readonly', false);
			}
			return;

		} else {
			//credit
			var $card = $($('input.card'));
			for(var i = 0; i<$card.length; i++){
				if($($card[i]).val()!=''){
					$('.atm').attr('readonly', true);
					return;
				}
				$('.atm').attr('readonly', false);
			}
		}
	});
});
</script>


<jsp:include page="/FrontEnd/include/footer.jsp"/>