/**
 * 
 */
$(function(){
	srhusr()
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked) 
     });
});

function srhusr(){
	var username = $("#LoginName").val()
	var realname = $("#RealName").val()
	var dep = $("#dep").find("option:selected").text()
	var role = $("#role").find("option:selected").text()
	var page = $.getUrlVar('page')
	
	if(page==undefined){
		page = 1;
	}
	
	$.ajax({
		type: 'POST',
		url: 'UserServlet',
		dataType: 'json',
		data: {
			flag:"srh",
			username:username,
			realname:realname,
			dep:dep,
			role:role,
			page: page
		},
		error: function(xhr, err){
			alert('Error：' + err + '！')
		},
		success: function(res){
			var usrs = res.usrs
			if(res.pages == "0"){
				alert(usrs)
			}else{
				$("#usrtb").empty()
				$("#nav").empty()
				$('#usrtb').after('<div id="nav"></div>')
				var rowsShown = 5
				var rowsTotal = res.pages
				var numPages = Math.ceil(rowsTotal/rowsShown)
				
				for(i = 0;i<numPages;i++) {
					var pageNum = i + 1
					$('#nav').append('<a href="user.html?page='+pageNum+'"'+' rel="'+i+'">'+pageNum+'</a> ')
				}
				
				for(var i=0; i < usrs.length; i++){
					var usr = '<tr><td><input type="checkbox" name="subBox"  id=cb"'+i+'"></td>'+
					'<td>'+usrs[i]["username"]+'</td>'+
					'<td>'+usrs[i]["password"]+'</td>'+
					'<td>'+usrs[i]["real"]+'</td>'+
					'<td>'+usrs[i]["dep"]+'</td>'+
					'<td>'+usrs[i]["role"]+'</td>'+'</tr>'
					$("#usrtb").append(usr)
				}
			     var $subBox = $("input[name='subBox']")
			     $subBox.click(function(){
			         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false)
			     });
			}
		}
	});
}

function deluser(){
	var checked = $("input[type='checkbox'][name='subBox']")
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var username = $(this).parent().next()[0].innerText
			if (username=="") {
				alert("登录名不能为空！")
				return;
			}
			var del = $(this).parent().parent()
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
					del.remove()
				}
			});
		}
	 });
	if(!ischecked){
		alert("请勾选要删除的项！");
		return;
	}
}

function upduser(){
	var checked = $("input[type='checkbox'][name='subBox']");
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var usr= $(this).parent().nextAll();
			var username = usr[0].innerText;
			var password = usr[1].innerText;
			var realname = usr[2].innerText;
			var dep = usr[3].innerText;
			var role = usr[4].innerText;
			if (username=="") {
				alert("登录名不能为空！");
				return;
			}
			var url = 'manage.html?username='+username+'&password='+password+
			'&realname='+encodeURIComponent(realname)+'&dep='+encodeURIComponent(dep)+
			'&role='+encodeURIComponent(role)
			window.open(url)
		}
	 });
	if(!ischecked){
		alert("请勾选要修改的项！");
		return;
	}
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