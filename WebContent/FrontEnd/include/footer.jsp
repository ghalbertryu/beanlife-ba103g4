<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
<!-- ----------------------------------------------FOOTER--------------------------------------------------------- -->
    <div class="footer">
      <div class="container text-center">
        <div class="row">
          <div class="col-xs-12 col-sm-1 col-sm-offset-4">
            <a href="#">服務條款</a>
          </div>
          <div class="col-xs-12 col-sm-1">
            <a href="#">常見問題</a>
          </div>
          <div class="col-xs-12 col-sm-1">
            <a href="<%=request.getContextPath()%>/FrontEnd/contact_us/contact_us.jsp">聯絡我們</a>
          </div>
          <div class="col-xs-12 col-sm-1">
            <a href="#">商業合作</a>
          </div>
        </div>
        <div class="col-xs-12 col-sm-12">
          <h5 class="small">COPYRIGHT (C) 2017 BeanLife, Inc. ALL RIGHTS RESERVED.</h5>
        </div>
      </div>
    </div>


<script type="text/javascript" src="<%=request.getContextPath()%>/FrontEnd/res/js/beanlife.footer.js"></script>



<script type="text/javascript">


//<!--  --------------------------------------------------------------私訊---------------------------------------------------------------->
$(function(){
	//login connect
	if('${mem_ac}'!=''){
	  	//ready
	 	connect('${mem_ac}','sys');
	 }
	
	//.msg display
	 $('.msg').hide();
	 $('.msgBtn').show();
	  
	 $('#msgOff').click(function(){
	     $('.msg').hide();
	     $('.msgBtn').show();
	 });
	 
	 $('#msgOn').click(function(){
		 $('.msg').show();
		 $('.msgBtn').hide();
	 });
	 
	//msgTo
	$('.msgTo').click(function (){
		$('#msgOn').click();
		//check if already open
		var $urName = $(this).attr('id');
		for(var i =0 ; i <$('#msgTab li').length; i++){
			if($($('#msgTab li')[i]).children('a').attr('name') == $urName){
				return;
			}
		}
		openMsg($urName);
	});
	
	//when close do
	$('.msgClose').click(function(event){
		event.stopPropagation();
		event.stopImmediatePropagation();
		var $count = Number($(this).attr('count'));
		//check if active is true
		if($('#msgTab'+$count).hasClass('active')){
			$('#msgTab'+$count).removeClass('active');
			$('#msgTab0').addClass('active');
			$('#msg'+$count).removeClass('active');
			$('#msg0').addClass('active');
		}
		var $urname= $('#msgTab'+$count+'>a').attr('name');
		
		//send Msg to steal
		$index = $($('#${mem_ac}'+$urname)).attr('index');
	    var jsonObj = {"action" : "toSeal"};
	    webSocket[$index].send(JSON.stringify(jsonObj));

		disconnect ('${mem_ac}',$urname);
		$('#msg'+$count).remove();
		$('#msgTab'+$count).remove();
	});
});

