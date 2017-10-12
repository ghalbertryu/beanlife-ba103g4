<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gift_data.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>
<%@ page import="com.store.model.*"%>
<html>
  <head>
    <meta charset="UTF-8">
    <title>兌換贈品業務管理</title>
    <link rel="stylesheet prefetch" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/gift_data.css">
    
    <style>
    *{
    position: relative;
  
    }
    
      .card .right .right_bottom .giftcard .info h3 ,.card .right .right_bottom .giftcard .info  p {
          color: #F2F2F2; }
    .giftImg {
    height: 350px ;
     vertical-align: top;
    }
    .abovegiftcard{
  margin-top:50px;
     vertical-align: top;
 
    }
    .giftcard{
    height: 800px;
    }
    .cardrow{
    vertical-align: top;
   
    }
 .card .right .right_bottom  h4{
    color: #eee;
    }
    .selectPage{

    }
    .selectPageTable{
    float: right;
    
    }
    table{
    margin-top: 30px;
    color: black;
    float: right;
    }
     table *{
    
    color: black;
    
    }
     .card .right .right_middle .middle .btn {
        float: right;
        margin-top: 10px;
        padding: 10px 20px; }
        
       #modal-id *{
        color: #333;
        }
    #modal-id    .btn-primary{
        color: #eee;
        }
.card .right .right_bottom   .btn-danger  {
        color: #eee;
   padding: 15px 20px;
   font-size: 15px ;
        }
     #modal-update *{
        color: #333;
        }
       #modal-update    .btn-primary{
        color: #eee;
        }
      .deleteGift{
      float: right;
      display: inline-block;
     
      }
     #modal-update  .modal-header  .deleteGift  *{
        color: #eee; 
       
       }
       
       .update_gift_img{
       width: 150px;
  height: 100px;
  display: none;
       }
       .modify_gift_img{
       width: 150px;
       height: 100px;
       }
     
    </style>
    <%
    Gift_dataService gift_dataSvc = new Gift_dataService();

List<Gift_dataVO> list=gift_dataSvc.getAll();
    pageContext.setAttribute("list",list);
