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
<%@ page import="com.mem.model.*"%>
<%@ page import="com.mem_grade.model.*"%>
<%@ page import="com.ord.model.*"%>



<!-- --------------------------------------------------商品---------------------------------------------------------------------- -->
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="ordSvc" scope="page" class="com.ord.model.OrdService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />
<jsp:useBean id="like_revSvc" scope="page" class="com.like_rev.model.Like_revService" />
<jsp:useBean id="qaSvc" scope="page" class="com.qa.model.QaService" />
<jsp:useBean id="mem_gradeSvc" scope="page" class="com.mem_grade.model.Mem_gradeService" />

<c:set var="mem_ac" value="${session.mem_ac}" scope="page"/>
<c:set var="prodVO" value="${prodSvc.getOneProdR(param.prodNo)}" scope="page"/>
<c:set var="storeVO" value="${storeSvc.getOneStore(prodVO.store_no)}"/>
<c:set var="fo_list" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="like_rev_list" value="${like_revSvc.getAllByMem(mem_ac)}" scope="page"/>

<%--     <div class="modal" id="prod${p_index.count}"> --%>
<!--         <div class="modal-dialog modal-lg"> -->
<!--             <div class="modal-content fix-h scrollbar-macosx" id="modalX"> -->


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

<%--     <div class="modal" id="prod${p_index.count}"> --%>
<!--         <div class="modal-dialog modal-lg"> -->
<!--             <div class="modal-content fix-h scrollbar-macosx" id="modalX"> -->

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title bold">${prodVO.prod_name}</h4>
                </div>
                <div class="modal-body padlr0">
                    <div class="container-fluid">


                        <div class="row">
                            <div class="col-xs-12 col-sm-6">



                                <!-- ---------------商品圖片-------------- -->
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-11 col-sm-offset-1">
                                            <div id="prod-cas" class="carousel slide" data-ride="carousel">
                                                <!-- 幻燈片主圖區 -->
                                                <div class="carousel-inner">
                                                    <div class="item active">
                                                        <img class="img-responsive w800" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1" alt="prod_pic1">
                                                    </div>
                                                    <div class="item">
                                                        <img class="img-responsive w800" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=2" alt="prod_pic2">
                                                    </div>
                                                    <div class="item">
                                                        <img class="img-responsive w800" src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=3" alt="prod_pic3">
                                                    </div>
                                                </div>
                                                <!-- 上下頁控制區 -->
                                                <a class="left carousel-control" href="#prod-cas" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
                                                <a class="right carousel-control" href="#prod-cas" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
                                            </div>
                                            
                                        </div>
                                    </div>


                                    <div class="row mgt20">
                                        <div class="col-xs-12 col-sm-11 col-sm-offset-1 pointer changeStore" id="pp${prodVO.store_no}" name="${prodVO.store_no}" href="${prodVO.store_no}" data-toggle="modal">
                                            <h4 class="bold text-info">${storeSvc.getonestore(prodVO.store_no).store_name}</h4>
                                            <p>
                                                地址： ${storeSvc.getonestore(prodVO.store_no).store_add}<br>
                                                電話：  ${storeSvc.getonestore(prodVO.store_no).store_phone}<br>
                                            </p>
                                        </div>
                                    </div>

                                </div>
                            </div>


                            <!-- ---------------商品資訊-------------- -->
                            <div class="col-xs-12 col-sm-5">
                                <div class="well">
                                    <p>
                                        <small class="pull-right">最後編輯時間<br>${prodVO.ed_time}</small>
                                        豆種：${prodVO.bean_type} <br>
                                        生豆等級：${prodVO.bean_grade}<br>
                                        <br>
                                        生產國：${prodVO.bean_contry}<br>
                                        地區：${prodVO.bean_region}<br>
                                        農場：${prodVO.bean_farm}<br>
                                        生產者：${prodVO.bean_farmer}<br>
                                        海拔：${prodVO.bean_el}m<br>
                                        <br>
                                        處理法：${prodVO.proc}<br>
                                        烘焙度：${prodVO.roast}<br>
                                        <br>
                                        <small>商品編號：${prodVO.prod_no}</small>
                                    </p>
                                </div>
                                <h4 class="text-info inline-b bold">NT$${prodVO.prod_price}　${prodVO.prod_wt} lb</h4>
                                <div class="pull-right mgt10" title="${reviewSvc.getScoreByProd(prodVO.prod_no)}/5.0">

	                              <span class="glyphicon glyphicon-star ${(star['0'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
	                              <span class="glyphicon glyphicon-star ${(star['1'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
	                              <span class="glyphicon glyphicon-star ${(star['2'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
	                              <span class="glyphicon glyphicon-star ${(star['3'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
	                              <span class="glyphicon glyphicon-star ${(star['4'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
	                              <span>(${reviewSvc.getCountByProd(prodVO.prod_no)})</span>    

                                </div>
                                <p>
                                    <button type="button" class="bk${prodVO.prod_no} btn btn-default btn-sm zidx5 pull-right ${(isFollow)?'bor-info':''}" aria-label="Left Align">
                                        <span class="bk${prodVO.prod_no} count ${(isFollow)?'text-info':'tx-gray'}">${fo_prodSvc.getCountByProd(prodVO.prod_no)}</span>
                                        <span class="bk${prodVO.prod_no} glyphicon glyphicon-bookmark ${(isFollow)?'text-info':'tx-gray'}" aria-hidden="true"></span>
                                    </button>
                                    標價/重量：<span class="bold text-danger">NT$<fmt:formatNumber value="${prodVO.prod_price/prodVO.prod_wt}" maxFractionDigits="1"/>/lb</span><br>
                                    運費：NT$${prodVO.send_fee}<span class="text-warning bold mgl20">滿$${storeSvc.getonestore(prodVO.store_no).store_free_ship}免運費</span><br>
                                    供應數量：${prodVO.prod_sup}
                                </p>
                                
								<c:if test="${prodVO.store_no!=storeSvc.getOneByMem(mem_ac).store_no}">
                                <div class="container-fluid">
                                    <div class="row">
                                    	<c:if test="${prodVO.prod_stat=='下架'}">
			                                <div class="btn btn-block btn-warning" disabled>下架中</div>
		                                </c:if>
                                        <div class="col-xs-7 col-sm-6 pad0">
                                        	<c:if test="${prodVO.prod_stat=='上架'}" var="disable">
                                            <span id="sub" class="glyphicon glyphicon-minus btn btn-default btn-sm btn-danger" aria-hidden="true"></span>
                                            <input class="btn w50" type="text" maxlength="3" name="amount" value="1">
                                            <span id="add" class="glyphicon glyphicon-plus  btn btn-default btn-sm btn-danger" aria-hidden="true"></span>
											</c:if>
                                        </div>
                                        <div class="col-xs-5 col-sm-6">
                                        	<c:if test="${prodVO.prod_stat=='上架'}">
                                            	<div id="intoCart" class="btn btn-default btn-info">加入購物車</div>
											</c:if>
                                        </div>
                                    </div>
                                </div>
								</c:if>

                            </div>
                        </div>


