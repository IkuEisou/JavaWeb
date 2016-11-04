<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/user.js"></script>
<title>用户管理</title>
</head>
<body onload="onload()">
<table border="1" >
	<tr bgcolor="#99ccff">
	<td colspan="2"><B>创建/更新用户</B></td>
	</tr>
	<tr>
		<td >用户名:  <input type="text" placeholder="10文字/英数字+記号" id="id_add" name="id_add" size="30"></td>
		<td >密码:  <input type="password" placeholder="10文字/英数字+記号"  id="id_pass" name="id_pass" size="30"></td>
	</tr>
	<tr >
	    <td colspan="2" align="right" ><input type="submit" value="OK" onclick="regist()"></td>
	</tr>
	<tr bgcolor="#99ccff">
		<td colspan="2"><B>删除用户</B></td>
	</tr>	
	<tr >
		<td colspan="2" >用户名:  <input id="id_de" name="id_de" type="text" placeholder="10文字/英数字+記号" size="30"></td>
	</tr>
	<tr >
		<td colspan="2" align="right" ><input type="submit" value="OK" onclick="deluser()"></td>
	</tr>
</table>
</body>
</html>