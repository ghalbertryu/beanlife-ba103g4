<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>
<%@ page import="com.prod.model.*"%>
<%@ page import="com.act.model.*"%>
<%@ page import="com.convert_gift.model.*"%>
<%@ page import="com.store.model.*"%>
<html>
  <head>
    <meta charset="UTF-8">
    <title>廣告管理</title>
    <link rel="stylesheet prefetch" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/style.css">
    
 
    
    <%--date picker專用css --%>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/BackEnd/res/css/bootstrap-datetimepicker.min.css" />
    
  <style type="text/css">
    *{
      position: relative;
    }
    .ad_img{
      width: 150px;
      height: 120px;
      background-size: contain;
      background-position:center,center;
      background-repeat: no-repeat;
      left: 50%;
      transform: translate(-50%,0%);
    
    }
    
 
    
    td{
      vertical-align: middle;
     
     padding: 10px;
    }
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{

  vertical-align: middle;
  text-align: center;
}
.prod_no{
text-decoration: none;
cursor: pointer;
}
.prod_no:hover{
text-decoration: none;
}
 .card .right .right_middle .middle .btn {
        float: right;
        margin-top: -50px;
        padding: 10px 20px; }
        
  .date_picker{
  margin-left: -20px;

  padding: 20px;
  }
   
  .control-label {
  width: 100px;
  float: left;
  padding-left: 0px;
  }
  #prod_no_selector{
  margin-right: 30px;
  }
  .delete{
  display: inline-block;
  }
  #prod_name{
  display: inline-block;

  }
  .prod_name_update{
   display: inline-block;
 
  }
  .update{
  display: inline-block;
  }
  
  .return_img,.update_return_img{
  width: 150px;
  height: 100px;
   background-size: contain;
      background-position:center,center;
      background-repeat: no-repeat;
     
  
  }
  </style>
