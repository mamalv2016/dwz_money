<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">  
  </head>
  
  <body>
     <a href='gridTree/MyJsp.jsp'>ǰ̨�����</a><br>
     <a href='gridTree/MyJspForJava.jsp'>��̨�����</a><br>
     <a href='gridTree/MyLazyTree.jsp'>��������ʾ��1</a><br>
     <a href='javascript:alert("��ʱ������!")'>��������ʾ��2(lazyPage:true)</a><br>
     <a href='javascript:alert("��ʱ������!")'>��������ʾ��3(lazyMorePage:true)</a><br> 
	 <a href='http://blog.sina.com.cn/s/blog_3efe6ef10100unl6.html'>bug����</a><br>
	 <a href='http://lishuiqing1987.j38.80data.com/demo/demo1.rar'>1.0�汾demo����</a><br>
	 <a href='http://lishuiqing1987.j38.80data.com/demo/demo2.rar'>2.0�汾demo����</a><br>
	 <a href='http://code.google.com/p/gridtree-jquery-plugin-demo/'>google code</a><br>
  </body>
</html>
