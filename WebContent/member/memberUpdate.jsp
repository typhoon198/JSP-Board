<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="mem" value="${requestScope.mem}"/>

<html>
<head>
 <meta charset="UTF-8" />
 <meta name="viewport" content="width=device-width, initial-scale=1" />
<title>수정</title>
<link href="style.css" rel="stylesheet" type="text/css">
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
	function execDaumPostcode() {
		  new daum.Postcode({
		    oncomplete: function(data) {
		      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
		      // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
		      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
		      var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
		      var extraRoadAddr = ''; // 도로명 조합형 주소 변수

		      // 법정동명이 있을 경우 추가한다. (법정리는 제외)
		      // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
		      if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
		        extraRoadAddr += data.bname;
		      }
		      // 건물명이 있고, 공동주택일 경우 추가한다.
		      if(data.buildingName !== '' && data.apartment === 'Y'){
		        extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
		      }
		      // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
		      if(extraRoadAddr !== ''){
		        extraRoadAddr = ' (' + extraRoadAddr + ')';
		      }
		      // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
		      if(fullRoadAddr !== ''){
		        fullRoadAddr += extraRoadAddr;
		      }

		      // 우편번호와 주소 정보를 해당 필드에 넣는다.
		      document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
		      document.getElementById('roadAddress').value = fullRoadAddr;
		      //document.getElementById('jibunAddress').value = data.jibunAddress;
			  self.close();
		    }
		  }).open();
		}
	function inputCheck(){
		

		if(document.regFrm.pwd.value==""){
			alert("비밀번호를 입력해 주세요.");
			document.regFrm.pwd.focus();
			return;
		}
		if(document.regFrm.repwd.value==""){
			alert("비밀번호를 확인해 주세요");
			document.regFrm.repwd.focus();
			return;
		}
		if(document.regFrm.pwd.value != document.regFrm.repwd.value){
			alert("비밀번호가 일치하지 않습니다.");
			document.regFrm.repwd.value="";
			document.regFrm.repwd.focus();
			return;
		}
		if(document.regFrm.name.value==""){
			alert("이름을 입력해 주세요.");
			document.regFrm.name.focus();
			return;
		}
		if(document.regFrm.birthday.value==""){
			alert("생년월일을 입력해 주세요.");
			document.regFrm.birthday.focus();
			return;
		}
		if(document.regFrm.email.value==""){
			alert("이메일을 입력해 주세요.");
			document.regFrm.email.focus();
			return;
		}
	    var str=document.regFrm.email.value;	   
	    var atPos = str.indexOf('@');
	    var atLastPos = str.lastIndexOf('@');
	    var dotPos = str.indexOf('.'); 
	    var spacePos = str.indexOf(' ');
	    var commaPos = str.indexOf(',');
	    var eMailSize = str.length;
	    if (atPos > 1 && atPos == atLastPos && 
		   dotPos > 3 && spacePos == -1 && commaPos == -1 
		   && atPos + 1 < dotPos && dotPos + 1 < eMailSize);
	    else {
	          alert('E-mail주소 형식이 잘못되었습니다.\n\r다시 입력해 주세요!');
		      document.regFrm.email.focus();
			  return;
	    }
	    if(document.regFrm.zipcode.value==""){
			alert("우편번호를 검색해 주세요.");
			return;
		}
		if(document.regFrm.job.value=="0"){
			alert("직업을 선택해 주세요.");
			document.regFrm.job.focus();
			return;
		}
	}
	
</script>
</head>
<body bgcolor="#FFFFCC" onLoad="regFrm.id.focus()">
	<div align="center">
		<br /><br />
		<form name="regFrm" method="post" action="${contextPath }/modify" accept-charset="UTF-8">
			<table align="center" border="0" cellspacing="0" cellpadding="5">
				<tr>
					<td align="center" valign="middle" bgcolor="#FFFFCC">
						<table border="1" cellspacing="0" cellpadding="2" align="center" width="600">
							<tr align="center" bgcolor="#996600">
								<td colspan="3"><font color="#FFFFFF"><b>회원 수정</b></font></td>
							</tr>
							<tr>
								<td width="20%">아이디</td>
								<td width="80%"><input name="id" size="15"
									value="${mem.id }" readonly> </td>
							</tr>
							<tr>
								<td>패스워드</td>
								<td><input type="password" name="pwd" size="15" value="${mem.pwd }"></td>
							</tr>

							<tr>
								<td>이름</td>
								<td><input name="name" size="15" value="${mem.name }">
								</td>
							</tr>
							<tr>
								<td>성별</td>
								<td>
								<c:choose>
								<c:when test="${mem.gender==1 }">
								남 <input type="radio" name="gender" value="1" checked="checked"> 
								여 <input type="radio" name="gender" value="2">
								</c:when>
								<c:otherwise>
								남 <input type="radio" name="gender" value="1"> 
								여 <input type="radio" name="gender" value="2" checked="checked">
								</c:otherwise>
								</c:choose>								
								</td>
							</tr>
							<tr>
								<td>생년월일</td>
								<td><input name="birthday" size="6" value="${mem.birthday}">
									ex)881030</td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input name="email" size="30" value="${mem.email}">
								</td>
							</tr>
							<tr>
								<td>우편번호</td>
								<td><input id="zipcode" name="zipcode" size="5" value="${mem.zipcode}" readonly>
									<input type="button" value="우편번호찾기" onClick="execDaumPostcode()" >
								</td>
							</tr>
							<tr>
								<td>도로명주소</td>
								<td><input id="roadAddress" name="address1" size="45"  value="${mem.address1}" readonly>
								</td>
							</tr>													<tr>
								<td>나머지주소</td>
								<td><input id="namujiAddress" name="address2" size="30" value="${mem.address2}">
								</td>
							</tr>
							<tr>
								<td>취미</td>
								<td>인터넷<input type="checkbox" name="hobby" value="인터넷">
									여행<input type="checkbox" name="hobby" value="여행"> 게임<input
									type="checkbox" name="hobby" value="게임"> 영화<input
									type="checkbox" name="hobby" value="영화"> 운동<input
									type="checkbox" name="hobby" value="운동">
								</td>
							</tr>
							
							<tr>
								<td>직업</td>
								<td><select name=job>
										<option value="0" selected>선택하세요.
										<option value="회사원">회사원
										<option value="연구전문직">연구전문직
										<option value="교수학생">교수학생
										<option value="일반자영업">일반자영업
										<option value="공무원">공무원
										<option value="의료인">의료인
										<option value="법조인">법조인
										<option value="종교,언론,에술인">종교.언론/예술인
										<option value="농,축,수산,광업인">농/축/수산/광업인
										<option value="주부">주부
										<option value="무직">무직
										<option value="기타">기타
								</select>
								</td>
							<script>document.regFrm.job.value="${mem.job}";</script>

							</tr>
							<tr>
							<td colspan="3" align="center">
								<input type="submit" value="수정완료" onClick="inputCheck()"> &nbsp; &nbsp; 
								<input type="reset" value="다시쓰기">
							</td>	
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>