%>
    <%
    ActService actSvc=new ActService();
     List<ActVO>act_vo_list= actSvc.getAll();
    int act_count=0;
    for(int i=0;i<act_vo_list.size();i++){
    	if(act_vo_list.get(i).getAct_stat().equals("待審核")){
    		act_count++;
    	}
    }
    
    pageContext.setAttribute("act_count",act_count);
    
    Convert_giftService convert_giftSvc=new Convert_giftService();
    List<Convert_giftVO> convert_gift_vo_list= convert_giftSvc.getAll();
    int convert_gift_count=0;
    for(int i=0;i<convert_gift_vo_list.size();i++){
    	if(convert_gift_vo_list.get(i).getApply_stat().equals("待出貨")){
    		convert_gift_count++;
    	}
    }
    
    pageContext.setAttribute("convert_gift_count",convert_gift_count);
    
    StoreService storeSvc=new StoreService();
    List<StoreVO>store_vo_list= storeSvc.getAll();
   int store_count=0;
   for(int i=0;i<store_vo_list.size();i++){
   	if(store_vo_list.get(i).getStore_stat().equals("待審中")){
   		store_count++;
   	}
   }
   
   pageContext.setAttribute("store_count",store_count);
    %>
  </head>
  <body>
    <div class="container_fluid titlebar"><a class="form-inline titlebarForm" href="<%=request.getContextPath()%>/BackEnd/main.jsp"><img class="icon" src="<%=request.getContextPath()%>/BackEnd/res/images/BeanLifeLogo2.png" alt="">
        <h1>Bean-Life</h1></a></div>
    <div class="container card">
      <div class="row composing">
        <div class="col-xs-2 left"><a class="h3 title" href="#action" aria-expanded="false" aria-controls="action" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-futbol-o"></div><a class="h3 act" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp"> 活動審核</a></a><a class="h3 title" href="#mem" aria-expanded="false" aria-controls="mem" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-address-card-o"></div><span class="h3">會員管理</span>
            <ul class="collapse" id="mem"><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a><a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a></ul></a><a class="h3 title" href="#gift" aria-expanded="false" aria-controls="gift" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-gift"> </div><span class="h3">平台業務管理</span>
            <ul class="collapse" id="gift"><a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a><a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a></ul></a></div>
        <div class="right col-xs-10">
          <div class="col-xs-12 right_top"><img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
            <h2>你好</h2><a class="fa fa-bell dropdown-toggle" href="#" data-toggle="dropdown"></a>
            <ul class="dropdown-menu">
             
              <li><a  href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
            </ul>
          </div>
          <div class="col-xs-12 right_middle">
            <div class="col-xs-12 middle">
              <h2 class="check">兌換贈品業務管理</h2>
              <a href='#modal-id' data-toggle="modal"  class="btn btn-success">上架</a>
            </div>
          </div>
          <div class="col-xs-12 right_bottom">
            <%@ include file="page1.file" %> 
           <div class="row  cardrow">
         
           <c:forEach var="gift_data_vo"  items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
           <FORM METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet" name="form1" >
              <div class="col-xs-4 col-sm-4  abovegiftcard">
                <div class="giftcard"><img class="giftImg"src="<%=request.getContextPath()%>/GiftImg.do?gift_no=${gift_data_vo.gift_no }" >
                  <div class="info">
                    <h3>${gift_data_vo.gift_name}</h3>
                    <p>${gift_data_vo.gift_cont}</p>
                    <h3>所需積分${gift_data_vo.gift_pt}</h3>
                    <div class="getitem">
                       <button type="submit" class="btn btn-danger">修改/下架</button>
                      <h3>剩下${gift_data_vo.gift_remain}個</h3>
                    </div>
                    <div class="upTime">
                      <h4>上架時間</h4>
                      <h4>${gift_data_vo.gift_launch_date}</h4>
                    </div>
                    <input type="hidden"  class="gift_no" name="GIFT_NO"  value=${gift_data_vo.gift_no }>
                    <input type="hidden" name="whichPage" value="<%=whichPage%>"  >
                    <input type="hidden" name="gift_data.jsp" value="<%=request.getServletPath() %>">
                    <input type="hidden"   name="action"  value="getOne_For_Display">
                  </div>
                </div>
              </div>
              </FORM>
              </c:forEach>
              
              
         <%--    
              <div class="col-xs-4 col-sm-4">
                <div class="giftcard"><img src="images/cup.jpg" alt="">
                  <div class="info">
                    <h3>頂級咖啡杯</h3>
                    <p>頂級，獨一無二</p>
                    <h3>所需積分50</h3>
                    <div class="getitem">
                      <button class="btn-danger">下架</button>
                      <h3>剩下5個</h3>
                    </div>
                    <div class="upTime">
                      <h4>上架時間</h4>
                      <h4>2017/08/28        </h4>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-xs-4 col-sm-4">
                <div class="giftcard"><img src="images/cup.jpg" alt="">
                  <div class="info">
                    <h3>極品咖啡杯</h3>
                    <p>品嘗極品咖啡專用</p>
                    <h3>所需積分50</h3>
                    <div class="getitem">
                      <button class="btn-danger">下架</button>
                      <h3>剩下1個</h3>
                    </div>
                    <div class="upTime">
                      <h4>上架時間</h4>
                      <h4>2017/08/28     </h4>
                    </div>
                  </div>            
                </div>
              </div>
              --%>  
            
            </div>
            
            <%-- 
            <ul class="pager">
              <li class="previous"><a href="#">← 上一頁</a></li>
              <li class="next"><a href="#">下一頁 →    </a></li>
              --%>
           
          </div>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
            <%@ include file="page2.file" %>
        </div>
        
      </div>
    </div>
    
 <%--         贈品上架的modal                        --%>
 
 <% Gift_dataVO gift_data_VO=(Gift_dataVO) request.getAttribute("gift_data_VO");  %>
 
    <div class="modal fade" id="modal-id">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">贈品上架</h3>
					</div>
					<div class="modal-body">
					<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font color='red'  >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgs} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>
