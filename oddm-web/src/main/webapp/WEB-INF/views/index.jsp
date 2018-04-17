<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单管理系统</title>
<link href="${ctx}/style/css/index.css" rel="stylesheet" type="text/css">
<script src="${ctx}/jslib/aliyun-oss-sdk-4.4.4.min.js"></script>
<script src="${ctx}/jslib/promise-6.1.0.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	var layout_west_tree_url = '';
	var edit_modal_flag = true;//编辑页面时为true 显示弹窗
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果没有登录,直接跳转到登录页面
		layout_west_tree_url = '${ctx}/resource/tree';
	}else{
		window.location.href='${ctx}/admin/index';
	}

	$(function() {
		var timeoutflag = "";
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
					var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
					
			/*****************延迟刷新，修复狂点击的问题*******************/
					if(timeoutflag != ""){
						//console.log("退出"+timeoutflag);
						return;
			        }
					timeoutflag=setTimeout(function(){
						//console.log(new Date().getMilliseconds());
						clearTimeout(timeoutflag);
						//console.log("清除定时任务"+timeoutflag);
						timeoutflag = "";
					},800);
					
					//如果索引为0，则刷新主页的公告，修复首页不刷新的问题 
					if (index == 0) {
						loadHomePage();
					}
					mainPage.tabs('update', {
						tab : index_tabs.tabs('getSelected'), //获取当前被选中的页面
						options : {
							//Title : '新标题',
							}
						}
					);
			/************************************/
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
			} ],
			 onBeforeClose: function(title,index){
		    		var target = this;
		    		if(title.indexOf("(编辑)") > 0 && edit_modal_flag) {
		    			$.messager.confirm('提示','确认关闭当前编辑界面 ',function(r){
			    			if (r){
			    		 		var opts = $(target).tabs('options');
			    				var bc = opts.onBeforeClose;
			    				opts.onBeforeClose = function(){};  // allowed to close now
			    				$(target).tabs('close',index);
			    				opts.onBeforeClose = bc;  // restore the event function */
			    				// edit_modal_flag = false;
			    			}
			    		});
		    			return false;
		    		}
		    	
		    	  },
		    	  onClose:function(title,index) {
		    	
		    	edit_modal_flag = true;//编辑页面时为true 显示弹窗
		    }
		});
		
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
	     //加载首页的数据通知公告，见index.js
	     loadHomePage();
	});

	 function load_main_menu(){
		 $.ajax({
	        	type: 'GET',
	            url:  layout_west_tree_url,
	            dataType:"json",
	            contentType: "application/json; charset=utf-8",
	            success: function(data){
	            	var menus = [];
	            	// console.log(data)
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
		var opts = {
			title : params.title,
			closable : true,
			iconCls : params.iconCls,
			content : iframe,
			border : false,
			fit : true,
			//id : params.id
		};
		//获取index.jsp页面的tab
		var parent_tabs = $('#index_tabs');
		var allTabs=$('#index_tabs').tabs('tabs');//遍历所有tab
		
		var i = 0;
		for (var v in allTabs) { // v就是索引String类型
			 //console.log("curtab  "+  allTabs[v].panel('options').title);
			 var curtab = allTabs[v];
			 if (params.title == curtab.panel('options').title) {
				//获取tab的iframe对象  
		        $("iframe",curtab);//获取tab中的iframe
		        //$("iframe",curtab).contents().find("input").val();获取tab中的iframe中的input的值
			  	//console.log("curtab  "+  $("iframe",tab).attr('id'));
		        var preId = $("iframe",curtab).attr('id');//获取上一个id
		        if (typeof(params.id) != "undefined" && typeof(preId) != "undefined" && params.id != null && preId != null && preId != params.id) {
		        	//增加新的
		        } else {
		        	i++;
		        	//刷新选中的
		        	parent_tabs.tabs('select', parseInt(v));
					parent_tabs.tabs('update', {
						tab : curtab, //获取当前被选中的页面
						options : {}
						}
					); 
					return;
		        }
			 }
		}
		if (i == 0) {
			parent_tabs.tabs('add', opts);
		}
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
                    	<c:choose>
						   <c:when test="${sessionInfo.sex == '3'}">  
                        		<img alt="" src="${ctx}/style/images/InitialheadFeMale.png" height="45px" width="45px">
						   </c:when>
						   <c:otherwise> 
                        		<img alt="" src="${ctx}/style/images/InitialheadMale.png" height="40px" width="40px">
						   </c:otherwise>
						</c:choose>
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
                          <!--   <div class="third-menu" title="用户信息修改" onclick="editUserPwd()"></div> -->
                            <div class="third-menu" title="用户信息修改" ></div>
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
            <div id="index_tabs" style="overflow: hidden;" data-flag="fasle">
				<div title="首页" data-options="border:false" style="overflow: hidden;">
				<!--	<div style="padding:10px 0 10px 10px">
				 		<h2>系统介绍</h2>
						<div class="light-info">
							<div class="light-tip icon-tip"></div>
							<div>欢迎来到<span style="color: #00CCFF;font-size: 1.2em;">&nbsp;APICAL&nbsp;</span>订单管理系统。</div>
						</div> 
						</div>-->
				<!-- <div style="float: right;">
					<embed wmode="transparent" src="http://chabudai.sakura.ne.jp/blogparts/honehoneclock/honehone_clock_wh.swf" quality="high" bgcolor="#ffffff" width="160" height="70" name="honehoneclock" align="middle" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer">
				</div> -->
				<div style="width: 340px;margin-top: 2%;margin-left: 15px">
					<div id="noticeId" class="easyui-panel" title="公告通知" style="width:100%;height:210px;padding:10px;background:#FFFFFF;"> 
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
	
</body>
	<style>
		/*ie6提示*/
		#ie6-warning{width:100%;position:absolute;top:0;left:0;background:#fae692;padding:5px 0;font-size:12px}
		#ie6-warning p{width:960px;margin:0 auto;}
	</style>
	<script src="${ctx}/lib/index.js"></script>
	<script src="${ctx}/jslib/sockjs.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${ctx}/lib/websocket.js" type="text/javascript" charset="utf-8"></script>
</html>