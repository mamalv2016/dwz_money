<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ include file="/include.inc.jsp" %>
<%@ page import="java.io.*" %>
<html>
<title>�ϴ��ɹ�</title>
���⣺<s:property value="arg"/><br>
<%
String[] uploadFileName = (String[])request.getAttribute("uploadFileName");
for(int i=0;i<uploadFileName.length;i++) %>
	�ļ�Ϊ��<img src="/upload/<%=uploadFileName[i] %>"/>
</html>