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
				var usr = '<tr><td><input type="checkbox"></td>'+
				'<td>'+usrs[i]["username"]+'</td>'+
				'<td>'+usrs[i]["password"]+'</td>'+
				'<td>'+usrs[i]["real"]+'</td>'+
				'<td>'+usrs[i]["dep"]+'</td>'+
				'<td>'+usrs[i]["role"]+'</td>'+'</tr>'
				$("#usrtb").append(usr)
			}
		}
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
				var usr = '<tr><td><input type="checkbox"></td>'+
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