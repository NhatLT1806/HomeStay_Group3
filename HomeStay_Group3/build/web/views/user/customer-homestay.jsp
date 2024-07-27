<%@ page import="java.net.URLEncoder" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>HomeStay</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/customer-homestay.css">
        <!-- Google Web Fonts -->
        <style>

            .pagination {
                margin-top: 50px;
                justify-content: center;
                width: 100%;
                display: flex;
                list-style: none;
                padding: 0;
            }

            .pagination a {
                color: black;
                float: left;
                padding: 8px 16px;
                text-decoration: none;
                transition: background-color .3s;
                border: 1px solid #ddd; /* Slightly rounding the borders */
                margin: 0 4px;
                border-radius: 5px; /* Adds rounded corners to the pagination links */
            }

            .pagination a.active {
                background-color: #4CAF50;
                color: white;
                border: 1px solid #4CAF50;
            }

            .pagination a:hover:not(.active) {
                background-color: #ddd;
            }

        </style>
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <div style="display: flex; align-items: center; justify-content: center">
            <div style="display: flex; justify-content: center; align-content: center">
                <div style="margin-right: 15px">
                    Tìm kiếm: 
                </div>
                <form action="homestay" style="display: flex">
                    <input type="hidden" value="view-own-homestay" name="action"/>
                    <select name="status" class="form-select" aria-label="Default select example">
                        <option  value="" selected>Tất cả</option>
                        <option   ${status == 0 ? 'selected' : ''} value="0">Đang chờ phê duyệt</option>
                        <option   ${status == 1 ? 'selected' : ''} value="1">Duyệt thành công</option>
                        <option   ${status == 2 ? 'selected' : ''}  value="2">Bị từ chối</option>
                    </select>
                    <button style="margin-left: 15px" type="submit" class="btn btn-success">Filter</button>
                </form>
            </div>
        </div>
        <div
            class="timeshare-owner-management"
            style="
            display: grid;
            grid-template-columns: auto auto;
            justify-content: center;
            align-items: center;
            gap: 30px;
            margin-top: 2%;
            "
            >
            <c:forEach items="${HOMESTAY}" var="h">
                <div
                    class="member-info-container"
                    style="padding: 20px 20px 0px; min-width: 500px"
                    >
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
                                    background-image: url('data:image/png;base64,${h.image}');
                                    height: 11rem;
                                    "
                                    ></div>
                            </div>
                            <div
                                class="timeshare-title"
                                style="text-align: left; margin-left: 20px"
                                >
                                <h3 class="timeshare-details-title">${h.name}</h3>
                                <h5>${h.address}</h5>
                                <h4>
                                    ${h.description}
                                </h4>

                                <!-- watting for acppet -->
                                <c:if test="${h.status == 0}">
                                    <div style="background-color: rgb(254, 182, 0); width: fit-content"  >
                                        <h4>Đang đợi phê duyệt</h4>
                                    </div>
                                </c:if>
                                <!-- acppet -->
                                <c:if test="${h.status == 1}">
                                    <div style="background-color: #0582ae; width: fit-content">
                                        <h4 style="color: white">Sẵn sàng cho thuê</h4>
                                    </div>
                                </c:if>
                                <!-- watting for decline -->
                                <c:if test="${h.status == 2}">
                                    <div   style="background-color: rgb(244, 67, 54); width: fit-content" >
                                        <h4>Bị từ chối</h4>
                                    </div>
                                </c:if>
                                <c:if test="${h.status == 4}">
                                    <div   style="background-color: #0582ae; width: fit-content" >
                                        <h4>Đã ẩn</h4>
                                    </div>
                                </c:if>
                             

                                <a style="text-decoration: none; padding: 10px; width: 100px; border-radius: 10px; display: block; color: #fff;background: #1677ff;" class="btn flex" href="homestay?action=view-detail-homestay&homestayId=${h.homestayId}">Chi tiết</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="pagination">
                <c:choose>
                    <c:when test ="${selectedPage - 1 < 1}">
                        <a class="page-link" href="#">«</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="homestay?action=view-own-homestay&status=${status}&search=${search}&index=${selectedPage-1}">«</a>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                    <a class="page-link ${i == selectedPage ? "active" : "" }" href="homestay?action=view-own-homestay&status=${status}&search=${search}&index=${i}">${i}</a> 
                </c:forEach>
                <c:choose>
                    <c:when test ="${selectedPage >= endP}">
                        <a class="page-link" href="#">»</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="homestay?action=view-own-homestay&status=${status}&search=${search}&index=${selectedPage+1}">»</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <jsp:include page="footer.jsp" />

    </body>

    <script>
        const profilePicture = document.getElementById('profile-picture');
        const imageInput = document.getElementById('image-input');

        profilePicture.addEventListener('click', () => {
            imageInput.disabled = false;
            imageInput.click();
        });
        const updateAvatar = false;
        imageInput.addEventListener('change', () => {
            imageInput.disabled = false;
            const file = imageInput.files[0];
            const formData = new FormData();
            formData.append('image', file);

            const reader = new FileReader();
            reader.onload = () => {
                profilePicture.src = reader.result;
            };
            reader.readAsDataURL(file);
            updateAvatar = true;
        });
        if (!updateAvatar && imageInput.value != null) {
            imageInput.disabled = true;
        }
        document.addEventListener("DOMContentLoaded", function () {
            const phoneInput = document.getElementById("Phone");

            phoneInput.addEventListener("input", function () {
                const regex = /^\d{0,10}$/;
                if (!regex.test(phoneInput.value)) {
                    // If validation fails, show a custom error message
                    phoneInput.setCustomValidity("Số điện thoại phải gồm 10 chữ số và không chứa ký tự đặc biệt.");
                } else {
                    // Clear custom error message
                    phoneInput.setCustomValidity("");
                }
            });
        });
    </script>
</html>