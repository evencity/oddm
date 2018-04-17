// 格式化金钱逗号分隔小数，支持小数位数
function toMoney(s, n) { 
	n = n > 0 && n <= 20 ? n : 2; 
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
	t = ""; 
	for (i = 0; i < l.length; i++) { 
	t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
	} 
	return t.split("").reverse().join("") + "." + r; 
}

//格式化整数，以逗号分开
function formatNum(strNum) {
	if(strNum == null || strNum == "") return 0;
	if (strNum.length <= 3) return strNum; 
	if (!/^(\+|-)?(\d+)(\.\d+)?$/.test(strNum)) return strNum;
	var a = RegExp.$1, b = RegExp.$2, c = RegExp.$3;
	var re = new RegExp();
	re.compile("(\\d)(\\d{3})(,|$)");
	while (re.test(b)) {
		b = b.replace(re, "$1,$2$3");
	}
	return a + "" + b + "" + c;
}


