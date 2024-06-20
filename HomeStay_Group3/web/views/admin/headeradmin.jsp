<%-- 
    Document   : header.jsp
    Created on : May 15, 2024, 6:38:46 PM
    Author     : nguye
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
            crossorigin="anonymous"
            />
        <link rel="stylesheet" href="main.css" />
        <style>
            .headercustom{
                background-color: blue;
            }
        </style>
    </head>
    <body>
        <header class="p-3 headercustom  ">
            <div class="container">
                <div
                    class="d-flex flex-wrap align-items-center justify-content-end justify-content-lg-end"
                    >
                    <div style="margin-left:5px;" class="dropdown text-start">
                            <a
                                href="#"
                                class="d-block link-dark text-decoration-none dropdown-toggle show"
                                id="dropdownUser1"
                                data-bs-toggle="dropdown"
                                aria-expanded="true"
                                >
                                <img
                                    src="https://github.com/mdo.png"
                                    alt="mdo"
                                    width="32"
                                    height="32"
                                    class="rounded-circle"
                                    />
                            </a>
                            <ul
                                class="dropdown-menu text-small "
                                aria-labelledby="dropdownUser1"
                                style="
                                position: absolute;
                                inset: 0px 0px auto auto;
                                margin: 0px;
                                transform: translate3d(110px, 34px, 0px);
                                "
                                data-popper-placement="bottom-end"
                                >
                                <li><a class="dropdown-item" href="#">Settings</a></li>
                                <li><a class="dropdown-item" href="profile.html">Profile</a></li>
                                <li><a class="dropdown-item" href="forgot">Security</a></li>
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/auth?action=logout">Sign out</a></li>
                            </ul>
                        </div>
                </div>
            </div>
        </header>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"
        ></script>
    </body>
</html>


