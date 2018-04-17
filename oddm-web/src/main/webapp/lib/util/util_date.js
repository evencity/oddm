// 通用工具类js

/**************** 格式化日期，传入毫秒数，返回更新时间 ****************/
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

    /************ 格式化日期，传入毫秒数，仅返回日期 ***************/
	function dayformatter(milliseconds){
		if (milliseconds == null || milliseconds == "") return "";
		var oDate = new Date(milliseconds),  
        oYear = oDate.getFullYear(),  
        oMonth = oDate.getMonth()+1,  
        oDay = oDate.getDate(),  
        oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间  
        return oTime;  
	}
	//补0操作  
    function getzf(num){  
        if(parseInt(num) < 10){  
            num = '0'+num;  
        }  
        return num;  
    }
    /**************** 格式化日期，传入2015-02-02 ****************/
