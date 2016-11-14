/**
 * 
 */
$(function(){  
	$.ajax({
		type: 'POST',
		url :'UserServlet',
		dataType:'json',
		data:{
			flag:"srhall",
		},
		error: function(xhr,err){
			alert('request failed:'+err+'!')
		},
		success: function(usrs){
			for(var i=0; i < usrs.length; i++){
				var usr = '<tr><td><input type="checkbox" name="subBox"  id="cb'+i+'"></td>'+
				'<td>'+usrs[i]["username"]+'</td>'+
				'<td>'+usrs[i]["password"]+'</td>'+
				'<td>'+usrs[i]["real"]+'</td>'+
				'<td>'+usrs[i]["dep"]+'</td>'+
				'<td>'+usrs[i]["role"]+'</td>'+'</tr>'
				$("#usrtb").append(usr)
			}
		}
	});
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked); 
     });
     var $subBox = $("input[name='subBox']");
     $subBox.click(function(){
         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false);
     });
});

function srhusr(){
	var username = $("#LoginName").val()
	var realname = $("#RealName").val()
	var dep = $("#dep").find("option:selected").text()
	var role = $("#role").find("option:selected").text()
	$.ajax({
		type: 'POST',
		url: 'UserServlet',
		dataType: 'json',
		data: {
			flag:"srh",
			username:username,
			realname:realname,
			dep:dep,
			role:role
		},
		error: function(xhr, err){
			alert('Error：' + err + '！')
		},
		success: function(usrs){
			$("#usrtb").empty()
			for(var i=0; i < usrs.length; i++){
				var usr = '<tr><td><input type="checkbox" name="subBox"  id=cb"'+i+'"></td>'+
				'<td>'+usrs[i]["username"]+'</td>'+
				'<td>'+usrs[i]["password"]+'</td>'+
				'<td>'+usrs[i]["real"]+'</td>'+
				'<td>'+usrs[i]["dep"]+'</td>'+
				'<td>'+usrs[i]["role"]+'</td>'+'</tr>'
				$("#usrtb").append(usr)
			}
		}
	});
}

function deluser(){
	var checked = $("input[type='checkbox'][name='subBox']");
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			var username = $(this).parent().next()[0].innerText;
			if (username=="") {
				alert("请勾选用户");
				return;
			}
			$(this).parent().parent().remove();
			$.ajax({
				type: 'POST',
				url :'UserServlet',
				dataType:'json',
				data:{
					flag:"delete",
					username:username,
				},
				error: function(xhr,err){
					alert('request failed:'+err+'!')
				},
				success: function(data){
					//alert(data.msg);
				}
			});
		}
	 });
}

function upduser(){
	var checked = $("input[type='checkbox'][name='subBox']");
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			var usr= $(this).parent().nextAll();
			var username = usr[0].innerText;
			var password = usr[1].innerText;
			var realname = usr[2].innerText;
			var dep = usr[3].innerText;
			var role = usr[4].innerText;
			if (username=="") {
				alert("请勾选用户");
				return;
			}
//			window.open('manage.html')
			$.ajax({
				type: 'post',
				url :'UserServlet',
				dataType:'json',
				data:{
					flag:"regist",
					username:username,
					password:password,
					realname:realname,
					dep:dep,
					role:role
				},
				error: function(xhr,err){
					alert('request failed:'+err+'!')
				},
				success: function(data){
					//alert(data.msg);
				}
			});
		}
	 });
}