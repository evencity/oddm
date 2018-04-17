/*setInterval (function () {
   $(".z-title").animate({opacity:"1",marginLeft:"-200px"},10000);
    $(".z-title").animate({opacity:"0",marginLeft:"100%"},0)
}, 3000);*/

/**************** 用户操作 ****************/
	function logout(){
		$.messager.confirm('提示','确定要退出?',function(r){
			if (r){
				progressLoad();
				$.post(ctx +'/admin/logout', function(result) {
					if(result.success){
						progressClose();
						window.location.href=ctx +'/admin/index';
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
			href : ctx+'/user/editPwdPage',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
					f.submit();
				}
			} ]
		});
	}

/**************** 格式化日期，传入毫秒数 ****************/
	function dateformatter(milliseconds){
		var oDate = new Date(milliseconds),  
        oYear = oDate.getFullYear(),  
        oMonth = oDate.getMonth()+1,  
        oDay = oDate.getDate(),  
        oHour = oDate.getHours(),  
        oMin = oDate.getMinutes(),  
        oSen = oDate.getSeconds(),  
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay) +' '+ getzf(oHour) +':'+ getzf(oMin) +':'+getzf(oSen);//最后拼接时间  
        return oTime;  
	}
	//补0操作  
    function getzf(num){  
        if(parseInt(num) < 10){  
            num = '0'+num;  
        }  
        return num;  
    }  
/**************** 公告通知 ****************/
    var currentMsec = new Date().getTime();
    //console.log(currentMsec);
    function loadHomePage() {
    	progressLoad();
    	$.ajax({
    	  	  type: "POST",
    	  	  url : ctx + '/sysNotice/dataGrid2',
    	  	  data :"page=1&rows=7",
    	  	  dataType: "json",
	 		  success: function(msg){
	 			progressClose();
				if (msg != null && msg.rows != null) {
					var html = "";
					var j = 0;
					//如果1天内发布的 则显示闪烁，加一个类名，后面用jquery特效
					for(var i in msg.rows){	
						j++;
						if (j <= 6) {
							//console.log(msg.rows[i].title);
							if ((currentMsec-msg.rows[i].pubdate) < 86400000) {
								if (msg.rows[i].title.length >16) {
			   	   					html += "<li class= \"noticte_color\" onclick='showDetail(this,\""+msg.rows[i].title+"\")' id="+msg.rows[i].id+">"+dateformatter(msg.rows[i].pubdate)+"&nbsp;&nbsp;"+msg.rows[i].title.substr(0, 15)+"..."+"</a></li><br />";
			   					} else {
			   	   					html += "<li class= \"noticte_color\" onclick='showDetail(this,\""+msg.rows[i].title+"\")' id="+msg.rows[i].id+">"+dateformatter(msg.rows[i].pubdate)+"&nbsp;&nbsp;"+msg.rows[i].title+"</a></li> <br />";
			   					}
							} else {
								if (msg.rows[i].title.length >16) {
			   	   					html += "<li onclick='showDetail(this,\""+msg.rows[i].title+"\")' id="+msg.rows[i].id+">"+dateformatter(msg.rows[i].pubdate)+"&nbsp;&nbsp;"+msg.rows[i].title.substr(0, 15)+"..."+"</a></li><br />";
			   					} else {
			   	   					html += "<li onclick='showDetail(this,\""+msg.rows[i].title+"\")' id="+msg.rows[i].id+">"+dateformatter(msg.rows[i].pubdate)+"&nbsp;&nbsp;"+msg.rows[i].title+"</a></li> <br />";
			   					}
							}
						}
	 				}
					if (html != "") {
						$('#noticeId').html(html);
						if (j > 6) {
							$('#noticeId').append("<button id='moreNotice' type='button' style='float:right;margin-top:-4px;margin-right: 11px'>>>查看更多</button>")
							$("#moreNotice").click(function(){
								var url = ctx+"/sysNotice/managerForAll";
							  	var title = "公告通知";
								window.parent.addTab({
									url : url,
									title : title,
								});
							});
						}
						$('#noticeId li').css({"list-style": "none","line-height":"0.8","color":"blue"});
						$('.noticte_color').css({"color":"red"});
					}
	 			}
			  },
	    	  error: function(e) { 
	    		  progressClose();
	    	  }
    	  	});
    }
	function showDetail(id, title){
	  	var url = ctx+"/sysNotice/getPage?id="+id.id;
	  	var title = "公告:"+title;
		window.parent.addTab({
			url : url,
			title : title,
		});
	}
/**************** 公告通知 ****************/
