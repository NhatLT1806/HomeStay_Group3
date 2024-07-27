<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <title>Document</title>
        <style>
            .dropbtn {
                background-color: #3498DB;
                color: white;
                padding: 16px;
                font-size: 16px;
                border: none;
                cursor: pointer;
            }

            .dropbtn:hover, .dropbtn:focus {
                background-color: #2980B9;
            }

            .dropdown {
                position: relative;
                display: inline-block;
            }

            .dropdown-content {
                display: none;
                position: absolute;
                background-color: #f1f1f1;
                min-width: 160px;
                overflow: auto;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
                z-index: 1;
            }

            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            .dropdown a:hover {
                background-color: #ddd;
            }

            .show {
                display: block;
            }
        </style>
    </head>
    <body>
        <section class="navBarSection">
            <header class="navbarHeader flex">
                <div class="logoDiv">
                    <a class="navbarLogo flex" href="auth"
                       ><img
                            src="${pageContext.request.contextPath}/images/Logo.png" style="height: 100px; width: 100px; border-radius: unset"
                            alt=""
                            class="logo"
                            />
                        <h1>HomeHaven</h1></a
                    >
                </div>
                <div class="navBar">
                    <ul class="navbarLists flex">
                        <li class="navbarItem"><a class="nav-link" href="auth">Home</a></li>
                        <!-- Button trigger modal -->
                        <li class="navbarItem">
                            <c:if test="${USER != null && USER.roleId == 3}">
                                <a href="profile?action=send-order">Lượt tạo nhà: ${WALLET}</a>
                            </c:if>
                        </li>

                        <c:if test="${USER.roleId == 3}">

                            <li class="navbarItem">
                                <a class="nav-link" href="homestay?action=post">Tạo nhà</a>
                            </li>
                            <li class="navbarItem">
                                <a class="nav-link" href="homestay?action=view-own-homestay"
                                   >Quản lí nhà</a
                                >
                            </li>
                            <li class="navbarItem">
                                <a class="nav-link" href="book?action=view-room-booking"
                                   >Danh sách yêu cầu</a
                                >
                            </li>
                        </c:if>
                        <c:if test="${USER.roleId == 2}">
                            <li class="navbarItem">
                                <a class="nav-link" href="room?action=view-favourite-room"
                                   >Phòng yêu thích</a
                                >
                            </li>
                            <li class="navbarItem">
                                <a class="nav-link" href="book?action=view-booking-history"
                                   >Yêu cầu đang gửi</a
                                >
                            </li>

                        </c:if>

                        <a href="NotificationController?action=view"  class="btn btn-secondary position-relative">
                            Thông báo
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                ${NOTIFY}
                                <span class="visually-hidden">unread messages</span>
                            </span>
                        </a>
                        <c:choose>

                            <c:when test="${USER != null}">
                                <li  style="display: flex; align-items: center; cursor: pointer">

                                </li>
                                <div class="dropdown">
                                    <button onclick="myFunction()" class="dropbtn"> ${USER.firstName} ${USER.lastName}
                                    </button>
                                    <div id="myDropdown" class="dropdown-content">
                                        <a class="dropdown-item " href="profile?action=view">Profile</a>
                                        <a class="dropdown-item " href="auth?action=logout">Logout</a>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <li class="navbarItem loginItem" style="">
                                    <a class="nav-link" href="auth?action=login">Login</a>
                                </li>
                            </c:otherwise>
                        </c:choose>

                    </ul>
                    <div class="closeNavbar actionNavbarbutton">
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
                            d="M464 32H48C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48 48h416c26.5 0 48-21.5 48-48V80c0-26.5-21.5-48-48-48zm-83.6 290.5c4.8 4.8 4.8 12.6 0 17.4l-40.5 40.5c-4.8 4.8-12.6 4.8-17.4 0L256 313.3l-66.5 67.1c-4.8 4.8-12.6 4.8-17.4 0l-40.5-40.5c-4.8-4.8-4.8-12.6 0-17.4l67.1-66.5-67.1-66.5c-4.8-4.8-4.8-12.6 0-17.4l40.5-40.5c4.8-4.8 12.6-4.8 17.4 0l66.5 67.1 66.5-67.1c4.8-4.8 12.6-4.8 17.4 0l40.5 40.5c4.8 4.8 4.8 12.6 0 17.4L313.3 256l67.1 66.5z"
                            ></path>
                        </svg>
                    </div>
                    <div class="toggleNavbar actionNavbarbutton">
                        <svg
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
                        <path d="M5 5m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M12 5m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M19 5m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M5 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M12 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M19 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M5 19m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M12 19m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        <path d="M19 19m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0"></path>
                        </svg>
                    </div>
                </div>
            </header>



            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        </section>
    </body>

    <script>
                                        /* When the user clicks on the button, 
                                         toggle between hiding and showing the dropdown content */
                                        function myFunction() {
                                            document.getElementById("myDropdown").classList.toggle("show");
                                        }

// Close the dropdown if the user clicks outside of it
                                        window.onclick = function (event) {
                                            if (!event.target.matches('.dropbtn')) {
                                                var dropdowns = document.getElementsByClassName("dropdown-content");
                                                var i;
                                                for (i = 0; i < dropdowns.length; i++) {
                                                    var openDropdown = dropdowns[i];
                                                    if (openDropdown.classList.contains('show')) {
                                                        openDropdown.classList.remove('show');
                                                    }
                                                }
                                            }
                                        }
    </script>
</html>
