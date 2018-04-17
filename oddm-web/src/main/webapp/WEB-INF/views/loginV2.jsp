<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="incV2.jsp"></jsp:include>
<meta charset="utf-8">
<title>用户登录</title>
<link rel="icon" href="${ctx}/style/images/apical-logo-min.ico" type="image/x-icon"/> 
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link href="${ctx}/style/css/base.css" rel="stylesheet" type="text/css">
<link href="${ctx}/style/css/loginV2.css" rel="stylesheet" type="text/css">
<script src="${ctx}/jslib/easyui1.4.2/jquery.cookie.js" type="text/javascript"></script>
<script>
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果登录,直接跳转到index页面
		window.location.href='${ctx}/adminV2/index';
	}
	$(function() {
		//调用css方法重新计算页面布局
		window.onresize = css;  
	    css(); 
	    //页脚显示版权
	    var date = new Date($.ajax({async: false}).getResponseHeader("Date"));
		var myYear = date.getFullYear();
		var copyright = "Copyright © "+myYear+" APICAL CO., LTD. All Rights Reserved.";
		$('#login-footer').html(copyright);
		
		$('#loginform').form({
		    url:'${ctx}/adminV2/login',
		    onSubmit : function() {
		    	save();
				var isValid = $(this).form('validate');
				if(!isValid){
					progressClose();
				} 
				return isValid;
			},
		    success:function(result){
		    	result = $.parseJSON(result);
		    	progressClose();
		    	if (result.success) {
		    		window.location.href='${ctx}/adminV2/index';
		    	}else{
		    		$.messager.show({
		    			title:'提示',
		    			msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
		    			showType:'show'
		    		});
		    	}
		    }
		});
	});
	function css() {
		//高
		var windowH = $(window).height();
		var mainbodyH = windowH-150;
		if (mainbodyH < 400) {
			mainbodyH = 400;
		}
		var loginformH = 250;
		$("#login-mainbody").height(mainbodyH);
		$("#loginform").height(loginformH);
		$("#loginform").css({"margin-top": (mainbodyH-loginformH)/2-1});
		//宽
	 	var windowW = $(window).width();
		var mainbodyRightW = windowW/2;
		//alert(mainbodyRightW);
	 	if (mainbodyRightW < 400) {
			mainbodyRightW = 400;
		} 
		var loginformW = 300;
		/* $("#login-mainbody").width(windowW); */
		$("#loginform").width(loginformW);
		$("#loginform").css({"margin-left": (mainbodyRightW-loginformW)/2-1}); 

		
	}
	  //记住用户名密码   
	function save() {
		$.cookie("login-version", "V2", { expires: 7 });//设置版本，noSession.jsp可以控制页面跳转
		//alert("没有缓存cookie save");
		if ($("#ck_rmbUser").is(':checked')) {
			var str_username = $("#txt_username").val();
			var str_password = $("#txt_password").val();
			$.cookie("rmbUser", "true", { expires: 7 });
			//存储一个带7天期限的cookie
			$.cookie("ln", str_username, { expires: 7 });
			$.cookie("lp", str_password, { expires: 7 });
		}else {
			$.cookie("rmbUser", "false", { expire: -1 });
			$.cookie("ln", "", { expires: -1 });
			$.cookie("lp", "", { expires: -1 });
		}   
	};
/*  	function submitForm(){
		$('#loginform').submit();
	} 
	
	function clearForm(){
		$('#loginform').form('clear');
	}
	
	//回车登录
	function enterlogin(){
		if (event.keyCode == 13){
        	event.returnValue=false;
        	event.cancel = true;
        	$('#loginform').submit();
    	}
	}  */
</script>
</head>
<body>
   	<div id="login-wrap">
		<div id="login-header"></div>
		<div id="login-mainbody">
			<div id="login-leftSide"></div>
			<div id="login-rightSide">
				<form  method="post" id="loginform">
					<div id="title">管理系统登录</div>
			    	<div class="login">
			        	<div><input id="txt_username" class="easyui-textbox" type="text" name="loginname" data-options="iconCls:'icon-man',required:true" style="width:250px;height:35px" value=""/></div>
			            <div><input id="txt_password" class="easyui-textbox" type="password" name="password" data-options="iconCls:'icon-lock',required:true" style="width:250px;height:35px" value=""/> </div>
		        	    <input id="ck_rmbUser" type="checkbox" />
		        	    <label for="ck_rmbUser">&nbsp;记住密码</label>
		       	        <button class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="width:80px;">登录</button>
			        </div>
			    	<div id="tip"></div>
			    </form>
		    </div>
		</div>
		<!--[if lte IE 7]>
		<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
		<a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
		<![endif]-->
		<div id="login-footer"></div>
	</div>
</body>
<script type="text/javascript">
if ($.cookie("rmbUser") == "true") {
	$("#ck_rmbUser").attr("checked", true);
	//alert("loginname: "+ $.cookie("loginname"));
	$("#txt_username").attr("value", $.cookie("ln"));
	//alert("loginname: "+ $("#txt_username").val());
	$("#txt_password").attr("value", $.cookie("lp"));
} 
</script>
<style>
	/*ie6提示*/
	#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
	#ie6-warning p{width:960px;margin:0 auto;}
</style>
</html>