<%
AdService adSvc=new AdService();
List<AdVO> list=  adSvc.getAll();
pageContext.setAttribute("list",list);
%>
<%
 ProdService my_prodSvc=new ProdService();
 pageContext.setAttribute("my_prodSvc",my_prodSvc);
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
      <div class="row">
        <div class="col-xs-2 left"><a class="h3 title" href="#action" aria-expanded="false" aria-controls="action" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-futbol-o"></div><a class="h3" href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp"> 活動審核</a></a><a class="h3 title" href="#mem" aria-expanded="false" aria-controls="mem" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-address-card-o"></div><span class="h3">會員管理</span>
            <ul class="collapse" id="mem"><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem.jsp">會員資料管理</a><a  href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">廠商店家授權</a><a  href="<%=request.getContextPath()%>/BackEnd/mem/mem_pt.jsp">積分管理</a></ul></a><a class="h3 title" href="#gift" aria-expanded="false" aria-controls="gift" data-toggle="collapse" style="text-decoration: none;">
            <div class="fa fa-gift"> </div><span class="h3">平台業務管理</span>
            <ul class="collapse" id="gift"><a href="<%=request.getContextPath()%>/BackEnd/ad/ad.jsp">廣告管理</a><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">兌換贈品管理</a><a href="<%=request.getContextPath()%>/BackEnd/gift/gift_data.jsp">兌換贈品業務管理</a></ul></a></div>
        <div class="right col-xs-10">
          <div class="col-xs-12 right_top"><img src="<%=request.getContextPath()%>/BackEnd/res/images/bear.jpg" alt="">
            <h2>你好</h2><a class="fa fa-bell dropdown-toggle" href="#" data-toggle="dropdown"></a>
            <ul class="dropdown-menu">
              
              <li><a href="<%=request.getContextPath()%>/BackEnd/act/action_check.jsp">${act_count }項活動未審核</a></li>
              <li><a href="<%=request.getContextPath()%>/BackEnd/reg_store/listAllStore.jsp">${store_count }項廠商會員申請未審核</a></li>
              <li><a  href="<%=request.getContextPath()%>/BackEnd/gift/convert_gift.jsp">${convert_gift_count }項兌換贈品申請</a></li>
            </ul>
          </div>
          <div class="col-xs-12 right_middle">
            <div class="col-xs-12  middle">
              <h2 class="check">廣告管理</h2>
                <a href='#modal-id' data-toggle="modal"  class="btn btn-success">上架</a>
            </div>
          </div>
          <div class="col-xs-12 right_bottom">
          <%@ include file="ad_page1.file" %> 
            <table class="table table-striped">
              <tr>
                <th>廣告編號</th>
                <th>商品名稱</th>
                <th>廣告標題</th>
                <th>廣告圖片</th>
                <th>開始時間</th>
                <th>結束時間</th>
                <th>管理</th>
              </tr>
              <c:forEach var="ad_vo"  items="${list}" begin="<%=pageIndex %>" end="<%=pageIndex+rowsPerPage-1%>">
              <tr>
                <td  valign="middle">${ad_vo.ad_no }</td>
                <td> ${my_prodSvc.getOneProd(ad_vo.prod_no).prod_name}</td>
                <td>${ad_vo.ad_title }</td>
           <%--     <td><img class="ad_img"src="<%=request.getContextPath()%>/AdImg.do?ad_no=${ad_vo.ad_no }"></td> --%>
            <td class="ad_img_td"><div class="ad_img" style="background-image:url('<%=request.getContextPath()%>/AdImg.do?ad_no=${ad_vo.ad_no }')" ></div></td>
                <td>${ad_vo.ad_op_date }</td>
                <td>${ad_vo.ad_ed_date }</td>
                <td>
                <Form METHOD="post" ACTION="<%=request.getContextPath() %>/ad_management/ad_managementServlet"  class="delete">
                  <button class="btn btn-danger  delete_ad"  type="button">下架</button>
                 <input type="hidden" name="whichPage" value="<%=whichPage %>">
                 <input type="hidden"  name="ad.jsp"  value="<%=request.getServletPath() %>">  
                  <input type="hidden" name="action" value="delete">
                  
                  </Form>
                  <Form  METHOD="post" ACTION="<%=request.getContextPath() %>/ad_management/ad_managementServlet"  class="update">
                  <button class="btn btn-success  update_ad" type="button">修改</button>
                   <input type="hidden" name="whichPage" value="<%=whichPage %>">
                     <input type="hidden"  name="ad.jsp"  value="<%=request.getServletPath() %>"> 
                       <input type="hidden" name="action" value="getOne_For_Display">
                  </Form>
                </td>
              </tr>
                </c:forEach>
            </table>
          
          </div>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
          <%@ include file="ad_page2.file" %> 
           
        </div>
      </div>
     
    </div>
    
    
    <%--         廣告修改及取消的modal                        --%>
	<% AdVO ad_VO=(AdVO) request.getAttribute("ad_VO");  
 pageContext.setAttribute("ad_VO",ad_VO);
 
 %>
    <%
 ProdService prodService=new ProdService();
    %>
		<div class="modal fade" id="modal_update">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">廣告修改</h3>
					</div>
					
	<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad_management/ad_managementServlet" name="form1" enctype="multipart/form-data">
	<input type="hidden"  name="ad_no"   class="ad_no_forUpdate"   value="<%= (ad_VO==null)? "":ad_VO.getAd_no()%>" >
	<input type="hidden" name="prod_no" class="prod_no_forUpdate"  value="<%= (ad_VO==null)? "":ad_VO.getProd_no()%>">
	<input type="hidden"  name="ad.jsp"  value="<%=request.getServletPath() %>"> 
	<input type="hidden" name="action" value="update">
	<input type="hidden" name="whichPage" value="<%=request.getAttribute("whichPage")  %>">
					
					<div class="modal-body">
					<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgsForUpdate}">
	<font color='red'  >請修正以下錯誤:
	<ul>
		<c:forEach var="message" items="${errorMsgsForUpdate} ">
			<li>${message}</li>
		</c:forEach>
	</ul>
	</font>
