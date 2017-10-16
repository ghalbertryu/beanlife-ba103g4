<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>

<div class="container cart-tab-block content">
	<div class="row">
		<div class="col-xs-12 col-sm-6 col-sm-offset-3 mgt20">
		<div class="table-responsive">
			<table class="table">
    		  <tr class="success" align="center">
       			<td>
       				<form action=""></form>
       				你好${mem_ac}  您已申請成為店家，請靜待管理員審核。<br><a href="<%=request.getContextPath()%>/FrontEnd/index/index.jsp">回首頁</a>
       			</td>
     		 </tr>
  			</table>
		</div>
	</div>
</div>
</div>



<jsp:include page="/FrontEnd/include/footer.jsp"/>