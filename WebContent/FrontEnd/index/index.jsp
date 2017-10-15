<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.fo_prod.model.*"%>
<%@ page import="com.review.model.*"%>
<%@ page import="com.like_rev.model.*"%>
<%@ page import="com.qa.model.*"%>
<%@ page import="com.cart_list.model.*"%>
<%@ page import="com.act.model.*"%>


<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="like_revSvc" scope="page" class="com.like_rev.model.Like_revService" />
<jsp:useBean id="qaSvc" scope="page" class="com.qa.model.QaService" />
<jsp:useBean id="cart_listSvc" scope="page" class="com.cart_list.model.Cart_listService" />
<jsp:useBean id="actSvc" scope="page" class="com.act.model.ActService" />

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="hotProdVOs" value="${sessionScope.hotProdVOs}" scope="page"/>
<%-- <c:set var="hotProdVOs" value="${prodSvc.all}" scope="page"/> --%>
<c:set var="prodVOs" value="${hotProdVOs}" scope="page"/>
<c:set var="fo_list" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="like_rev_list" value="${like_revSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="cart_listSet" value="${cart_listSvc.getVOsByMem(mem_ac)}" scope="page"/>
<c:set var="actlist" value="${actSvc.getNew(10)}" scope="page"/>

<style>

.mgb30{
cursor: pointer;
}

