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
        <jsp:include page="header.jsp" />\

        <div style="display: flex; align-items: center; justify-content: center">
            <span
                class="ant-input-group-wrapper ant-input-group-wrapper-outlined css-dev-only-do-not-override-1k979oh ant-input-search ant-input-search-with-button"
                style="max-width: 700px; margin-top: 2%"
                ><span
                    class="ant-input-wrapper ant-input-group css-dev-only-do-not-override-1k979oh"
                    ><input
                        placeholder="Search with title..."
                        class="ant-input css-dev-only-do-not-override-1k979oh ant-input-outlined"
                        type="text"
                        value="" /><span class="ant-input-group-addon"
                        ><button
                            type="button"
                            class="ant-btn css-dev-only-do-not-override-1k979oh ant-btn-primary ant-input-search-button"
                            >
                            <span class="ant-btn-icon"
                                  ><span
                                    role="img"
                                    aria-label="search"
                                    class="anticon anticon-search"
                                    ><svg
                                        viewBox="64 64 896 896"
                                        focusable="false"
                                        data-icon="search"
                                        width="1em"
                                        height="1em"
                                        fill="currentColor"
                                        aria-hidden="true"
                                        >
                                    <path
                                        d="M909.6 854.5L649.9 594.8C690.2 542.7 712 479 712 412c0-80.2-31.3-155.4-87.9-212.1-56.6-56.7-132-87.9-212.1-87.9s-155.5 31.3-212.1 87.9C143.2 256.5 112 331.8 112 412c0 80.1 31.3 155.5 87.9 212.1C256.5 680.8 331.8 712 412 712c67 0 130.6-21.8 182.7-62l259.7 259.6a8.2 8.2 0 0011.6 0l43.6-43.5a8.2 8.2 0 000-11.6zM570.4 570.4C528 612.7 471.8 636 412 636s-116-23.3-158.4-65.6C211.3 528 188 471.8 188 412s23.3-116.1 65.6-158.4C296 211.3 352.2 188 412 188s116.1 23.2 158.4 65.6S636 352.2 636 412s-23.3 116.1-65.6 158.4z"
                                        ></path></svg></span
                                ></span></button></span></span
                ></span>
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
                                        <h4>Waiting for the accept</h4>
                                    </div>
                                </c:if>
                                <!-- acppet -->
                                <c:if test="${h.status == 1}">
                                    <div style="background-color: #0582ae; width: fit-content">
                                        <h4 style="color: white">Waiting for book</h4>
                                    </div>
                                </c:if>
                                <!-- watting for decline -->
                                <c:if test="${h.status == 2}">
                                    <div   style="background-color: rgb(244, 67, 54); width: fit-content" >
                                        <h4>This is decline from the admin</h4>
                                    </div>
                                </c:if>
                                <!-- have offer -->
                                <c:if test="${h.status == 3}">
                                    <button class="owner-management-button">SEE THE ORDER</button>
                                </c:if>
                                <!-- Booked -->
                                <c:if test="${h.status == 4}">
                                    <div style="background-color: green; width: fit-content">
                                        <h4 style="color: white">This time share is booked</h4>
                                    </div>
                                </c:if>

                                <a style="text-decoration: none; padding: 10px; width: 100px; border-radius: 10px; display: block; color: #fff;background: #1677ff;" class="btn flex" href="homestay?action=homestay-detail&homestayId=${h.homestayId}">Details</a>
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
                        <a class="page-link" href="league?action=listLeague&search=${search}&index=${selectedPage-1}">«</a>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                    <a class="page-link ${i == selectedPage ? "active" : "" }" href="league?action=listLeague&search=${search}&index=${i}">${i}</a> 
                </c:forEach>
                <c:choose>
                    <c:when test ="${selectedPage >= endP}">
                        <a class="page-link" href="#">»</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="league?action=listLeague&search=${search}&index=${selectedPage+1}">»</a>
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