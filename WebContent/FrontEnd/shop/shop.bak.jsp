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


<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="like_revSvc" scope="page" class="com.like_rev.model.Like_revService" />
<jsp:useBean id="qaSvc" scope="page" class="com.qa.model.QaService" />
<jsp:useBean id="cart_listSvc" scope="page" class="com.cart_list.model.Cart_listService" />


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="prodlist" value="${prodSvc.all}" scope="page"/>
<c:set var="fo_list" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="like_rev_list" value="${like_revSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="cart_listSet" value="${cart_listSvc.getVOsByMem(mem_ac)}" scope="page"/>





    <!-- funcbar -->
    <nav id="funcbar" class="navbar navbar-default navbar-fixed-top mgt-depn-nav func-h zidx1 bg-light-brn border0" role="navigation">
      <div class="container padt8">
        <div class="col-xs-12 col-sm-2 col-sm-offset-1">
          <select class="form-control">
            <option>請選擇產地</option>
                <option>巴西</option>
                <option>越南</option>
                <option>台灣</option>
                <option>爪哇</option>
                <option>越南</option>
                <option>台灣</option>
                <option>爪哇</option>
            </select>
        </div>
        <div class="col-xs-12 col-sm-2">
          <select class="form-control">
            <option>請選擇處理法</option>
                <option>日曬  </option>
                <option>半水洗</option>
                <option>水洗</option>
                <option>蜜處理</option>
            </select>
        </div>
        <div class="col-xs-12 col-sm-2 padt8">
          <input type="range" name="points" min="0" max="7">
        </div>
        <div class="col-xs-12 col-sm-4">
          <form>
           <div class="input-group">
             <input type="text" class="form-control" placeholder="Search">
             <div class="input-group-btn">
               <button class="btn btn-default" type="submit">
                 <i class="glyphicon glyphicon-search"></i>
               </button>
             </div>
           </div>
          </form>
        </div>
      </div>
    </nav>














    <!-- -------------------------------------------------------廣告 ------------------------------>
    <div class="container mgt120 mgb30">
      <div class="row">
        <div class="col-xs-12 col-sm-10 col-sm-offset-1">
          <div id="ad-cas" class="carousel slide" data-ride="carousel">
              <!-- 幻燈片主圖區 -->
              <div class="carousel-inner">
                  <div class="item active">
                      <img src="<%=request.getContextPath()%>/FrontEnd/res/img/ad1.png" alt="ad_pic1">
                  </div>
                  <div class="item">
                      <img src="<%=request.getContextPath()%>/FrontEnd/res/img/ad2.png" alt="ad_pic2">
                  </div>
                  <div class="item">
                      <img src="<%=request.getContextPath()%>/FrontEnd/res/img/ad3.png" alt="ad_pic3">
                  </div>
              </div>
              <!-- 上下頁控制區 -->
              <a class="left carousel-control" href="#ad-cas" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
              <a class="right carousel-control" href="#ad-cas" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
          </div>
        </div>
      </div>
    </div>



















    <!--CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC-->



    <div class="container content">
      <div class="row ">

        <div class="col-xs-12 col-sm-8 col-sm-offset-2 mgb30">
          <div class="container-floid">
            <div class="row">
              <div class="col-xs-12 col-sm-12">
                <div class="pull-left inline-b">
                  <h4 class="text-danger">找到${prodlist.size()}項商品</h4>
                </div>
                <div class="pull-right inline-b">
                  <select class="form-control ">
                        <option>排序依評價</option>
                        <option>排序依熱門</option>
                        <option>排序依日期</option>
                    </select>
                </div>
              </div>
            </div>
          </div>

          <div class="container-floid">



            <!-- ------------------商品陳列結果---------------- -->

    			<c:forEach var="prodVO" items="${prodlist}" varStatus="p_index">
    			<%
		    		String prod_no = ((ProdVO)pageContext.getAttribute("prodVO")).getProd_no();
		    		//此會員對此商品是否Follow的Boolean
		           	Boolean isFollow = false;
		           	List<Fo_prodVO> fo_list = (List<Fo_prodVO>)pageContext.getAttribute("fo_list");
		           	for (Fo_prodVO fo_prodVO: fo_list){
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
			         	<a id="${prodVO.prod_no}" href='#modal-id' data-toggle="modal">
                <div class="row cus-prod-row zidx0">
                  <div class="col-xs-12 col-sm-2 vam-div150">
                    <img class="img-responsive mg-auto vam-img  rd10" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1">
                  </div>
                    <div class="col-xs-12 col-sm-10">
                      <h4 class="inline-b bold">${prodVO.prod_name}</h4><small class="inline-b pull-right">${prodVO.ed_time}</small>
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
                      <p>${fn:substring(prodVO.prod_cont,0,120)} ${(prodVO.prod_cont.length() >120)? '...' : ''}</p>
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
        </div>
      </div>
    </div>



<jsp:include page="/FrontEnd/include/footer.jsp"/>