/////////////////open Msg Tap
function openMsg($urName){
	//add Tab
	var $count = Number($($('#msgTab li:last')).attr('count'))+1;
	$('#msgTab').append(' <li role="presentation"  count="'+
			$count+'" id="msgTab'+$count+'"><a href="#msg'+
			$count+'" aria-controls="msg'+$count+
			'" role="tab" data-toggle="tab"  name="'+$urName+'">'+$urName+'<button id="msgClose'+$count+'" type="button" class="close msgClose" count="'+$count+'">&times;</button></a></li>');
	
	//add Tab-content
	var $content = $('.msg .tab-content');
	var urlstr = '<%=request.getContextPath()%>/FrontEnd/msg/msgInner.jsp?myName=${mem_ac}&urName='+$urName+'&count='+$count;
	$.ajax({
		url : urlstr,
		type : 'GET',
		dataType: "html",
		async: false,
		success : function(result) {
			$content.append(result);
		},
		error : function(xhr) {
			alert('Ajax request 發生錯誤');
		}
	});
	
	//change active
	for(var i = 0 ; i< $count;i++){
		$('#msgTab'+i).removeClass('active');
		$('#msg'+i).removeClass('active');
	}
	$('#msgTab'+ $count).addClass('active');
	$('#msg'+$count).addClass('active');
	
	//scrollTop to End
	var messagesArea = $('#msg'+$count+'>div>div');
	$(messagesArea).scrollTop(messagesArea.prop('scrollHeight'));
	
	//when close do
	$('.msgClose').click(function(event){
		event.stopPropagation();
		event.stopImmediatePropagation();
		var $count = Number($(this).attr('count'));
		//check if active is true
		if($('#msgTab'+$count).hasClass('active')){
			$('#msgTab'+$count).removeClass('active');
			$('#msgTab0').addClass('active');
			$('#msg'+$count).removeClass('active');
			$('#msg0').addClass('active');
		}
		var $urname= $('#msgTab'+$count+'>a').attr('name');
		
		//send Msg to steal
		$index = $($('#${mem_ac}'+$urname)).attr('index');
	    var jsonObj = {"action" : "toSeal"};
	    webSocket[$index].send(JSON.stringify(jsonObj));

		disconnect ('${mem_ac}',$urname);
		$('#msg'+$count).remove();
		$('#msgTab'+$count).remove();
	});
}


////////////////////webSocket
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));

var webSocket = [];
function connect(myName,urName) {
	$index = $($('#'+myName+urName)).attr('index');
	// 建立 websocket 物件
	var MyPoint = "/MyEchoServer/"+myName+"/"+urName;
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	webSocket[$index] = new WebSocket(endPointURL);

	//onopen
	webSocket[$index].onopen = function(event) {
		// console.log('connect'+myName+urName);
		// updateStatus("WebSocket 成功連線");
	};

	//onmessage
	webSocket[$index].onmessage = function(event) {
	  var messagesArea = $('#'+myName+urName);
      var jsonObj = JSON.parse(event.data);
      var message = jsonObj.userName + ": " + jsonObj.message + "\r\n";
      if(urName=='sys' && jsonObj.userName!='sys'){
      	if($($('#'+myName+jsonObj.userName)).length==0){
      		$('#msgOn').click();
      		openMsg(jsonObj.userName);
      	}
      	return false;
      }

      var msghtml ='';
      if(jsonObj.userName== urName){
      	msghtml = '<div class="container-fluid"><div class="row"><div class="media"><div class="media-left">'+
      	'<img  class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do?memAc='+urName+'">'+
      	'</div><div class="media-body"><p class="col-xs-11 col-sm-10 well">'+
      				jsonObj.message+"\r\n"+
						'</p></div></div></div></div>';
      } else if (jsonObj.userName==myName){
      	msghtml = '<div class="container-fluid"><div class="row"><div class="media"><div class="media-body">'+
      				'<p class="col-xs-11 col-xs-offset-1 col-sm-10 col-sm-offset-2 well">'+
      				jsonObj.message+"\r\n"+
						'</p></div><div class="media-right">'+
						'<img class="media-object round-img w50" src="<%=request.getContextPath()%>/mem/memImg.do?memAc='+myName+'"></div></div></div></div>';
      }
      
      messagesArea.append(msghtml);
      $(messagesArea).scrollTop(messagesArea.prop('scrollHeight'));
	};

	//onclose
	webSocket[$index].onclose = function(event) {
		console.log('close');
		// updateStatus("WebSocket 已離線");
	};
}

function sendMessage(myName,urName) {
  var userName = myName;
  var inputMessage = $("#msgIn"+myName+urName);
  var message = inputMessage.val().trim();
  if (message === ""){
      alert ("訊息請勿空白!");
      // inputMessage.focus();
  }else{
  	$index = $($('#'+myName+urName)).attr('index');
      var jsonObj = {"userName" : userName, "message" : message};
      webSocket[$index].send(JSON.stringify(jsonObj));
      inputMessage.val('');
      // inputMessage.focus();
  }
}


function disconnect (myName,urName) {
	$index = $($('#'+myName+urName)).attr('index');
	webSocket[$index].close();

}


function updateStatus(newStatus) {
	// statusOutput.innerHTML = newStatus;
}
	
	

</script>


  </body>
</html>