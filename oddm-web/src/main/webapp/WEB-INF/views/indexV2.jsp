<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="incV2.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>爱培科订单管理系统</title>
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	var index_layout;
	var index_tabs;
	var index_tabsMenu;
	var layout_west_tree;
	var layout_west_tree_url = '';
	
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果没有登录,直接跳转到登录页面
		layout_west_tree_url = '${ctx}/resourceV2/tree';
	}else{
		window.location.href='${ctx}/adminV2/index';
	}
	$(function() {
		if(!window.console){//浏览器日志打印兼容
			window.console = {log : function(){}};
		}
		index_layout = $('#index_layout').layout({
			fit : true
		});
		
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
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					/* index_tabs.tabs('getTab', index).panel('open').panel('refresh'); 不行 */
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
		
		layout_west_tree = $('#layout_west_tree').tree({
			url : layout_west_tree_url,
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {//如果连接不为空
					var url = '${ctx}' + node.attributes.url;
					addTab({ //点击后在主界面就会出现新的界面
						url : url,
						title : node.text, //菜单名称
						iconCls : node.iconCls,
						id : url.slice(9)
					});
				} else {
					$(this).tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);   //展开点击的文本的子节点
				}
			}
		});
	});
	
	function addTab(params) {
		var iframe = '<iframe src="' + params.url + '" id="'+ params.id +'" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
		var t = $('#index_tabs');
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true
		};
		if (t.tabs('exists', opts.title)) {
			t.tabs('select', opts.title);
		} else {
			t.tabs('add', opts);
		}
	}
	
	function logout(){
		$.messager.confirm('提示','确定要退出?',function(r){
			if (r){
				progressLoad();
				$.post( '${ctx}/adminV2/logout', function(result) {
					if(result.success){
						progressClose();
						window.location.href='${ctx}/adminV2/index';
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
	<div id="index_layout">
		<div data-options="region:'north',border:false" style=" overflow: hidden;" >
			<div id="header">
			<span style="float: right; padding-right: 20px;">欢迎 <b>${sessionInfo.name}</b>&nbsp;&nbsp; <!-- <a href="javascript:void(0)" onclick="editUserPwd()" style="color: #fff">修改密码</a>&nbsp;&nbsp; --><a href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;
    		</span>
    		<span class="header"><span id="project_name">爱培科订单管理系统</span></span>
    		</div>
		</div>
		<div data-options="region:'west',split:true" title="主导航" style="width: 160px; overflow: hidden;overflow-y:auto;">
			<div class="well well-small" style="padding: 5px 5px 5px 5px;">
				<ul id="layout_west_tree"></ul>
			</div>
		</div>
		<div data-options="region:'center'" style="overflow: hidden;">
			<div id="index_tabs" style="overflow: hidden;">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
					<div style="padding:10px 0 10px 10px">
						<h2>系统介绍</h2>
						<!-- <div class="light-info">
							<div class="light-tip icon-tip"></div>
							<div>JAVA快速开发平台。</div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false" style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee" >&nbsp; </div>
	</div>
</body>
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
</html>