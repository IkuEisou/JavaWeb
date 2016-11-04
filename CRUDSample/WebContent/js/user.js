function loginCheck(){
	var username = $("#username").val();
	var password = $("#password").val();
	if (username==""||password=="") {
		alert("please input username and password");
		return false;
	}
}

function regist(){
	var username = $("#id_add").val();
	var password = $("#id_pass").val();
	var token = $("#token").val();
	if (username==""||password=="") {
		alert("please input username and password");
		return false;
	}
	$.ajax({
		type: 'POST',
		url: 'UserServlet',
		dataType: 'json',
		data: {
			flag:"regist",
			username:username,
			password:password,
			token:token,
		},
		error: function(xhr, err){
			alert('Error：' + err + '！')
		},
		success: function(data){
			alert(data.msg);	
		}
	});
}

function deluser(){
	var username = $("#id_de").val();
	var token = $("#token").val();
	if (username=="") {
		alert("please input username");
		return;
	}
	if(!confirm("confirm the deletion?")){ return flase;}
	$.ajax({
		type: 'POST',
		url :'UserServlet',
		dataType:'json',
		data:{
			flag:"delete",
			username:username,
			token:token,
		},
		error: function(xhr,err){
			alert('request failed:'+err+'!')
		},
		success: function(data){
			alert(data.msg);
		}
	});
}

function onload(){
	$.ajax({
		type: 'POST',
		url :'UserServlet',
		dataType:'json',
		data:"flag=checkLogin",
		error: function(xhr,err){
		},
		success: function(data){
			if(data.msg=="NOLOGIN"){
				alert("please login.");
				window.location.href="login.html";
			}
		}
	});
}
