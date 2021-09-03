<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="status" value="${requestScope.status}"/>
<c:set var="nowPage" value="${requestScope.nowPage}"/>
<c:set var="num" value="${requestScope.num}"/>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:choose>
<c:when test="${status==1}">
<script>
	alert("글삭제 완료");
	location.href="${contextPath}/list?nowPage=${nowPage }";
</script>
</c:when>

<c:when test="${status==0}">
<script>
	alert("비밀번호 불일치");
	location.href="${contextPath}/read?nowPage=${nowPage }&num=${num }";
</script>
</c:when>
<c:otherwise>
<script>
	alert("글삭제 실패");
	location.href="${contextPath}/read?nowPage=${nowPage }&num=${num }";
</script>
</c:otherwise>
</c:choose>
