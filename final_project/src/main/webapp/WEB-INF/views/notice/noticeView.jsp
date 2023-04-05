<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<head>

<!-- Basic -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<!-- Mobile Meta -->
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<!-- Site Meta -->
<title>Lonely Suffers</title>
<meta name="keywords" content="서핑,파도타기">
<meta name="description" content="파도타기를 좋아하는 사람들을 위한 웹사이트">
<meta name="author" content="KH정보교육원">
<link rel="stylesheet" href="/css/notice.css">
<script src="https://code.jquery.com/jquery-3.6.1.js"></script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<section class="section">
			<div class="container">
				<div class="row">
					<h1 style="text-align:center; font-size:50px">공지사항</h1>
					<hr>
					<table>
						<tr>
							<td style="text-align:center; font-size:40px">${n.noticeTitle }</td>
						</tr>
						<tr>
							<td>${n.noticeDate }</td>
							<span>|</span>
							<td>${n.noticeContent }</td>
						</tr>
						<tr>
							<th colspan="6"><a
								href="/noticeUpdateFrm.do?noticeNo=${n.noticeNo }">수정하기</a> <!--  <a href="/deleteNotice.do?noticeNo=${n.noticeNo }">삭제</a>-->
							</th>
						</tr>
					</table>
				</div>
			</div>
		</section>

		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<!-- 기본 .js 파일들 -->
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script src="js/parallax.js"></script>
	<script src="js/animate.js"></script>
	<script src="js/custom.js"></script>
	<!-- 추가 .js파일들이 필요하면 아래에 넣으세요 -->

</body>
</html>
