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


<div class="content container mgt-depn-nav">

	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
	
	
	<div class="product col-sm-2">
	<table class="store" >
	<tr><td align="center"><h2>${storeVO.store_name}</h2></td></tr>
	<tr><td align="center"><img src="<%=request.getContextPath()%>/store/storeImg.do?store_no=${storeVO.store_no}&index=1" width='150'></td></tr>
	<tr><td align="center"><h4><a class="showStore" name="${storeVO.store_no}" href='#modal-inner' data-toggle="modal" >預覽商場</a></h4></td></tr>
	<tr><td align="center"><h4><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></h4></td></tr>
	</table>
	</div>
	
		<div class="product col-sm-10">
			<table class="table-bordered table-responsive pro_all">
				<caption >
					<font size="20">我的商品</font>
					<ul class="verticnav">
						<li><small><a href="<%=request.getContextPath()%>/FrontEnd/prod_mag/addprod.jsp">新增商品</a></small></li>
					</ul>	
				</caption>
				<tr>
					<th>商品名稱</th>
					<th>商品圖片</th>
					<th>豆種</th>
					<th>價格</th>
					<th>烘培度</th>
					<th>狀態</th>
					<th></th>
					<th></th>
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
						<td>${prodVO.prod_price}</td>
						<td>${prodVO.roast}</td>
						<td>${prodVO.prod_stat}</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value="修改商品資料" class="btn-primary"> 
										<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
										<input type="hidden" name="store_no"	value="<%=store_no%>">  
										<input type="hidden" name="action" value="getOne_For_Update">
										<input type="hidden" name="whichPage"	value="<%=whichPage%>">  
							</FORM>
						</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value=${prodVO.prod_stat.equals("上架") ? "下架" : "上架" } class=${prodVO.prod_stat.equals("上架") ? "btn-danger" : "btn-info" }> 
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
		
		
<script src="<%=request.getContextPath()%>/FrontEnd/res/js/sorttable.js"></script>
<script>
$(".pro_all img:first-child").css(
	    {"width":"70px"}
	)
	


</script>



<jsp:include page="/FrontEnd/include/footer.jsp"/>

