<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理系统</title>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
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
	.z-main-div{
	    height: 100%;
	    width: 100%;
	    position: relative;
	}
	.z-header{
	    height: 60px;
	    width: 100%;
	    line-height:30px;
	    overflow: hidden;
	    text-align: center;
	}
	.z-header-buttonsAll .a_menu{
		border: 1px solid #DDDDDD;
		background: white;
		padding: 10px;
		border-radius: 5px;
		cursor: pointer;
		font-size: 1.1em;
		color: #333;
	}
	.z-header-buttonsAll .a_menu:hover{
		background: #04B5F9;
	}
	.z-header-buttonsAll .menuhidden{
		display: none;
	}
	.z-header-logo{
	    float: left;
	    width: 18%;
	    height: 60px;
	}
	.z-title{
	    height: 60px;
	    line-height:60px;
	    width: 100%;
	    font-size: 1.8em;
	    color: #FFF;
	}
	.z-header-buttons{
	    float: left;
	    height: 60px;
	    line-height:60px;
	    text-align: left;
	}
	.z-main{
	    height: calc(100% - 60px - 30px);
	    width: 100%;
	}
	.z-main-left{
	    float: left;
	    width: 180px;
	    height: 100%;
	}
	.z-left-main{
	    width: 100%;
	    height: 100%;
	    position: relative;
	    color: #FFF;
	}
	.z-left-top{
	    background: url("${ctx}/style/images/systembg.png") no-repeat -80px -250px;
	    height: 65px;
	    width: 100%;
	    line-height:65px;
	    overflow: hidden;
	    text-align: center;
	}
	.z-left-topImg{
	    float: left;
	    margin-left: 10%;
	    margin-top: 8%;
	}
	.z-left-topName{
	    float: left;
	    margin-left: 6%;
	    font-size: 1.5em;
	}
	.z-left-middle{
	    background: url("${ctx}/style/images/muebg.png") repeat top left;
	    height: calc(100% - 50px - 65px);
	    width: 100%;
	    overflow: auto;
	
	}
	.z-left-content{
	    height: 100%;
	    width: 90%;
	    margin: 0 auto;
	}
	.z-left-menu{
	    width: 50%;
	    float: left;
	    cursor: pointer;
	    margin-top: 10px;
	}
	.z-left-menu .icon_img img{
	    border-radius: 7px;
	
	}
	.z-left-menu .icon_img img:hover{
	    background: rgb(0,153,153);
	    box-shadow: 1px 1px 5px rgb(0,0,0);
	}
	.z-left-bottom{
	    background:url('${ctx}/style/images/systembg.png') no-repeat -80px -315px;
	    height: 50px;
	    width: 100%;
	    line-height:50px;
	    overflow: hidden;
	    text-align: center;
	    position: absolute;
	    bottom: 0;
	}
	.z-main-right{
	    float: left;
	    width: calc(100% - 190px);
	    height: 100%;
	    background-color: #FFF;
	    border-radius: 5px;
	}
	.z-main-right .tab-content{
	    height: calc(100% - 40px);
	}
	.z-footer{
	    height: 30px;
	    width: 100%;
	    line-height:30px;
	    overflow: hidden;
	    text-align: center;
	    position: absolute;
	    bottom: 0;
	}
	.z-left-bottom-menu{
	    width: 90%;
	    height: 100%;
	    margin: 0 auto;
	}
	.z-left-bottom-menuLeft{
	    float: left;
	    width: 25%;
	    height: 90%;
	    cursor: pointer;
	}
	.z-left-bottom-menuLeft .first-menu{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -268px -314px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .first-menu:hover{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -268px -337px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .second-menu{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -291px -314px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .second-menu:hover{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -291px -337px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .third-menu{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -314px -314px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .third-menu:hover{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -314px -337px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .fourth-menu{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -337px -314px;
	    margin: 35% 0% 0% 20%;
	}
	.z-left-bottom-menuLeft .fourth-menu:hover{
	    width: 23px;
	    height: 21px;
	    background: url('${ctx}/style/images/systembg.png') no-repeat -337px -337px;
	    margin: 35% 0% 0% 20%;
	}
	.menu_active{
		background: #04B5F9;
	}
	.messager-body {
	    padding: 10px;
	    overflow: auto;
}
</style>
<script type="text/javascript">
	var ctx = '${ctx}';
	var layout_west_tree_url = '';
	
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果没有登录,直接跳转到登录页面
		layout_west_tree_url = '${ctx}/resource/tree';
	}else{
		window.location.href='${ctx}/admin/index';
	}
	$(function() {/* 
		index_layout = $('#index_layout').layout({
			fit : true
		});
		*/
		/* ("body").keydown(function () {     
            if (event.keyCode == "13") {//keyCode=13回车键
                alert("0");
            }
        }); */
       /*  $.messager.show({
			title: "连接断开",
			msg: '与服务器连接断开，'+timeoutInterval/1000+'秒后将自动尝试重连注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。注册用户登录后才能发表评论，请 登录 或 注册，访问网站首页。',
			timeout: 0,//毫秒
			height: 120,
			showType:'slide',
			style:{
				 left : '',  
	            top : '',  
	              
	            right : '0px',//窗口离右边距离,于left互斥  
	            bottom : '0px',//窗口离下边距离,于top互斥  
	              
	            position:'fixed'//元素定位方式：fixed固定。 默认:absolute绝对定位  
			} 
		}); */
		index_tabs = $('#index_tabs').tabs({
			fit : true,
			border : false,
			tools : [{
				iconCls : 'icon-home',
				handler : function() {
					index_tabs.tabs('select', 0);
				}
			}, {
				iconCls : 'icon-refresh',
				handler : function() {
					/* var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					index_tabs.tabs('getTab', index).panel('open').panel('refresh'); */
					var mainPage = $('#index_tabs'); //面板所在的父容器
					mainPage.tabs('update', {
						tab : index_tabs.tabs('getSelected'), //获取当前被选中的页面
						options : {
							//Title : '新标题',
							}
						}
					);
				}
			}, {
				iconCls : 'icon-del',
				handler : function() {
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					var tab = index_tabs.tabs('getTab', index);
					if (tab.panel('options').closable) {
						index_tabs.tabs('close', index);
					}
				}
			} ]
		});
		
		/* layout_west_tree = $('#layout_west_tree').tree({
			url : layout_west_tree_url,
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url = '${ctx}' + node.attributes.url;
					addTab({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});
				}
			}
		});  */
		//加载菜单栏
		load_main_menu();
	   
	     
	     $('body').off('click','.z-left-menu').on('click','.z-left-menu',function(){
	    	var id = $(this).attr('id').substr(10,$(this).attr('id').length);
	    	$('.'+'menu_'+id).css('display','block');
	    	$('.'+'menu_'+id).siblings().css('display','none');
	    	$('.'+'menu_'+id).find('a:first').click();
	     })
	     $('.z-header-buttonsAll').off('click','a').on('click','a',function(){
			var url = $(this).attr("url");
			var title = $(this).attr("title");
			var id = $(this).attr("data-addtab");
			
			$(this).css('background','#04B5F9');
			$(this).css('color','#FFF');
			$(this).siblings().css('background','#FFF');
			$(this).siblings().css('color','#000');
			addTab({
				url : url,
				title : title,
				id : id
				/* iconCls : node.iconCls */
			});
	     })
	     
	     
	});
	 function load_main_menu(){
		 $.ajax({
	        	type: 'GET',
	            url:  layout_west_tree_url,
	            dataType:"json",
	            contentType: "application/json; charset=utf-8",
	            success: function(data){
	            	var menus = [];
	            	console.log(data)
	            	for(var i in data){	
	            		 if(data[i].pid == null || data[i].pid == undefined){
	            			//如果url为空则说明是一级菜单
	            			var content = ' <div class="z-left-menu" align="center" id="firstMenu_'+ data[i].id +'"> '+
	            			 					'<div class="icon_img"> '+
					                              ' <img alt="'+ data[i].text +'" src="'+ '${ctx}' +data[i].icon +'" height="60px" width="60px"> '+
					                             '</div> '+
					                            ' <div class="iconfont">'+ data[i].text +'</div> '+
					                       '</div> ';
					        $('.z-left-content').append(content);  
	            		}else if(data[i].pid != null && data[i].url.indexOf('manager') > -1){
	            				//二级菜单
		            			if(!$('.'+'menu_'+ data[i].pid).length > 0 ){
		            				var $contentDiv = $('<div class="menu_'+ data[i].pid +' menuhidden"></div>');
		            				$('.z-header-buttonsAll').append($contentDiv)
		            			} 
		            			var $content = $('<a type="button" class="a_menu" style="width:120px" data-addtab="'+ data[i].url +'" url="'+ '${ctx}'+ data[i].url +'" title="'+ data[i].text +'" ajax="true"><i class="glyphicon glyphicon-envelope icon_before_menu"></i>'+ data[i].text +' </a>');
		            			$('.'+'menu_'+ data[i].pid).append($content); 
	            			
	            		}  
	            	} 
	            }
	     })
	}
	 function addTab(params) {
		var iframe = '<iframe src="' + params.url + '" id="'+ params.id +'" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
		
		var t = $('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true,
			//id : params.id
		};
		//var orderInOrderNo = {id  opts.title};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
			var parent_tabs = $('#index_tabs');
			parent_tabs.tabs('update', {
				tab : parent_tabs.tabs('getSelected'), //获取当前被选中的页面
				options : {}
				}
			); 

		} else {
			t.tabs('add', opts);
		}
	} 

    
	
	function logout(){
		$.messager.confirm('提示','确定要退出?',function(r){
			if (r){
				progressLoad();
				$.post( '${ctx}/admin/logout', function(result) {
					if(result.success){
						progressClose();
						window.location.href='${ctx}/admin/index';
					}
				}, 'json');
			}
		});
	}
	

	function editUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 300,
			height : 250,
			href : '${ctx}/user/editPwdPage',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}

	
</script>
</head>
<body>
<div id="selectInputMenu"></div>
	<div id="loading" style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
	<img src="${ctx}/style/images/ajax-loader.gif" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
	</div>
	<div class="z-main-div">
    <!-- 头部 -->
    <div class="z-header">
        <div class="z-header-logo">
            <div class="z-title" align="center">爱培科订单管理系统</div>
        </div>
        <div class="z-header-buttons">
            <div class="z-header-buttonsAll">
            </div>
        </div>
    </div>
    <!-- 中部 -->
    <div class="z-main">
        <!-- 左边 -->
        <div class="z-main-left">
            <div class="z-left-main">
                <div class="z-left-top">
                    <div class="z-left-topImg">
                        <img alt="" src="${ctx}/style/images/Initialhead.jpg" height="45px" width="45px">
                    </div>
                    <div class="z-left-topName">${sessionInfo.name}</div>
                </div>
                <div class="z-left-middle">
                    <div class="z-left-content" ><!--  style="color: black;font-weight: 599;" -->
                    </div>
                </div>
                <div class="z-left-bottom">
                    <div class="z-left-bottom-menu">
                        <div class="z-left-bottom-menuLeft">
                            <div class="first-menu" title="帮助"></div>
                        </div>
                        <div class="z-left-bottom-menuLeft">
                            <div class="second-menu" title="意见反馈"></div>
                        </div>
                        <div class="z-left-bottom-menuLeft">
                            <div class="third-menu" title="用户信息修改" onclick="editUserPwd()"></div>
                        </div>
                        <div class="z-left-bottom-menuLeft">
                            <div class="fourth-menu" title="退出" onclick="logout()"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右边 -->
        <div class="z-main-right">
            <!-- <div id="tabs" style="height: 100%">
                Nav tabs
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">首页</a></li>
                </ul>
                Tab panes
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="home">

                    </div>
                </div>

            </div> -->
            <div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<div style="padding:10px 0 10px 10px">
						<h2>系统介绍</h2>
						<div class="light-info">
							<div class="light-tip icon-tip"></div>
							<div>欢迎来到<span style="color: #00CCFF;font-size: 1.2em;">&nbsp;APICAL&nbsp;</span>订单管理系统。</div>
						</div>
					</div>
				</div>
			</div>
        </div>
    </div>
    <!-- 底部 -->
    <div class="z-footer"></div>
</div>
	
	<!--[if lte IE 7]>
	<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 8</a> 或以下浏览器：
	<a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
	<![endif]-->
	
	<style>
		/*ie6提示*/
		#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
		#ie6-warning p{width:960px;margin:0 auto;}
	</style>
	<script src="${ctx}/jslib/sockjs.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/lib/websocket.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>