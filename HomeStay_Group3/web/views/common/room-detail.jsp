
<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/timeshare-detail.css">
    </head>
    <body>
        <jsp:include page="header.jsp" />
        <div class="main-information">
            <div class="main-left">
                <div class="rental-card">
                    <div class="rental-card-header">
                        <div class="resort-image image image-tilt_left hide-for-small-only">
                            <div
                                class="resort-frame-image background-cover"
                                style="
                                background-image: url('data:image/png;base64,${ROOM.image}');
                                "
                                ></div>
                        </div>
                        <div class="timeshare-title">
                            <h4>${ROOM.name}</h4>
                            <p class="timeshare-details-title">
                                <b>Địa chỉ: </b>${HOMESTAY.address}
                            <p>
                            <p >
                                <b>Diện tích: </b> ${ROOM.area} m2
                            </p>
                            <p >
                                <b>Giá: </b><fmt:formatNumber value="${ROOM.price}" type="number" pattern="#,###"/>₫
                            </p>
                            <p >
                                <b>Cho phép ở: </b> ${ROOM.maxParticipants} người
                            </p>
                        </div>
                    </div>
                    <div class="rental-info">
                        <b>Tiện ích của phòng: </b>
                        <c:forEach items="${SERVICES}" var="service">
                            <ul>
                                <li>${service.categoryName}</li>
                            </ul>
                        </c:forEach>
                        <div>
                            <div class="info-section d-flex">
                                <svg
                                    stroke="currentColor"
                                    fill="currentColor"
                                    stroke-width="0"
                                    viewBox="0 0 512 512"
                                    class="icon"
                                    height="1em"
                                    width="1em"
                                    xmlns="http://www.w3.org/2000/svg"
                                    >
                                <path
                                    d="M55.379 25l-28.4 142H172.27L256 83.271 339.729 167H485.02l-28.4-142zM256 108.727L179.729 185H41v302h158v-87c0-18.25 7.166-33.077 18.021-42.727C227.877 347.624 242 343 256 343s28.123 4.624 38.979 14.273C305.834 366.923 313 381.75 313 400v87h158V185H332.271zm0 38.544l57 57V297H199v-92.729zm0 25.456l-39 39V279h78v-67.271zM71 199h98v98H71zm272 0h98v98h-98zM89 217v30h62v-30zm272 0v30h62v-30zM89 265v14h62v-14zm272 0v14h62v-14zM71 359h98v98H71v-98zm272 0h98v98h-98v-98zm-87 2c-10 0-19.877 3.376-27.021 9.727C221.834 377.077 217 386.25 217 400v87h78v-87c0-13.75-4.834-22.923-11.979-29.273C275.877 364.376 266 361 256 361zM89 377v62h62v-62zm272 0v62h62v-62z"
                                    ></path>
                                </svg>
                                <div
                                    style="
                                    padding: 0px 0.714286rem 1.42857rem;
                                    font-size: 1rem;
                                    line-height: 1.5;
                                    font-family: Roboto, Helvetica, Arial, sans-serif;
                                    "
                                    >
                                    <p>${service.name}</p>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <dl class="tabs">
                    <dd id="resortInfoTabHeader" class="">
                        <a href="/posting/R1171762/resort-info" data-select-tab="resortInfo"
                           >Thông tin về nhà</a
                        >
                    </dd>
                </dl>
                <div class="member-info-container">
                    <div class="rental-card-detail">
                        <div class="rental-card-header">
                            <div
                                class="resort-image image image-tilt_left hide-for-small-only"
                                style="
                                background-color: rgba(0, 0, 0, 0.3);
                                min-width: 11rem;
                                min-height: 11rem;
                                top: 0px;
                                left: 0px;
                                "
                                >
                                <div
                                    class="resort-frame-image background-cover"
                                    style="
                                    background-image: url('data:image/png;base64,${HOMESTAY.image}');
                                    height: 11rem;
                                    "
                                    ></div>
                            </div>
                            <div
                                class="timeshare-title"
                                style="text-align: left; margin-left: 20px"
                                >
                                <h3 class="timeshare-details-title">
                                    ${HOMESTAY.name}
                                </h3>
                                <h5>${HOMESTAY.address}</h5>
                                <p>
                                    ${HOMESTAY.description}
                                </p>
                            </div>
                        </div>
                    </div>
                    <c:if test="${USER == null}">
                        <div class="member-info-alert">
                            <svg
                                stroke="currentColor"
                                fill="currentColor"
                                stroke-width="0"
                                viewBox="0 0 512 512"
                                class="info-icon"
                                height="24"
                                width="24"
                                xmlns="http://www.w3.org/2000/svg"
                                >
                            <path
                                d="M256 8C119.043 8 8 119.083 8 256c0 136.997 111.043 248 248 248s248-111.003 248-248C504 119.083 392.957 8 256 8zm0 110c23.196 0 42 18.804 42 42s-18.804 42-42 42-42-18.804-42-42 18.804-42 42-42zm56 254c0 6.627-5.373 12-12 12h-88c-6.627 0-12-5.373-12-12v-24c0-6.627 5.373-12 12-12h12v-64h-12c-6.627 0-12-5.373-12-12v-24c0-6.627 5.373-12 12-12h64c6.627 0 12 5.373 12 12v100h12c6.627 0 12 5.373 12 12v24z"
                                ></path></svg
                            ><span
                                >Bạn cần trở thành thành viên của hệ thống mới có thể book phòng</span
                            >
                        </div>
                        <a href="auth?action=login" class="btn-member">Đăng kí thành viên</a>
                        <div class="member-info-login">
                            Đã đăng kí? <a href="auth?action=login">Đăng nhập»</a>
                        </div>
                    </c:if>

                </div>
            </div>
            <div class="main-right">
                <div class="booking-card">
                    <div class="price-info">
                        <svg
                            stroke="currentColor"
                            fill="currentColor"
                            stroke-width="0"
                            viewBox="0 0 640 512"
                            class="money-icon"
                            height="1em"
                            width="1em"
                            xmlns="http://www.w3.org/2000/svg"
                            >
                        <path
                            d="M320 144c-53.02 0-96 50.14-96 112 0 61.85 42.98 112 96 112 53 0 96-50.13 96-112 0-61.86-42.98-112-96-112zm40 168c0 4.42-3.58 8-8 8h-64c-4.42 0-8-3.58-8-8v-16c0-4.42 3.58-8 8-8h16v-55.44l-.47.31a7.992 7.992 0 0 1-11.09-2.22l-8.88-13.31a7.992 7.992 0 0 1 2.22-11.09l15.33-10.22a23.99 23.99 0 0 1 13.31-4.03H328c4.42 0 8 3.58 8 8v88h16c4.42 0 8 3.58 8 8v16zM608 64H32C14.33 64 0 78.33 0 96v320c0 17.67 14.33 32 32 32h576c17.67 0 32-14.33 32-32V96c0-17.67-14.33-32-32-32zm-16 272c-35.35 0-64 28.65-64 64H112c0-35.35-28.65-64-64-64V176c35.35 0 64-28.65 64-64h416c0 35.35 28.65 64 64 64v160z"
                            ></path></svg
                        ><span class="price">$<fmt:formatNumber value="${ROOM.price}" type="number" pattern="#,###"/>₫</span>
                    </div>
                    <c:choose>
                        <c:when test="${USER == null}">
                            <button class="btn-request accept">Yêu cầu là thành viên</button>
                        </c:when>
                        <c:otherwise>
                            <div class="terms-and-conditions">
                                <label for="termsCheckbox" class="terms-checkbox"
                                       ><span id="rentalAgreement"
                                       >I have read the
                                        <a class="agreement-link" href="/auth?action=login">Chấp nhận</a
                                        >, and agree to all <span class="nowrap">terms.</span></span
                                    ><input type="checkbox" id="termsCheckbox" checked=""
                                            /></label>





                                <c:choose>
                                    <c:when test="${ROOM.status == 1}">
                                        <c:if test="${HOMESTAY.userId != USER.id}">
                                            <a style="text-decoration: none; display: block; margin-top: 20px" data-bs-toggle="modal" data-bs-target="#confirmBooking"  class="btn-request accept">Chấp nhận thuê</a>
                                        </c:if>

                                        <%-- ---------------------Hiện modal -------------------- --%>

                                        <div class="modal fade" id="confirmBooking" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="exampleModalLabel">Xác nhận thuê phòng</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body" style="text-align: left">
                                                        <div>
                                                            <p class="timeshare-details-title">
                                                                <b>Tên phòng: </b>${ROOM.name}
                                                            <p>
                                                            <p >
                                                                <b>Diện tích: </b> ${ROOM.area} m2
                                                            </p>
                                                            <p >
                                                                <b>Giá: </b><fmt:formatNumber value="${ROOM.price}" type="number" pattern="#,###"/>₫
                                                            </p>
                                                            <p >
                                                                <b>Cho phép ở: </b> ${ROOM.maxParticipants} người
                                                            </p>
                                                            <p >
                                                                <b>Tên nhà: </b>  ${HOMESTAY.name}
                                                            </p>

                                                            <p >
                                                                <b>Địa chỉ phòng trọ: </b>  ${HOMESTAY.address}
                                                            </p>
                                                            <p>Bạn có chắc chắc muốn thuê phòng này. Vui lòng kiểm tra thông tin trước khi gửi yêu cầu.</p>

                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <a  href="book?action=book&roomId=${ROOM.roomId}" type="button" class="btn btn-success">Xác nhận</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </c:when>
                                    <c:otherwise>
                                        <a style="text-decoration: none; display: block; margin-top: 20px">Phòng này đã được thuê</a>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </c:otherwise>
                    </c:choose>

                    <hr />
                    <div style="color: green">${MESSAGE}</div>

                    <div class="poster-info">
                        <div class="poster-avatar">
                            <c:choose>
                                <c:when test="${OWNER.avatar != null}">
                                    <img src="data:image/png;base64,${OWNER.avatar}" alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 50px; cursor: pointer; margin: 10px auto;border-radius:50%;border: 2px solid #1b730d">
                                </c:when>
                                <c:otherwise>
                                    <img src="https://placehold.co/100x100" alt="Profile picture" id="profile-picture" class=" border-3 border-green-500 p-1 mb-3" style="width: 50px; cursor: pointer; margin: 10px auto;border-radius:50%;border: 2px solid #1b730d">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="poster-details">
                            <span class="poster-name">
                                <p style="margin-left: 3px; color: rgb(231, 76, 60)">
                                    ${OWNER.firstName} ${OWNER.lastName}
                                </p></span
                            ><span class="member-since">Email: ${OWNER.email}</span>
                            <span class="member-since">Phone ${OWNER.phone}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.min.js" integrity="sha384-j0CNLUeiqtyaRmlzUHCPZ+Gy5fQu0dQ6eZ/xAww941Ai1SxSY+0EQqNXNE6DZiVc" crossorigin="anonymous"></script>
    <jsp:include page="footer.jsp" />
</html>