<script type="text/javascript">
	//add sub button
    $("#add").on("click", function(){
    	var $amount = Number($("[name='amount']").val());
        if(isNaN($amount)||$amount>=${prodVO.prod_sup}){
        	$amount = ${prodVO.prod_sup};
        } else {
            $amount++;
        }
        $("[name='amount']").val($amount);
    });
    $("#sub").on("click", function(){
        var $amount = Number($("[name='amount']").val());
        if(isNaN($amount)||$amount<=1){
            $amount = 1;
        } else {
            $amount--;
        }
        $("[name='amount']").val($amount);
    });
    //Number check
    $("[name='amount']").blur(function(){
    	var $amount = Number($("[name='amount']").val());
    	if(isNaN($amount)||$amount<=0){
    		$amount = 1;
    		$("[name='amount']").val(1);
    	} else if ($amount>${prodVO.prod_sup}){	
    		$amount = ${prodVO.prod_sup};
    		$("[name='amount']").val(${prodVO.prod_sup});
    	}
    });

    //intoCart
    var $btnIntoCart = $("#intoCart").click(function(){
    	if(${mem_ac==null}){
    		 $('#modal-login').modal("show");
    		 return false;
    	}
        var $action = "insert";
        var $prod_no = "${prodVO.prod_no}"
        var $amount = Number($("[name='amount']").val());
        var $mem_ac = "${mem_ac}";
        $.ajax({
            url : "<%=request.getContextPath()%>/cart_list/cart_listAjax.do",
            type : 'post',
            contentType: "application/json",
            data: JSON.stringify({action:$action, prod_no: $prod_no,mem_ac: $mem_ac, amount:$amount}),
            dataType: "JSON",
            async: false,
            success : function(jdata) {
                if(jdata.err!=null){
                    alert(jdata.err);
                } else {
                    $('#cartList').empty();
                    var cartSize = 0;
                    for(var i = 0; i<jdata.length; i++){
                        $('#cartList').append('<li><span class="text-info">'+
                                jdata[i].prod_name+'</span></li><li class="text-right"><span class="text-danger">$'+jdata[i].prod_price+
                                'ｘ'+jdata[i].amount+'</span></li>');
                        cartSize += Number(jdata[i].amount);
                    }
                    $('#cartList').append('<li role="presentation" class="divider"></li><a href="<%=request.getContextPath()%>/FrontEnd/cart/cart.jsp"><div  class="btn btn-info btn-sm pull-right">前往購物車</div></a>');
                    $('#cartSize').text(cartSize);
                    $("[name='amount']").val(1);
                    console.log('成功加入購物車');
                    isAdd=true;
                }   
            },
            error : function(xhr) {
                console.log('加入購物車失敗');
            }
        });
    });

    //foProd
    var $btnFoProd = $("#modal-inner button.bk${prodVO.prod_no}").click(function(){
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
                        $('.bk${prodVO.prod_no}').addClass('text-info');
                        $('.bk${prodVO.prod_no}').removeClass('tx-gray');
                        $('button.bk${prodVO.prod_no}').addClass('bor-info');
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



                        <div class="row mgt20">
                            <div class="col-xs-12 col-sm-6">
                                <%
                                    ProdVO prodVO = (ProdVO) pageContext.getAttribute("prodVO");
                                    String prod_cont = prodVO.getProd_cont().replaceAll("(\r\n|\n)", "<br>");
                                    pageContext.setAttribute("prod_cont",prod_cont);
                                %>
                                <!-- ---------------商品描述-------------- -->
                                <div class="container-fluid">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-11 col-sm-offset-1">
                                            <p>
                                               ${prod_cont}
                                            </p>
                                        </div>
                                    </div>
                                </div>



                            </div>
                            <div class="col-xs-12 col-sm-5 pad0">
                                <svg class="" height="300" >
                                    <polygon id="polygon1" points="" style="fill:white;stroke:black;stroke-width:1" />
                                    <polygon id="polygon2" points="" style="fill:white;stroke:black;stroke-width:1" />
                                    <polygon id="polygon3" points="" style="fill:white;stroke:black;stroke-width:1" />
                                    <polygon id="polygon4" points="" style="fill:white;stroke:black;stroke-width:1" />
                                    <polygon id="polygon5" points="" style="fill:white;stroke:black;stroke-width:1" />

                                    <polygon id="aroma" points="" style="fill:rgba(0, 154, 100, 0.6);stroke:rgb(0, 150, 150);stroke-width:2" />

                                    <line id="line1" x1="0" y1="0" x2="" y2="" style="stroke:silver;stroke-width:1" />
                                    <line id="line2" x1="0" y1="0" x2="" y2="" style="stroke:silver;stroke-width:1" />
                                    <line id="line3" x1="0" y1="0" x2="" y2="" style="stroke:silver;stroke-width:1" />
                                    <line id="line4" x1="0" y1="0" x2="" y2="" style="stroke:silver;stroke-width:1" />
                                    <line id="line5" x1="0" y1="0" x2="" y2="" style="stroke:silver;stroke-width:1" />

                                    <text id="b_body" x="" y="" style="font-weight:bold; fill:black;">醇度</text>
                                    <text id="b_acid" x="" y="" style="font-weight:bold; fill:black;">酸度</text>
                                    <text id="b_after" x="" y="" style="font-weight:bold; fill:black;">餘味</text>
                                    <text id="b_bal" x="" y="" style="font-weight:bold; fill:black;">平衡度</text>
                                    <text id="b_aroma" x="" y="" style="font-weight:bold; fill:black;">香氣</text>
                                </svg>

                            </div>
                        </div>









                        <!-- -----------------------------------------互動區塊--------------------------------------------- -->
                        <div class="row mgt20">
                            <div class="col-xs-12 col-sm-12">
                                <div role="tabpanel">
                                    <!-- 標籤面板：標籤區 -->
                                    <ul class="nav nav-tabs " role="tablist">
                                        <li role="presentation" class="${(param.status==2)?'':'active'} w50p text-center">
                                            <a href="#rev_prod${p_index.count}" aria-controls="rev_prod${p_index.count}" role="tab" data-toggle="tab" class="bold">心得評論</a>
                                        </li>
                                        <li role="presentation" class="${(param.status==2)?'active':''} w50p text-center">
                                            <a href="#qa_prod${p_index.count}" aria-controls="qa_prod${p_index.count}" role="tab" data-toggle="tab" class="bold">問與答</a>
                                        </li>
                                    </ul>






									


                                    <!-- 標籤面板：內容區 -->
                                    <div class="tab-content">
                                    
                                    	<!-- --------------------心得評論---------------- -->
                                        <div role="tabpanel" class="tab-pane ${(param.status==2)?'':'active'}" id="rev_prod${p_index.count}">
                                        
                                        	<c:set var="reviewlist" value="${reviewSvc.getVOByProd(prodVO.prod_no)}"/>

											<c:if test="${reviewlist.size()==0}">
												<div class="col-xs-12 col-sm-12 text-center padt10 mgb30">
													<h2 class="tx-gray">目前尚無評論</h2>
												</div>
											</c:if>
											
                                            <c:forEach var="reviewVO" items="${reviewlist}">
                                            
                                            <div class="row cus-art-row zidx0 mgt20">
                                            	<c:set var="revMem_ac" value="${ordSvc.getOrdByOrdno(reviewVO.ord_no).mem_ac}"/>
                                            	<c:set var="revMemVO" value="${memSvc.getOneMem(revMem_ac)}"/>
                                            	 <!-- -----------------Mem名稱------------------- -->
                                                <div class="col-xs-6 col-sm-1 col-sm-offset-1 padr0 ">
                                                    <img class="img-responsive mg-auto vam-img round-img" src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${revMem_ac}">
                                                </div>
                                               
                                                <div class="col-xs-6 col-sm-2">
                                                    <span class="text-info">${revMemVO.mem_ac}</span>
                                                    <br>
                                                    <span>${mem_gradeSvc.getOneMem_grade(revMemVO.grade_no).grade_title}</span>
                                                    <br>
                                                    <small>${reviewVO.rev_date}</small>
                                                </div>
                                                
                                                
                                                <!-- -----------------沖泡方式------------------- -->               
                                                <c:set var="use_way" value="${fn:split(reviewVO.use_way, ',')}" />
                                                <div class="col-xs-12 col-sm-4 col-sm-offset-1 mgt10 mg-auto">
                                                    <div class="container-fluid padt10">
                                                        <div class="row">
                                                            <div class="col-xs-3 col-sm-3 text-center">
                                                                <img class="img-responsive mg-auto inline-b w45 " src="<%=request.getContextPath()%>/FrontEnd/res\img\icon\weight.png">
                                                                <small class="text-info">${use_way[0]}g</small>
                                                            </div>
                                                            <div class="col-xs-3 col-sm-3 text-center">
                                                                <img class="img-responsive mg-auto inline-b w45" src="<%=request.getContextPath()%>/FrontEnd/res\img\icon\kettle.png">
                                                                <small class="text-info">${use_way[1]}ml</small>
                                                            </div>
                                                            <div class="col-xs-3 col-sm-3 text-center">
                                                                <img class="img-responsive mg-auto inline-b w45" src="<%=request.getContextPath()%>/FrontEnd/res\img\icon\drgree.png">
                                                                <small class="text-info">${use_way[2]}°C</small>
                                                            </div>
                                                            <div class="col-xs-3 col-sm-3 text-center">
                                                                <img class="img-responsive mg-auto inline-b w45" src="<%=request.getContextPath()%>/FrontEnd/res\img\icon\timer.png">
                                                                <small class="text-info"><fmt:formatNumber value="${use_way[3]/60}" maxFractionDigits="0"/>m${use_way[3]%60}s</small>
                                                                 
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>
                                                
                                                
                                                
	                                            <%
		                                            //此評論的分數轉換星星Boolean
		                                          	Boolean [] starOfRev = new Boolean[5];
		                                          	int scoreOfRev = ((ReviewVO)pageContext.getAttribute("reviewVO")).getProd_score();
		                                          	for (int i = 0 ; i < scoreOfRev ; i++){
		                                          		starOfRev[i] = true;
		                                          	}
		                                          	pageContext.setAttribute("starOfRev",starOfRev); 
		                                          	
		                                          	
		                                          	String rev_no = ((ReviewVO)pageContext.getAttribute("reviewVO")).getRev_no();
		                                        	//此會員對此Review是否Like的Boolean
		                                          	Boolean isLike = false;
		                                          	List<Like_revVO> like_rev_list = (List<Like_revVO>)pageContext.getAttribute("like_rev_list");
		                                          	for (Like_revVO like_revVO: like_rev_list){
		                                          		if(like_revVO.getRev_no().equals(rev_no)){	                              			
		                                          			isLike = true;
		                                          		}
		                                          	}
		                                          	pageContext.setAttribute("isLike",isLike);
	                                            %>    
                                              
                                                 <!-- -----------------給分 喜歡------------------- -->
                                                <div class="col-xs-12 col-sm-2 col-sm-offset-1">

                                                    <div class="container-fluid">
                                                        <div class="row">
                                                            <div class="col-xs-6 col-sm-12 padt10">
                                                                <span class="glyphicon glyphicon-star ${(starOfRev['0'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
                                                                <span class="glyphicon glyphicon-star ${(starOfRev['1'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
                                                                <span class="glyphicon glyphicon-star ${(starOfRev['2'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
                                                                <span class="glyphicon glyphicon-star ${(starOfRev['3'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
                                                                <span class="glyphicon glyphicon-star ${(starOfRev['4'])? 'tx-brn' : 'tx-gray'}" aria-hidden="true"></span>
                                                            </div>

                                                             <div class="col-xs-6 col-sm-12 padt10">                                              
                                                                <button type="button" class="lk${reviewVO.rev_no} btn btn-default btn-xs zidx5 ${(isLike)?'bor-like':''}" aria-label="Left Align">
                                                                    <span class="lk${reviewVO.rev_no} count ${(isLike)?'tx-like':'tx-gray'}">${like_revSvc.getCountByRev(reviewVO.rev_no)}</span>
                                                                    <span class="lk${reviewVO.rev_no} glyphicon glyphicon-heart ${(isLike)?'tx-like':'tx-gray'}" aria-hidden="true"></span>
                                                                </button>
                                                            </div>



                                                        </div>
                                                    </div>
                                                </div>

                                                <!-- -----------------心得內文------------------- -->
                                                <div class="col-xs-12 col-sm-10 col-sm-offset-1  mgt10">
                                                    <p>${reviewVO.rev_cont}</p>
                                                </div>

                                            </div>


<script type="text/javascript">
//likeRev
var $btnLikeRev = $("#modal-inner button.lk${reviewVO.rev_no}").click(function(){
	if(${mem_ac==null}){
		 $('#modal-login').modal("show");
		 return false;
	}
    var $action = "likeRev";
    var $rev_no = "${reviewVO.rev_no}"
    $.ajax({
        url : "<%=request.getContextPath()%>/like_rev/like_revAjax.do",
        type : 'post',
        contentType: "application/json",
        data: JSON.stringify({action:$action, rev_no: $rev_no}),
        dataType: "JSON",
        async: false,
        success : function(jdata) {
            if(jdata.err!=null){
                alert(jdata.err);
            } else {
                if(jdata.isAdd==1){
		            $('.lk${reviewVO.rev_no}.count').each(function(){$(this).text(jdata.count)})
                    $('.lk${reviewVO.rev_no}').addClass('tx-like');
                    $('.lk${reviewVO.rev_no}').removeClass('tx-gray');
                    $('button.lk${reviewVO.rev_no}').addClass('bor-like');
                } else{
		            $('.lk${reviewVO.rev_no}.count').each(function(){$(this).text(jdata.count)})
                    $('button.lk${reviewVO.rev_no}').removeClass('bor-like');
                    $('.lk${reviewVO.rev_no}').removeClass('tx-like');
                    $('.lk${reviewVO.rev_no}').addClass('tx-gray');
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
                                        
                                        
                                        
                                        
                                        
                                         <!-- --------------------問與答---------------- -->
                                        <div role="tabpanel" class="${(param.status==2)?'active':''} tab-pane" id="qa_prod${p_index.count}">
                                        	
                                        	<c:set var="qalist" value="${qaSvc.getVOByProd(prodVO.prod_no)}"/>
											
											<c:if test="${qalist.size()==0}">
												<div class="col-xs-12 col-sm-12 text-center padt10 mgb30">
													<h2 class="tx-gray">目前尚無人提問</h2>
												</div>
											</c:if>
											
                                            <c:forEach var="qaVO" items="${qalist}">
                                            <c:set var="qaMem_ac" value="${qaVO.mem_ac}"/>
                                            <c:set var="qaMemVO" value="${memSvc.getOneMem(qaMem_ac)}"/>
                                            <div class="row cus-qa-row zidx0 mgt20">
                                            	<%
                                            		String qaMem_acReplaced = (String) pageContext.getAttribute("qaMem_ac");
                                            		qaMem_acReplaced = qaMem_acReplaced.replaceAll("(?<=\\w{1})\\w(?=\\w{1})", "*");
							                        pageContext.setAttribute("qaMem_acReplaced",qaMem_acReplaced);
							                    %>                            
                                                <div class="col-xs-12 col-sm-9 col-sm-offset-1">
                                                    <span class="text-info">${qaMem_acReplaced}</span><small class="mgl20">${qaVO.qa_date}</small>
                                                    <p>${qaVO.qa_cont}</p>
                                                </div>

												<c:if test="${qaVO.qa_reply_cont!=null}">
                                                <div class="col-xs-12 col-sm-9 col-sm-offset-2 well">
                                                    <span class="text-info">${storeSvc.getonestore(prodVO.store_no).store_name}</span><small class="mgl20">${qaVO.qa_reply_date}</small>
                                                    <p>${qaVO.qa_reply_cont}</p>
                                                </div>
                                                </c:if>

                                                <c:if test="${qaVO.qa_reply_cont==null&&storeVO.mem_ac==mem_ac}">
                                                  <form method="get" action="prodPage.jsp" >
                                                    <div class="col-xs-12 col-sm-9 col-sm-offset-2">
                                                      <div class="form-group">
                                                         <textarea class="form-control" rows="3" id="qa_reply_cont${qaVO.qa_no}"  name="qa_reply_cont" qa_no="${qaVO.qa_no}" placeholder="回覆..." required></textarea>
                                                        </div>
                                                        <span class="btn btn-default btn-sm mgb10 pull-right" id="replyBtn${qaVO.qa_no}" >回覆</span>
                                               <!--          <input type="submit" name="replyBtn${qaVO.qa_no}" value="回覆" class="btn btn-default btn-sm mgb10 pull-right" id="replyBtn${qaVO.qa_no}" > -->
                                                    </div>
                                                  </form>
                                                </c:if>

                                            </div>



<script type="text/javascript">
    
var $replyBtn = $("#replyBtn${qaVO.qa_no}").click(function(){
		if(${mem_ac==null}){
			 $('#modal-login').modal("show");
			 return false;
		}
        var $action = "replyQa";
        var $prod_no =  "${prodVO.prod_no}";
        var $qa_no =  $("#qa_reply_cont${qaVO.qa_no}").attr('qa_no');
        var $qa_reply_cont = $('#qa_reply_cont${qaVO.qa_no}').val();
        var urlstr = '<%=request.getContextPath()%>/qa/qaAjax.do';
        $.ajax({
            url : urlstr,
            type : 'POST',
            contentType: "application/json",
            data: JSON.stringify({action:$action, qa_no: $qa_no,prod_no:$prod_no, qa_reply_cont:$qa_reply_cont}),
            dataType: "html",
            async: false,
            success : function(result) {
                if(result.err!=null){
                    console.log(result.err);
                }else{
                     while($modalX.children().length > 0){
                        $modalX.empty();
                    }
                    $modalX.html(result);
                }
            },
            error : function(xhr) {
                alert('Ajax request 發生錯誤');
            }
        });
    });

</script>







                                            </c:forEach> <!-- qaVO -->

                                            <c:if test="${storeVO.mem_ac!=mem_ac&&mem_ac!=null}">
                                            <div class="row mgt20">
                                                <div class="container-fluid">
                                                    <div class="row">
                                                        <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                                                          <div class="form-group">
                                                              <textarea class="form-control" rows="5" id="qa_cont"  name="qa_cont" placeholder="${(param.errQa_cont==null)?'提出詢問...':param.errQa_cont}" required></textarea>
                                                        
                                                            </div>
                                                            <span class="btn btn-default btn-sm pull-right" id="askBtn" >提問</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            </c:if>









                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>



                    </div>
                </div>


<script>

var $modalX = $("#modalX");
$(function(){
	//changeStore
	$(".changeStore").click(function(){
		changeStore($(this).attr("name"));
	});
});

//change store function
function changeStore($storeNo){
// 	var storeNo =  $("#pp${prodVO.store_no}").attr("href");
    var urlstr = '<%=request.getContextPath()%>/FrontEnd/store/storePage.jsp?storeNo='+ $storeNo;
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

var $askBtn = $("#askBtn").click(function(){
		if(${mem_ac==null}){
			 $('#modal-login').modal("show");
			 return false;
		}
        var $action = "addQa";
        var $prod_no =  "${prodVO.prod_no}";
        var $qa_cont = $('#qa_cont').val();
        var urlstr = '<%=request.getContextPath()%>/qa/qaAjax.do';
        $.ajax({
            url : urlstr,
            type : 'POST',
            contentType: "application/json",
            data: JSON.stringify({action:$action, prod_no: $prod_no,qa_cont:$qa_cont}),
            dataType: "html",
            async: false,
            success : function(result) {
                if(result.err!=null){
                    console.log(result.err);
                }else{
                     while($modalX.children().length > 0){
                        $modalX.empty();
                    }
                    $modalX.html(result);
                }
            },
            error : function(xhr) {
                alert('Ajax request 發生錯誤');
            }
        });
    });

//draw bean_attribute pic
function setPol(){
    var p = 2*Math.PI/5;
    var s = Math.PI/10;
    var r=100;
    var c = 150;
    var step=20;

    //draw line
    $("#line1").attr("x1",c+r*Math.cos(p*1-s)).attr("y1",c+r*Math.sin(p*1-s)).attr("x2",c).attr("y2",c);
    $("#line2").attr("x1",c+r*Math.cos(p*2-s)).attr("y1",c+r*Math.sin(p*2-s)).attr("x2",c).attr("y2",c);
    $("#line3").attr("x1",c+r*Math.cos(p*3-s)).attr("y1",c+r*Math.sin(p*3-s)).attr("x2",c).attr("y2",c);
    $("#line4").attr("x1",c+r*Math.cos(p*4-s)).attr("y1",c+r*Math.sin(p*4-s)).attr("x2",c).attr("y2",c);
    $("#line5").attr("x1",c+r*Math.cos(p*0-s)).attr("y1",c+r*Math.sin(p*0-s)).attr("x2",c).attr("y2",c);

    //draw text
    var tx = r+step;
    $('#b_body').attr('x',c+tx*Math.cos(p*4-s)-step/1.4).attr('y',c+tx*Math.sin(p*4-s)+step/1.7);
    $('#b_acid').attr('x',c+tx*Math.cos(p*5-s)-step/1.4).attr('y',c+tx*Math.sin(p*5-s));
    $('#b_after').attr('x',c+tx*Math.cos(p*1-s)-step/1.4).attr('y',c+tx*Math.sin(p*1-s));
    $('#b_bal').attr('x',c+tx*Math.cos(p*2-s)-step/1.4).attr('y',c+tx*Math.sin(p*2-s));
    $('#b_aroma').attr('x',c+tx*Math.cos(p*3-s)-step/1.4).attr('y',c+tx*Math.sin(p*3-s));

    //draw 5polygon
    $("#polygon1").attr("points",
                (c+r*Math.cos(p*1-s)) + "," + (c+r*Math.sin(p*1-s)) + " " +
                (c+r*Math.cos(p*2-s)) + "," + (c+r*Math.sin(p*2-s)) + " " +
                (c+r*Math.cos(p*3-s)) + "," + (c+r*Math.sin(p*3-s)) + " " +
                (c+r*Math.cos(p*4-s)) + "," + (c+r*Math.sin(p*4-s)) + " " +
                (c+r*Math.cos(p*0-s)) + "," + (c+r*Math.sin(p*0-s)) + " "
                 );
    r-=step;
    $("#polygon2").attr("points",
                (c+r*Math.cos(p*1-s)) + "," + (c+r*Math.sin(p*1-s)) + " " +
                (c+r*Math.cos(p*2-s)) + "," + (c+r*Math.sin(p*2-s)) + " " +
                (c+r*Math.cos(p*3-s)) + "," + (c+r*Math.sin(p*3-s)) + " " +
                (c+r*Math.cos(p*4-s)) + "," + (c+r*Math.sin(p*4-s)) + " " +
                (c+r*Math.cos(p*0-s)) + "," + (c+r*Math.sin(p*0-s)) + " " 
                 );
    r-=step;
    $("#polygon3").attr("points",
                (c+r*Math.cos(p*1-s)) + "," + (c+r*Math.sin(p*1-s)) + " " +
                (c+r*Math.cos(p*2-s)) + "," + (c+r*Math.sin(p*2-s)) + " " +
                (c+r*Math.cos(p*3-s)) + "," + (c+r*Math.sin(p*3-s)) + " " +
                (c+r*Math.cos(p*4-s)) + "," + (c+r*Math.sin(p*4-s)) + " " +
                (c+r*Math.cos(p*0-s)) + "," + (c+r*Math.sin(p*0-s)) + " " 
                 );
    r-=step;
    $("#polygon4").attr("points",
                (c+r*Math.cos(p*1-s)) + "," + (c+r*Math.sin(p*1-s)) + " " +
                (c+r*Math.cos(p*2-s)) + "," + (c+r*Math.sin(p*2-s)) + " " +
                (c+r*Math.cos(p*3-s)) + "," + (c+r*Math.sin(p*3-s)) + " " +
                (c+r*Math.cos(p*4-s)) + "," + (c+r*Math.sin(p*4-s)) + " " +
                (c+r*Math.cos(p*0-s)) + "," + (c+r*Math.sin(p*0-s)) + " " 
                 );
    r-=step;
    $("#polygon5").attr("points",
                (c+r*Math.cos(p*1-s)) + "," + (c+r*Math.sin(p*1-s)) + " " +
                (c+r*Math.cos(p*2-s)) + "," + (c+r*Math.sin(p*2-s)) + " " +
                (c+r*Math.cos(p*3-s)) + "," + (c+r*Math.sin(p*3-s)) + " " +
                (c+r*Math.cos(p*4-s)) + "," + (c+r*Math.sin(p*4-s)) + " " +
                (c+r*Math.cos(p*0-s)) + "," + (c+r*Math.sin(p*0-s)) + " " 
                 );
  	//draw bean_attr
    $("#aroma").attr("points",
                (c+step*${prodVO.bean_attr_body}*Math.cos(p*4-s)) + "," + (c+step*${prodVO.bean_attr_body}*Math.sin(p*4-s)) + " " +
                (c+step*${prodVO.bean_attr_acid}*Math.cos(p*5-s)) + "," + (c+step*${prodVO.bean_attr_acid}*Math.sin(p*5-s)) + " " +
                (c+step*${prodVO.bean_attr_after}*Math.cos(p*1-s)) + "," + (c+step*${prodVO.bean_attr_after}*Math.sin(p*1-s)) + " " +
                (c+step*${prodVO.bean_attr_bal}*Math.cos(p*2-s)) + "," + (c+step*${prodVO.bean_attr_bal}*Math.sin(p*2-s)) + " " +
                (c+step*${prodVO.bean_attr_aroma}*Math.cos(p*3-s)) + "," + (c+step*${prodVO.bean_attr_aroma}*Math.sin(p*3-s)) + " "
                 );
}

    setPol();


</script>
