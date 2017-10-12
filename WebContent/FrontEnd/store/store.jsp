<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.fo_prod.model.*"%>
<%@ page import="com.fo_store.model.*"%>


<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="prodSvc" scope="page" class="com.prod.model.ProdService" />
<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="memVO" value="${memSvc.getOneMem(mem_ac)}" scope="page"/>



<div class="container mgt-depn-nav-lg">
		<div class="row">
			<div class="col-xs-12 col-sm-8 col-sm-offset-2 mgb10 ">
				<h3 class="bold">店家中心</h3>
			</div>
			<div class="col-xs-12 col-sm-8 col-sm-offset-2">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-12 col-sm-4 text-center">
							<div class="col-xs-12 col-sm-6 col-sm-offset-3 ">
								<img class="img-responsive round-img" src="http://fakeimg.pl/200x200/00CED1/FFF/?text=img+placeholder">
							</div>
							<span>${memVO.mem_ac}</span><br>
							<small><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></small>
						</div>
						<div class="col-xs-12 col-sm-8">
							<h4 class="bold text-info">${storeSvc.getonestore(prodVO.store_no).store_name}</h4>
                           <p>
	                                                地址： ${storeSvc.getonestore(prodVO.store_no).store_add}<br>
	                                                電話：  ${storeSvc.getonestore(prodVO.store_no).store_phone}<br>
                           </p>
						</div>
					</div>
				</div>
			</div>
			

		</div>
	</div>

	
   
		


<jsp:include page="/FrontEnd/include/footer.jsp"/>