<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet" name="form1" enctype="multipart/form-data">
						 <div class="form-group">
    <label for="ad_title" class="h3">贈品名稱</label>
    <input type="text" class="form-control" id="ad_title"  name="GIFT_NAME" placeholder="請輸入標題"  value="<%= (gift_data_VO==null)? "":gift_data_VO.getGift_name()%>"/>
  					</div>
						<div class="modal-body">
				<h3> 剩餘數量</h3>
				<div class="input-group number-spinner">

				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text"  maxlength="3" name="GIFT_REMAIN"  class="form-control text-center" value="<%= (gift_data_VO==null)? "1":gift_data_VO.getGift_remain()%>">
				<span class="input-group-btn">
					<button   type="button" class="btn btn-default" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>

				<h3> 所需積分</h3>
				<div class="input-group number-spinner">

				<span class="input-group-btn">
					<button  type="button" class="btn btn-default" data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text" maxlength="3" name="GIFT_PT" class="form-control text-center" value="<%= (gift_data_VO==null)? "1":gift_data_VO.getGift_pt()%>">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>

				<div class="form-group">
  				  <label for="exampleInputFile">贈品圖片</label>
  					  <input type="file" id="myfiles"  name="GIFT_IMG"  value="">
 					 	<output id="mylist">
 					 	<%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String gift_img="";
  						  if(gift_data_VO!=null){
 					 	byte[] data=gift_data_VO.getGift_img();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						gift_img = sb.toString();
  						}else{
  							gift_img="";
  						}
 						%>	
 					 	<img class="update_gift_img"  src="<%=gift_img %>">
 					 	</output>
  						</div>
					<h3>贈品描述</h3>
					<textarea class="form-control"  name="GIFT_CONT" rows="3"  id="gift_cont"><%= (gift_data_VO==null)? "" :gift_data_VO.getGift_cont()%></textarea>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<input type="hidden" name="action" value="insert">
						<input type="hidden" name="GIFT_LAUNCH_DATE" value="" class="nowTime">
						<input type="hidden" name="gift_data.jsp" value="<%=request.getServletPath() %>">
						<button type="submit" class="btn btn-primary">Save changes</button>
					</div>
					</FORM>
				</div>
				
			</div>
				
		</div>
	</div>
	
	
	<%--         贈品修改及取消的modal                        --%>
	
    <div class="modal fade" id="modal-update">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">贈品修改</h3>
						<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet" name="form1" >
						<button  type="submit" class="btn btn-danger deleteGift"  style="color: #eee">下架</button>
						<input type="hidden" name="action" value="delete">
						<input type="hidden" name="gift_no" value="${gift_data_vo.gift_no }">
						<input type="hidden"  name="gift_data.jsp" value="<%=request.getServletPath() %>">
						</FORM>
						
						
						
					</div>
					<div class="modal-body">
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
<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/gift_management/gift_managementServlet" name="form1" enctype="multipart/form-data">
						 <div class="form-group">
    <label for="ad_title" class="h3">贈品名稱</label>
    <input type="text" class="form-control" id="ad_title"  name="gift_name" placeholder="請輸入標題" value="${gift_data_vo.gift_name }">
  					</div>
						<div class="modal-body">
				<h3> 剩餘數量</h3>
				<div class="input-group number-spinner">

				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text" maxlength="3"  name="gift_remain"  class="form-control text-center" value="${gift_data_vo.gift_remain }">
				<span class="input-group-btn">
					<button   type="button" class="btn btn-default" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>

				<h3> 所需積分</h3>
				<div class="input-group number-spinner">

				<span class="input-group-btn">
					<button  type="button" class="btn btn-default" data-dir="dwn"><span class="glyphicon glyphicon-minus"></span></button>
				</span>
				<input type="text" maxlength="3" name="gift_pt" class="form-control text-center" value="${gift_data_vo.gift_pt }">
				<span class="input-group-btn">
					<button type="button" class="btn btn-default" data-dir="up"><span class="glyphicon glyphicon-plus"></span></button>
				</span>
			</div>

				<div class="form-group">
  				  <label for="exampleInputFile">贈品圖片</label>
  					  <input type="file" id="myfiles2"  name="gift_img"  value="">
 					 	<output id="mylist2"><img class="modify_gift_img" src="<%=request.getContextPath()%>/GiftImg.do?gift_no=${gift_data_vo.gift_no }" ></output>
  						</div>
					<h3>贈品描述</h3>
					<textarea class="form-control"  name="gift_cont" rows="3"  >${gift_data_vo.gift_cont }</textarea>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="gift_no" value="${gift_data_vo.gift_no }">
						<input type="hidden" name="gift_launch_date" value="" class="nowTime">
						<input type="hidden" name="whichPage" value="<%=whichPage%>"  >
						<input type="hidden" name="gift_data.jsp" value="<%=request.getServletPath() %>">
						<button type="submit" class="btn btn-primary">Save changes</button>
					</div>
					</FORM>
				</div>
				
			</div>
				
		</div>
	
	</div>
	
	
	
	
	
	
	
    
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript">
    if(("a${gift_data_VO.gift_img}"!="a")){
    	$(".update_gift_img").css("display","block");
    }
    // 數字的+、-按鈕控制
			$(document).on('click', '.number-spinner button', function () {    
	var btn = $(this),
		oldValue = btn.closest('.number-spinner').find('input').val().trim(),
		newVal = 0;
	
	if (btn.attr('data-dir') == 'up') {
		newVal = parseInt(oldValue) + 1;
	} else {
		if (oldValue > 1) {
			newVal = parseInt(oldValue) - 1;
		} else {
			newVal = 1;
		}
	}
	btn.closest('.number-spinner').find('input').val(newVal);
});
		
  // 上傳預覽
	function handleFileSelect(evt) {
		$("#mylist").empty();
		
		
		
	    var files = evt.target.files; // FileList object

	    // Loop through the FileList and render image files as thumbnails.
	    for (var i = 0, f; f = files[i]; i++) {

	      // Only process image files.
	      if (!f.type.match('image.*')) {
	        continue;
	      }

	      var reader = new FileReader();

	      // Closure to capture the file information.
	      reader.onload = (function(theFile) {
	        return function(e) {
	          // Render thumbnail.
	          var span = document.createElement('span');
	          span.innerHTML = ['<img class="thumb" src="', e.target.result,
	                            '" title="', escape(theFile.name), '"/>'].join('');
	          document.getElementById('mylist').insertBefore(span, null);
	          $(".thumb").width(150).height(100);
	                        
	        };
	      })(f);

	      // Read in the image file as a data URL.
	      reader.readAsDataURL(f);
	    }
	  }
	  document.getElementById('myfiles').addEventListener('change', handleFileSelect, false);
	  
	  function handleFileSelect2(evt) {
			$("#mylist2").empty();
			
			
			
		    var files = evt.target.files; // FileList object

		    // Loop through the FileList and render image files as thumbnails.
		    for (var i = 0, f; f = files[i]; i++) {

		      // Only process image files.
		      if (!f.type.match('image.*')) {
		        continue;
		      }

		      var reader = new FileReader();

		      // Closure to capture the file information.
		      reader.onload = (function(theFile) {
		        return function(e) {
		          // Render thumbnail.
		          var span = document.createElement('span');
		          span.innerHTML = ['<img class="thumb" src="', e.target.result,
		                            '" title="', escape(theFile.name), '"/>'].join('');
		          document.getElementById('mylist2').insertBefore(span, null);
		          $(".thumb").width(150).height(100);
		                        
		        };
		      })(f);

		      // Read in the image file as a data URL.
		      reader.readAsDataURL(f);
		    }
		  }
	  document.getElementById('myfiles2').addEventListener('change', handleFileSelect2, false);
	  
	  
	  // 得到現在時間並設定到#nowTime上作為參數給controller
	  var d = new Date();
	  var year = d.getFullYear();
	  var month = d.getMonth() + 1; // 记得当前月是要+1的
	  var dt = d.getDate();
	  var today = year + "-" + month + "-" + dt;  
	$(".nowTime").val(today);   
	
	
	
	// 如果上架有錯誤forward回來時會開啟上架modal
	if(${not empty errorMsgs}){
	 $("#modal-id").modal({show: true});
	}
	
	// 如果修改有錯誤forward回來時會開啟修改modal
	if(${not empty errorMsgsForUpdate}){
		 $("#modal-update").modal({show: true});
		}
	if(${not empty openModal}){
		 $("#modal-update").modal({show: true});
		}
	
	errorMsgsForUpdate
	<%--
	$("#modal-update").click(function(){
		var gift_no=$(".gift_no").val();
		${gift_dataSvc.}
		<% gift_dataSvc.getOneGift_data(gift_no); %>
		
		
	})
		
	--%>
	
	
	</script>	
  </body>
</html>