<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="status" value="${requestScope.status}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:choose>
<c:when test="${status==1}">
	<script>
	alert("회원가입을 하였습니다.");
	location.href="${contextPath}/main.jsp"	
	</script>
</c:when>
<c:otherwise>
	<script>
	alert("회원가입 실패");
	location.href="${contextPath}/member/member.jsp"
	</script>
</c:otherwise>
</c:choose>
