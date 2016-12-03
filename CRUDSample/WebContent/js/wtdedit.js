/**
 * 
 */
$(function(){
	var dh = $.getUrlVar('dh')
	var dw = $.getUrlVar('dw')
	var page = $.getUrlVar('page')
	if(page==undefined){
		page = 1;
	}
	if(dh != undefined && dw != undefined){
		$("title")[0].innerHTML = "修改委托单"
		$("#topic")[0].innerHTML = "<B>修改委托单</B>"
		$("#dh").val(dh);
		$("#query").hide()
		$('#dw option').each(function(){
		    if( $(this)[0].innerHTML == dw){
		    	$('#dw').attr('value', $(this).val())
		     }
		});
		
		$.ajax({
			type: 'POST',
			url: 'WtdServlet',
			dataType: 'json',
			data: {
				flag:"srhwtd",
				st:"",
				et:"",
				dh:dh,
				dw:dw,
				page: page
			},
			error: function(xhr, err){
				alert('Error：' + err + '！')
			},
			success: function(res){
				var wtds = res.wtds
				if(res.pages == "0"){
					alert(wtds)
				}else{
					for(var i=0; i < wtds.length; i++){				
						wh = wtds[i]["wh"]
						srhaqf(wh,wtds[i]["fee"])
						
					}
				}
			}
		});
	}else{
		$.ajax({
			type: 'POST',
			url: 'DwServlet',
			dataType: 'json',
			data: {
				flag:"srhall"
			},
			error: function(xhr, err){
				alert('Error：' + err + '！')
			},
			success: function(res){
				var dws = res.dws
				for(var i=0; i < dws.length; i++){
					dw = '<option>'+dws[i]["name"]+'</option>'
					$("#dw").append(dw)
				}
			}
		});
		srhaqf("","")
	}
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked) 
     });
});

function srhaqf(wh,fe){
	var dw = $("#dw").find("option:selected").text()
	var page = $.getUrlVar('page')
	var usrs
	var jy
	var fee = '<input type="text" id="fee" value="'+fe+'">'
		
	if(page==undefined){
		page = 1;
	}
	$.ajax({
		type: 'POST',
		url: 'UserServlet',
		dataType: 'json',
		data: {
			flag:"srhall",
			role:"检验员"
		},
		error: function(xhr, err){
			alert('Error：' + err + '！')
		},
		success: function(res){
			usrs = res.usrs
			jy = '<select>'
			for(var i=0; i < usrs.length; i++){
				jy += '<option>'+usrs[i]["real"]+'</option>'
			}
			jy += '</select>'
		}
	});
	$.ajax({
		type: 'POST',
		url: 'AqfServlet',
		dataType: 'json',
		data: {
			flag:"srh",
			xh:"",
			wh:wh,
			name:"",
			dw:dw,
			zt:"",
			page: page
		},
		error: function(xhr, err){
			alert('Error：' + err + '！')
		},
		success: function(res){
			var aqfs = res.aqfs
			if(res.pages == "0"){
				alert(aqfs)
			}else{
				$("#aqftb").empty()
				$("#nav").empty()
				$('#aqftb').after('<div id="nav"></div>')
				var rowsShown = 5
				var rowsTotal = res.pages
				var numPages = Math.ceil(rowsTotal/rowsShown)
				
				for(i = 0;i<numPages;i++) {
					var pageNum = i + 1
					$('#nav').append('<a href="aqf.html?page='+pageNum+'"'+' rel="'+i+'">'+pageNum+'</a> ')
				}
				
				for(var i=0; i < aqfs.length; i++){
					var aqf = '<tr><td><input type="checkbox" name="subBox"  id=cb"'+i+'"></td>'+
					'<td>'+(i+1)+'</td>'+
					'<td>'+aqfs[i]["wh"]+'</td>'+
					'<td>'+aqfs[i]["xh"]+'</td>'+
					'<td>'+aqfs[i]["name"]+'</td>'+
					'<td>'+aqfs[i]["dw"]+'</td>'+
					'<td>'+aqfs[i]["tj"]+'</td>'+
					'<td>'+aqfs[i]["gzjz"]+'</td>'+
					'<td>'+aqfs[i]["gzyl"]+'</td>'+
					'<td>'+aqfs[i]["zdyl"]+'</td>'+
					'<td>'+aqfs[i]["fsdm"]+'</td>'+
					'<td>'+aqfs[i]["azwz"]+'</td>'+
					'<td>'+aqfs[i]["zt"]+'</td>'+
					'<td>'+jy+'</td>'+
					'<td>'+fee+'</td>'+
					'</tr>'
					$("#aqftb").append(aqf)
				}
			     var $subBox = $("input[name='subBox']")
			     $subBox.click(function(){
			         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false)
			     });
			}
		}
	});
}

function save(){
	var checked = $("input[type='checkbox'][name='subBox']")
	var dw = $("#dw").find("option:selected").text()
	var dh = $("#dh").val()
	var ischecked = false

	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var aqf= $(this).parent().nextAll()
			var wh = aqf[1].innerText
			var dw = aqf[4].innerText
			var zt = aqf[11].innerText
			var jy = aqf[12].children[0].selectedOptions[0].innerText
			var fee =$(aqf[13].children[0]).val()

			if(zt == "校验中"){
				alert("不能重复检验！")
				return
			}

			$.ajax({
				type: 'POST',
				url: 'WtdServlet',
				dataType: 'json',
				data: {
					flag:"add",
					wh:wh,
					dw:dw,
					jy:jy,
					dh:dh,
					fee:fee
				},
				error: function(xhr, err){
					alert('Error：' + err + '！')
				},
				success: function(data){
					alert(data.msg);	
				}
			});
		}
	 });
	if(!ischecked){
		alert("请勾选要保存的项！")
		return
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