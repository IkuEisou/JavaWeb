/**
 * 
 */
$(function(){
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
	srhwtd()
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked) 
     });
});

function srhwtd(){
	var st = $("#st").val()
	var et = $("#et").val()
	var dh = $("#dh").val()
	var page = $.getUrlVar('page')
	var dw = $("#dw").find("option:selected").text()
	
	if(page==undefined){
		page = 1;
	}
	
	if(st > et){
		alert("起始时间应该早于完了时间！")
		return;
	}
	
	$.ajax({
		type: 'POST',
		url: 'WtdServlet',
		dataType: 'json',
		data: {
			flag:"srhwtd",
			st:st,
			et:et,
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
				$("#wtdtb").empty()
				$("#nav").empty()
				$('#wtdtb').after('<div id="nav"></div>')
				var rowsShown = 5
				var rowsTotal = res.pages
				var numPages = Math.ceil(rowsTotal/rowsShown)
				
				for(i = 0;i<numPages;i++) {
					var pageNum = i + 1
					$('#nav').append('<a href="wtd.html?page='+pageNum+'"'+' rel="'+i+'">'+pageNum+'</a> ')
				}
				
				for(var i=0; i < wtds.length; i++){
					var wtd = '<tr><td><input type="checkbox" name="subBox"  id=cb"'+i+'"></td>'+
					'<td>'+(i+1)+'</td>'+
					'<td>'+wtds[i]["dh"]+'</td>'+					
					'<td>'+wtds[i]["dw"]+'</td>'+
					'<td>'+wtds[i]["time"]+'</td>'+'</tr>'
					$("#wtdtb").append(wtd)
				}
			     var $subBox = $("input[name='subBox']")
			     $subBox.click(function(){
			         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false)
			     });
			}
		}
	});
}

function delwtd(){
	var checked = $("input[type='checkbox'][name='subBox']")
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var item = $(this).parent().next().next()[0].innerText
			if (item=="") {
				alert("单号不能为空！")
				return;
			}
			var del = $(this).parent().parent()
			$.ajax({
				type: 'POST',
				url :'WtdServlet',
				dataType:'json',
				data:{
					flag:"delete",
					dh:item,
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

function updwtd(){
	var checked = $("input[type='checkbox'][name='subBox']");
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var wtd= $(this).parent().nextAll();
			var dh = wtd[1].innerText;
			var dw = wtd[2].innerText;

			var url = 'editwtd.html?dh='+dh+'&dw='+dw
			window.open(url)
		}
	 });
	if(!ischecked){
		alert("请勾选要更新的项！");
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