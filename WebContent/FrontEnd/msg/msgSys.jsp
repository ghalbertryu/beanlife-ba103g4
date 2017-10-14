<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.msg.model.*"%>

<meta charset="utf-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


<c:set var="myName" value="${requestScope.myName}" scope="page"/>
<c:set var="urName" value="${requestScope.urName}" scope="page"/>
<c:set var="message" value="${requestScope.message}" scope="page"/>

						      


<script type="text/javascript">

$(document).ready(function(){
	connectSys('${myName}','${urName}');
});



////////////////////webSocket
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));

var webSocketSys = null;
function connectSys(myName,urName) {
	// 建立 websocket 物件
	var MyPoint = "/MyEchoServer/"+myName+"/"+urName;
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	webSocketSys = new WebSocket(endPointURL);

	//onopen
	webSocketSys.onopen = function(event) {
		sendMessageSys("${message}");
	};

	//onmessage
	webSocketSys.onmessage = function(event) {
	 
	};

	//onclose
	webSocketSys.onclose = function(event) {
		console.log('sys close');
		// updateStatus("WebSocket 已離線");
	};
}

function sendMessageSys(message) {
	console.log('sys send');
    var jsonObj = {"userName" : "sys", "message" : message};
    webSocketSys.send(JSON.stringify(jsonObj));
    console.log('sendEnd');
    disconnectSys();
}


function disconnectSys () {
	webSocketSys.close();

}


function updateStatus(newStatus) {
	// statusOutput.innerHTML = newStatus;
}
</script>

<!-- ------------------------------------------------------------------------------------------------------------------------- -->