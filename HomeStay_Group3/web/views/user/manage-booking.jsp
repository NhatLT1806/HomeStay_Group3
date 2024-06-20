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
                        <div class="desc" style="height: 20px"><p>Phone Number: ${book.bookedByPhone}</p></div>        
                        <div class="desc" style="height: 20px"><p>Email: ${book.bookedByEmail}</p></div>
                        <div class="desc" style="height: 20px"><p>Phone Number: ${book.bookedByPhone}</p></div>
                        <c:choose>
                            <c:when test="${book.status != 0}">
                                <c:if test="${book.status == 1}">
                                    <button class="btn btn-success">Thuê thành công</button>
                                </c:if>

                                <c:if test="${book.status == 0}">
                                    <button class="btn btn-warning">Đang gửi yêu cầu</button>
                                </c:if>
                                <c:if test="${book.status == 2}">
                                    <button class="btn btn-warning">Bị từ chối</button>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div class="fees flex" style="justify-content: space-between">
                                    <a href="book?action=confirm-booking&requestId=${book.requestId}&roomId=${book.roomId}"
                                       class="btn flex"
                                       style="
                                       width: 48%;
                                       display: flex;
                                       align-items: center;
                                       justify-content: center;
                                       background-color: rgb(49, 151, 149);
                                       "
                                       >
                                        <svg
                                            stroke="currentColor"
                                            fill="currentColor"
                                            stroke-width="0"
                                            viewBox="0 0 24 24"
                                            height="1em"
                                            width="1em"
                                            xmlns="http://www.w3.org/2000/svg"
                                            style="font-size: 1.2rem; margin-right: 3px"
                                            >
                                        <g id="Circle_Check">
                                        <g>
                                        <path
                                            d="M15.81,10.4a.5.5,0,0,0-.71-.71l-3.56,3.56L9.81,11.52a.5.5,0,0,0-.71.71l2.08,2.08a.513.513,0,0,0,.71,0Z"
                                            ></path>
                                        <path
                                            d="M12,21.934A9.934,9.934,0,1,1,21.933,12,9.945,9.945,0,0,1,12,21.934ZM12,3.067A8.934,8.934,0,1,0,20.933,12,8.944,8.944,0,0,0,12,3.067Z"
                                            ></path>
                                        </g>
                                        </g>
                                        </svg>
                                        Accept</a>
                                    <a href="book?action=reject-booking&requestId=${book.requestId}&roomId=${book.roomId}"
                                       class="btn flex"
                                       style="
                                       width: 48%;
                                       display: flex;
                                       align-items: center;
                                       justify-content: center;
                                       background-color: rgb(211, 72, 54);
                                       "
                                       >
                                        <svg
                                            stroke="currentColor"
                                            fill="currentColor"
                                            stroke-width="0"
                                            viewBox="0 0 24 24"
                                            height="1em"
                                            width="1em"
                                            xmlns="http://www.w3.org/2000/svg"
                                            style="font-size: 1.2rem; margin-right: 3px"
                                            >
                                        <g id="Circle_Remove">
                                        <g>
                                        <path
                                            d="M9.525,13.765a.5.5,0,0,0,.71.71c.59-.59,1.175-1.18,1.765-1.76l1.765,1.76a.5.5,0,0,0,.71-.71c-.59-.58-1.18-1.175-1.76-1.765.41-.42.82-.825,1.23-1.235.18-.18.35-.36.53-.53a.5.5,0,0,0-.71-.71L12,11.293,10.235,9.525a.5.5,0,0,0-.71.71L11.293,12Z"
                                            ></path>
                                        <path
                                            d="M12,21.933A9.933,9.933,0,1,1,21.934,12,9.945,9.945,0,0,1,12,21.933ZM12,3.067A8.933,8.933,0,1,0,20.934,12,8.944,8.944,0,0,0,12,3.067Z"
                                            ></path>
                                        </g>
                                        </g></svg
                                        >Decline
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </c:forEach>


        </div>
        <jsp:include page="footer.jsp" />
    </body>

</html>
