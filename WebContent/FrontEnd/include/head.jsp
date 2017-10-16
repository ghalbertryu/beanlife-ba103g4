<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="java.util.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.cart_list.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.msg.model.*"%>
<%@ page import="com.sys_msg.model.*"%>

<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="cart_listSvc" scope="page" class="com.cart_list.model.Cart_listService" />
<jsp:useBean id="msgSvc" scope="page" class="com.msg.model.MsgService" />
<jsp:useBean id="sys_msgSvc" scope="page" class="com.sys_msg.model.Sys_msgService" />
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />

<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="urNames" value="${msgSvc.getAllPairByMem(mem_ac)}" scope="page"/>
<c:set var="cart_listSet" value="${cart_listSvc.getVOsByMem(mem_ac)}" scope="page"/>






<!DOCTYPE html>
<html lang="">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>BeanLife</title>
    <link rel="Shortcut Icon" type="image/x-icon" href="<%=request.getContextPath()%>/FrontEnd/res/img/logo/BeanLifeIco2.ico" />

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
    <link href="<%=request.getContextPath()%>/FrontEnd/res/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/res/plugin/jquery.scrollbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/FrontEnd/res/css/beanlife.base.css">
    
    
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/FrontEnd/res/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/res/plugin/jquery.scrollbar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/res/js/beanlife.base.js"></script>
	
	<!--     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"> -->
	<!-- 	<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script> -->



  </head>
  <body>

    <!-- NavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbarNavbar -->
    <nav class="navbar navbar-default navbar-fixed-top bg-light-brn border0 cus-nav" role="navigation">
      <div class="container">
        <div>
          <button type="button" class="navbar-toggle pull-left cus-ham" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">選單切換</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>


          <div class="pull-left">
            <a href="<%=request.getContextPath()%>/FrontEnd/index/index.jsp">
              <img class="w50 padt5 padb2 mgr20" src="<%=request.getContextPath()%>/FrontEnd/res/img/BeanLifeLogoR.png">
            </a>
          </div>
          
          <div class="pull-right padt5">
            <div class="dropdown pull-right">
                      <a class="navbar-brand dropdown-toggle" data-toggle="dropdown" href="<%=request.getContextPath()%>/FrontEnd/cart/cart.jsp">
                        <span class=" glyphicon glyphicon-shopping-cart"></span>
                        <c:set var="cartSize" value="0"/>
                        <c:forEach var="cart_listVO" items="${cart_listSvc.getVOsByMem(mem_ac)}">
                            <c:set var="cartSize" value="${cart_listVO.prod_amount+cartSize}"/>
                        </c:forEach>
                        <span id="cartSize" class="badge cus-badge">${cartSize}</span>
                      </a>
                      <ul id="cartList" class="dropdown-menu zidx5 padlr5">
	                    
	                    <c:forEach var="cart_listVO" items="${cart_listSet}">
	                    	<li>
	                    		<span class="text-info">${prodSvc.getOneProd(cart_listVO.prod_no).prod_name}</span>
	                       </li>
                         <li class="text-right">
                          <span class="text-danger">$${prodSvc.getOneProd(cart_listVO.prod_no).prod_price}ｘ${cart_listVO.prod_amount}</span>
	                    	</li>
	                    </c:forEach>
	                   
                    <li role="presentation" class="divider"></li>
                    <a href="<%=request.getContextPath()%>/FrontEnd/cart/cart.jsp"><div  class="btn btn-info btn-sm pull-right">前往購物車</div></a>
                  </ul>
                    </div>

                    <div class="dropdown pull-right">
                      <a class="navbar-brand dropdown-toggle" data-toggle="dropdown" href="#">
                        <span class="glyphicon glyphicon-user"></span>
                      </a>
                      <ul class="dropdown-menu zidx5">
                      
                    <c:if test="${storeSvc.getOneByMem(mem_ac) != null}">
                    <c:if test="${storeSvc.getOneByMem(mem_ac).store_stat.equals('審核通過')}">
                      <li><a href="<%=request.getContextPath()%>/FrontEnd/prod_mag/listAllpro_bystore.jsp">店家中心</a></li>
                      <li><a href="<%=request.getContextPath()%>/FrontEnd/ord_mag/listAllorder_bystore.jsp">訂單管理</a></li>
                      <li role="presentation" class="divider"></li>
                    </c:if>
                    </c:if>
                    
                    <c:if test="${mem_ac!= null}">
                    <li><a href="<%=request.getContextPath()%>/FrontEnd/mem/mem.jsp">會員中心</a></li>
                    <li><a href="<%=request.getContextPath()%>/FrontEnd/buyerorder/buyerorder.jsp?status=1">購買清單</a></li>
                    <li><a href="<%=request.getContextPath()%>/FrontEnd/act/my_act.jsp">我的活動</a></li>
                    <li><a href="<%=request.getContextPath()%>/memlogin/memlogin.do?action=logout">登出</a></li>
                    </c:if>
                    
                    <c:if test="${mem_ac== null}">
                    <li><a id="login" href="#">登入</a></li>
                    <li><a href="<%=request.getContextPath()%>/FrontEnd/reg_mem/reg_member.jsp">註冊</a></li>
                    </c:if>
                  </ul>
                    </div>

                    <div class="pull-right">
                      <a id="btn-search" class="navbar-brand" href="#">
                          <span class="glyphicon glyphicon-search"></span>
                      </a>
                    </div>
                </div>
        </div>


        <div class="h-block-depn-nav"></div>

        <!-- 手機隱藏選單區 -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <!-- 左選單 -->
          <ul class="nav navbar-nav">
            <li><a class="padt20 padb17" href="<%=request.getContextPath()%>/FrontEnd/shop/shop.jsp">找豆</a></li>
            <li><a class="padt20 padb17" href="<%=request.getContextPath()%>/FrontEnd/act/act.jsp">辦活動</a></li>
            <c:if test="${not empty mem_ac}" >
            <li><a class="padt20 padb17" href="<%=request.getContextPath()%>/FrontEnd/gift/gift_data_frontEnd.jsp">積分兌換</a></li>
            </c:if>
          </ul>
        </div>
        <!-- 手機隱藏選單區結束 -->
      </div>
    </nav>