</style>





		<!--BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB-->
		<div class="container-fluid mgt-depn-nav">
			<div class="row">
			<div id="index-cas" class="carousel slide" data-ride="carousel">
			    <!-- 幻燈片小圓點區 -->
			    <ol class="carousel-indicators">
			        <li data-target="#index-cas" data-slide-to="0" class=""></li>
			        <li data-target="#index-cas" data-slide-to="1" class=""></li>
			        <li data-target="#index-cas" data-slide-to="2" class="active"></li>
			    </ol>
			    <!-- 幻燈片主圖區 -->
			    <div class="carousel-inner">
			        <div class="item">
			            <img src="<%=request.getContextPath()%>/FrontEnd/res/img/in1.png" alt="">
			            <div class="container">
			                <div class="carousel-caption">
			                    <h2 class="pull-left">快速挑選咖啡豆</h2 class="pull-left">
			                </div>
			            </div>
			        </div>
			        <div class="item">
			            <img src="<%=request.getContextPath()%>/FrontEnd/res/img/in2.png" alt="">
			            <div class="container">
			                <div class="carousel-caption">
			                    <h2 class="pull-left">活動與同好聚會</h2>
			                </div>
			            </div>
			        </div>
			        <div class="item active">
			            <img src="<%=request.getContextPath()%>/FrontEnd/res/img/in3.png" alt="">
			            <div class="container">
			                <div class="carousel-caption">
			                    <h2 class="pull-left">達人經驗分享</h2>
			                </div>
			            </div>
			        </div>
			    </div>
			    <!-- 上下頁控制區 -->
			    <a class="left carousel-control" href="#index-cas" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
			    <a class="right carousel-control" href="#index-cas" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
			</div>
		</div>
	</div>











		<!--CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC-->
		<div class="container cus-tab-block content">
			<div class="row">
				<div class="col-xs-12 col-sm-10 col-sm-offset-1">
					<div role="tabpanel">
					    <!-- 標籤面板：標籤區 -->
					    <ul class="nav nav-tabs " role="tablist">
					        <li role="presentation" class="active w50p text-center bold">
					            <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">熱門商品</a>
					        </li>
					        <li role="presentation" class="w50p text-center bold">
					            <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">最新活動</a>
					        </li>
					    </ul>
					
					    <!-- 標籤面板：內容區 -->
					    <div class="tab-content cus-tab-content">



					    	<!-- tab1111111111111111111111111111111 -->
					        <div role="tabpanel" class="tab-pane active" id="tab1">
								
									<c:forEach var="prodVO" items="${prodVOs}" varStatus="s">
									<c:if test="${s.index mod 4==0}">
									<div class="container-fluid">
									<div class="row">
									</c:if>
									
									<c:if test="${s.index mod 2==0}">
									<div class="col-xs-12 col-sm-6 pad0">
									</c:if>

									
									<%
										String prod_no = ((ProdVO)pageContext.getAttribute("prodVO")).getProd_no();
										//此會員對此商品是否Follow的Boolean
		                              	Boolean isFollow = false;
		                              	for (Fo_prodVO fo_prodVO: (List<Fo_prodVO>)pageContext.getAttribute("fo_list")){
		                              		if(fo_prodVO.getProd_no().equals(prod_no)){	                              			
		                              			isFollow = true;
		                              		}
		                              	}
		                              	pageContext.setAttribute("isFollow",isFollow);


		                              	
		                              	//此商品的分數轉換星星Boolean
		                              	Boolean [] star = new Boolean[5];
		                              	Double score = ((ReviewService)(pageContext.getAttribute("reviewSvc"))).getScoreByProd(prod_no);
		                              	long scoreLong = Math.round(score);
		                              	for (int i = 0 ; i < scoreLong ; i++){
		                              		star[i] = true;
		                              	}
		                              	pageContext.setAttribute("star",star);
		                              	      	
		                            %>

				                      <!-- ////////////////////////////// -->
				                      <div class="col-xs-6 col-sm-6 padt10">
				                        <a id="${prodVO.prod_no}" class="showProd" name="${prodVO.prod_no}" href='#modal-inner' data-toggle="modal">
				                          
				                          <img class="img-responsive  mg-auto vam-img  rd10" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
				                          
				                          <h4 class="bold">${prodVO.prod_name}</h4>
				                          <p class="inline-b bold text-info">NT$ ${prodVO.prod_price}</p>
				                         
										  
				                          <button type="button" class="bk${prodVO.prod_no} btn btn-default btn-xs zidx5 pull-right ${(isFollow)?'bor-info':''}" aria-label="Left Align">
				                              <span class="bk${prodVO.prod_no} count ${(isFollow)?'text-info':'tx-gray'}">${fo_prodSvc.getCountByProd(prodVO.prod_no)}</span>
				                              <span class="bk${prodVO.prod_no} glyphicon glyphicon-bookmark ${(isFollow)?'text-info':'tx-gray'}" aria-hidden="true"></span>	
				                          </button>

				                          <p class="bold">${prodVO.bean_contry}　${prodVO.proc}　${prodVO.roast}</p>

				                          <div title="${reviewSvc.getScoreByProd(prodVO.prod_no)}/5.0">
				                          	  
				                              <span class="glyphicon glyphicon-star ${(star['0'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				                              <span class="glyphicon glyphicon-star ${(star['1'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				                              <span class="glyphicon glyphicon-star ${(star['2'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				                              <span class="glyphicon glyphicon-star ${(star['3'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				                              <span class="glyphicon glyphicon-star ${(star['4'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				                              <span>(${reviewSvc.getCountByProd(prodVO.prod_no)})</span>
				                          </div>
				                        </a>
				                      </div>
				                      
				                      
<script>

//foProd
var $btnFoProd = $("button.bk${prodVO.prod_no}").click(function(){
	if(${mem_ac==null}){
		 $('#modal-login').modal("show");
		 return false;
	}
    var $action = "foProd";
    var $prod_no = "${prodVO.prod_no}"
    $.ajax({
        url : "<%=request.getContextPath()%>/fo_prod/fo_prodAjax.do",
        type : 'post',
        contentType: "application/json",
        data: JSON.stringify({action:$action, prod_no: $prod_no}),
        dataType: "JSON",
        async: false,
        success : function(jdata) {     
            if(jdata.err!=null){
                alert(jdata.err);
            } else {
                if(jdata.isAdd==1){
					$('.bk${prodVO.prod_no}.count').each(function(){$(this).text(jdata.count)})
					$('button.bk${prodVO.prod_no}').addClass('bor-info');
                    $('.bk${prodVO.prod_no}').addClass('text-info');
                    $('.bk${prodVO.prod_no}').removeClass('tx-gray');
                } else{
					$('.bk${prodVO.prod_no}.count').each(function(){$(this).text(jdata.count)})
                    $('button.bk${prodVO.prod_no}').removeClass('bor-info');
                    $('.bk${prodVO.prod_no}').removeClass('text-info');
                    $('.bk${prodVO.prod_no}').addClass('tx-gray');
                }
            }
        },
        error : function(xhr) {
            console.log('修改收藏失敗');
        }
    });
    return false;
});
</script>                   
				                      
				                    <c:if test="${s.index mod 2==1 || s.count==prodVOs.size()}">
									</div>
									</c:if>  
				                    

									<c:if test="${s.index mod 4==3 || s.count==prodVOs.size()}">
									</div>
									</div>
									</c:if>
								
				                    </c:forEach>





								

					        </div>
















					        <!-- tab333333333333333333333333333333333 -->
					        <div role="tabpanel" class="tab-pane" id="tab3">
								<div class="container-fluid">
									
									<div class="row">
										<c:forEach var="actVO" items="${actlist}">
											<a href="<%=request.getContextPath()%>/act_management/act_managementServlet?action=goto_act_detail&act_no=${actVO.act_no }">
											<div class="row mgb30 mgt20 ">
												<div class="col-xs-12 col-sm-6">
													<img class="img-responsive  mg-auto vam-img" src="<%=request.getContextPath()%>/act/actImg.do?act_no=${actVO.act_no}&index=1">
												</div>
												<div class="col-xs-12 col-sm-6">
													<h4 class="inline-b bold">${actVO.act_name}</h4>
													<p>${fn:substring(actVO.act_cont,0,160)}...</p>
													<h5 class="inline-b pull-left text-info">${actVO.act_op_date}</h5>
													<h5 class="inline-b pull-left mgl20 text-info">${fn:substring(actVO.act_add,0,3)}</h5>
													
												</div>
											</div>
											</a>
										</c:forEach>
									</div>
									




									
								</div>
					        </div>
					    </div>
					</div>
				</div>
			</div>
		</div>





<script>
$(".mgb30").click(function(){
$(this).parent().submit();
	
	
	
	
})



</script>



<jsp:include page="/FrontEnd/include/footer.jsp"/>