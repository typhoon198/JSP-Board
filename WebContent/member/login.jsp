<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="idKey" value="${sessionScope.idKey}"/>
<html>
<head>
<title>로그인</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
</script>
</head>
<body bgcolor="#FFFFCC">
<br/><br/>
 <div align="center">
<c:choose>
<c:when test="${empty idKey }">
<form name="loginFrm" method="post" action="${contextPath }/login">
	<table>
		<tr>
			<td align="center" colspan="2"><h4>로그인</h4></td>
		</tr>
		<tr>
			<td>아 이 디</td>
			<td><input name="id" value="" requried></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="pwd" value="" requried></td>
		</tr>
		<tr>
			<td colspan="2">
				<div align="right">
					<input type="submit" value="로그인">
				</div>
			</td>
		</tr>
	</table>
</form>

</c:when>
<c:otherwise>
		<b>${idKey }</b>님 환영 합니다.
		<p>제한된 기능을 사용 할 수가 있습니다.</p>
		<a href="${contextPath }/logout">로그아웃</a>
</c:otherwise>
</c:choose>
		
	</div>
</body>
</html>