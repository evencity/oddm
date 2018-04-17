<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>

 <meta charset="utf-8">
<title>Insert title here</title>
<script src="./jslib/easyui1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="./jslib/easyui1.4.2/jquery.easyui.min.js" charset="utf-8"></script>
<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
<script type="text/javascript">
$(function() {
	$('#testToken').form({
	    url:'${ctx}/token/download',
	    
	    success:function(result){
	    	console.log(result);
	    	result = $.parseJSON(result);
	    	
	    },
	    onLoadSuccess:function(data){
	    	console.log(data);
	    }
	});  
});  
function submitForm(){
	$('#testToken').submit();
	
}
</script>
</head>

<body>
	<form  method="post" id="testToken">
          <div class="login-head">
              <span>token验证</span>
          </div>
             <!--  <input id="11" type="text" name="fileType" value="document"/>
              <input id="12" type="text"  name="fileDetail" value="order"/>
              <input id="13" type="text"  name="year" value="2016"/>
              <input id="15" type="text"  name="orderNo" value="WS1508"/> -->
              <input id="16" type="text"  name=filePath value="document/order/2016/WS150800012/A5067卡通箱SN贴纸WS16080048(iMap-5700)V1.rar"/>
          <div class="login-bottom">
              <div class="login-submit">
                  <input id="loginSubmit" class="loginButton" value="验证" type="button" onclick="submitForm()"/>
                  <input id="" class="loginButton" value="清除" type="button" onclick="clearInput()"/>
              </div>
          </div>
</form>
  <input type="file" id="file" />
   xxxx<input type="file" id="clearInput" />
   <img id="imageId">
  <script type="text/javascript">
  var client ;
  var storeAs = 'document/order/2016/AAA/tet.txt';
  function clearInput(){
	  var obj = document.getElementById('clearInput') ;

	  obj.outerHTML=obj.outerHTML;
  }
 	$(function(){
 		var param = {filePath:'picture/produce/2016/0000000000/delz.png'}
 		 $.ajax({
 			//url : '${pageContext.request.contextPath}/token/update?fileType=document&fileDetail=order&year=2016&orderNo=1234567uio',
 			//url : '${pageContext.request.contextPath}/token/update?fileType=picture&fileDetail=produce&orderNo=WS16080047',
 			url : '${pageContext.request.contextPath}/token/download',
			type : "POST",
 			dataType : "json",
 			data : JSON.stringify(param),
 			contentType : "application/json; charset=utf-8",
 			success : function(data) {
 					client = new OSS.Wrapper({
 			        /* region: 'oss-cn-hangzhou',
 			        accessKeyId: 'STS.J2zp9joYHitngQDi2K3CHnsLU',
 			        accessKeySecret: '5Y5jeADovTCoipqv4FYZGm97618XjRfVjHuKbvh519U7',
 			        stsToken: 'CAESmQQIARKAAW5cl68kMLgSFYYmHTettTNZinHNr/Hi2Gt7q/cVaYsFYAW2mIlkE/CgRXnidmZADmTPVpRSdA1aXAk3zSK00LRJROgCAutpbve8CX4bknshZIWjuO9MX/+8DXWQD5gJ6fC2xwxc8SuKPT1ZfUEaSaXMSVkBv8stQriHPBn6hAZpGh1TVFMuSjJ6cDlqb1lIaXRuZ1FEaTJLM0NIbnNMVSISMzYwODIzNTU3NTc3MTgzNjg0KglhbGljZS0wMDEwvJG/5oYrOgZSc2FNRDVC3gEKATEa2AEKBUFsbG93EnAKDEFjdGlvbkVxdWFscxIGQWN0aW9uGlgKDW9zczpHZXRPYmplY3QKDW9zczpQdXRPYmplY3QKDW9zczpMaXN0UGFydHMKGG9zczpBYm9ydE11bHRpcGFydFVwbG9hZAoPb3NzOkxpc3RPYmplY3RzEl0KDlJlc291cmNlRXF1YWxzEghSZXNvdXJjZRpBCi1hY3M6b3NzOio6KjpvZGRtL2RvY3VtZW50L29yZGVyLzIwMTYvV1MxNTA4LyoKEGFjczpvc3M6KjoqOm9kZG1KEDE1MTQ3NTEyODYwMzc1OTdSBTI2ODQyWg9Bc3N1bWVkUm9sZVVzZXJgAGoSMzYwODIzNTU3NTc3MTgzNjg0chthbGl5dW5vc3N0b2tlbmdlbmVyYXRvcnJvbGV43ZjPooi12AI=',
 			        bucket: 'oddm' */
 						region: data.region,
 						accessKeyId: data.accessKeyId,
 						accessKeySecret: data.accessKeySecret,
 						stsToken : data.securityToken,
 						bucket: data.bucket
 			      });
 					// storeAs = data.path+"Ammmmmmmmmm(4344)V1.txt";
 					storeAs = data.path;
					console.log(storeAs);
				    var objectKey = data.path;
					
				    //var saveAs = path.split('/')[path.split("/").length-1];
				    console.log(objectKey + ' => ');
					var result = client.signatureUrl(objectKey, {
					     expires: 3600,
					     response: {
					    	//'Content-Type':'image/jpeg',
					    	//'x-oss-process':'image/resize,w_200'
					     },
					   /*   request : {
					    	 'x-oss-process':'image/resize,w_200'
					     },
					     process:'image/resize,w_200' */
					});
					console.log(result)
					$("#imageId").attr("src",result);
					//$("#imageId").css("height",'500px');
					//console.log($("#imageId").css("heght"));
					//var s = client.get(storeAs);
					//var style = "image/resize,m_fixed,w_100,h_100";  
					//console.log(s.setProcess())

 			}
 	  })
 	 document.getElementById('file').addEventListener('change', function (e) {
	      var file = e.target.files[0];
	      console.log(file);
	     //增 和 替换
	        console.log(file.name + ' => ' + storeAs);
	      client.multipartUpload(storeAs, file).then(function (result) {
	        console.log(result);
	        console.log("........................");
	      }).catch(function (err) {
	        console.log(err);
	      });   
	      /*  //删
	      client.delete(storeAs).then(function (result) {
		        console.log(result);
		      }).catch(function (err) {
		        console.log(err);
		      });  */
	    });
 	})
   
  </script>
</body>
</html>