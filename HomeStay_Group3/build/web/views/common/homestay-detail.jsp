<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    List Room
                </h3>
            </div>
            <div class="secContent grid">
                <c:forEach items="${ROOMS}" var="room">
                    <div data-aos="fade-up" class="singleDestination aos-init aos-animate" style="min-height: unset; max-height: unset">
                        <div class="new-ribbon secondary">$${room.price}/ day</div>
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
                                        Max participants: ${room.maxParticipants}
                                    </span>
                                </div>
                                <div class="grade">
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
                                </div>
                            </div>
                            <a style="display: block" class="btn flex" href="room?action=room-detail&roomId=${room.roomId}">Book</a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
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
                <c:otherwise><a class="page-link" href="league?action=listLeague&search=${search}&index=${selectedPage+1}">»</a><
                </c:otherwise>
            </c:choose>
        </div>

    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
