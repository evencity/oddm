<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
<!--
	#selectInputMenu{
		position: absolute;
		background: white;
		z-index: 1;
		border: 1px solid;
		overflow: auto;
		max-height: 200px;
		z-index: 9999;
	}
	#selectInputMenu ul{
		padding: 0px;
		margin : 0px;
		list-style: none;
	}
	#selectInputMenu ul li{
		padding : 8px;
		cursor: pointer;
	}
	#selectInputMenu li:hover{
		background: #0cf;
	}
	#merchandiserName{border:1px solid #CCC;width:180px;padding:5px 0 2px 4px;font-size:12px;color:#999;border-radius: 4px;}
-->
</style>
<script type="text/javascript">
	$(function() {
		$('body').click(function(e) {
			//console.log(e.target.tagName)判断当前点击的元素
			if(e.target.tagName.toLocaleLowerCase() !='input'){
				$("#selectInputMenu").hide(); 
			}
		 }); 
		 $('body').off('keyup','#merchandiserName').on('keyup','#merchandiserName',function(){
			 var input = $(this);
			 if(input.val().trim() != "" && input.val() != null){
				 $.ajax({
					type: 'POST',
				    url: "${ctx}/user/listMerchandiser?username="+ encodeURIComponent($('#merchandiserName').val().trim()),
				    dataType: "json",
				    contentType: "application/json; charset=utf-8",
				    success: function(data){
				    	if(data.length > 0){
				    		$("#selectInputMenu").css('display','block');
							var width = input.css('width');
							var top = input.offset().top;
							var height = input.css('height')
							var left = input.offset().left;
							$("#selectInputMenu").empty();
							$('#selectInputMenu').css('width',width);
							$('#selectInputMenu').css({ "top": top + 25, "left": left});
					    	$("#selectInputMenu").empty(); 
					    	var $ul = $('<ul></ul>');
						  	for(var i in data){
						  		var $content = $('<li class="inputValueLi">'+  data[i].username +'</li>');
						  		$content.data('merchandiserId',data[i].id)
								$ul.append($content)
						  	}
							$('#selectInputMenu').append($ul)
							$('#selectInputMenu').off('click','.inputValueLi').on('click','.inputValueLi',function(e,msg){
								var liValue = $(this).html();
								input.val(liValue);
								input.data('merchandiserId',$(this).data('merchandiserId'))
								console.log(input.data('merchandiserId'))
								$("#selectInputMenu").css('display','none');
								
							})
				    	}
				   	}
				 }) 
			 }
		 });
		
	});
</script>
<div style="margin: 20px 0px 0px 20px">
	<table >
		<tr>
			<td>跟单名称：</td>
			<td>
				<input id="merchandiserName" type="text" placeholder="请输入业务名称">
			</td>
		</tr>
	</table>
</div>
