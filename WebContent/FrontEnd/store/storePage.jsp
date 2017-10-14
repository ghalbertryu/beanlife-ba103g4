<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.fo_prod.model.*"%>
<%@ page import="com.fo_store.model.*"%>
<%@ page import="com.review.model.*"%>
<%@ page import="com.like_rev.model.*"%>
<%@ page import="com.qa.model.*"%>



<!-- --------------------------------------------------店家---------------------------------------------------------------------- -->
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="fo_storeSvc" scope="page" class="com.fo_store.model.Fo_storeService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />
    
<c:set var="mem_ac" value="${session.mem_ac}" scope="page"/>
<c:set var="storeVO" value="${storeSvc.getOneStore(param.storeNo)}" scope="page"/>
<c:set var="prodSet" value="${storeSvc.getProdsByStoreR(param.storeNo)}" scope="page"/>
<c:set var="fo_prodlist" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="fo_storelist" value="${fo_storeSvc.getAllByMem(mem_ac)}" scope="page"/>

<!--     <div class="modal" id="store1"> -->
<!--       <div class="modal-dialog modal-lg"> -->
<!--         <div class="modal-content fix-h scrollbar-macosx"> -->

		
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
          </div>
          <div class="modal-body">

            <div class="container-fluid">
              <div class="row">
                <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                  
                  <div id="store-cas" class="carousel slide" data-ride="carousel">
                      <!-- 幻燈片主圖區 -->
                      <div class="carousel-inner">
                          <div class="item active">
                              <img class="img-responsive" src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" alt="store_pic1">
                          </div>
                          <div class="item">
                              <img class="img-responsive" src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=2" alt="store_pic2">
                          </div>
                          <div class ="item">
                              <img class="img-responsive" src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=3" alt="store_pic3">
                          </div>
                      </div>
                      <!-- 上下頁控制區 -->
                      <a class="left carousel-control" href="#store-cas" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
                      <a class="right carousel-control" href="#store-cas" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                  </div>
                    
                  

                </div>
              </div>
            </div>

            <div class="container-fluid">
              <div class="row">
                <div class="col-xs-12 col-sm-5 col-sm-offset-1">
                	<%
                        StoreVO storeVO = (StoreVO) pageContext.getAttribute("storeVO");
                        String store_cont = storeVO.getStore_cont().replaceAll("(\r\n|\n)", "<br>");
                        pageContext.setAttribute("store_cont",store_cont);

                        
                        //此會員對此Store是否Follow的Boolean
                        Boolean isFollow = false;
                        for (Fo_storeVO fo_storeVO: (List<Fo_storeVO>)pageContext.getAttribute("fo_storelist")){
                          if(fo_storeVO.getStore_no().equals(storeVO.getStore_no())){
                            isFollow = true;
                          }
                        }
                        pageContext.setAttribute("isFollow",isFollow);
                    %>
                
                  <h3 class="text-info bold">${storeVO.store_name}</h3>
                  <p>${store_cont}</p>

                </div>
                <div class="col-xs-12 col-sm-5 mgt20">
                  <div class="">
                    <p>
                      地址： ${storeVO.store_add}<br>
                      電話：  ${storeVO.store_phone}<br>
                    </p>
                    <button type="button" class="bk${storeVO.store_no} btn btn-default btn-sm zidx5 pull-right ${(isFollow)?'bor-info':''}" aria-label="Left Align">
                        <span class="bk${storeVO.store_no} count ${(isFollow)?'text-info':'tx-gray'}">${fo_storeSvc.getCountByStore(storeVO.store_no)}</span>
                        <span class="bk${storeVO.store_no} glyphicon glyphicon-bookmark ${(isFollow)?'text-info':'tx-gray'}" aria-hidden="true"></span>
                    </button>
                    <h4 class="text-warning">全店滿$${storeVO.store_free_ship}免運費</h4>

                    <small class="">店家編號 ${storeVO.store_no}</small>
                  </div>

                  <div id="map"></div>

                </div>
              </div>
            </div>


            <div class="container-fluid padt20">
              <div class="row">
        
                    <div class="col-xs-12 col-sm-10 col-sm-offset-1">
   
						<c:forEach var="prodVO" items="${prodSet}">
						<%
							String prod_no = ((ProdVO)pageContext.getAttribute("prodVO")).getProd_no();
							//此會員對此商品是否Follow的Boolean
                             	isFollow = false;
                             	for (Fo_prodVO fo_prodVO: (List<Fo_prodVO>)pageContext.getAttribute("fo_prodlist")){
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
	                      <div class="col-xs-12 col-sm-3 padt10">
	                        <a id="sp${prodVO.prod_no}" class="changeProd" name="${prodVO.prod_no}" href='${prodVO.prod_no}' data-toggle="modal">
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
				                     

				    	</c:forEach>




                    </div>
                  </div>
                </div>
          </div>


<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDe7Hy0dluCzRjH-1M55UoLXXFER4UbhXY&callback=initMap" async defer></script>
<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDe7Hy0dluCzRjH-1M55UoLXXFER4UbhXY&libraries=geometry&sensor=false&callback=initMap"></script> -->
<script>
  var map;
  function initMap() {
      var store = {lat: ${storeVO.store_add_lat}, lng: ${storeVO.store_add_lon}};
      var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: store
      });
      var marker = new google.maps.Marker({
        position: store,
        map: map
      });
  };

var $modalX = $("#modalX");
$(function(){
	//changeProd
	$(".changeProd").click(function(){
		changeProd($(this).attr("name"));
	});
});
  
function changeProd($prodNo){
// 	var prodNo =  $("#sp${prodVO.prod_no}").attr("href");
	var urlstr = '<%=request.getContextPath()%>/FrontEnd/prod/prodPage.jsp?prodNo='+$prodNo;
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
	$modalX.scrollTop(0);
}

//foStore
var $btnFoStore = $("#modal-inner button.bk${storeVO.store_no}").click(function(){
	if(${mem_ac==null}){
		 $('#modal-login').modal("show");
		 return false;
	}
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
