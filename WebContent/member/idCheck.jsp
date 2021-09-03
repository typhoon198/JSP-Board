<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>ID 중복체크</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function pass() {
	opener.document.regFrm.id.readonly=true;//사용가능한 아이디 수정불가
	opener.document.getElementById("checkBtn").disabled=true;//중복검사완료하면 중복확인 버튼 비활성화
	opener.document.getElementById("checkBtn").value="중복확인완료";
	self.close();
	}
	window.addEventListener('load', function () {
		document.getElementById("cid").innerText = opener.document.regFrm.id.value;
		//부모창에서 넘겨온값 받기
	});
	
</script>
</head>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br /> 
		<p><span id="cid"></span>
		<c:choose>
			<c:when test="${requestScope.status==1 }">
			는 이미 존재한는 ID입니다.</p>
			<input type="button" value="닫기" onClick="self.close()">
			</c:when>
			<c:when test="${requestScope.status==0 }">
			는  사용 가능 합니다.</p>
			<input type="button" value="닫기" onClick="pass()">
			</c:when>
			<c:otherwise>
			<p>DB연결 실패</p>
			<input type="button" value="닫기" onClick="self.close()">
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>