<!-- --------------------------------------NAV尾NAV尾NAV尾NAV尾NAV尾----------FUNC BAR開頭--------------------------------------------- -->

<!--  --------------------------------------------------------------跳窗inner---------------------------------------------------------------->





    <div class="modal fade" id="modal-inner">
        <div class="modal-dialog modal-lg">
            <div class="modal-content fix-h scrollbar-macosx" id="modalX">

<!-- 				這邊是跳窗塞內容 -->
                
            </div>
        </div>
    </div>
    
<!--  --------------------------------------------------------------跳窗結束---------------------------------------------------------------->
<!--  --------------------------------------------------------------跳窗Login---------------------------------------------------------------->


    <div class="modal fade" id="modal-login">
        <div class="modal-dialog modal-md">
            <div class="modal-content scrollbar-macosx" id="modalL">


              <div class="container-folid pad15">
                <div class="row">
                  <div class="col-xs-12 col-sm-10 col-sm-offset-1">
                  
			<form method="post" action="<%=request.getContextPath()%>/memlogin/memlogin.do">
			
              <div class="container-fluid">
                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <h3>登入</h3>
                    <div class="input-group">
                      <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                      <input id="mem_ac" type="text" class="form-control" name="mem_ac" placeholder="帳號">
                    </div>
                    <div class="input-group mgt10">
                      <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                      <input id="mem_pwd" type="password" class="form-control" name="mem_pwd" placeholder="密碼">
                    </div>
                   
                    
                    <input type="hidden" name="action" value="login">
                    <div class="container-fluid mgt20">
                      <div class="row">
                        <div class="col-xs-6 col-sm-6 padl0">
                          <span id="btn-signIn" class="btn btn-primary btn-block">註冊</span>
                        </div>
                        <div class="col-xs-6 col-sm-6 padr0">
                          <input type="submit" value="登入" class=" btn btn-danger btn-block">
                        </div>
                      </div>
                    </div>
                  	
                  </div>
                </div>
              </div>

              </form>

                  </div>
                </div>
              </div>


            </div>
        </div>
    </div>


<script type="text/javascript">
  $('#btn-signIn').click(function(){
    window.location.href = "<%=request.getContextPath()%>/FrontEnd/reg_mem/reg_member.jsp";
  });
</script>
<!--  --------------------------------------------------------------跳窗結束---------------------------------------------------------------->


<c:if test="${mem_ac!=null}">
<!--  --------------------------------------------------------------私訊開始---------------------------------------------------------------->
			<div id="msgOn" class="btn  msgBtn bg-light-g" style="display:none;">
               	 私訊
           </div>
			

           <div class="msg" style="display:none;">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-xs-12 col-sm-12">
                    <span id="msgOff" class="btn btn-sm glyphicon glyphicon-minus pull-right"></span>
                  </div>
                </div>
              </div>
 
              <div role="tabpanel">
                  <!-- 標籤面板：標籤區 -->
                  <ul class="nav nav-tabs" role="tablist" id="msgTab">
                      <li role="presentation"  class="active" count="0" id="msgTab0">
                          <a href="#msg0" aria-controls="msg0" role="tab" data-toggle="tab" name="SYS">
                           	 通知
                          </a>
                           
                      </li>
                      
                      <c:forEach var="urName" items="${urNames}" varStatus="s">
					        <li role="presentation" count="${s.count}" id="msgTab${s.count}">
					            <a href="#msg${s.count}" aria-controls="msg${s.count}" role="tab" data-toggle="tab" name="${urName}">
					           		${urName}
					           		<button id="msgClose${s.count}" type="button" class="close msgClose" count="${s.count}">&times;</button>
					            </a>
					        </li>
				        </c:forEach>
                      
                     
                     
  
                  </ul>
              
                  <!-- 標籤面板：內容區 -->
                  <div class="tab-content ">
  
  
                   <div role="tabpanel" class="tab-pane active" id="msg0">
                    <div class="container-fluid ">
                      <div id="${mem_ac}sys" class="row  fix-h30 scrollbar-macosx" index="1">
 
 <c:forEach var="sys_msgVO" items="${sys_msgSvc.getAllByMem(mem_ac)}">                     
                        <div class="container-fluid">
                          <div class="row">
                          
                            <div class="media">
                              <div class="media-left">
                                <img  class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do">
                              </div>
                              <div class="media-body">
                                <p class="col-xs-11 col-sm-10 well">
									${sys_msgVO.msg_cont}
                                </p>
                              </div>
                            </div>

                          </div>
                        </div>
