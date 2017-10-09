
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.act.model.*"%>

  

<jsp:include page="/FrontEnd/include/head.jsp"/>
<c:set var="mem_ac" value="${sessionScope.mem_ac}" scope="page"/>
  

      <link rel='stylesheet prefetch' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>

  <style>
  
  



.third {
  color: white;
  background-color: #6F5C60;
}

.circles {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
  width: 100%;
}
.circles .circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  border: solid 1px black;
}
.circles .circle h2 {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -110%);
}
.circles .line {
  width: 50px;
  border: solid 5px black;
}

.title {
  text-align: center;
}

.endMessage {
  border: solid 1px black;
  padding: 30px 50px;
  border-radius: 50px;
  margin-top: 50px;
}

h2 {
  display: inline-block;
  font-size: 25px;
}

.next {
  height: 100px;
}
.next .previous {
  position: absolute;
  right: 35%;
  top: 30%;
}


.bottom {
  position: relative;
  left: 50%;
  transform: translate(-50%, 0);
  font-weight: bold;
}
  
  
  .next .previous:hover{
  text-decoration: none;
  }
  .next  form .end {
  position: absolute;
  right: 20%;
  top: 30px;
  padding: 8px 10px;

}
  
  .my_area{

   width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #eee8e1;

  }
  
  .my_area *{
   position: relative;
  }
  .move_forWatch{
  margin-top: 57px;
  }
  
  body .next .previous{
  color: #eee;
  }
  
  .fa-check-square{
  left: 50%;
  transform: translate(-50%,0%);
  font-size: 50px;
  padding-left: 10px;
  }
  .write_ok{
  margin-left: 10px;
  }
  
  .fa-paper-plane{
  margin-right: 10px;
  }
  </style>
  
  
  
<div  class="my_area  content">
  <div class="move_forWatch">
<div class="circles">
  <div class="circle first">
    <h2>1</h2>
  </div>
  <div class="line"></div>
  <div class="circle second">
    <h2>2</h2>
  </div>
  <div class="line"></div>
  <div class="circle third">
    <h2>3</h2>
  </div>
</div>
<h1 class="fa fa-check-square"><span class="write_ok">填寫完成</span></h1>
<div class="container">
  <div class="col-md-12 endMessage">
  
    <h3>感謝您的填寫，您的活動已送往審核，如審核通過將在您的信箱通知您，如果資料確定無誤請按確認送出，如需修改請點擊上一步修改。
    
    </h3>
    <h3>成功舉辦活動可獲得5積分，收集夠多積分即可在兌換專區兌換BeanLife精美贈品喔~</h3>
  </div>
</div>
<div class="next">
  <a class="btn-success btn-lg previous"  href="<%=request.getContextPath() %>/FrontEnd/act/start_act2.jsp">上一步</a>
  <form  method="post"  action="<%=request.getContextPath() %>/act_management/act_managementServlet">
  <button class="btn-success btn-lg end"><span  class="fa fa-paper-plane"></span>確認送出</button>
  <input type="hidden" name="action"  value="start_act_complete">
  <input type="hidden" name="start_act3.jsp" value="<%=request.getServletPath() %>">
  </form>
</div>
</div>
</div>
  
<jsp:include page="/FrontEnd/include/footer.jsp"/>
