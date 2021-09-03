<%@ page contentType="text/html; charset=UTF-8" %>
<%
      String strTitle = "JSP Home";
	  String cPath = request.getContextPath();
%>
<html>
<head>

    <link rel="stylesheet" href="./css/styles.css" />

<title><%=strTitle%></title>
</head>
<frameset frameborder="0" framespacing="0" border="0" rows="130,*" >
	<frame  frameborder="0" scrolling="NO" noresize name="head" src="<%=cPath%>/head.jsp">
	<frameset name="body" frameborder="0" framespacing="0" border="0" rows="*,20"> <!-- 240,* -->		
        <frameset name="main" frameborder="0" framespacing="0" border="0" cols="240,*"> <!-- *,0,37,12 -->
    			<frame name="left" marginwidth="0" marginheight="0" frameborder="0" scrolling="NO" resize="NO" src="<%=cPath%>/left.jsp">
    			<frame name="content" src="<%=cPath%>/main.jsp" scrolling="YES" marginwidth="0" marginheight="0" frameborder="0" noresize>
        </frameset>
		<frame name="copy" src="<%=cPath%>/copy.jsp" scrolling="NO" marginwidth="0" marginheight="0" frameborder="0" noresize>        
	</frameset>
</frameset>
</html>