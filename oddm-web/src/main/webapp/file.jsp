<!-- oscar999  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta name="author" content="oscar999">
  <title></title>
  <script src="./jslib/easyui1.4.2/jquery.min.js"
	type="text/javascript" charset="utf-8"></script>
  <script>
  var f = null;
  function  handleFiles(files) {
	 // console.log(files);
	
	if (f == null) {
		f = files.files;
		console.log(f);
		
		
	} else {
		f[0].name ="aaaa";
		files.files = f;
	}
	 

  }


  </script>
  
  </head>
  <body>
  <input type="file" accept=".rar" id="file1"   />

  <input type="file" accept=".rar" id="file2"  onchange="handleFiles(this)"/>
  
  <br><br><br><br><br><br><br> 
<input type="text" value=""/><input type="file" value="C:\abc.txt" onchange="this.previousSibling.value=this.value;" style="width:20"/><br><br> 
  
  
  </body>
  <script>

  		var x=document.getElementById("file1");
 	     //console.log("ddd:"+x);

  		//x.disabled = true;
  		//x.value = "ddddddd.zip"; //file.jsp:44 Uncaught InvalidStateError: Failed to set the 'value' property on 'HTMLInputElement': This input element accepts a filename, which may only be programmatically set to the empty string.
  		//x.defaultValue = "ddd.zip";
  	     //console.log("ddd:"+x.defaultValue);
  	     //console.log("ddd:"+x);

  	    /*  var objFile= x; 
		var WshShell=new ActiveXObject("WScript.Shell"); 
		objFile.focus(); 
		WshShell.SendKeys("D:\\Desktop\\d2.html");  */


  </script>
</html>