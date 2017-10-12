<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.msg.model.*"%>

<jsp:useBean id="msgSvc" scope="page" class="com.msg.model.MsgService" />


<c:set var="mem_ac" value="${param.myName}" scope="page"/>
<c:set var="urName" value="${param.urName}" scope="page"/>
<c:set var="count" value="${param.count}" scope="page"/>




						        <div role="tabpanel" class="tab-pane" id="msg${count}">
									<div class="container-fluid ">
										<div id="${mem_ac}${urName}" class="row fix-h30 scrollbar-macosx" index="${count}">
										
<c:forEach var="msgVO" items="${msgSvc.getAllByPair(mem_ac,urName)}">

											<div class="container-fluid">
												<div class="row">
												
												<c:if test="${msgVO.mem_sen==urName}">
													<div class="media">
														<div class="media-left">
															<img  class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do?memAc=${urName}">
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

<!-- ------------------------------------------------------------------------------------------------------------------------- -->