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

        <div style="display: flex; justify-content: center; align-content: center">
            <div style="margin-right: 15px">
                Tìm kiếm: 
            </div>
            <form action="book" style="display: flex">
                <input type="hidden" value="view-room-booking" name="action"/>
                <select name="status" class="form-select" aria-label="Default select example">
                    <option  value="" selected>Tất cả</option>
                    <option   ${status == 0 ? 'selected' : ''} value="0">Chờ xác nhận</option>
                    <option   ${status == 1 ? 'selected' : ''} value="1">Đã xác nhận</option>    

                    <option   ${status == 2 ? 'selected' : ''}  value="2">Bị từ chối</option>
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
                                <a href="homestay?action=view-detail-homestay&homestayId=${book.homestayId}">
                                    <h1 class="destTitle">${book.roomName}</h1>
                                </a>                                
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
                                    <a  href="contract?roomId=${book.roomId}">Xem hợp đồng</a>
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
                                    <a  data-bs-toggle="modal" data-bs-target="#accept-${book.requestId}" aria-hidden="true"

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

                                    <div class="modal fade" id="accept-${book.requestId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" >Yêu cầu thuê phòng</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Cho phép người dùng <b>${book.bookedByEmail}</b> thuê phòng. Bạn sẽ không thể hoàn tác hành động trên.
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <a class="btn btn-success"  href="book?action=confirm-booking&requestId=${book.requestId}&roomId=${book.roomId}" >Xác nhận</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div> 

                                    <a   data-bs-toggle="modal" data-bs-target="#reject-${book.requestId}" aria-hidden="true"
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
                                    <div class="modal fade" id="reject-${book.requestId}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" >Yêu cầu thuê phòng</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Không cho phép người dùng <b>${book.bookedByEmail}</b> thuê phòng. Bạn sẽ không thể hoàn tác hành động trên.
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <a class="btn btn-danger" href="book?action=reject-booking&requestId=${book.requestId}&roomId=${book.roomId}" >Xác nhận</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div> 


                                </div>
                            </c:otherwise>
                        </c:choose>

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
                    <li class="page-item"><a class="page-link" href="book?action=view-room-booking&status=${status}&index=${selectedPage-1}">«</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                <a class="page-link ${i == selectedPage ? "active" : "" }" href="book?action=view-room-booking&status=${status}&index=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">»</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="book?action=view-room-booking&status=${status}&index=${selectedPage+1}">»</a></li>
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
