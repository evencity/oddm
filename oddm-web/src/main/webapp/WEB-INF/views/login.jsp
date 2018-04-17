<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta charset="utf-8">
<title>用户登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link href="${ctx}/style/css/base.css" rel="stylesheet" type="text/css">
<link href="${ctx}/style/css/login.css" rel="stylesheet" type="text/css">
<script src="${ctx}/jslib/easyui1.4.2/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/lib/encrypt/base64.js" type="text/javascript"></script>
<script src="${ctx}/lib/encrypt/BigInt.js" type="text/javascript"></script>
<script src="${ctx}/lib/encrypt/Barrett.js" type="text/javascript"></script>
<script src="${ctx}/lib/encrypt/RSA.js" type="text/javascript"></script>
<style type="text/css">
	html,body{
	    height: 100%;
	    width: 100%;
	    font: 12px arial;
	    color:#000;
	    background: url("${ctx}/style/images/mainBackGround.jpg");
	    margin: 0;
	    padding: 0;
	    background-size: cover;
	}
	.z-login{
		 color:#FFF;
	    width: 100%;
	    height: 100%;
	    position:relative;
	}
	.z-login-main{
	    width: 343px;
	    height: 245px;
	    background: url("${ctx}/style/images/systembg.png") no-repeat;
	    margin: 0 auto;
	    position:relative;
	    top: calc(50% - 125px);
	}
	.z-login-contain{
	    height: 100%;
	    width: 70%;
	    margin: 0 auto;
	}
	.z-login-contain .login-head{
	    padding: 24px 0px 20px 0px;
	}
	.z-login-contain .login-head span{
	    font-size: 1.8em;
	}
	.z-login-contain .login-username input{
	    padding: 0px 0px 0px 45px;
	    width: 185px;
	    background: #fff url("${ctx}/style/images/systembg.png") no-repeat -346px 0px;
		height: 32px;
	}
	.z-login-contain .login-password{
	    margin-top: 10px;
	}
	.z-login-contain .login-password input{
	    padding: 0px 0px 0px 45px;
	    width: 185px;
	    border: 1px solid #fff;
	    color: #333;
	    background: #fff url("${ctx}/style/images/systembg.png") no-repeat -346px -64px;
		height: 32px;
	}
	.login-bottom{
	    margin-top: 10px;
	}
	.login-bottom .login-remember{
	    float: left;
	    font-size: 1.2em;
	    padding-top: 3px;
	    width: 152px;
	}
	.login-bottom .login-submit{
	    float: left;
	}
	#login_foot{
		float: right;
		height: 30px;
		width: 198px;
		//background-color: #a2c4c9;
	}
	#login_foot a {
    	color: white;font-size:16px;font-weight: bold;
		padding: 3px 6px;
	}
	.login-bottom .login-submit input{
	    width: 80px;
	    height: 30px;
	    background: url("${ctx}/style/images/systembg.png") no-repeat 0px -246px;
	    border: 0;
	    cursor: pointer;
	    color: #FFF;
	}
	.login-bottom .login-submit input:hover{
	    background: url("${ctx}/style/images/systembg.png") no-repeat 0px -276px;
	}
