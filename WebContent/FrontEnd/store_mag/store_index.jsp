<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.ord.model.*"%>
<%@ page import="com.ord_list.model.*"%>
<%@ page import="com.review.model.*"%>
<%@ page import="com.fo_prod.model.*"%>



<jsp:useBean id="storeSvc" scope="page" class="com.store.model.StoreService" />
<jsp:useBean id="fo_prodSvc" scope="page" class="com.fo_prod.model.Fo_prodService" />
<jsp:useBean id="fo_storeSvc" scope="page" class="com.fo_store.model.Fo_storeService" />
<jsp:useBean id="reviewSvc" scope="page" class="com.review.model.ReviewService" />
<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<c:set var="fo_prodlist" value="${fo_prodSvc.getAllByMem(mem_ac)}" scope="page"/>
<c:set var="fo_storelist" value="${fo_storeSvc.getAllByMem(mem_ac)}" scope="page"/>

<%
	
	String store_no = (String) session.getAttribute("store_no");
	ProdService prodSvc = new ProdService();
	OrdService ordSvc = new OrdService();
	
	StoreVO storeVO=(StoreVO)storeSvc.getonestore(store_no);
	pageContext.setAttribute("storeVO",storeVO);
	
	
	Set<ProdVO> prodVOs = storeSvc.getProdsByStore_no(store_no);
	pageContext.setAttribute("prodVOs",prodVOs);
	
	
	
%>

 

		
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