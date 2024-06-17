<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
        
        <title>Document</title>
    </head>
    <body>
        <section class="navBarSection">
            <header class="navbarHeader flex">
                <div class="logoDiv">
                    <a class="navbarLogo flex" href="auth"
                       ><img
                            src="${pageContext.request.contextPath}/images/logo.jpg"
                            alt=""
                            class="logo"
                            />
                        <h1>Home Stay</h1></a
                    >
                </div>
                <div class="navBar">
                    <ul class="navbarLists flex">
                        <li class="navbarItem"><a class="nav-link" href="auth">Home</a></li>
                        <li class="navbarItem">
                            <a class="nav-link" href="homestay?action=post">Post Home Stay</a>
                        </li>
                        <li class="navbarItem">
                            <a class="nav-link" href="homestay?action=view-own-homestay"
                               >Manage Booking</a
                            >
                        </li>
                        <li class="navbarItem">
                            <a class="nav-link" href="/faqs">How it works</a>
                        </li>
                        <c:choose>
                            <c:when test="${USER != null}">
                                <li class="btn" style="display: flex; align-items: center; cursor: pointer" >
                                    <img alt="avatar" src="${pageContext.request.contextPath}/images/avatar.png" style="width: 32px; height: 32px; border-radius: 50%"  />&nbsp;
                                    <button type="button" id="dropdownMenuButton1" class="btn dropdown-toggle " data-bs-toggle="dropdown" aria-expanded="false" style="color: rgb(0, 0, 0)">${USER.firstName} ${USER.lastName}</button>
                                </li>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                    <a class="dropdown-item " href="profile?action=view">Profile</a>
                                    <a class="dropdown-item " href="auth?action=logout">Logout</a>
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
        </section>
    </body>
</html>
