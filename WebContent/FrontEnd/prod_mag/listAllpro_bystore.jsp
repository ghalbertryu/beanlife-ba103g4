<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>

<style>

.showProd:hover {
    text-decoration: underline;
}
</style>
<%
	String mem_ac = (String) session.getAttribute("mem_ac");
	StoreService storeSvc = new StoreService();
	
	StoreVO storeVO=storeSvc.getOneByMem(mem_ac);
	pageContext.setAttribute("storeVO", storeVO);
	String store_no = storeVO.getStore_no();
	pageContext.setAttribute("store_no", store_no); 
	Set<ProdVO> set;
	set = storeSvc.getProdsByStore_no(storeVO.getStore_no());
	session.setAttribute("set", set);
%>


<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-10 col-sm-offset-1">
			<h3 class="bold">店家中心</h3>

	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
	
	<div class="product col-sm-2 padlr0">
	<div class="table-responsive">  
	<table class="store" >
	<tr><td align="center"><h4>${storeVO.store_name}</h5></td></tr>
	<tr><td align="center"><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" width='150'></td></tr>
	<tr><td align="center"><h5><a class="showStore" name="${storeVO.store_no}" href='#modal-inner' data-toggle="modal" >預覽商場</a></h5></td></tr>
	<tr><td align="center"><h5><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></h5></td></tr>
	</table>
	</div>
	</div>
	
	
		<div class="product col-sm-10 padlr0">
			<div class="table-responsive">          
  			<table class="table table-bordered pro_all">
				<caption >
					<a href="<%=request.getContextPath()%>/FrontEnd/prod_mag/addprod.jsp"><span class="btn btn-primary pull-right">新增商品</span></a>
<!-- 					<ul class="verticnav"> -->
<!-- 						<li></li> -->
<!-- 					</ul>	 -->
				</caption>
				<tr>
					<th>商品名稱</th>
					<th>商品圖片</th>
					<th>豆種</th>
					<th class="w50">價格</th>
					<th class="w70">烘培度</th>
					<th class="w50">狀態</th>
					<th >修改</th>
					<th>操作</th>
				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="prodVO" items="${set}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
					<tr>
						<td>
						<a class="showProd" name="${prodVO.prod_no}" href='#modal-inner' data-toggle="modal" >${prodVO.prod_name}</a>
						</td>
						<td><img src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1"></td>
						<td>${prodVO.bean_type}</td>
						<td>NT$${prodVO.prod_price}</td>
						<td>${prodVO.roast}</td>
						<td>${prodVO.prod_stat}</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value="修改商品資料" class="btn-warning btn btn-xs"> 
										<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
										<input type="hidden" name="store_no"	value="<%=store_no%>">  
										<input type="hidden" name="action" value="getOne_For_Update">
										<input type="hidden" name="whichPage"	value="<%=whichPage%>">  
							</FORM>
						</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value=${prodVO.prod_stat.equals("上架") ? "下架" : "上架" } class= 'btn btn-xs ${prodVO.prod_stat.equals("上架") ? "btn-danger" : "btn-success" }'> 
										<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
										<input type="hidden" name="prod_stat" value="${prodVO.prod_stat}">
										<input type="hidden" name="action" value="Update_prodstat">
										<input type="hidden" name="whichPage"	value="<%=whichPage%>">
							</FORM>
						</td>
					</tr>
				</c:forEach>
				
			</table>
			　<%@ include file="page2.file"%>
			</div>
				　
		</div>

</div>
</div>		
</div>		
		
		
<script src="<%=request.getContextPath()%>/FrontEnd/res/js/sorttable.js"></script>
<script>
$(".pro_all img:first-child").css(
	    {"width":"70px"}
	)
	


</script>



<jsp:include page="/FrontEnd/include/footer.jsp"/>