</c:forEach> 
                      </div>
                    </div>
                    
                    <div>
                      <textarea class="form-control" rows="3" placeholder="輸入私訊..." id="msgIn${mem_ac}sys" disabled></textarea>
                      <span id="submit${mem_ac}sys" class="btn btn-primary btn-sm pull-right" disabled>送出</span>
                    </div>
                    
                  </div>
  
  

<!-- ------------------------------------------------------------------------------------------------------------------------- -->

<c:forEach var="urName" items="${urNames}" varStatus="s">


						     <div role="tabpanel" class="tab-pane" id="msg${s.count}">
									<div class="container-fluid ">
										<div id="${mem_ac}${urName}" class="row fix-h30 scrollbar-macosx" index="${s.count+1}">
										
<c:forEach var="msgVO" items="${msgSvc.getAllByPair(mem_ac,urName)}">

											<div class="container-fluid">
												<div class="row">
												
												<c:if test="${msgVO.mem_sen==urName}">
													<div class="media">
														<div class="media-left">
															<c:if test="${storeSvc.getOneByMem(urName)!=null}">
															<a class="showStore" name="${storeSvc.getOneByMem(urName).store_no}" href='#modal-inner' data-toggle="modal">
															</c:if>
																<img  class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${urName}">
															<c:if test="${storeSvc.getOneByMem(urName)!=null}">
															</a>
															</c:if>
														</div>
														<div class="media-body">
															<p class="col-xs-11 col-sm-10 well">
																${msgVO.msg_cont}
															</p>
															
														</div>
													</div>
												</c:if>
												
												<c:if test="${msgVO.mem_sen==mem_ac}">
													<div class="media">
														<div class="media-body">
															<div class="col-xs-11 col-xs-offset-1 col-sm-10 col-sm-offset-2">
																<div class=" pull-right  well bg-light-g">${msgVO.msg_cont}</div>
															</div>
														</div>
														<div class="media-right">
															<img class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${mem_ac}">
														</div>
													</div>
												</c:if>

													
												</div>
											</div>
</c:forEach> <%-- msgVOs--%>

										</div>

									</div>
									<div>
										<textarea class="form-control" rows="3" placeholder="輸入私訊..." id="msgIn${mem_ac}${urName}"></textarea>
										<span id="submit${mem_ac}${urName}" class="btn btn-primary btn-sm pull-right">送出</span>
									</div>
									
						        </div>


<script type="text/javascript">

$(document).ready(function(){
	connect('${mem_ac}','${urName}');
	$('#submit${mem_ac}${urName}').click(function(){
// 		console.log('sub${mem_ac}${urName}');
		sendMessage('${mem_ac}','${urName}');
	});
	$('#msgIn${mem_ac}${urName}').keypress(function(e){
		if(e.keyCode == 13){
			sendMessage('${mem_ac}','${urName}');
		}
	});
});

</script>

</c:forEach> <%-- urNames --%>
<!-- ------------------------------------------------------------------------------------------------------------------------- -->
  
  
                  </div>
              </div>
           </div>

<!--  --------------------------------------------------------------私訊結束---------------------------------------------------------------->

<style type="text/css">

	.fix-h30{
		height:30vh;
		overflow-y: auto;
		overflow-x: hidden;
	}
	.msg{
/* 		margin-top:50vh; */
		z-index: 1000; position: fixed; right: 30px; bottom: 0; width:300px; 
    	background-color:snow;
	}
	.msgBtn{
/* 		margin-top:50vh; */
		z-index: 1000; position: fixed; right: 30px; bottom: 0;
	}

</style>
			
</c:if>	



<script type="text/javascript">
  $(document).ready(function(){  
    $('#btn-search').click(function(){
        $('#funcbar').slideToggle('300');
    });
  });

	
  if(${sessionScope.showLogin}){
      $('#modal-login').modal("show");
  }

  $('#login').click(function(){
	  $('#modal-login').modal("show");
	  return false;
  });


</script>
<c:set var="showLogin" value="${false}" scope="session"/>

