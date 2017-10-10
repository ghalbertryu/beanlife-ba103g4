<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>
<%@ page import="com.prod.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<%
	
	session.getAttribute("store_no");
	String store_no = (String) session.getAttribute("store_no");
	StoreService storeSvc = new StoreService();
	Set<ProdVO> set;
	set = storeSvc.getProdsByStore_no(store_no);
	session.setAttribute("set", set);
	StoreVO storeVO=storeSvc.getonestore(store_no);
	session.setAttribute("storeVO", storeVO); 
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
	
	
	
	
	<small><a href="<%=request.getContextPath()%>/FrontEnd/store_mag/store_databypass.jsp">修改店家資料</a></small>
	<div class="shop">
		<div class="product col-sm-8">
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
						<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
						<a class="aprod">${prodVO.prod_name}</a>
						<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
						<input type="hidden" name="action" value="getprod_display">
						<input type="hidden" name="whichPage"value="<%=whichPage%>">  
						</FORM>
						</td>
						<td><img src="<%=request.getContextPath()%>/prod/prodImg.do?prod_no=${prodVO.prod_no}&index=1"></td>
						<td>${prodVO.bean_type}</td>
						<td>${prodVO.prod_price}</td>
						<td>${prodVO.roast}</td>
						<td>${prodVO.prod_stat}</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value="修改商品資料" class="btn btn-info"> 
										<input type="hidden" name="prod_no" value="${prodVO.prod_no}">
										<input type="hidden" name="store_no"	value="<%=store_no%>">  
										<input type="hidden" name="action" value="getOne_For_Update">
										<input type="hidden" name="whichPage"	value="<%=whichPage%>">  
							</FORM>
						</td>
						<td><FORM METHOD="post"
										ACTION="<%=request.getContextPath()%>/prod/Prod_manag.do">
										<input type="submit" value=${prodVO.prod_stat.equals("上架") ? "下架" : "上架" } class="btn btn-info"> 
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



	<div class="modal fade" id="modal-id">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title">商品詳情</h4>
					</div>
					<div class="modal-body">
						商品名稱：${prodVO.prod_name}<br>
						豆種：${prodVO.bean_type}<br>
						生豆等級：${prodVO.bean_grade}<br>
						生產國：${prodVO.bean_contry}<br>
						地區：${prodVO.bean_region}<br>
						農場：${prodVO.bean_farm}<br>
						生產者：${prodVO.bean_farmer}<br>
						海拔：${prodVO.bean_el}<br>
						處理法：${prodVO.proc}<br>
						烘焙度：${prodVO.roast}<br>
						風味-酸度：${prodVO.bean_attr_acid}<br>
						風味-香氣：${prodVO.bean_attr_aroma}<br>
						風味-醇度：${prodVO.bean_attr_body}<br>
						風味-餘味：${prodVO.bean_attr_after}<br>
						風味-平衡度：${prodVO.bean_attr_bal}<br>
						香味：${prodVO.bean_aroma}<br>
						標價  $NT：${prodVO.prod_price}<br>
						重量  lb：${prodVO.prod_wt}<br>
						運費：${prodVO.send_fee}<br>
						供應數量：${prodVO.prod_sup}<br>
						商品描述：${prodVO.prod_cont}<br>
						上架狀態：${prodVO.prod_stat}<br>
						最後編輯時間：${prodVO.ed_time}
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
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
	
$(".aprod").click(function(){
	$(this).parent().submit();
})
if(${not empty openModal}){
	$("#modal-id").modal({show:true});
}

</script>



<jsp:include page="/FrontEnd/include/footer.jsp"/>

