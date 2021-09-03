<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="pb" value="${requestScope.pb}" />
<c:set var="search" value="${requestScope.search}" />


<html>
<head>
<title>Servlet,JSP 게시판</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function list() {
		document.listFrm.action = "${contextPath}/list";
		document.listFrm.submit();
	}

	function pageing(page) {
		document.readFrm.nowPage.value = page;
		document.readFrm.submit();
	}
	function read(num) {
		document.readFrm.num.value = num;
		document.readFrm.action = "${contextPath}/read";
		document.readFrm.submit();
	}

	function check() {
		if (document.searchFrm.keyWord.value == "") {
			alert("검색어를 입력하세요.");
			document.searchFrm.keyWord.focus();
			return;
		}
		document.searchFrm.submit();
	}
</script>

</head>
<body bgcolor="#FFFFCC">

	<div align="center">
		<br />
		<h2>JSPBoard</h2>
		<br />
		<table align="center" width="600">
			<tr>
				<td>Total : ${pb.totalCnt} Articles(<font color="red">
						${pb.nowPage}/${pb.totalPage}Pages</font>)
				</td>
			</tr>
		</table>
		<table align="center" width="600" cellpadding="3">
			<tr>
				<td align="center" colspan="2"><c:choose>
						<c:when test="${pb.list==null}">
						등록된 게시물이 없습니다.
						</c:when>
						<c:otherwise>
							<table width="100%" cellpadding="2" cellspacing="0">
								<tr align="center" bgcolor="#D0D0D0" height="120%">
									<td>번 호</td>
									<td>제 목</td>
									<td>이 름</td>
									<td>날 짜</td>
									<td>조회수</td>
								</tr>
								<c:forEach var="list" items="${pb.list }" varStatus="articleNum">

									<tr>
										<td align="center">${articleNum.count }</td>
										<td><c:if test="${list.depth>0 }">
												<c:forEach var="i" begin="0" end="${list.depth }" step="1">
									&nbsp;&nbsp;
								</c:forEach>
											</c:if> <a href="javascript:read(${list.num })">${list.subject }</a></td>
										<td align="center">${list.name }</td>
										<td align="center">${list.regdate }</td>
										<td align="center">${list.count }</td>
									</tr>
								</c:forEach>
							</table>

						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<td colspan="2"><br /> <br /></td>
			</tr>
			<tr>
				<td>
					<!-- 페이징 및 블럭 처리 Start--> 
					
					<c:if test="${pb.totalPage!=0 }">

						<c:if test="${pb.startPage!=1 }">
							<a href="javascript:pageing(${pb.prev })">prev...</a>
						</c:if>&nbsp;

			<c:forEach var="k" begin="${pb.startPage }" end="${pb.endPage}" step="1">
							<a href="javascript:pageing(${k })"> <c:if
									test="${k==pb.nowPage}">
									<font color="blue">
								</c:if> [${k }] <c:if test="${k==pb.nowPage}">
									</font>
								</c:if>
							</a>
						</c:forEach> &nbsp; <c:if test="${pb.endPage!=pb.totalPage}">
							<a href="javascript:pageing(${pb.next })">.....next</a>
						</c:if>
					</c:if> <!-- 페이징 및 블럭 처리 End-->
				</td>
				<td align="right"><a href="board/write.jsp">[글쓰기]</a> 
				<a href="./list">[처음으로]</a></td>
			</tr>
		</table>
		<hr width="600" />
		<form name="searchFrm" method="post" action="${contextPath}/list">
			<table width="600" cellpadding="4" cellspacing="0">
				<tr>
					<td align="center" valign="bottom">
					<select name="keyField"
						size="1">
							<option value="name">이 름</option>
							<option value="subject">제 목</option>
							<option value="content">내 용</option>
					</select> 
					<input size="16" name="keyWord">
					<input type="button" value="찾기" onClick="javascript:check()">
					<input type="hidden" name="nowPage" value="1"></td>
				</tr>
			</table>
		</form>
		<form name="listFrm" method="post">
			<input type="hidden" name="reload" value="true">
			<input type="hidden" name="nowPage" value="1">
		</form>
		<form name="readFrm" method="get">
			<input type="hidden" name="num"> 
			<input type="hidden" name="nowPage" value="${pb.nowPage }"> 
				<input type="hidden" name="keyField" value="${search.keyField }"> 
				<input type="hidden" name="keyWord" value="${search.keyWord }">
		</form>
	</div>
	
	${pb.nowPage } ${search.keyField }   ${search.keyWord }
</body>
</html>