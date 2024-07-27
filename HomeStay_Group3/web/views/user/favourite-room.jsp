<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list-booking.css">
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div style="display: flex; justify-content: center; align-content: center; margin-top: 30px">
            <c:if test="${ROOMS.size() == 0} ">
                <h1>Bạn chưa có yêu cầu booking nào</h1>
            </c:if>
            <h4>Danh sách các nhà trọ yêu thích</h4>
        </div>
        <div style="width: 100%; text-align: center">
            <p>Nhấn vào tên phòng để xem chi tiết của phòng trọ mà bạn đã yêu thích</p>
        </div>
        <div class="secContent grid">


            <c:forEach items="${ROOMS}" var="room">
                <div 
                    data-aos="fade-up"
                    class="singleDestination aos-init aos-animate"
                    style="min-height: 28rem">
                    <div class="imageDiv">
                        <img
                            src="data:image/png;base64,${room.image}"
                            alt="user"
                            />
                    </div>
                    <div class="cardInfo">
                        <div class="cardBody-title">
                            <div>
                                <h1 class="destTitle">
                                    <a href="room?action=room-detail&roomId=${room.roomId}">${room.name}</a></h1>
                                <p >
                                    <b>Diện tích: </b> ${room.area} m2
                                </p>
                                <p >
                                    <b>Giá: </b><fmt:formatNumber value="${room.price}" type="number" pattern="#,###"/>₫
                                </p>
                                <p >
                                    <b>Cho phép ở: </b> ${room.maxParticipants} người
                                </p>
                            </div>
                        </div>
                        <a class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#removeFavourtie-${room.roomId}" style="margin-top: 50px;">Xóa khỏi danh sách yêu thích</a>
                        <div class="modal fade" id="removeFavourtie-${room.roomId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Xóa phòng này khỏi danh sách yêu thích</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        Bạn có chắc xóa phòng này khỏi danh sách yêu thích của mình hay không ?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <a  href="room?action=remove-from-favourite&roomId=${room.roomId}" type="button" class="btn btn-success">Xác nhận</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>

        </div>

        <div class="pagination" style="justify-content: center;">
            <c:choose>
                <c:when test ="${selectedPage - 1 < 1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">«</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="room?action=view-favourite-room&index=${selectedPage-1}">«</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                <a class="page-link ${i == selectedPage ? "active" : "" }" href="room?action=view-favourite-room&index=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">»</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="room?action=view-favourite-room&index=${selectedPage+1}">»</a></li>
                    </c:otherwise>
                </c:choose>
        </div>


    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>

    <jsp:include page="footer.jsp" />
</body>

</html>
