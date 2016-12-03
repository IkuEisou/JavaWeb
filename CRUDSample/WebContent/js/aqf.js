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
	srhaqf()
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked) 
     });
});

function srhaqf(){
	var xh = $("#xh").val()
	var wh = $("#wh").val()
	var name = $("#name").val()
	var dw = $("#dw").val()
	var page = $.getUrlVar('page')
	var zt = $("#zt").find("option:selected").text()
	
	if(page==undefined){
		page = 1;
	}
	
	$.ajax({
		type: 'POST',
		url: 'AqfServlet',
		dataType: 'json',
		data: {
			flag:"srh",
			xh:xh,
			wh:wh,
			name:name,
			dw:dw,
			zt:zt,
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
					'<td>'+aqfs[i]["zt"]+'</td>'+'</tr>'
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

function delaqf(){
	var checked = $("input[type='checkbox'][name='subBox']")
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var item = $(this).parent().next().next()[0].innerText
			if (item=="") {
				alert("位号不能为空！")
				return;
			}
			var del = $(this).parent().parent()
			$.ajax({
				type: 'POST',
				url :'AqfServlet',
				dataType:'json',
				data:{
					flag:"delete",
					wh:item,
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

function updaqf(){
	var checked = $("input[type='checkbox'][name='subBox']");
	var ischecked = false;
	$(checked).each(function(){
		if($(this).attr("checked")==true) 
		{
			ischecked = true
			var aqf= $(this).parent().nextAll();
			var wh = aqf[1].innerText;
			var xh = aqf[2].innerText;
			var name = aqf[3].innerText;
			var dw = aqf[4].innerText;
			var gctj = aqf[5].innerText;
			var gzjz = aqf[6].innerText;
			var gzyl = aqf[7].innerText;
			var zdyl = aqf[8].innerText;
			var fsdm = aqf[9].innerText;
			var azwz = aqf[10].innerText;
			var zt = aqf[11].innerText;

			var url = 'aqfedit.html?wh='+wh+'&xh='+xh+
			'&name='+encodeURIComponent(name)+'&dw='+encodeURIComponent(dw)+
			'&zt='+encodeURIComponent(zt)+'&gzjz='+encodeURIComponent(gzjz)+
			'&gctj='+gctj+'&gzyl='+gzyl+'&zdyl='+zdyl+'&fsdm='+fsdm+
			'&azwz='+encodeURIComponent(azwz)
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