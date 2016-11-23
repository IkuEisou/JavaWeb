function save(){
	var xh = $("#xh").val()
	var wh = $("#wh").val()
	var name = $("#name").val()
	var dw = $("#dw").val()
	var gctj = $("#gctj").val()
	var dw = $("#dw").val()
	var gzjz = $("#gzjz").val()
	var gzyl = $("#gzyl").val()
	var zdyl = $("#zdyl").val()
	var fsdm = $("#fsdm").val()
	var azwz = $("#azwz").val()
	var zt = $("#zt").find("option:selected").text()

	$.ajax({
		type: 'POST',
		url: 'AqfServlet',
		dataType: 'json',
		data: {
			flag:"add",
			xh:xh,
			wh:wh,
			name:name,
			dw:dw,
			gctj:gctj,
			dw:dw,
			gzjz:gzjz,
			gzyl:gzyl,
			zdyl:zdyl,
			fsdm:fsdm,
			azwz:azwz,
			zt:zt
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
	var xh = $.getUrlVar('xh')
	var wh = $.getUrlVar('wh')
	var name = decodeURIComponent($.getUrlVar('name'))
	var dw = decodeURIComponent($.getUrlVar('dw'))
	var gctj = $.getUrlVar('gctj')
	var gzjz = decodeURIComponent($.getUrlVar('gzjz'))
	var gzyl = $.getUrlVar('gzyl')
	var zdyl = $.getUrlVar('zdyl')
	var fsdm = decodeURIComponent($.getUrlVar('fsdm'))
	var azwz = decodeURIComponent($.getUrlVar('azwz'))
	var zt = decodeURIComponent($.getUrlVar('zt'))
	
	if(xh != undefined && wh != undefined){
		$("title")[0].innerHTML = "安全阀修改"
		$("#topic")[0].innerHTML = "<B>安全阀修改</B>"
		$("#xh").val(xh);
		$("#wh").val(wh);
		$("#name").val(name);
		$("#gctj").val(gctj);
		$("#dw").val(dw);
		$("#gzjz").val(gzjz);
		$("#gzyl").val(gzyl);
		$("#zdyl").val(zdyl);
		$("#fsdm").val(fsdm);
		$("#azwz").val(azwz);

		$('#zt option').each(function(){
		    if( $(this)[0].innerHTML == zt){
		    	$('#zt').attr('value', $(this).val())
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
