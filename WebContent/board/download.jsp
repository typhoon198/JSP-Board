<%@page contentType="application; charset=EUC-KR"%>
<%@page import="com.ti.control.Download"%>
<jsp:useBean id="bMgr" class="com.ti.control.Download" />
<%
	  bMgr.downLoad(request, response, out, pageContext);
%>