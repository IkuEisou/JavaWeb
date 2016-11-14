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

function onload(){
	var username = $.getUrlVar('username')
	var password = $.getUrlVar('password')
	var realname = decodeURIComponent($.getUrlVar('realname'))
	var dep = decodeURIComponent($.getUrlVar('dep'))
	var role = decodeURIComponent($.getUrlVar('role'))
	if(username != undefined && realname != undefined){
		$("title")[0].innerHTML = "修改用户"
		$("#topic")[0].innerHTML = "<B>修改用户</B>"
		$("#id_add").val(username);
		$("#id_pass").val(password);
		$("#id_real").val(realname);

		$('#id_dep option').each(function(){
		    if( $(this)[0].innerHTML == dep){
		    	$('#id_dep').attr('value', $(this).val())
		     }
		});
		$('#id_role option').each(function(){
		    if( $(this)[0].innerHTML == role){
		    	$('#id_role').attr('value', $(this).val())
		     }
		});
	}	
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

$.extend({
	  getUrlVars: function(){
	    var vars = [], hash;
	    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	    for(var i = 0; i < hashes.length; i++)
	    {
	      hash = hashes[i].split('=');
	      vars.push(hash[0]);
	      vars[hash[0]] = hash[1];
	    }
	    return vars;
	  },
	  getUrlVar: function(name){
	    return $.getUrlVars()[name];
	  }
	});
