<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="status" value="${requestScope.status}"/>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:choose>
<c:when test="${status==0}">
<c:set var="msg" value="아이디가 존재하지 않습니다."/>
</c:when>
<c:when test="${status==-1}">
<c:set var="msg" value="비밀번호가 다릅니다."/>
 </c:when>
<c:otherwise>
<c:set var="msg" value="로그인 성공"/>
</c:otherwise>
</c:choose>
<script>
alert("${msg}");
top.document.location.reload(); //프레임 전체 새로고침
location.href="${contextPath}/login.jsp";
</script>

