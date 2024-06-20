<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
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
                .searchBox {
                    box-shadow: 0 0.0714285714rem 0.2142857143rem 0 rgba(0, 0, 0, 0.2);
                    margin: 0 auto 1.5rem;
                    background: #fff;
                    padding: 1.0714285714rem;
                    position: relative;
                    max-width: 70rem;
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
                        <div class="hero-image_quote-photo">
                            <img
                                width="179"
                                height="157"
                                src="https://sankid.vn/wp-content/uploads/2020/06/11-8.jpg"
                                class="lazy"
                                alt=""
                                />
                        </div>
                        <blockquote class="hero-image_quote-text">
                            Explore our exclusive Timeshare Vacation experience! Immerse
                            yourself in a realm of opulence and tranquility with our top-tier
                            timeshare selections. Whether you crave a seaside haven, a serene
                            mountain hideaway, or an exhilarating urban getaway, we offer the
                            ideal vacation retreat for you. Our goal is to curate
                            unforgettable holiday memories for you. Delve into our wide array
                            of timeshare choices, outstanding amenities, and unwavering
                            customer service. Rent a timeshare with us and transform each
                            vacation into an unforgettable journey.
                        </blockquote>
                        <div class="hero-image_quote-author-container">
                            <cite class="hero-image_quote-author">Phong &amp; Tien </cite
                            ><time class="hero-image_quote-date" datetime="2015"
                                   >VIP Members since 2019</time
                            >
                        </div>
                    </div>
                </div>
            </div>
            <!--<form>-->
            <div class="searchBox">
                <form action="auth" style="display: flex; justify-content: space-between" data-aos="fade-up" class="cardDiv grid aos-init aos-animate">
                
                    <div class="destinationInput">
                        <label for="city">
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
                                    ></circle>
                            </svg>
                            <div style="width: 4px"></div>
                            Search your timeshare:</label
                        >
                        <div class="input flex">
                            <input
                                type="text"
                                placeholder="Enter name here...."
                                name="search"
                                value="${search}"
                                />
                        </div>
                    </div>
                    <div class="dateInput">
                        <label for="date">
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
                                    fill="none"
                                    stroke-miterlimit="10"
                                    stroke-width="32"
                                    d="M416.07 272a160 160 0 1 0-160 160 160 160 0 0 0 160-160zM142.12 91.21A46.67 46.67 0 0 0 112 80l-2.79.08C83.66 81.62 64 104 64.07 131c0 13.21 4.66 19.37 10.88 27.23a4.55 4.55 0 0 0 3.24 1.77h.88a3.23 3.23 0 0 0 2.54-1.31L142.38 99a5.38 5.38 0 0 0 1.55-4 5.26 5.26 0 0 0-1.81-3.79zm227.76 0A46.67 46.67 0 0 1 400 80l2.79.08C428.34 81.62 448 104 447.93 131c0 13.21-4.66 19.37-10.88 27.23a4.55 4.55 0 0 1-3.24 1.76h-.88a3.23 3.23 0 0 1-2.54-1.31L369.62 99a5.38 5.38 0 0 1-1.55-4 5.26 5.26 0 0 1 1.81-3.79z"
                                    ></path>
                                <path
                                    fill="none"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="32"
                                    d="M256.07 160v112h-80m240 160-40-40m-280 40 40-40"
                                    ></path>
                            </svg>
                            <div style="width: 4px"></div>
                            Select your Date:</label
                        >
                        <div class="input flex">
                            <input
                                type="date"
                                placeholder="Enter name here...."
                                name="publicDate"
                                value=""
                                />
                        </div>
                    </div>
<!--                    <div class="Price">
                        <div class="label_total flex">
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
                                    fill="none"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="32"
                                    d="M403.29 32H280.36a14.46 14.46 0 0 0-10.2 4.2L24.4 281.9a28.85 28.85 0 0 0 0 40.7l117 117a28.86 28.86 0 0 0 40.71 0L427.8 194a14.46 14.46 0 0 0 4.2-10.2v-123A28.66 28.66 0 0 0 403.29 32z"
                                    ></path>
                                <path d="M352 144a32 32 0 1 1 32-32 32 32 0 0 1-32 32z"></path>
                                <path
                                    fill="none"
                                    stroke-linecap="round"
                                    stroke-linejoin="round"
                                    stroke-width="32"
                                    d="m230 480 262-262a13.81 13.81 0 0 0 4-10V80"
                                    ></path>
                            </svg>
                            <div style="width: 4px"></div>
                            <label for="price">Max price:&nbsp;</label>
                            <p class="total">$0</p>
                        </div>
                        <div class="input flex">
                            <input
                                type="range"
                                max="1000"
                                min="0"
                                name="maxPrice"
                                value="0"
                                />
                        </div>
                    </div>-->
                    <button class="buttonFilterAction">SEARCH</button>
                </div>
                <div
                    data-aos="fade-up"
                    class="homeFooterIcons flex aos-init aos-animate"
                    >
                    <div class="searchOptions">
                        <svg
                            stroke="currentColor"
                            fill="currentColor"
                            stroke-width="0"
                            viewBox="0 0 20 20"
                            aria-hidden="true"
                            class="icon"
                            height="1em"
                            width="1em"
                            xmlns="http://www.w3.org/2000/svg"
                            >
                            <path
                                fill-rule="evenodd"
                                d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z"
                                clip-rule="evenodd"
                                ></path></svg
                        ><span>MORE FILTER</span>
                    </div>
                    <div class="rightIcons">
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
                                d="M426.8 64H85.2C73.5 64 64 73.5 64 85.2v341.6c0 11.7 9.5 21.2 21.2 21.2H256V296h-45.9v-56H256v-41.4c0-49.6 34.4-76.6 78.7-76.6 21.2 0 44 1.6 49.3 2.3v51.8h-35.3c-24.1 0-28.7 11.4-28.7 28.2V240h57.4l-7.5 56H320v152h106.8c11.7 0 21.2-9.5 21.2-21.2V85.2c0-11.7-9.5-21.2-21.2-21.2z"
                                ></path></svg
                        ><svg
                            stroke="currentColor"
                            fill="currentColor"
                            stroke-width="0"
                            viewBox="0 0 448 512"
                            class="icon"
                            height="1em"
                            width="1em"
                            xmlns="http://www.w3.org/2000/svg"
                            >
                            <path
                                d="M224,202.66A53.34,53.34,0,1,0,277.36,256,53.38,53.38,0,0,0,224,202.66Zm124.71-41a54,54,0,0,0-30.41-30.41c-21-8.29-71-6.43-94.3-6.43s-73.25-1.93-94.31,6.43a54,54,0,0,0-30.41,30.41c-8.28,21-6.43,71.05-6.43,94.33S91,329.26,99.32,350.33a54,54,0,0,0,30.41,30.41c21,8.29,71,6.43,94.31,6.43s73.24,1.93,94.3-6.43a54,54,0,0,0,30.41-30.41c8.35-21,6.43-71.05,6.43-94.33S357.1,182.74,348.75,161.67ZM224,338a82,82,0,1,1,82-82A81.9,81.9,0,0,1,224,338Zm85.38-148.3a19.14,19.14,0,1,1,19.13-19.14A19.1,19.1,0,0,1,309.42,189.74ZM400,32H48A48,48,0,0,0,0,80V432a48,48,0,0,0,48,48H400a48,48,0,0,0,48-48V80A48,48,0,0,0,400,32ZM382.88,322c-1.29,25.63-7.14,48.34-25.85,67s-41.4,24.63-67,25.85c-26.41,1.49-105.59,1.49-132,0-25.63-1.29-48.26-7.15-67-25.85s-24.63-41.42-25.85-67c-1.49-26.42-1.49-105.61,0-132,1.29-25.63,7.07-48.34,25.85-67s41.47-24.56,67-25.78c26.41-1.49,105.59-1.49,132,0,25.63,1.29,48.33,7.15,67,25.85s24.63,41.42,25.85,67.05C384.37,216.44,384.37,295.56,382.88,322Z"
                                ></path></svg
                        ><svg
                            stroke="currentColor"
                            fill="currentColor"
                            stroke-width="0"
                            role="img"
                            viewBox="0 0 24 24"
                            class="icon"
                            height="1em"
                            width="1em"
                            xmlns="http://www.w3.org/2000/svg"
                            >
                            <path
                                d="M12.49 10.2722v-.4496h1.3467v6.3218h-.7704a.576.576 0 01-.5763-.5729l-.0006.0005a3.273 3.273 0 01-1.9372.6321c-1.8138 0-3.2844-1.4697-3.2844-3.2823 0-1.8125 1.4706-3.2822 3.2844-3.2822a3.273 3.273 0 011.9372.6321l.0006.0005zM6.9188 7.7896v.205c0 .3823-.051.6944-.2995 1.0605l-.03.0343c-.0542.0615-.1815.206-.2421.2843L2.024 14.8h4.8948v.7682a.5764.5764 0 01-.5767.5761H0v-.3622c0-.4436.1102-.6414.2495-.8476L4.8582 9.23H.1922V7.7896h6.7266zm8.5513 8.3548a.4805.4805 0 01-.4803-.4798v-7.875h1.4416v8.3548H15.47zM20.6934 9.6C22.52 9.6 24 11.0807 24 12.9044c0 1.8252-1.4801 3.306-3.3066 3.306-1.8264 0-3.3066-1.4808-3.3066-3.306 0-1.8237 1.4802-3.3044 3.3066-3.3044zm-10.1412 5.253c1.0675 0 1.9324-.8645 1.9324-1.9312 0-1.065-.865-1.9295-1.9324-1.9295s-1.9324.8644-1.9324 1.9295c0 1.0667.865 1.9312 1.9324 1.9312zm10.1412-.0033c1.0737 0 1.945-.8707 1.945-1.9453 0-1.073-.8713-1.9436-1.945-1.9436-1.0753 0-1.945.8706-1.945 1.9436 0 1.0746.8697 1.9453 1.945 1.9453z"
                                ></path>
                        </svg>
                    </div>
                    <div class="leftIcons">
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
                                d="M464 480H48c-26.51 0-48-21.49-48-48V80c0-26.51 21.49-48 48-48h416c26.51 0 48 21.49 48 48v352c0 26.51-21.49 48-48 48zM128 120c-22.091 0-40 17.909-40 40s17.909 40 40 40 40-17.909 40-40-17.909-40-40-40zm0 96c-22.091 0-40 17.909-40 40s17.909 40 40 40 40-17.909 40-40-17.909-40-40-40zm0 96c-22.091 0-40 17.909-40 40s17.909 40 40 40 40-17.909 40-40-17.909-40-40-40zm288-136v-32c0-6.627-5.373-12-12-12H204c-6.627 0-12 5.373-12 12v32c0 6.627 5.373 12 12 12h200c6.627 0 12-5.373 12-12zm0 96v-32c0-6.627-5.373-12-12-12H204c-6.627 0-12 5.373-12 12v32c0 6.627 5.373 12 12 12h200c6.627 0 12-5.373 12-12zm0 96v-32c0-6.627-5.373-12-12-12H204c-6.627 0-12 5.373-12 12v32c0 6.627 5.373 12 12 12h200c6.627 0 12-5.373 12-12z"
                                ></path></svg
                        ><svg
                            stroke="currentColor"
                            fill="none"
                            stroke-width="2"
                            viewBox="0 0 24 24"
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            class="icon"
                            height="1em"
                            width="1em"
                            xmlns="http://www.w3.org/2000/svg"
                            >
                            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                            <path
                                d="M4 4m0 1a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1z"
                                ></path>
                            <path
                                d="M4 14m0 1a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1z"
                                ></path>
                            <path
                                d="M14 14m0 1a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v4a1 1 0 0 1 -1 1h-4a1 1 0 0 1 -1 -1z"
                                ></path>
                            <path d="M14 7l6 0"></path>
                            <path d="M17 4l0 6"></path>
                        </svg>
                    </div>
                </form>
            </div>

            <!--</form>-->
        </section>

        <section class="main container section">
            <div class="secTitle">
                <h3 data-aos="fade-up" class="title aos-init aos-animate">
                    TOP HOMESTAY
                </h3>
            </div>
            <div class="secContent grid">
                <c:forEach items="${HOMESTAY}" var="homestay">
                    <div data-aos="fade-up" class="singleDestination aos-init aos-animate">
                        <div class="new-ribbon secondary">$500 / day</div>
                        <div class="imageDiv">
                            <img
                                src="data:image/png;base64,${homestay.image}"
                                alt="The Bay - Ha Long Homestay"
                                />
                        </div>
                        <div class="cardInfo">
                            <div class="cardBody-title">
                                <div>
                                    <h1 class="destTitle">${homestay.name}</h1>
                                    <span class="continent flex"
                                          ><svg stroke="currentColor"  fill="currentColor"   stroke-width="0"  viewBox="0 0 512 512"    class="icon"
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
                                                ></circle></svg
                                        ><span class="name">${homestay.address}</span></span
                                    ><span class="continent flex" style="margin-top: 3px"
                                           ><svg
                                            stroke="currentColor"
                                            fill="currentColor"
                                            stroke-width="0"
                                            viewBox="0 0 24 24"
                                            class="icon"
                                            height="1em"
                                            width="1em"
                                            xmlns="http://www.w3.org/2000/svg"
                                            style="font-size: 20px; margin-right: 3px"
                                            >
                                            <g id="Calendar_Date">
                                                <path
                                                    d="M18.435,4.955h-1.94v-1.41c0-0.26-0.23-0.51-0.5-0.5c-0.27,0.01-0.5,0.22-0.5,0.5v1.41h-7v-1.41
                                                    c0-0.26-0.23-0.51-0.5-0.5c-0.27,0.01-0.5,0.22-0.5,0.5v1.41h-1.93c-1.38,0-2.5,1.12-2.5,2.5v11c0,1.38,1.12,2.5,2.5,2.5h12.87
                                                    c1.38,0,2.5-1.12,2.5-2.5v-11C20.935,6.075,19.815,4.955,18.435,4.955z M19.935,18.455c0,0.83-0.67,1.5-1.5,1.5H5.565
                                                    c-0.83,0-1.5-0.67-1.5-1.5v-8.42h15.87V18.455z M19.935,9.035H4.065v-1.58c0-0.83,0.67-1.5,1.5-1.5h1.93v0.59
                                                    c0,0.26,0.23,0.51,0.5,0.5c0.27-0.01,0.5-0.22,0.5-0.5v-0.59h7v0.59c0,0.26,0.23,0.51,0.5,0.5c0.27-0.01,0.5-0.22,0.5-0.5v-0.59
                                                    h1.94c0.83,0,1.5,0.67,1.5,1.5V9.035z"
                                                    ></path>
                                                <path
                                                    d="M11.492,17.173v-3.46c0-0.059-0.064-0.095-0.114-0.064l-0.638,0.392
                                                    c-0.1,0.061-0.228-0.01-0.228-0.128v-0.651c0-0.105,0.055-0.203,0.146-0.257l0.764-0.457c0.047-0.028,0.1-0.043,0.154-0.043h0.626
                                                    c0.166,0,0.3,0.134,0.3,0.3v4.367c0,0.166-0.134,0.3-0.3,0.3h-0.409C11.626,17.473,11.492,17.339,11.492,17.173z"
                                                    ></path>
                                            </g></svg
                                        ><span class="name">2024/03/29 - 2024/05/29</span></span
                                    >
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
                            <div class="desc">
                                <p>
                                    ${homestay.description}
                                </p>
                            </div>
                            <a style="display: block" class="btn flex" href="homestay?action=homestay-detail&homestayId=${homestay.homestayId}">Details</a>
                        </div>
                    </div>
                </c:forEach>


        </section>
        <div class="pagination">
            <c:choose>
                <c:when test ="${selectedPage - 1 < 1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">«</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="auth?&search=${search}&index=${selectedPage-1}">«</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="i" begin="1" end="${endP}">
                <a class="page-link ${i == selectedPage ? "active" : "" }" href="auth?search=${search}&index=${i}">${i}</a> 
            </c:forEach>
            <c:choose>
                <c:when test ="${selectedPage >= endP}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#">»</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="auth?search=${search}&index=${selectedPage+1}">»</a></li>
                    </c:otherwise>
                </c:choose>
        </div>

        </div>
        <jsp:include page="footer.jsp" />
    </body>
</html>