</c:if>


<label for="ad_title" class="h4">廣告名稱</label>
    <input type="text" class="form-control" id="ad_title"  name="ad_title" placeholder="請輸入標題"  value="<%= (ad_VO==null)? "":ad_VO.getAd_title()%>"/>
<label for="prod_name" class="h4"  id="prod_no_selector"  class="prod_name_update">商品名稱</label>
<h4  id="prod_name" ><%= (ad_VO==null)? "":prodService.getOneProd(ad_VO.getProd_no()).getProd_name()%></h4>

<div class=op_date>
                <label for="dtp_input2" class="col-md-2 control-label  h4">開始時間</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control  test" size="16" type="text"  name="ad_op_date"  value="<%= (ad_VO==null)? "":((ad_VO.getAd_op_date()==null)?"":ad_VO.getAd_op_date())%>" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
           </div>
           <div class=end_date>
                <label for="dtp_input2" class="col-md-2 control-label  h4 ">結束時間</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control  test" size="16" type="text"  name="ad_ed_date"  value="<%= (ad_VO==null)? "":((ad_VO.getAd_ed_date()==null)?"":ad_VO.getAd_ed_date())%>" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
            </div>

<div class="form-group">
  				  <label for="exampleInputFile"  class="h4">廣告圖片</label>
  					  <input type="file" id="myfiles2"  name="ad_img"  value="">
 					 	<output id="mylist2">
 					 	<%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String ad_img="";
  						  if(ad_VO!=null){
 					 	byte[] data=ad_VO.getAd_img();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						ad_img = sb.toString();
  						}else{
  							ad_img="";
  						}
 						%>	
 				<%--  	<img class="return_img" src="<%=ad_img %>"> --%>	
 				<div class="return_img" style="background-image:url('<%=ad_img %>')" ></div>
 					 	</output>
  						</div>


					</div>
								
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<button type="submit" class="btn btn-primary">確定修改</button>
					</div>
				</FORM>
				</div>
		
			</div>
			
		</div>
    
    
    
    
    
    
    <%--         廣告上架的modal                   --%>
 
 <% AdVO ad_vo=(AdVO) request.getAttribute("ad_vo");  
 pageContext.setAttribute("ad_vo",ad_vo);
 
 %>
 <%
 ProdService prodSvc=new ProdService();
 List<ProdVO> prodList=prodSvc.getAll();
 pageContext.setAttribute("prodList",prodList);
 %>
    <div class="modal fade" id="modal-id">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h3 class="modal-title">廣告上架</h3>
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
<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad_management/ad_managementServlet" name="form1" enctype="multipart/form-data">
						
						<div class="modal-body">
						 <div class="form-group">
    <label for="ad_title" class="h4">廣告名稱</label>
    <input type="text" class="form-control" id="ad_title"  name="ad_title" placeholder="請輸入標題"  value="<%= (ad_vo==null)? "":ad_vo.getAd_title()%>"/>
  					</div>
	
	
 					<label for="prod_no" class="h4"  id="prod_no_selector">商品名稱</label>
			<select class="selectpicker"  name="prod_no"  id="prod_no">
			<option>請選擇 </option>
			 <c:forEach var="prod_vo"  items="${prodList}" >	
			  <option value="${prod_vo.prod_no }">${prod_vo.prod_name}</option>
			  
			  </c:forEach>		
				</select>
				
						
					<%-- Date picker --%>	
			<div class="form-group  date_picker">
			<div class=op_date>
                <label for="dtp_input2" class="col-md-2 control-label  h4">開始時間</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control  test" size="16" type="text"  name="ad_op_date"  value="<%= (ad_vo==null)? "":((ad_vo.getAd_op_date()==null)?"":ad_vo.getAd_op_date())%>" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
           </div>
           <div class=end_date>
                <label for="dtp_input2" class="col-md-2 control-label  h4 ">結束時間</label>
                <div class="input-group date form_date col-md-5" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd">
                    <input class="form-control  test" size="16" type="text"  name="ad_ed_date"  value="<%= (ad_vo==null)? "":((ad_vo.getAd_ed_date()==null)?"":ad_vo.getAd_ed_date())%>" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
					<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
				<input type="hidden" id="dtp_input2" value="" /><br/>
            </div>
			
			
			

		

				<div class="form-group">
  				  <label for="exampleInputFile"  class="h4">廣告圖片</label>
  					  <input type="file" id="myfiles"  name="ad_img"  value="">
 					 	<output id="mylist">
 					 	<%-- 如果上架失敗，將之前上傳的那一張用base64編碼後秀出來預覽，讓使用者不用重新上傳 --%>
 					 	
 					 	<% 
 					 	String ad_update_img="";
  						  if(ad_vo!=null){
 					 	byte[] data=ad_vo.getAd_img();
  						StringBuilder sb = new StringBuilder();
  						sb.append("data:image/png;base64,");
  						sb.append(com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(data));
  						ad_update_img = sb.toString();
  						}else{
  							ad_update_img="";
  						}
 						%>	
 				<%-- 	 	<img  class="update_return_img" src="<%=ad_update_img %>"> --%>
 				<div class="update_return_img" style="background-image:url('<%=ad_update_img %>')" ></div>
 					 	</output>
  						</div>
				

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
						<input type="hidden" name="action" value="insert">
						<input type="hidden" name="ad.jsp" value="<%=request.getServletPath() %>" class="nowTime">
					
						<button type="submit" class="btn btn-primary">廣告上架</button>
					</div>
					</FORM>
				</div>
				
			</div>
				
		</div>
	</div>
    
    
    
    
    
    
    
    
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <%--date picker專用js --%> 
     <script type="text/javascript"  src="<%=request.getContextPath()%>/BackEnd/res/js/bootstrap-datetimepicker.js"></script> 
 <script type="text/javascript"  src="<%=request.getContextPath()%>/BackEnd/res/js/bootstrap-datetimepicker.fr.js"></script>
  

    
    <script>
    
    $(document).ready(function(){
    	
    	if(${not empty openModal}){
    		 $("#modal_update").modal({show:true, keyboard:true});

    		 
    		}
    		
    	   
    
    
 // 如果上架有錯誤forward回來時會開啟上架modal
	if(${not empty errorMsgs}){
	 $("#modal-id").modal({show: true});
	 // 下拉選單能顯示之前所選的
	  $('.selectpicker option[value=<%= (ad_vo==null)? "":ad_vo.getProd_no()%>]').attr('selected', true);
	}
 
	if(${not empty errorMsgsForUpdate}){
		 $("#modal_update").modal({show: true});
		 		 
		}
 
 
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
	  
	  
	  
	  <%--Date picker--%>
	  $('.form_date').datetimepicker({
	        language:  'en',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
	
$(".delete_ad").click(function(){

	var ad_no=  $(this).parent().parent().prev().prev().prev().prev().prev().prev().text();
	$(this).parent().append("<input type='hidden' name=ad_no "+ "value="+ad_no+">");
	$(this).parent().submit();
	
})
$(".update_ad").click(function(){

	var ad_no=  $(this).parent().parent().prev().prev().prev().prev().prev().prev().text();
	$(this).parent().append("<input type='hidden' name=ad_no "+ "value="+ad_no+">");
 	$(this).parent().submit();
	
})

    });
    
    if(!("${ad_vo.ad_img}"!="")){
    	$(".update_return_img").css("display","none");
    }
    
    </script>
     
    
  </body>
</html>