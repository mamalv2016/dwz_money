<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ include file="/include.inc.jsp" %>
<form  enctype="multipart/form-data" method="post" 
	action="/upload/test2!saveFile.do">
	�ļ����⣺<input name="arg"/><br>
	�ϴ��ļ���<input type="file" name="upload"/><br> 
	�ϴ��ļ���<input type="file" name="upload"/><br> 
	�ϴ��ļ���<input type="file" name="upload"/><br> 
	<input name="dd" type="submit" value="�ύ"/>
</form>
<a href='/upload/down.do'>�����ļ�</a><br>
<a href='/upload/down2.do'>���������ļ�</a><br>
<a href='/upload/down3.do'>����word�ļ�</a><br>
<a href='/upload/down3.do?inputPath=/WEB-INF/download/sss.png&fileName=test.png'>���������ļ�</a><br>
<a href='/upload/down3.do?inputPath=/WEB-INF/download/sss.png&fileName=test.png'>����ѹ���ļ�</a><br>