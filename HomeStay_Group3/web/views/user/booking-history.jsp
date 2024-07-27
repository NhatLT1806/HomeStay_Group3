<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div style="display: flex; justify-content: center; align-content: center">
            <div style="margin-right: 15px">
                Tìm kiếm: 
            </div>
            <form action="book" style="display: flex">
                <input type="hidden" value="view-booking-history" name="action"/>
                <select name="status" class="form-select" aria-label="Default select example">
                    <option  value="" selected>Tất cả</option>
                    <option   ${status == 0 ? 'selected' : ''} value="0">Đang gửi yêu cầu</option>
                    <option   ${status == 1 ? 'selected' : ''} value="1">Thuê thành công</option>
                    <option   ${status == 2 ? 'selected' : ''}  value="2">Bị từ chối</option>
                    <option   ${status == 3 ? 'selected' : ''}  value="3">Đã hủy</option>
                </select>
                <button style="margin-left: 15px" type="submit" class="btn btn-success">Filter</button>
            </form>
        </div>
        <div class="secContent grid">

            <c:if test="${BOOKINGS == null} ">
                <h1>Bạn chưa có yêu cầu booking nào</h1>
            </c:if>



            <c:forEach items="${BOOKINGS}" var="book">
                <div
                    data-aos="fade-up"
                    class="singleDestination aos-init aos-animate"
                    style="min-height: 28rem"
                    >
                    <div class="imageDiv">
                        <img
                            src="data:image/png;base64,${book.roomImage}"
                            alt="user"
                            />
                    </div>
                    <div class="cardInfo">
                        <div class="cardBody-title">
                            <div>
                                <h1 class="destTitle">
                                    <a href="room?action=room-detail&roomId=${book.roomId}">
                                    ${book.roomName}</h1>
                                </a>
                                <span class="continent flex" style="margin-top: 10px"
                                      ><span class="name">Date: ${book.createAt}</span></span
                                >
                            </div>
                        </div>
                        <div class="desc" style="height: 20px"><p>Owner ${book.bookedByPhone}</p></div>        
                        <div class="desc" style="height: 20px"><p>Email: ${book.bookedByEmail}</p></div>
                        <div class="desc" style="height: 20px"><p>Phone Number: ${book.bookedByPhone}</p></div>

                        <c:if test="${book.status == 1}">
                            <button class="btn btn-success">Thuê thành công</button>
                            <a  href="contract?roomId=${book.roomId}">Xem hợp đồng</a>
                        </c:if>

                        <c:if test="${book.status == 0}">
                            <button class="btn btn-warning"> Đang gửi yêu cầu</button>
                            <a class="btn btn-danger" 
                               data-bs-toggle="modal" data-bs-target="#info-${book.requestId}" aria-hidden="true"
                               >Hủy yêu cầu</a>

                            <div class="modal fade" id="info-${book.requestId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" >Yêu cầu thuê phòng</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            Hành động này sẽ  KHÔNG cho phép yêu cầu này gửi tới chủ homestay. Bạn sẽ không thể hoàn tác hành động trên.
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                            <a class="btn btn-danger" href="book?action=cancle-booking&requestId=${book.requestId}" >Xác nhận</a>
                                        </div>
                                    </div>
                                </div>
                            </div> 


                        </c:if>
                        <c:if test="${book.status == 2}">
                            <button class="btn btn-warning">Bị từ chối</button>
                        </c:if>
                        <c:if test="${book.status == 3}">
                            <button class="btn btn-danger">Đã hủy yêu cầu</button>
                        </c:if>
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
                    <li class="page-item"><a class="page-link" href="book?action=view-booking-history&status=${status}&index=${selectedPage-1}">«</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                <a class="page-link ${i == selectedPage ? "active" : "" }" href="book?action=view-booking-history&status=${status}&index=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">»</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="book?action=view-booking-history&status=${status}&index=${selectedPage+1}">»</a></li>
                    </c:otherwise>
                </c:choose>
        </div>
        <jsp:include page="footer.jsp" />
    </body>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"
    ></script>
</html>
