/**
 * 
 */
$(function(){
	srhaqf()
	 $("#checkAll").click(function() {
         $('input[name="subBox"]').attr("checked",this.checked) 
     });
     var $subBox = $("input[name='subBox']")
     $subBox.click(function(){
         $("#checkAll").attr("checked",$subBox.length == $("input[name='subBox']:checked").length ? true : false)
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