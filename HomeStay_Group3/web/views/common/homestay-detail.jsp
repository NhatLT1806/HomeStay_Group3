<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/room-detail.css" />
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
        <input type="hidden" id="success" name="MESSAGE" value="${MESSAGE}"/>     
        <input type="hidden" id="error" name="MESSAGE" value="${ERROR}"/>
        <section class="home">
            <div class="overlay"></div>
            <div class="homeContent container">
                <div class="hero-image_quote-container">
                    <div class="hero-image_quote">
                        <div class="hero-image_quote-photo" style="width: 400px">
                            <img
                                width="300"
                                height="300"
                                src="data:image/png;base64,${HOMESTAY.image}"
                                class="lazy"
                                alt=""
                                />
                        </div>
                        <div style="width: 10px;"></div>
                        <blockquote class="hero-image_quote-text">
                            <div class="hero-image_quote-author-container">
                                <cite class="hero-image_quote-author">${HOMESTAY.name} </cite
                                ><time class="hero-image_quote-date" datetime="2015"
                                       >VIP Members since 2019</time
                                >
                            </div>
                            <div style="height: 20px;">

                            </div>
                            <span>
                                ${HOMESTAY.description}
                            </span>
                        </blockquote>
                    </div>
                </div>
            </div>
        </section>

        <section class="main container section" style="margin-top: 6%">
            <div class="secTitle">
                <h3 data-aos="fade-up" class="title aos-init aos-animate">
                    Danh sách phòng của homestay
                </h3>
            </div>

            <div style="display: flex; justify-content: space-between">
                <form style="text-align: center" action="homestay" method="get" style="display: flex">
                    <input type="hidden" name="action" value="homestay-detail"/>             
                    <input type="hidden" name="homestayId" value="${homestayId}"/> 

                    <label for="ownerId">Tên phòng</label>
                    <input type="text" id="ownerId" name="search" value="${search}">
                    <button style="background-color: #4CAF50;
                            color: white;
                            border: 1px solid #4CAF50; padding: 7px" type="submit" value="Search">Tìm kiếm</button>
                </form>

                <form style="text-align: center" action="homestay" method="get">
                    <input type="hidden" name="action" value="filter-by-price"/>             
                    <input type="hidden" name="homestayId" value="${homestayId}"/> 
                    <div style="display: flex; align-content: center">
                        <div class="slider-container">
                            <label for="minPrice">Giá từ:</label>
                            <input  value="${MIN_PRICE}" type="range" id="minPrice" name="min-price" min="1000000" max="10000000"  oninput="updatePriceRange()">
                            <label id="minPriceLabel"></label>
                        </div>
                        <div class="slider-container">
                            <label for="maxPrice">đến</label>
                            <input value="${MAX_PRICE}" type="range" id="maxPrice" name="max-price" min="1000000" max="10000000" oninput="updatePriceRange()">
                            <label id="maxPriceLabel"></label>
                        </div>
                        <button style="background-color: #4CAF50; color: white; border: 1px solid #4CAF50; padding: 7px; width: 100px; margin-left: 15px" type="submit">Lọc</button>
                    </div>
                </form>

            </div>
            <div style="display: flex;">
                <div>
                    <form style="text-align: center" action="homestay" method="get">
                        <input type="hidden" name="action" value="filter-by-services"/>             
                        <input type="hidden" name="homestayId" value="${homestayId}"/> 
                        <div style="display: flex; text-align: left">
                            <div class="textField">
                                <h3>Dịch vụ</h3>
                                <div class="checkbox-container">
                                    <c:forEach items="${CATEGORIES}" var="category" varStatus="status">
                                        <div class="checkbox-item">
                                            <input type="checkbox" name="selectedServices" value="${category.id}" 
                                                   id="service-${category.id}" }>
                                            <label for="service-${category.id}">${category.name}</label>
                                        </div>
                                    </c:forEach>
                                </div>
                                <span class="error"></span>
                            </div>
                        </div>
                        <button style="background-color: #4CAF50; color: white; border: 1px solid #4CAF50; padding: 7px; width: 100%" type="submit">Lọc</button>
                    </form>
                </div>
                <div class="secContent grid">
                    <c:forEach items="${ROOMS}" var="room">
                        <div data-aos="fade-up" class="singleDestination aos-init aos-animate" style="min-height: unset; max-height: unset; margin: 20px;">
                            <c:choose>
                                <c:when test="${room.status == 1}">
                                    <div class="new-ribbon secondary">
                                        Còn phòng
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="new-ribbon secondary" style="background: #0056b3">
                                        Hết phòng
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <div class="imageDiv">
                                <img
                                    src="data:image/png;base64,${room.image}"
                                    alt="The Bay - Ha Long Homestay"
                                    />
                            </div>
                            <div class="cardInfo">
                                <div class="cardBody-title">
                                    <div>
                                        <h1 class="destTitle">${room.name}</h1>
                                        <span class="continent flex">
                                            <!--                                        <svg stroke="currentColor"  fill="currentColor"   stroke-width="0"  viewBox="0 0 512 512"    class="icon"
                                                                                      height="1em"
                                                                                      width="1em"
                                                                                      xmlns="http://www.w3.org/2000/svg"
                                                                                      >
                                                                                    <path
                                                                                        fill="none"
                                                                                        stroke-linecap="round"
                                                                                        stroke-linejoin="round"
                                                                                        stroke-width="32"
                                                                                        d="M256 48c-79.5 0-144 61.39-144 137 0 87 96 224.87 131.25 272.49a15.77 15.77 0 0 0 25.5 0C304 409.89 400 272.07 400 185c0-75.61-64.5-137-144-137z"
                                                                                        ></path>
                                                                                    <circle
                                                                                        cx="256"
                                                                                        cy="192"
                                                                                        r="48"
                                                                                        fill="none"
                                                                                        stroke-linecap="round"
                                                                                        stroke-linejoin="round"
                                                                                        stroke-width="32"
                                                                                        ></circle></svg>-->
                                        </span>

                                        <span>
                                            <b>Cho phép ở: </b> ${room.maxParticipants} người
                                        </span>
                                        <div>
                                            <b>Giá: </b><fmt:formatNumber value="${room.price}" type="number" pattern="#,###"/>₫
                                        </div>
                                        <div>
                                            <b>Diện tích: </b> ${room.area} m2
                                        </div>


                                    </div>
                                    <a class="grade" href="room?action=add-to-favourite&roomId=${room.roomId}">
                                        <span style="font-size: 0.85rem"
                                              ><svg
                                                stroke="currentColor"
                                                fill="currentColor"
                                                stroke-width="0"
                                                viewBox="0 0 512 512"
                                                height="1em"
                                                width="1em"
                                                xmlns="http://www.w3.org/2000/svg"
                                                style="color: red; font-size: 1.8rem"
                                                >
                                            <path
                                                d="M256 448a32 32 0 0 1-18-5.57c-78.59-53.35-112.62-89.93-131.39-112.8-40-48.75-59.15-98.8-58.61-153C48.63 114.52 98.46 64 159.08 64c44.08 0 74.61 24.83 92.39 45.51a6 6 0 0 0 9.06 0C278.31 88.81 308.84 64 352.92 64c60.62 0 110.45 50.52 111.08 112.64.54 54.21-18.63 104.26-58.61 153-18.77 22.87-52.8 59.45-131.39 112.8a32 32 0 0 1-18 5.56z"
                                                ></path></svg
                                            ></span>
                                    </a>
                                </div>
                                <a style="display: block" class="btn flex" href="room?action=room-detail&roomId=${room.roomId}">Xem chi tiết</a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>
        <div class="pagination">
            <c:choose>
                <c:when test ="${selectedPage - 1 < 1}">
                    <a class="page-link" href="#">«</a>
                </c:when>
                <c:otherwise>
                    <a class="page-link" href="homestay?action=homestay-detail&homestayId=${homestayId}&search=${search}&index=${selectedPage-1}">«</a>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="1" end="${endP}">
                <a class="page-link ${i == selectedPage ? "active" : "" }" href="homestay?action=homestay-detail&homestayId=${homestayId}&search=${search}&index=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <a class="page-link" href="#">»</a>
                </c:when>
                <c:otherwise><a class="page-link" href="homestay?action=homestay-detail&homestayId=${homestayId}&search=${search}&index=${selectedPage+1}">»</a><
                </c:otherwise>
            </c:choose>
        </div>

    </div>
    <h4>Danh sách phản hồi</h4>
    <c:forEach items="${COMMENTS}" var="comment">
        <div class="row p-3" id="faq">
            <div class="col-md-1">
                <c:choose>
                    <c:when test="${comment.avatar != null}">
                        <img  src="data:image/png;base64,${comment.avatar}" alt="" width="100"  class="rounded-circle reviews" />
                    </c:when>
                    <c:otherwise>
                        <img src="https://placehold.co/100x100" width="50" >
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-11">
                <p class="heading-md">name: ${comment.userName}</p>
                <span style="color: gray">${comment.createAt}</span>
                <p style="padding-top: 10px; font-size: 18px">
                    ${comment.content}
                </p>
            </div>
        </div>
    </c:forEach>

    <nav aria-label="Page navigation example" style="display: flex; justify-content:center;margin-top: 15px;">
        <div class="pagination">
            <c:choose>
                <c:when test ="${selectedPage2 - 1 < 1}">
                    <a class="page-link" href="#">«</a>
                </c:when>
                <c:otherwise>
                    <a class="page-link" href="homestay?action=homestay-detail&homestayId=${homestayId}&index2=${selectedPage2-1}">«</a>
                </c:otherwise>
            </c:choose>
            <c:forEach var="i" begin="1" end="${endP2}">
                <a class="page-link ${i == selectedPage2 ? "active" : "" }" href="homestay?action=homestay-detail&homestayId=${homestayId}&index2=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <a class="page-link" href="#">»</a>
                </c:when>
                <c:otherwise><a class="page-link" href="homestay?action=homestay-detail&homestayId=${homestayId}&index2=${selectedPage2+1}">»</a><
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
    <div class=" p-3">
        <form action="CommentRoomController" method="POST">
            <div class="form-group">
                <input type="hidden" name="homestayId" value="${homestayId}"/>
                <label for="exampleFormControlTextarea1">Your Feedback</label >
                <textarea
                    class="form-control"
                    id="feedback" name="feedback"
                    rows="3"
                    ></textarea>
            </div>
            <button type="submit" class="btn btn-primary mt-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
                <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576zm6.787-8.201L1.591 6.602l4.339 2.76z"></path>
                </svg>
                Send
            </button>
        </form>

    </div>
    <jsp:include page="footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>

        var error = document.getElementById('error');
        var message = document.getElementById('success');

        if (message.value) {
            Swal.fire({
                title: message.value,
                icon: "success",
                showCancelButton: true,
                confirmButtonText: "Confirm",
            })
        }
        if (error.value) {
            Swal.fire({
                title: error.value,
                icon: "info",
                showCancelButton: true,
                confirmButtonText: "Confirm",
            });
        }
        ;
        function updatePriceRange() {
            var minPrice = document.getElementById("minPrice").value;
            var maxPrice = document.getElementById("maxPrice").value;
            console.log(minPrice);
            console.log(maxPrice);
            document.getElementById("minPriceLabel").textContent = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(minPrice);
            document.getElementById("maxPriceLabel").textContent = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(maxPrice);
        }
        updatePriceRange();
    </script>
</body>
</html>
