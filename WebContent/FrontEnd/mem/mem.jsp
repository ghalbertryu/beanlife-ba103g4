<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem_grade.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.fo_prod.model.*"%>
<%@ page import="com.fo_store.model.*"%>
<%@ page import="com.review.model.*"%>

<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="mem_gradeSvc" scope="page" class="com.mem_grade.model.Mem_gradeService" />
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="fo_storeSvc" scope="page" class="com.fo_store.model.Fo_storeService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="memVO" value="${memSvc.getOneMem(mem_ac)}" scope="page"/>
<c:set var="fo_prodList" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="fo_storeList" value="${fo_storeSvc.getAllByMem(mem_ac)}" scope="page"/>



<div class="container mgt-depn-nav-lg">
		<div class="row">
			<div class="col-xs-12 col-sm-8 col-sm-offset-2 mgb10">
				<h3 class="bold">會員中心</h3>
			</div>
			<div class="col-xs-8 col-xs-offset-2 col-sm-2 col-sm-offset-5">
				<img class="round-img img-responsive" src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${memVO.mem_ac}">
			</div>

			<div class="col-xs-12 col-sm-4 col-sm-offset-4 text-center mgt20">
				<span>${memVO.mem_ac}</span><br>
				${mem_gradeSvc.getOneMem_grade(memVO.grade_no).grade_title}　${memVO.mem_pt}/${memVO.mem_total_pt}<br>
				<small><a href="<%=request.getContextPath()%>/FrontEnd/reg_mem/mem_data.jsp">修改資料</a>　<a href="<%=request.getContextPath()%>/FrontEnd/gift/gift_data_frontEnd.jsp">積分兌換</a>　<a href="<%=request.getContextPath()%>/FrontEnd/reg_store/UpToStore.jsp">申請店家</a></small>
			</div>
		</div>
	</div>

	<div class="container content mgt20 mgb40">
		<div class="row">
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">
				
				<div role="tabpanel">
				    <!-- 標籤面板：標籤區 -->
				    <ul class="nav nav-tabs" role="tablist">
				        <li role="presentation" class="active w33p text-center bold">
				            <a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">收藏商品</a>
				        </li>
				        <li role="presentation" class="w33p text-center bold">
				            <a href="#tab2" aria-controls="tab2" role="tab" data-toggle="tab">收藏店家</a>
				        </li>
				        <li role="presentation" class="w33p text-center bold">
				            <a href="#tab3" aria-controls="tab3" role="tab" data-toggle="tab">收藏活動</a>
				        </li>
				    </ul>
				
				    <!-- 標籤面板：內容區 -->
				    <div class="tab-content">
				        <div role="tabpanel" class="tab-pane active" id="tab1">
				        
				        
				<c:if test="${fo_prodList.size()==0}">
					<div class="col-xs-12 col-sm-12 text-center padt20">
						<h2 class="tx-gray">目前尚無收藏商品</h2>
					</div>
				</c:if>
							
				 <!-- ------------------商品陳列結果---------------- -->
    			<c:forEach var="fo_prodVO" items="${fo_prodList}" varStatus="p_index">
    			<c:set var="prodVO" value="${prodSvc.getOneProdR(fo_prodVO.prod_no)}"/>
    			<%
		    		String prod_no = ((ProdVO)pageContext.getAttribute("prodVO")).getProd_no();
		    		//此會員對此商品是否Follow的Boolean
		           	Boolean isFollow = false;
		           	List<Fo_prodVO> fo_prodList = (List<Fo_prodVO>)pageContext.getAttribute("fo_prodList");
		           	for (Fo_prodVO fo_prodVO: fo_prodList){
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
			  	<a id="${prodVO.prod_no}" href='#modal-inner' data-toggle="modal">
                <div class="row cus-prod-row zidx0">
                  <div class="col-xs-8  col-xs-offset-2 col-sm-2 col-sm-offset-0 vam-div120">
                    <img class="img-responsive mg-auto vam-img  rd10" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
                  </div>
                    <div class="col-xs-12 col-sm-10">
                      <small class="inline-b pull-right">收藏時間：${fo_prodVO.fo_date}</small>
                      <h4 class="inline-b bold">${prodVO.prod_name}</h4>
                      <div>
                        <h5 class="inline-b bold text-info">NT$ ${prodVO.prod_price}　${prodVO.bean_contry}　${prodVO.proc}　${prodVO.roast}　${storeSvc.getonestore(prodVO.store_no).store_name}</h5>
                        <button type="button" class="bk${prodVO.prod_no} btn btn-default btn-sm inline-b pull-right zidx5 ${(isFollow)?'bor-info':''}" aria-label="Left Align">
                            <span class="bk${prodVO.prod_no} count ${(isFollow)?'text-info':'tx-gray'}">${fo_prodSvc.getCountByProd(prodVO.prod_no)}</span>
                            <span class="bk${prodVO.prod_no} glyphicon glyphicon-bookmark ${(isFollow)?'text-info':'tx-gray'}" aria-hidden="true"></span>
                        </button>
                        <div class="pull-right mgr20" title="${reviewSvc.getScoreByProd(prodVO.prod_no)}/5.0">
                       		<span class="glyphicon glyphicon-star ${(star['0'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				            <span class="glyphicon glyphicon-star ${(star['1'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				            <span class="glyphicon glyphicon-star ${(star['2'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				            <span class="glyphicon glyphicon-star ${(star['3'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
				            <span class="glyphicon glyphicon-star ${(star['4'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>                   
                            <span>(${reviewSvc.getCountByProd(prodVO.prod_no)})</span>
                        </div>
                      </div>
                      <p>${fn:substring(prodVO.prod_cont,0,100)} ${(prodVO.prod_cont.length() >100)? '...' : ''}</p>
                    </div>
                </div>
                </a>
                
                
<script>
//show Prod
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


//foProd
var $btnFoProd = $("button.bk${prodVO.prod_no}").click(function(){
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

				</c:forEach>







				        </div>
				        <div role="tabpanel" class="tab-pane" id="tab2">
				        
				        
						<c:if test="${fo_storeList.size()==0}">
							<div class="col-xs-12 col-sm-12 text-center padt20">
								<h2 class="tx-gray">目前尚無收藏店家</h2>
							</div>
						</c:if>
							
							
						<c:forEach var="fo_storeVO" items="${fo_storeList}" varStatus="s_index">
    					<c:set var="storeVO" value="${storeSvc.getOneStore(fo_storeVO.store_no)}"/>					
	                	<%
	                        StoreVO storeVO = (StoreVO) pageContext.getAttribute("storeVO");
  
	                        //此會員對此Store是否Follow的Boolean
	                        Boolean isFollow = false;
	                        for (Fo_storeVO fo_storeVO: (List<Fo_storeVO>)pageContext.getAttribute("fo_storeList")){
	                          if(fo_storeVO.getStore_no().equals(storeVO.getStore_no())){
	                            isFollow = true;
	                          }
	                        }
	                        pageContext.setAttribute("isFollow",isFollow);
	                    %>
    					
						<a id="${storeVO.store_no}" href='#modal-inner' data-toggle="modal">
		                <div class="row cus-prod-row zidx0">
		                  <div class="col-xs-8  col-xs-offset-2 col-sm-2 col-sm-offset-0 vam-div120 pad0">
		                    <img class="img-responsive mg-auto vam-img  rd10" src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1">
		                  </div>
		                    <div class="col-xs-12 col-sm-10">
		                      <small class="inline-b pull-right">收藏時間：${fo_storeVO.fo_date}</small>
		                      <h4 class="inline-b bold">${storeVO.store_name}</h4>
		                      <div>
		                        <button type="button" class="bk${storeVO.store_no} btn btn-default btn-sm inline-b pull-right zidx5 ${(isFollow)?'bor-info':''}" aria-label="Left Align">
		                            <span class="bk${storeVO.store_no} count ${(isFollow)?'text-info':'tx-gray'}">${fo_storeSvc.getCountByStore(storeVO.store_no)}</span>
		                            <span class="bk${storeVO.store_no} glyphicon glyphicon-bookmark ${(isFollow)?'text-info':'tx-gray'}" aria-hidden="true"></span>
		                        </button>
		                     
		                      </div>
		                      <p>${fn:substring(storeVO.store_cont,0,120)} ${(storeVO.store_cont.length() >120)? '...' : ''}</p>
		                    </div>
		                </div>
		                </a>
		                
		                
<script>
var $modalX = $("#modalX");
var $btn = $("#${storeVO.store_no}").click(function(){
        var storeNo =  '${storeVO.store_no}';
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
        $("#modal-inner").on('shown.bs.modal', function () {
        	 initMap();
   	 	});
    });
    
    

//foStore
$("button.bk${storeVO.store_no}").click(function(){
  var $action = "foStore";
  var $store_no = "${storeVO.store_no}"
  $.ajax({
      url : "<%=request.getContextPath()%>/fo_store/fo_storeAjax.do",
      type : 'post',
      contentType: "application/json",
      data: JSON.stringify({action:$action, store_no: $store_no}),
      dataType: "JSON",
      async: false,
      success : function(jdata) {
          if(jdata.err!=null){
              alert(jdata.err);
          } else {
              if(jdata.isAdd==1){
                  $('.bk${storeVO.store_no}.count').each(function(){$(this).text(jdata.count)})
                  $('.bk${storeVO.store_no}').addClass('text-info');
                  $('.bk${storeVO.store_no}').removeClass('tx-gray');
                  $('button.bk${storeVO.store_no}').addClass('bor-info');
              } else{
                  $('.bk${storeVO.store_no}.count').each(function(){$(this).text(jdata.count)})
                  $('button.bk${storeVO.store_no}').removeClass('bor-info');
                  $('.bk${storeVO.store_no}').removeClass('text-info');
                  $('.bk${storeVO.store_no}').addClass('tx-gray');
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
		                
		                
		                
		                </c:forEach>






				        </div>
				        <div role="tabpanel" class="tab-pane" id="tab3">
<%-- 							<c:if test="${reviewlist.size()==0}"> --%>
								<div class="col-xs-12 col-sm-12 text-center padt20">
									<h2 class="tx-gray">目前尚無收藏活動</h2>
								</div>
<%-- 							</c:if> --%>

				        </div>
				    </div>
				</div>

			</div>
		</div>
	</div>


<jsp:include page="/FrontEnd/include/footer.jsp"/>