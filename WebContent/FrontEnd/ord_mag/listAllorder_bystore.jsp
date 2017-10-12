<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.ord.model.*"%>
<%@ page import="com.ord_list.model.*"%>



<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<%
// 	session.setAttribute("store_no", "S1000000002");
	String store_no = (String) session.getAttribute("store_no");
	
	StoreService storeSvc = new StoreService();
	ProdService prodSvc = new ProdService();
	OrdService ordSvc = new OrdService();
	
	StoreVO storeVO=(StoreVO)storeSvc.getonestore(store_no);
	request.setAttribute("storeVO",storeVO);
	
	
	Set<ProdVO> prodVOs = storeSvc.getProdsByStore_no(store_no);
	request.setAttribute("prodVOs",prodVOs);
	
	
	Set<OrdVO> ordVOs= new LinkedHashSet<OrdVO>();
	for(ProdVO prodVO : prodVOs){
		
		Set<Ord_listVO> ord_listVOs = prodSvc.getOrd_listByProd(prodVO.getProd_no());
		for(Ord_listVO ord_listVO : ord_listVOs){
			ordVOs.add(ordSvc.getOrdByOrdno(ord_listVO.getOrd_no()));
		}
	}
	session.setAttribute("ordVOs",ordVOs);
	
%>


<div class="content container mgt-depn-nav">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li>${message}</li>
				</c:forEach>
			</ul>
		</font>
	</c:if>
	
	
	
	<div class="shop">
		<div class="product col-sm-8">
			<table class="table-bordered table-responsive ord_all">
				<caption >
					<font size="20">我的訂單</font><br>請選擇狀態
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ord/Ord_manag.do">
						<select name="ord_stat" id="stat">
							<option value="getAll">請選擇</option>
							<option value="未付款" ${(param.ord_stat=='未付款')? 'selected':''}>未付款</option>
	　						<option value="已付款" ${(param.ord_stat=='已付款')? 'selected':''}>已付款</option>
							<option value="已確認付款" ${(param.ord_stat=='已確認付款')? 'selected':''}>已確認付款</option>
	　						<option value="已出貨" ${(param.ord_stat=='已出貨')? 'selected':''}>已出貨</option>
						</select>
						<input type="hidden" name="action" value="selectstat">
						<input type="hidden" name="mem_ac" value="${mem_ac}">
					</FORM>
					目前查詢狀態：${(param.ord_stat==null||param.ord_stat=='getAll')? '查詢全部':param.ord_stat}
				</caption>
				<tr>
					<th>訂單編號</th>
					<th>收貨人姓名</th>
					<th>訂單成立時間</th>
					<th>結帳商品總金額</th>
					<th>訂單狀態</th>
					<th></th>
					<th></th>
					
				</tr>
				<%@ include file="page1.file"%>
				<c:forEach var="ordVO" items="${ordVOs}" begin="<%=pageIndex%>"
							end="<%=pageIndex+rowsPerPage-1%>">
					<tr>
						<td>
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ord/Ord_manag.do">
						${ordVO.ord_no}
						<input type="hidden" name="ord_no" value="${ordVO.ord_no}">
						<input type="hidden" name="action" value="getord_display">
						<input type="hidden" name="whichPage"value="<%=whichPage%>">  
						</FORM>
						</td>
						<td>${ordVO.ord_name}</td>
						<td>${ordVO.ord_date}</td>
						<td>${ordVO.total_pay}</td>
						<td>${ordVO.ord_stat}</td>
						<td></td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/ord/Ord_manag.do">
										<input type="submit" value="進行訂單管理" class="btn btn-info"> 
										<input type="hidden" name="ord_no" value="${ordVO.ord_no}">
										<input type="hidden" name="store_no"	value="<%=store_no%>">  
										<input type="hidden" name="action" value="getOne_For_Update">
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
<script>
$("#stat").change(function(){
	$(this).parent().submit();
})

</script>


<jsp:include page="/FrontEnd/include/footer.jsp"/>