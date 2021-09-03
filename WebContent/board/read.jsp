<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="board" value="${requestScope.board}" />
<c:set var="nowPage" value="${requestScope.nowPage}" />
<c:set var="search" value="${requestScope.search}" />
<html>
<head>
<title>게시판 읽기</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function list(){
	    document.listFrm.submit();
	 } 	
	function down(filename){
		 document.downFrm.filename.value=filename;
		 document.downFrm.submit();
	}
</script>
</head>
<body bgcolor="#FFFFCC">
<br/><br/>
<table align="center" width="600" cellspacing="3">
 <tr>
  <td bgcolor="#9CA2EE" height="25" align="center">글읽기</td>
 </tr>
 <tr>
  <td colspan="2">
   <table cellpadding="3" cellspacing="0" width="100%"> 
    <tr> 
  <td align="center" bgcolor="#DDDDDD" width="10%"> 이 름 </td>
  <td bgcolor="#FFFFE8">${board.name}</td>
  <td align="center" bgcolor="#DDDDDD" width="10%"> 등록날짜 </td>
  <td bgcolor="#FFFFE8">${board.regdate}</td>
 </tr>
 <tr> 
    <td align="center" bgcolor="#DDDDDD"> 제 목</td>
    <td bgcolor="#FFFFE8" colspan="3">${board.subject}</td>
   </tr>
   <tr> 
     <td align="center" bgcolor="#DDDDDD">첨부파일</td>
     <td bgcolor="#FFFFE8" colspan="3">
     <c:if test="${!empty board.filename }">
     <a href="javascript:down('${board.filename }')">${board.filename }</a>
  		 &nbsp;&nbsp;<font color="blue">(${board.filesize }KBytes)</font>  
  	</c:if>
  		  등록된 파일이 없습니다.
     </td>
   </tr>
   <tr> 
    <td colspan="4"><br/><pre>${board.content}</pre><br/></td>
   </tr>
   <tr>
    <td colspan="4" align="right">
     ${board.ip}로 부터 글을 남기셨습니다./  조회수  ${board.count}
    </td>
   </tr>
   </table>
  </td>
 </tr>
 <tr>
  <td align="center" colspan="2"> 
 <hr/>
 
 [ <a href="javascript:list()" >리스트</a> | 
 <a href="${contextPath}/updateboard?nowPage=${nowPage}&num=${board.num}" >수 정</a> |
 <a href="${contextPath}/parent?nowPage=${nowPage}&num=${board.num}" >답 변</a> |
 <a href="${contextPath}/deleteboard?nowPage=${nowPage}&num=${board.num}">삭 제</a> ]<br/>

  </td>
 </tr>
</table>

<form name="downFrm" action="download.jsp" method="post">
	<input type="hidden" name="filename">
</form>
<form name="listFrm" method="post" action="${contextPath}/list">
	<input type="hidden" name="nowPage" value="${nowPage }">
<c:if test="${empty search.keyWord}">
	<input type="hidden" name="keyField" value="${search.keyField }">
	<input type="hidden" name="keyWord" value="${search.keyWord }">
</c:if>
</form>
</body>
</html>