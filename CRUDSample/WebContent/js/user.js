function loginCheck(){
	var username = $("#username").val();
	var password = $("#password").val();
	if (username==""||password=="") {
		alert("please input username and password");
		return false;
	}
}

function regist(){
	var username = $("#id_add").val()
	var password = $("#id_pass").val()
	var realname = $("#id_real").val()
	var dep = $("#id_dep").find("option:selected").text()
	var role = $("#id_role").find("option:selected").text()

	if (username=="" || password=="") {
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
			realname:realname,
			dep:dep,
			role:role
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
	if (username=="") {
		alert("please input username");
		return;
	}
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
			next = document.getElementById("1row").nextSibling();
			
		}
	});
}

