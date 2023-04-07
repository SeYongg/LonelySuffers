<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="css/adminTable.css"></link>
<body>
	<jsp:include page="/WEB-INF/views/admin/adminMenu.jsp" />
    <div class="currProduct-wrapper product-wrapper admin-content">
        <form action="/adminSearchMember.do" method="get"  class="search-bar" name="search-member">
            <span class="material-symbols-outlined search-icon">search</span>
            <input type="search" placeholder="상품명으로 검색" name="searchMember">
        </form>
        <div>
            <div class="product-choice">
                <div>강습</div>
                <div>숙박</div>
            </div>
            <div class="list-detail-box" style="display:none;">
                <div><a href="#">상품 정보 수정</a>
                <a href="#">상품 판매 중지</a></div>
            </div>
            <div class="lesson-list list-wrapper">
                <form action="/changeGrade.do" method="get" name="memberList">
                    <div class="newProduct-top list-top">
                        <div class="count">전체 상품 <span>n</span></div>
                        <table>
                            <tr>
                                <th><input type="checkbox" name="memberCheck"></th>
                                <th>상품명</th>
                                <th>판매자</th>
                                <th>강습 레벨</th>
                                <th>가격</th>
                                <th>모집정원</th>
                                <th>지역</th>
                                <th>평점</th>
                                <th>상품 상태</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="memberCheck"></td>
                                <td><a href="#">개쉬운 서핑 너도 하자</a></td>
                                <td>hong123</td>
                                <td>Level1</td>
                                <td><span>120,000</span>원</td>
                                <td><span>40</span>명</td>
                                <td>강원도</td>
                                <td></td>
                                <td>판매중</td>
                                <td><span class="material-symbols-outlined list-detail">more_vert</span></td>
                            </tr>
                        </table>
                        <div></div>
                    </div>
                    <div class="list-bottom">
                        <div>
                            <input type="submit" value="선택 상품 승인" class="btn bc1">
                        </div>
                    </div>
                </form>
            </div>
            <div class="house-list list-wrapper" style="display:none;">
                <form action="/changeGrade.do" method="get" name="memberList">
                    <div class="newProduct-top list-top">
                        <div class="count">전체 상품 <span>n</span></div>
                        <table>
                            <tr>
                                <th><input type="checkbox" name="memberCheck"></th>
                                <th>상품명</th>
                                <th>판매자</th>
                                <th>숙박소 이름</th>
                                <th>가격</th>
                                <th>객실 인원</th>
                                <th>지역</th>
                                <th>평점</th>
                                <th>상품 상태</th>
                                <th></th>
                            </tr>
                            <tr>
                                <td><input type="checkbox" name="memberCheck"></td>
                                <td><a href="#">가격 실화? 멋지다 짜잔호텔</a></td>
                                <td>hong123</td>
                                <td>짜잔호텔</td>
                                <td><span>200,000</span>원</td>
                                <td><span>6</span>인실</td>
                                <td>강원도</td>
                                <td></td>
                                <td>판매중</td>
                                <td><span class="material-symbols-outlined list-detail">more_vert</span></td>
                            </tr>
                        </table>
                        <div></div>
                    </div>
                    <div class="list-bottom">
                        <div>
                            <input type="submit" value="선택 상품 승인" class="btn bc1">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="js/admin.js"></script>
</body>
<script>
    /*메뉴 제목*/
    $(document).ready(function(){
        $(".top-menu-title").text("등록된 상품 관리");
        $(".product-choice>div").first().click();
    });

</script>
</html>