</style>
<script>

	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果登录,直接跳转到index页面
		window.location.href='${ctx}/admin/index';
	}
	
	var publicModulusHex = "9a78eb89d9a2a6d0cbd031a63da6b8c44beec88e43c83968f625424661df3b0e5cab7fa4e3ab522b89eb8af47b24dc27b5ded862223ed16bbd4b3620a41e08ecebd1dfcc00577e7c6a35130f8b2621b27053e10000ad64a07ee214d08bed3acd35aa12134ed903d42c58c6a3e05b60b2d85d4e7094473dcbd81b1f05ee27f2bb";
	var publicExponentHex = "10001";
	var key;
	function createKey() {
	    setMaxDigits(130);
	    if (publicExponentHex && publicModulusHex) {
	        key = new RSAKeyPair(publicExponentHex, "", publicModulusHex);
	    } else {
	    	onsole.log("密钥未生成!");
	    }
	}
	
	function cmdEncryptClick(value) {
	    if (key) {
	        var t1 = new Date().getTime();
	        var encodeVal  = encryptedString(key, value);
	        var t2 = new Date().getTime();
	        //console.log("RSA 加密：原值 ："  +value + "  加密后： " + encodeVal +  "  耗时：" +(t2 - t1)	+ " milliseconds.");
	        return encodeVal;
	    } else {
	        return null;
	    }
	}
	
	$(function() {
		 createKey();
		 $('body').off('focus','#userName').on('focus','#userName',function(){
	         $(this).css('background','#fff url("${ctx}/style/images/systembg.png") no-repeat -346px -32px')
	     }).off('blur','#userName').on('blur','#userName',function(){
	         $(this).css('background','#fff url("${ctx}/style/images/systembg.png") no-repeat -346px 0px')
	     });
	     $('body').off('focus','#userPassword').on('focus','#userPassword',function(){
	         $(this).css('background','#fff url("${ctx}/style/images/systembg.png") no-repeat -346px -96px')
	     }).off('blur','#userPassword').on('blur','#userPassword',function(){
	         $(this).css('background','#fff url("${ctx}/style/images/systembg.png") no-repeat -346px -64px')
	     });
		 $('#loginform').form({
		    url:'${ctx}/admin/login',
		    onSubmit : function(param) {
		    	progressLoad();
			//	var isValid = $(this).form('validate');
			/* 	if(!isValid){
					progressClose();
				} */ 
			//	return isValid;
			},
		    success:function(result){
		    	$('#userPassword').val(pswrd);
		    	result = $.parseJSON(result);
		    	progressClose();
		    	if (result.success) {
		    		save();
		    		window.location.href='${ctx}/admin/index';
		    	}else{
		    		$.messager.show({
		    			title:'提示',
		    			msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
		    			showType:'show'
		    		});
		    	}
		    },
		    onLoadSuccess:function(data){
		    	console.log(data);
		    }
		});  
		/* $('.z-login-contain').off('click','#loginSubmit').on('click','#loginSubmit',function(){
            var username = $('#userName').val();
            var userpassword = $('#userPassword').val();
            var checked = $('.login-remember').find('input').prop('checked');
            var param = {"loginname":username,"password":userpassword}
            $.ajax({
            	type: 'POST',
                url: "${ctx}/admin/login" ,
                data: JSON.stringify(param) ,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function(data){
                	if(data.success){
                		window.location.href = '${ctx}/admin/index';
                	}else{
                		alert("用户名或密码错误")
                		 $.messager.show({
    		    			title:'提示',
    		    			msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
    		    			showType:'show'
    		    		});       
                	}
                }
            })
        }); */
		 $("body").keydown(function(event) {
			    if (event.keyCode == "13") {//keyCode=13是回车键
			    	event.returnValue=false;
		        	event.cancel = true;
		        	submitForm();
			    }
			});
	});
	var b = new Base64();
	 //记住用户名密码   
	function save() {
		$.cookie("login-version", "V1", { expires: 7 });//设置版本，noSession.jsp可以控制页面跳转
		//alert("没有缓存cookie save");
		if ($("#ck_rmb").is(':checked')) {
			var str_username = $("#userName").val();
			var str_password = $("#userPassword").val();
			$.cookie("rmbUser", "true", { expires: 7 });
			//存储一个带7天期限的cookie
			$.cookie("ln",  b.encode(str_username), { expires: 7 });
			$.cookie("lp",  b.encode(str_password), { expires: 7 });
		} else {
			$.cookie("rmbUser", "false", { expire: -1 });
			$.cookie("ln", "", { expires: -1 });
			$.cookie("lp", "", { expires: -1 });
		}   
	};
	var pswrd = null;
	function submitForm(){
		if($('#userName').val().trim() == '' || $('#userPassword').val().trim() ==''){
			$.messager.alert('提示', '用户名或者密码不能为空！', 'warning');
			return ;
		}
		if ($('#userPassword').val().trim().length > 64) {
			$.messager.alert('提示', '密码长度大于64位！', 'warning');
			$('#userPassword').val('');
			return ;
		}
		//加密
		pswrd = $('#userPassword').val();
		//console.log("cmdEncryptClick(pswrd) "+cmdEncryptClick(pswrd));
		$('#userPassword').val(cmdEncryptClick(pswrd));
		$('#loginform').submit();
	}
	
	function clearForm(){
		$('#loginform').form('clear');
	}
	
	//回车登录, easyui 会自动监听，不经过此方法
	function enterlogin(){
		if (event.keyCode == 13){
        	event.returnValue=false;
        	event.cancel = true;
        	//加密
    		submitForm();
    	}
	} 
	
</script>
</head>
<body> <!-- onkeydown="enterlogin()" -->
	<!-- <div class="login">
		<form  method="post" id="loginform">
		<div class="logo"></div>
	    <div class="login_form">
	    	<div class="user">
	        	<input class="text_value" type="text" name="loginname" data-options="required:true" value="admin" ></input>
	            <input class="text_value" type="password" name="password" value="" ></input>
	        </div>
	        <button class="button"  type="button" onclick="submitForm()">登录</button>
	    </div>
	    
	    <div id="tip"></div>
	    
	    </form>
	</div> --> 
	
	<div class="z-login">
	    <div style="position:absolute;top:50%;"></div>
	    <div class="z-login-main" >
	        <div class="z-login-contain">
		        <form  method="post" id="loginform">
		            <div class="login-head">
		                <span>系统登陆</span>
		            </div>
		            <div class="login-username">
		                <input id="userName" type="text"  name="loginname"  />
		            </div>
		            <div class="login-password">
		                <input id="userPassword" type="password" autocomplete="off" name="password"/>
		            </div>
		            <div class="login-bottom">
		                <div class="login-remember">
		                    <label>
		                        <input id="ck_rmb" type="checkbox" checked="checked">
		                        <span>记住密码</span>
		                    </label>
		                </div>
		                <div class="login-submit">
		                    <input id="loginSubmit" class="loginButton" value="登录" type="button" onclick="submitForm()"/>
		                </div>
		            </div>
				</form>
	        </div>
	        <%--    <div id="login_foot"><a href="${ctx}/adminV2/index">新风格登陆界面入口>></a> </div> --%>
	        
	    </div>
	</div> 
	
	<!--[if lte IE 7]>
	<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
	<a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
	<![endif]-->
	
	</body>
	<script type="text/javascript">
		if ($.cookie("rmbUser") == "true") {
			$("#ck_rmb").attr("checked", true);
			//alert("loginname: "+ $.cookie("loginname"));
			$("#userName").attr("value", b.decode($.cookie("ln")));
			//alert("loginname: "+ $("#txt_username").val());
			$("#userPassword").attr("value", b.decode($.cookie("lp")));
		}
		
		</script>
	<style>
		/*ie6提示*/
		#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
		#ie6-warning p{width:960px;margin:0 auto;}
	</style>
</html>