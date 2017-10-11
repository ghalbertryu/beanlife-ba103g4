<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="/FrontEnd/include/head.jsp"/>



		<div class="content container mgt-depn-nav">
			<div class="row">
				<div class="col-xs-12 col-sm-6">
					<div id="map"></div>
					<h5>聯絡地址：桃園市中壢區中大路 300-1 號（國立中央大學工程二館</h5>
					<h5>聯絡電話：03-425-7387</h5>
					<h5>傳真：03-425-9233</h5>
					<h5>e-mail：ba103@genei.info</h5>
				</div>
				<div class="col-xs-12 col-sm-6">
					<form METHOD="POST"
								ACTION="<%=request.getContextPath()%>/Contact/mail.do" name="form1">
						<h2>客服信箱</h2>
						<h3>請送出您的問題</h3>
						
						<h3>主旨：<input type="text" name="subject" value=""></h3>
						<h3>內容：<textarea rows="4" cols="50"  name="messageText"></textarea></h3>
						<input type="submit" value="送出信件">
						<input type="hidden" name="to" value="ba103@genei.info">
						<input type="hidden" name="action" value="mail_contact">
					</form>
				</div>
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
						信件已送出，我們將盡快回應您。
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
					</div>
				</div>
			</div>
		</div>



	<script>
      function initMap() {
        var uluru = {lat: 24.967742, lng: 121.191700};
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 17,
          center: uluru
        });
        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });
      }
      if(${not empty subject}){
    		$("#modal-id").modal({show:true});
    	}
    </script>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCLrfGty4UTt0tx-W-iI8kvMI07T7vgapc&callback=initMap">
    </script>


<jsp:include page="/FrontEnd/include/footer.jsp"/>