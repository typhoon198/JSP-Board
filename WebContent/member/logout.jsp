<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<script>
   alert('로그아웃 되었습니다.');
   top.document.location.reload(); 
   location.href="${contextPath}/left.jsp";
</script>