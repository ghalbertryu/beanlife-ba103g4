<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.store.model.*"%>


<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${request.mem_ac}" scope="page"/>

<div class="content container mgt-depn-nav">
	<div class="row">
		<div class="table-responsive">
			<table class="table">
    		  <tr class="success" align="center">
       			<td>
       				
       				你好${memVO.mem_ac}  恭喜你成為會員。<br>
       				<form METHOD="post"
							ACTION="<%=request.getContextPath()%>/mem/mem.do">
       				<a class="now" >馬上登入</a>
						<input type="hidden" name="action" value="loginnow">
       				</form>
       			</td>
     		 </tr>
  			</table>
		</div>
	</div>
</div>
<script>
$(".now").click(function(){
	$(this).parent().submit();
})
</script>
<jsp:include page="/FrontEnd/include/footer.jsp"/>