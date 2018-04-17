var interval = null; //定时器
/** The maximum number of reconnection attempts to make. Unlimited if null. */
var timeoutInterval = 12000;//单位毫秒
$(function(){//本js一被调用就执行，相当于main函数
	reconnectWebsocket();
	interval = setInterval(function() {
		console.log("websocket连接断开进行重连....使用定时器："+interval);
		reconnectWebsocket();
	}, timeoutInterval);
});
var i=0;
function reconnectWebsocket() {
	var url = null;
	i++;
	var websocket = getWebsocketObject();
	websocket.onopen = function (evnt) {
		console.log("链接服务器成功!:");
		send("1001");
		if (interval != null) {
			//console.log("清除定时器："+interval);
			clearInterval(interval);
			interval = null;
		}
    };
    websocket.onerror = function (evnt) {
    	/*for ( var p in evnt) {
    		console.log("onerror :"+p + "=" + evnt[p]);
		}*/
		console.log("onerror :"+ evnt.data);
		disconnect();//修复异常断开，同个定时器多个重连的问题
    };
    websocket.onclose = function (event) {
    	disconnect();
    	//console.log(new Date().toLocaleString() + ":on close:" + event.code + ":" + event.reason);
    };
    websocket.onmessage = function (evnt) {
    	//console.log("接收到服务器端消息推送："+evnt.data);
    	handleCmd(evnt.data);
    };
    
    function disconnect() {//断开连接
        if (websocket != null) {
        	websocket.close();
            websocket = null;
            if (interval == null) {
            	$.messager.show({
        			title: "连接断开",
        			msg: "与服务器连接断开，"+timeoutInterval/1000+"秒后将自动尝试重连...",
        			timeout: 4000,//毫秒
        			height: 80,
        			showType:'slide',
        		});
            	interval = setInterval(function() {
            		console.log("websocket连接断开进行重连....使用定时器："+interval);
            		reconnectWebsocket();
            	}, timeoutInterval);
            }
        }
    }
    function send(message) {//发送消息
        if (websocket != null) {
            websocket.send(message);
        } else {
        	console.log('未与服务器链接.');
        }
    }
}

function getWebsocketObject() {
	var websocket = null;
	if ('WebSocket' in window) {
		//console.log("ssui: "+sessionInfo_userId);
		//console.log("WebSocket");
	    if (window.location.protocol == 'http:') {
	        url = 'ws://' + window.location.host + ctx+ '/myHandler?userId='+sessionInfo_userId;
	    } else {
	        url = 'wss://' + window.location.host + ctx+ '/myHandler?userId='+sessionInfo_userId;
	    }
	    websocket = new WebSocket(url);
	    //console.log(url);//ws://localhost:8080/oddm-web/myHandler?userId=1
	} else if ('MozWebSocket' in window) { 
		//console.log("MozWebSocket");
	    if (window.location.protocol == 'http:') {
	        url = 'ws://' + ctx+ +'/myHandler?userId='+sessionInfo_userId;
	    } else {
	        url = 'wss://' + ctx+ +'/myHandler?userId='+sessionInfo_userId;
	    }
	    websocket = new MozWebSocket(url);
	    //websocket = new MozWebSocket("ws://${ctx}/myHandler");
	} else {
		//console.log("SockJS");
	    if (window.location.protocol == 'http:') {
	        url = 'http://' + window.location.host + ctx+'/sockjs/myHandler?userId='+sessionInfo_userId;
	    } else {
	        url = 'https://' + window.location.host + ctx+'/sockjs/myHandler?userId='+sessionInfo_userId;
	    }
	    websocket = new SockJS(url);
		//console.log("SockJS url : ",url);
	}
	return websocket;
};

//接收服务器端的命令，然后对应操作
function handleCmd(jsonMessage) {
	var data = {};
	try{
		data = JSON.parse(jsonMessage);
	}catch(e) {
		console.log("转换接收消息异常,非JSON格式:" + jsonMessage);
		console.log(e);
		return false;
	}
	if (data != null) {//判断不为空
    	switch (Number(data.cmd)) {
    		//首次通信
    		case 1001:
    			$.messager.show({
        			title: data.title,
        			msg: data.body,
        			timeout: 2000,//毫秒
        			height: 80,
        			showType:'slide',
        			style: {
        	            left : '',  
        	            top : '',  
        	            right : '0px',//窗口离右边距离,于left互斥  
        	            bottom : '0px',//窗口离下边距离,于top互斥  
        	            position:'fixed',//元素定位方式：fixed固定。 默认:absolute绝对定位  
        			},
        		});
    			break;
    		case 1111:
    			var length = data.body.length;
    			if (length !=null && length > 400) {
    				length = 300;
    			} else {
    				length = 150;
    			}
    			$.messager.show({
        			title: data.title,
        			msg: data.body+" 文档已上传，请及时审核！",
        			timeout: 0,//毫秒
        			height: length,
        			showType:'slide',
        			style: {
        	            left : '',  
        	            top : '',  
        	            right : '0px',//窗口离右边距离,于left互斥  
        	            bottom : '0px',//窗口离下边距离,于top互斥  
        	            position:'fixed',//元素定位方式：fixed固定。 默认:absolute绝对定位  
        			},
        		});
    			break;
    		default :
    			var length = data.body.length;
    			if (length !=null && length > 400) {
    				length = 300;
    			} else {
    				length = 150;
    			}
    			$.messager.show({
        			title: data.title,
        			msg: data.body,
        			timeout: 0,
        			height: length,
        			showType:'slide',
        			style: {
        	            left : '',  
        	            top : '',  
        	            right : '0px',//窗口离右边距离,于left互斥  
        	            bottom : '0px',//窗口离下边距离,于top互斥  
        	            position:'fixed',//元素定位方式：fixed固定。 默认:absolute绝对定位  
        			},
        		});
    	}
	}
}