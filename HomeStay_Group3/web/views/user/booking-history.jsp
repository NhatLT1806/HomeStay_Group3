<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list-booking.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
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
                                <h1 class="destTitle">${book.roomName}</h1>
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
                        </c:if>

                        <c:if test="${book.status == 0}">
                            <button class="btn btn-warning">Đang gửi yêu cầu</button>
                        </c:if>
                        <c:if test="${book.status == 2}">
                            <button class="btn btn-warning">Bị từ chối</button>
                        </c:if>
                    </div>
                </div>
            </c:forEach>


        </div>
        <jsp:include page="footer.jsp" />
    </body>

</html>
