<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.adminChatContent>ul>li>div{
	overflow: hidden;
	}
	.adminChatContent>ul>li>div>div{
		float: left;
	}
	
	.chatting{
    display:none;
	}
	
	.active_chat_content{
		display: block;
	}
	.messageArea{
	    overflow-y: auto;
	    border : 1px solid black;
	    height: 550px;
	    width:500px;
	    display: flex;
	    flex-direction: column;
	    background-color: #b2c7d9; 
	}
	
	.chat{
	    margin-bottom: 10px;
	    padding: 8px;
	    border-radius: 3px;
	}
	
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/admin/adminMenu.jsp" />
	<div class="admin-content">
		<div class="adminChatContent">
			<c:forEach items="${list }" var="ca" varStatus="status">
				<ul>
					<li><div class="active_chat_title"><input type="hidden" id="memberId" value="${ca.memberId }"><div>${status.count }</div><div>${ca.memberId }</div><div>
						<c:choose>
							<c:when test="${ca.chatActivation == 1}">
								채팅중
							</c:when>
							<c:otherwise>
								채팅종료
							</c:otherwise>
						</c:choose>
					</div></div></li>
					<li>
						<div class="chatting">
							<div class="messageArea">
							</div>
							<div class="sendBox">
							<input type="text" id="sendMsg">
							<button id="sendBtn" onClick="sendMsg();">전송</button> 
							</div>
						</div>
					</li>
				</ul>
			</c:forEach>
		</div>
	</div>
	
	<script src="js/adminChat.js"></script>
</body>
</html>